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
retry_count = 20
tasks_done = {}
#cwd = os.getcwd()

falied_playbook_path='/tmp/falied_playbook.yml'

def install_prerequisites(vm,return_dict):
	try:
            logger.info("Installing ansible prerequisites on: "+vm.ip)
            ssh = paramiko.SSHClient()
            ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
            ssh.connect(vm.ip, username=vm.user, key_filename=vm.key,timeout=5)
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
        retry = 0
	return "SUCCESS"
    
    
    
def create_faied_playbooks(failed_tasks,playbook_path):
    tasks = []
    hosts = []
    
    plays = []
    while not plays:
        logger.info("Loading: \'"+playbook_path+ "\'")        
        with open(playbook_path) as stream:
            plays = yaml.load(stream)
    
    
    
    logger.info("Number of plays loaded : \'"+str(len(plays))+ "\'")        
    logger.info("Number of failed tasks : \'"+str(len(failed_tasks))+ "\'")        
    failed_playsbooks = []
    failed = failed_tasks[0]
    
    host = failed['ip']
    failed_task = failed['task']
    failed_plays = []
    failed_play = {}
    retry_task = []
    hosts = ""
    
    if isinstance(failed_task, ansible.parsing.yaml.objects.AnsibleUnicode) or isinstance(failed_task, unicode) or isinstance(failed_task,str):
        task_name = str(failed_task)
    else:
        task_name =  str(failed_task._task.get_name())     
    
    hosts +=host+","
    
    if task_name == 'setup':
        found_first_failed_task = True
    else:
        found_first_failed_task = False
    
        
    for play in plays:
        for task in play['tasks']:
            if found_first_failed_task:
                retry_task.append(task)
            else:
                if task_name in tasks:
                    host_done = tasks_done[task_name]
                    if host_done == host or host_done == 'all':
                        logger.info("Task: \'"+task_name+ "\'. on host: "+ host+ " already done. Skipping" )
                        continue
                if 'name' in task and task['name'] == task_name:
                    retry_task.append(task)
                    logger.info("First faield task: \'"+task_name+ "\'. Host: "+ host)
                    found_first_failed_task = True
                elif task_name in task:
                    retry_task.append(task)
                    logger.info("First faield task: \'"+task_name+ "\'. Host: "+ host)
                    found_first_failed_task = True
                    
    
    failed_play['hosts'] = hosts
    failed_play['tasks'] = retry_task
    failed_plays.append(failed_play)
    failed_playsbooks.append(failed_plays)            
    
    logger.info("Number retruned playbooks : \'"+str(len(failed_playsbooks))+ "\'")        
    return failed_playsbooks
        

def execute_playbook(hosts, playbook_path,user,ssh_key_file,extra_vars,passwords):
    if not os.path.exists(playbook_path):
        logger.error('The playbook does not exist')
        return '[ERROR] The playbook does not exist'
    
    os.environ['ANSIBLE_HOST_KEY_CHECKING'] = 'false'
    ansible.constants.HOST_KEY_CHECKING = False

    os.environ['ANSIBLE_SSH_RETRIES'] = 'retry_count'
    ansible.constants.ANSIBLE_SSH_RETRIES = retry_count
    
    
    variable_manager = VariableManager()
    loader = DataLoader()

    inventory = Inventory(loader=loader, variable_manager=variable_manager,  host_list=hosts)


    Options = namedtuple('Options', ['listtags', 'listtasks', 'listhosts', 'syntax', 'connection','module_path', 'forks', 'remote_user', 'private_key_file', 'ssh_common_args', 'ssh_extra_args', 'sftp_extra_args', 'scp_extra_args', 'become', 'become_method', 'become_user', 'verbosity', 'check','host_key_checking','retries'])

    options = Options(listtags=False, listtasks=False, listhosts=False, syntax=False, connection='smart', module_path=None, forks=None, remote_user=user, private_key_file=ssh_key_file, ssh_common_args='', ssh_extra_args='', sftp_extra_args=None, scp_extra_args=None, become=True, become_method='sudo', become_user='root', verbosity=None, check=False , host_key_checking=False, retries=retry_count)
    
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
    
    answer = []
    failed_tasks = []
    ok = results_callback.host_ok
    for res in ok:         
        #failed_tasks.append(res)    
        resp = json.dumps({"host":res['ip'], "result":res['result']._result,"task":res['task']})
        logger.info("Task: "+res['task'] + ". Host: "+ res['ip'] +". State: ok")
        global tasks_done
        tasks_done[res['task']]= res['ip']
        answer.append({"host":res['ip'], "result":res['result']._result,"task":res['task']})
        

    unreachable = results_callback.host_unreachable   
    for res in unreachable:
        failed_tasks.append(res)
        resp = json.dumps({"host":res['ip'], "result":res['result']._result,"task":res['task']})
        logger.warning("Task: "+res['task'] + ". Host: "+ res['ip'] +". State: unreachable")
        answer.append({"host":res['ip'], "result":res['result']._result,"task":res['task']})

    host_failed = results_callback.host_failed
    for res in host_failed:
        #failed_tasks.append(res['result']) 
        resp = json.dumps({"host":res['ip'], "result":res['result']._result, "task":res['task']})
        logger.error("Task: "+res['task'] + ". Host: "+ res['ip'] +". State: host_failed")
        logger.error(resp)
        answer.append({"host":res['ip'], "result":res['result']._result,"task":res['task']})
        
    return answer,failed_tasks



def run(vm_list,playbook_path,rabbitmq_host,owner):
    hosts=""
    ssh_key_file=""
    rabbit = DRIPLoggingHandler(host=rabbitmq_host, port=5672,user=owner)
    logger.addHandler(rabbit)
    logger.info("DRIPLogging host: \'"+str(rabbitmq_host)+ "\'"+" logging message owner: \'"+owner+"\'")        

    
    manager = multiprocessing.Manager()
    return_dict = manager.dict()
    jobs = []   
    
    
    if os.path.exists(falied_playbook_path):
        os.remove(falied_playbook_path)
    
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

    answer,failed_tasks = execute_playbook(hosts,playbook_path,user,ssh_key_file,extra_vars,passwords)
    failed_playsbooks = []
    
    if failed_tasks:
        failed = failed_tasks[0]
        failed_task = failed['task']
        if isinstance(failed_task, ansible.parsing.yaml.objects.AnsibleUnicode) or isinstance(failed_task, unicode) or isinstance(failed_task,str):
            task_name = str(failed_task)
        else:
            task_name =  str(failed_task._task.get_name())

        retry_setup = 0        
        while task_name and task_name == 'setup' and retry_setup < retry_count :
            retry_setup+=1
            answer,failed_tasks = execute_playbook(hosts,playbook_path,user,ssh_key_file,extra_vars,passwords)
            if failed_tasks:
                failed = failed_tasks[0]
                failed_task = failed['task']
            
                if isinstance(failed_task, ansible.parsing.yaml.objects.AnsibleUnicode) or isinstance(failed_task, unicode) or isinstance(failed_task,str):
                    task_name = str(failed_task)
                else:
                    task_name =  str(failed_task._task.get_name())
            else:
                task_name = None
        
        while not failed_playsbooks:
            failed_playsbooks = create_faied_playbooks(failed_tasks,playbook_path)
        
        
    
    for failed_playbook in failed_playsbooks:
        hosts = failed_playbook[0]['hosts']
        logger.info("Writing new playbook at : \'"+falied_playbook_path+ "\'")  
        with open(falied_playbook_path, 'w') as outfile:
            yaml.dump(failed_playbook, outfile)
            
        retry_failed_tasks = 0
        failed_tasks = None
        done = False
        while not done:
            logger.info("Executing playbook : " + (falied_playbook_path) +" in host: "+hosts+" Retries: "+str(retry_failed_tasks))
            answer,failed_tasks = execute_playbook(hosts,falied_playbook_path,user,ssh_key_file,extra_vars,passwords)
            retry_failed_tasks+=1
            if retry_failed_tasks > retry_count or not failed_tasks:
                retry_failed_tasks = 0
                done = True
                break
            
        if os.path.exists(falied_playbook_path):
            os.remove(falied_playbook_path)
        
    if os.path.exists(falied_playbook_path):
        os.remove(falied_playbook_path)
    return json.dumps(answer)