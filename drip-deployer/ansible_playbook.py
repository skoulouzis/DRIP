 #! /usr/bin/env python

 # Copyright 2017 S. Koulouzis
 #
 # Licensed under the Apache License, Version 2.0 (the "License");
 # you may not use this file except in compliance with the License.
 # You may obtain a copy of the License at
 #
 #      http://www.apache.org/licenses/LICENSE-2.0
 #
 # Unless required by applicable law or agreed to in writing, software
 # distributed under the License is distributed on an "AS IS" BASIS,
 # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 # See the License for the specific language governing permissions and
 # limitations under the License.
 

__author__ = 'S. Koulouzis'

import json
from collections import namedtuple
from ansible.parsing.dataloader import DataLoader
from ansible.vars import VariableManager
from ansible.inventory import Inventory
from ansible.playbook.play import Play
from ansible.executor.task_queue_manager import TaskQueueManager
import ansible.executor.task_queue_manager
import ansible.inventory
import ansible.parsing.dataloader
import ansible.playbook.play
import ansible.plugins.callback
import ansible.vars
from ansible.executor.playbook_executor import PlaybookExecutor
from ansible.plugins import callback_loader
import os
import paramiko
import logging
import yaml
import sys
from results_collector import ResultsCollector


def install_prerequisites(vm):
	try:
		print "Installing ansible prerequisites in: %s" % (vm.ip)
		ssh = paramiko.SSHClient()
		ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
		sftp = ssh.open_sftp()
		file_path = os.path.dirname(os.path.abspath(__file__))
		sftp.chdir('/tmp/')
		install_script = file_path + "/" + "ansible_setup.sh"
		sftp.put(install_script, "ansible_setup.sh")
		
                stdin, stdout, stderr = ssh.exec_command("sudo hostname ip-%s" % (vm.ip.replace('.','-')))
		sshout = stdout.read()
		
                stdin, stdout, stderr = ssh.exec_command("sudo sh /tmp/ansible_setup.sh")
		stdout.read()
                
                parentDir = os.path.dirname(os.path.abspath(vm.key))
                os.chmod(parentDir, 0o700)
                os.chmod(vm.key, 0o600)
		print "Ansible prerequisites installed in: %s " % (vm.ip)
	except Exception as e:
		print '%s: %s' % (vm.ip, e)
		return "ERROR:"+vm.ip+" "+str(e)
	ssh.close()
	return "SUCCESS"
    
    
def execute_playbook(hosts, playbook_path,user,ssh_key_file,extra_vars,passwords):
    if not os.path.exists(playbook_path):
        print '[ERROR] The playbook does not exist'
        return '[ERROR] The playbook does not exist'
    
    os.environ['ANSIBLE_HOST_KEY_CHECKING'] = 'false'
    ansible.constants.HOST_KEY_CHECKING = False
    variable_manager = VariableManager()
    loader = DataLoader()

    inventory = Inventory(loader=loader, variable_manager=variable_manager,  host_list=hosts)


    Options = namedtuple('Options', ['listtags', 'listtasks', 'listhosts', 'syntax', 'connection','module_path', 'forks', 'remote_user', 'private_key_file', 'ssh_common_args', 'ssh_extra_args', 'sftp_extra_args', 'scp_extra_args', 'become', 'become_method', 'become_user', 'verbosity', 'check','host_key_checking'])

    options = Options(listtags=False, listtasks=False, listhosts=False, syntax=False, connection='smart', module_path=None, forks=None, remote_user=user, private_key_file=ssh_key_file, ssh_common_args=None, ssh_extra_args=None, sftp_extra_args=None, scp_extra_args=None, become=True, become_method='sudo', become_user='root', verbosity=None, check=False , host_key_checking=False)
    
    variable_manager.extra_vars = extra_vars
    
    pbex = PlaybookExecutor(playbooks=[playbook_path], 
                        inventory=inventory, 
                        variable_manager=variable_manager, 
                        loader=loader, 
                        options=options, 
                        passwords=passwords,
                        )
    results_callback = ResultsCollector()
    pbex._tqm._stdout_callback = results_callback
    results = pbex.run()
    
    ok = results_callback.host_ok
    answer = []
    for res in ok:
        resp = json.dumps({"host":res['ip'], "result":res['result']._result})
        answer.append({"host":res['ip'], "result":res['result']._result})
        

    unreachable = results_callback.host_unreachable
    for res in unreachable:
        resp = json.dumps({"host":res['ip'], "result":res['result']._result})
        answer.append({"host":res['ip'], "result":res['result']._result})
        

    host_failed = results_callback.host_failed
    for res in host_failed:
        resp = json.dumps({"host":res['ip'], "result":res['result']._result})
        answer.append({"host":res['ip'], "result":res['result']._result})

    return json.dumps(answer)

def run(vm_list,playbook_path):
    hosts=""
    ssh_key_file=""
    for vm in vm_list:
            ret = install_prerequisites(vm)
            hosts+=vm.ip+","
            ssh_key_file = vm.key
            user = vm.user
    if "ERROR" in ret: return ret
    
    extra_vars = {}
    passwords = {}
    print "Executing playbook: %s " % (playbook_path)
    return execute_playbook(hosts,playbook_path,user,ssh_key_file,extra_vars,passwords)