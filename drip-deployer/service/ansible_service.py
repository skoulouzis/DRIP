import base64
import os
import tempfile
import shutil
from stat import S_IREAD
import requests

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
            ansible_ssh_private_key = base64.b64decode(ansible_ssh_private_key_encoded)
            ansible_ssh_private_key_file_path = tmp_path + "/id_rsa"

            with open(ansible_ssh_private_key_file_path, "w") as ansible_ssh_private_key_file:
                print(ansible_ssh_private_key, file=ansible_ssh_private_key_file)
            os.chmod(ansible_ssh_private_key_file_path, S_IREAD)
        if ansible_ssh_user == None:
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
        print('[cluster: children]', file=k8s_hosts_file)
        print('k8 - master', file=k8s_hosts_file)
        print('worker', file=k8s_hosts_file)
        print('\n', file=k8s_hosts_file)
        print('[cluster:vars]', file=k8s_hosts_file)
        print('ansible_ssh_private_key_file=' + ansible_ssh_private_key_file_path, file=k8s_hosts_file)
        print('ansible_ssh_common_args=\'-o StrictHostKeyChecking=no\'', file=k8s_hosts_file)
        print('ansible_ssh_user=' + ansible_ssh_user, file=k8s_hosts_file)

    image_url = interfaces['Standard']['create']['inputs']['playbook']
    r = requests.get(image_url)
    with open(tmp_path+"/playbook.yml", 'wb') as f:
        f.write(r.content)
    return tmp_path


def run(interfaces, vms):
    tmp_path = tempfile.mkdtemp()
    write_ansible_files(vms, interfaces, tmp_path)
    return tmp_path
