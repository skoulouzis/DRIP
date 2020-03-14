import base64
import json
import logging
import os
import tempfile
from collections import namedtuple
from stat import S_IREAD
from subprocess import Popen, PIPE

import ansible
import requests
from ansible.executor.playbook_executor import PlaybookExecutor
from ansible.parsing.dataloader import DataLoader
from ansible.vars.manager import VariableManager

import re

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True


def write_inventory_file(tmp_path, vms):
    workers = []
    k8_master = None
    ansible_ssh_private_key_file_path = None
    ansible_ssh_user = None

    for vm_name in vms:
        attributes = vms[vm_name]['attributes']
        role = attributes['role']
        if 'public_ip' not in attributes:
            raise ValueError('VM: ' + vm_name + ' has no public_ip attribute')

        if role == 'master':
            k8_master = attributes['public_ip']
        else:
            workers.append(attributes['public_ip'])
        if ansible_ssh_private_key_file_path is None:
            ansible_ssh_private_key_encoded = attributes['user_key_pair']['keys']['private_key']
            ansible_ssh_private_key = base64.b64decode(ansible_ssh_private_key_encoded).decode('utf-8')
            ansible_ssh_private_key_file_path = tmp_path + "/id_rsa"

            with open(ansible_ssh_private_key_file_path, "w") as ansible_ssh_private_key_file:
                print(ansible_ssh_private_key, file=ansible_ssh_private_key_file)
            os.chmod(ansible_ssh_private_key_file_path, S_IREAD)
        if ansible_ssh_user is None:
            ansible_ssh_user = vms[vm_name]['properties']['user_name']

    k8s_hosts_path = tmp_path + "/k8s_hosts"

    with open(k8s_hosts_path, "w") as k8s_hosts_file:
        print('[k8-master]', file=k8s_hosts_file)
        print(k8_master, file=k8s_hosts_file)
        print('\n', file=k8s_hosts_file)
        print('[worker]', file=k8s_hosts_file)
        for worker in workers:
            print(worker, file=k8s_hosts_file)
        print('\n', file=k8s_hosts_file)
        print('[cluster:children]', file=k8s_hosts_file)
        print('k8-master', file=k8s_hosts_file)
        print('worker', file=k8s_hosts_file)
        print('\n', file=k8s_hosts_file)
        print('[cluster:vars]', file=k8s_hosts_file)
        print('ansible_ssh_private_key_file=' + ansible_ssh_private_key_file_path, file=k8s_hosts_file)
        print('ansible_ssh_common_args=\'-o StrictHostKeyChecking=no\'', file=k8s_hosts_file)
        print('ansible_ssh_user=' + ansible_ssh_user, file=k8s_hosts_file)
    logger.info("Returning inventory file at: " + str(k8s_hosts_path))
    return k8s_hosts_path


def write_playbooks(tmp_path, interface):
    playbook_paths = []
    interface_stage_list = ['install', 'create']
    # for interface_stage in interface:
    for interface_stage in interface_stage_list:
        playbook_url = interface[interface_stage]['inputs']['playbook']
        r = requests.get(playbook_url)
        playbook_path = tmp_path + "/" + interface_stage + '.yaml'
        with open(playbook_path, 'wb') as f:
            f.write(r.content)
        playbook_paths.append(playbook_path)
    return playbook_paths


def write_playbooks_from_tosca_interface(interfaces, tmp_path):
    playbook_paths = []
    for interface_name in interfaces:
        playbook_paths = playbook_paths + write_playbooks(tmp_path, interfaces[interface_name])
    logger.info("Returning playbook paths file at: " + str(playbook_paths))
    return playbook_paths


def run(inventory_path, playbook_path):
    logger.info("Executing playbook: " + str(playbook_path))
    p = Popen(["ansible-playbook", "-i", inventory_path, playbook_path,'--ssh-common-args=\'-o '
                                                                       'StrictHostKeyChecking=no\''], stdin=PIPE, stdout=PIPE, stderr=PIPE)
    output, err = p.communicate()
    # print(output.decode('utf-8'))
    # print(err.decode('utf-8'))
    logger.info("Playbook output: " + str(output.decode('utf-8')))
    logger.info("Playbook err: " + str(err.decode('utf-8')))
    rc = p.returncode
    return output, err


def parse_dashboard_tokens(out):
    token = None
    if 'admin-user-token' in out:
        m = re.search('\"token:      (.+?)\"', out)
        if m:
            token = m.group(1).strip()
    return token


def parse_api_tokens(out):
    api_key = None
    join_token = None
    discovery_token_ca_cert_hash = None
    if 'Join command is kubeadm join' in out:
        api_key = re.search("^msg.*", out)
        join_token = re.search("^\"stdout\": \"$", out)

        m = re.search('Join command is kubeadm join(.+?)\"', out)
        if m:
            found = m.group(1)
        m = re.search('--token (.+?)     --discovery-token-ca-cert-hash', found)
        if m:
            join_token = m.group(1)

        m = re.search('--discovery-token-ca-cert-hash (.+?) "}', out)
        if m:
            discovery_token_ca_cert_hash = m.group(1)

        m = re.search('--token (.+?)     --discovery-token-ca-cert-hash', found)
        if m:
            join_token = m.group(1)

        m = re.search('"stdout": "      (.+?)",', out)
        if m:
            api_key = m.group(1)
    return api_key, join_token, discovery_token_ca_cert_hash


def execute_playbook(hosts, playbook_path, user, ssh_key_file, extra_vars, passwords):
    if not os.path.exists(playbook_path):
        logger.error('The playbook does not exist')
        return '[ERROR] The playbook does not exist'

    os.environ['ANSIBLE_HOST_KEY_CHECKING'] = 'false'
    ansible.constants.HOST_KEY_CHECKING = False

    os.environ['ANSIBLE_SSH_RETRIES'] = 'retry_count'
    ansible.constants.ANSIBLE_SSH_RETRIES = 3

    variable_manager = VariableManager()
    loader = DataLoader()

    # inventory = Inventory(loader=loader, variable_manager=variable_manager, host_list=hosts)

    Options = namedtuple('Options',
                         ['listtags', 'listtasks', 'listhosts', 'syntax', 'connection', 'module_path', 'forks',
                          'remote_user', 'private_key_file', 'ssh_common_args', 'ssh_extra_args', 'sftp_extra_args',
                          'scp_extra_args', 'become', 'become_method', 'become_user', 'verbosity', 'check',
                          'host_key_checking', 'retries'])

    options = Options(listtags=False, listtasks=False, listhosts=False, syntax=False, connection='smart',
                      module_path=None, forks=None, remote_user=user, private_key_file=ssh_key_file, ssh_common_args='',
                      ssh_extra_args='', sftp_extra_args=None, scp_extra_args=None, become=True, become_method='sudo',
                      become_user='root', verbosity=None, check=False, host_key_checking=False, retries=retry_count)

    variable_manager.extra_vars = extra_vars

    # pbex = PlaybookExecutor(playbooks=[playbook_path],
    #                         inventory=inventory,
    #                         variable_manager=variable_manager,
    #                         loader=loader,
    #                         options=options,
    #                         passwords=passwords,
    #                         )
