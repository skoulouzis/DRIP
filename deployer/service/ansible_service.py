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

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True


def write_ansible_files(vms, interfaces, tmp_path):
    workers = []
    k8_master = None
    ansible_ssh_private_key_file_path = None
    ansible_ssh_user = None

    for vm_name in vms:
        attributes = vms[vm_name]['attributes']
        role = attributes['role']
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

    image_url = interfaces['Kubernetes']['install']['inputs']['playbook']

    r = requests.get(image_url)
    with open(tmp_path + "/install.yml", 'wb') as f:
        f.write(r.content)

    image_url = interfaces['Kubernetes']['create']['inputs']['playbook']
    r = requests.get(image_url)
    with open(tmp_path + "/create.yml", 'wb') as f:
        f.write(r.content)
    return tmp_path


def run(interfaces, vms):
    tmp_path = tempfile.mkdtemp()
    write_ansible_files(vms, interfaces, tmp_path)

    p = Popen(["ansible-playbook", "-i", tmp_path + "/k8s_hosts", tmp_path + "/install.yml"], stdin=PIPE, stdout=PIPE,
              stderr=PIPE)
    output, err = p.communicate()
    print(output.decode('utf-8'))
    print(err.decode('utf-8'))
    rc = p.returncode

    p = Popen(["ansible-playbook", "-i", tmp_path + "/k8s_hosts", tmp_path + "/create.yml"], stdin=PIPE, stdout=PIPE,
              stderr=PIPE)
    output, err = p.communicate()
    out = output.decode('utf-8')
    err = err.decode('utf-8')
    print(out)
    print(err)
    rc = p.returncode
    api_key = out
    return api_key


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
