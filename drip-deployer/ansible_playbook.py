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
from drip_logging.drip_logging_handler import *
import multiprocessing
from ansible.executor.task_executor import TaskExecutor
from ansible.playbook import Playbook


logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True


retry=0


def install_prerequisites(vm,return_dict):
	try:
            logger.info("Installing ansible prerequisites on: "+vm.ip)
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
            
            logger.info("Ansible prerequisites installed on: " + (vm.ip))
	except Exception as e:
            global retry
            if retry < 10:
                logger.warning(vm.ip + " " + str(e)+". Retrying")
                retry+=1
                return install_prerequisites(vm,return_dict)      
            logger.error(vm.ip + " " + str(e))
            return_dict[vm.ip] = "ERROR:"+vm.ip+" "+str(e)
            return "ERROR:"+vm.ip+" "+str(e)
        ssh.close()
        return_dict[vm.ip] = "SUCCESS"
	return "SUCCESS"
    
    
    
def run_faied(failed_tasks,inventory,variable_manager,loader,options,passwords,results_callback,playbook_path):
    tasks = []
    hosts = []
    #tqm = TaskQueueManager(inventory=inventory, variable_manager=variable_manager, loader=loader, options=options, passwords=passwords)
    

    #yml_plays = {}
    #with open(playbook_path) as stream:
        #yml_plays = yaml.load(stream)
        
   
    #failed_yml = {}
    #retry_task = []
    #hosts = []
    #for failed_task in failed_tasks:
        #name =  failed_task._task.get_name()
        #host = failed_task._host.get_name()
        
        #for play in yml_plays:
            #for task in play['tasks']:
                #if name in task['name']:
                    #retry_task.append(task)
        #hosts.append(host)
    #failed_yml['hosts'] = hosts
    #failed_yml['tasks'] = retry_task
                                        
    #with open('/tmp/failed.yml', 'w') as outfile:
        #yaml.dump(failed_yml, outfile, default_flow_style=False)    
        
    #play = Play().load('/tmp/failed.yml', variable_manager=variable_manager, loader=loader)
    #res = tqm.run(play=play)
    

def execute_playbook(hosts, playbook_path,user,ssh_key_file,extra_vars,passwords):
    if not os.path.exists(playbook_path):
        logger.error('[ERROR] The playbook does not exist')
        return '[ERROR] The playbook does not exist'
    
    os.environ['ANSIBLE_HOST_KEY_CHECKING'] = 'false'
    ansible.constants.HOST_KEY_CHECKING = False

    os.environ['ANSIBLE_SSH_RETRIES'] = '20'
    ansible.constants.ANSIBLE_SSH_RETRIES = 20
    
    
    variable_manager = VariableManager()
    loader = DataLoader()

    inventory = Inventory(loader=loader, variable_manager=variable_manager,  host_list=hosts)


    Options = namedtuple('Options', ['listtags', 'listtasks', 'listhosts', 'syntax', 'connection','module_path', 'forks', 'remote_user', 'private_key_file', 'ssh_common_args', 'ssh_extra_args', 'sftp_extra_args', 'scp_extra_args', 'become', 'become_method', 'become_user', 'verbosity', 'check','host_key_checking','retries'])

    options = Options(listtags=False, listtasks=False, listhosts=False, syntax=False, connection='smart', module_path=None, forks=None, remote_user=user, private_key_file=ssh_key_file, ssh_common_args='', ssh_extra_args='', sftp_extra_args=None, scp_extra_args=None, become=True, become_method='sudo', become_user='root', verbosity=None, check=False , host_key_checking=False, retries=20)
    
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
    
    failed_tasks = []
    for res in ok:        
        #failed_tasks.append(res['result'])    
        resp = json.dumps({"host":res['ip'], "result":res['result']._result,"task":res['task']})
        #logger.info(resp)
        
        answer.append({"host":res['ip'], "result":res['result']._result,"task":res['task']})
        

    unreachable = results_callback.host_unreachable   
    for res in unreachable:
        #failed_tasks.append(res['task'])    
        resp = json.dumps({"host":res['ip'], "result":res['result']._result,"task":res['task']})
        logger.info(resp)
        answer.append({"host":res['ip'], "result":res['result']._result,"task":res['task']})

    host_failed = results_callback.host_failed
    for res in host_failed:
        resp = json.dumps({"host":res['ip'], "result":res['result']._result, "task":res['task']})
        #logger.info(resp)
        answer.append({"host":res['ip'], "result":res['result']._result,"task":res['task']})
        
    if failed_tasks:
        run_faied(failed_tasks,inventory,variable_manager,loader,options,passwords,results_callback,playbook_path)


    return json.dumps(answer)


def run(vm_list,playbook_path,rabbitmq_host,owner):
    
    #Create /playbook.retry    
    hosts=""
    ssh_key_file=""
    rabbit = DRIPLoggingHandler(host=rabbitmq_host, port=5672,user=owner)
    logger.addHandler(rabbit)    
    
    manager = multiprocessing.Manager()
    return_dict = manager.dict()
    jobs = []        
    
    
    for vm in vm_list:
        #ret = install_prerequisites(vm,return_dict)
        p = multiprocessing.Process(target=install_prerequisites, args=(vm,return_dict,))
        jobs.append(p)
        p.start()
        hosts+=vm.ip+","
        ssh_key_file = vm.key
        user = vm.user
        
    for proc in jobs:
        proc.join()
    if "ERROR" in return_dict.values(): return "ERROR"
    
    extra_vars = {}
    passwords = {}
    logger.info("Executing playbook: " + (playbook_path))
    return execute_playbook(hosts,playbook_path,user,ssh_key_file,extra_vars,passwords)