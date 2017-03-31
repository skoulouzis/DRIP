#!/usr/bin/env python

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
import logging
import yaml
import sys
from results_collector import ResultsCollector



hosts='172.17.0.2,'
playbook_path = 'playbook.yml'
if not os.path.exists(playbook_path):
    print '[ERROR] The playbook does not exist'
    sys.exit()
    
user='vm_user'
ssh_key_file='id_ras'
extra_vars = {} #{'resultslocation':'/tmp/res','ansible_sudo_pass':'123'}
    

variable_manager = VariableManager()
loader = DataLoader()

inventory = Inventory(loader=loader, variable_manager=variable_manager,  host_list=hosts)


Options = namedtuple('Options', ['listtags', 'listtasks', 'listhosts', 'syntax', 'connection','module_path', 'forks', 'remote_user', 'private_key_file', 'ssh_common_args', 'ssh_extra_args', 'sftp_extra_args', 'scp_extra_args', 'become', 'become_method', 'become_user', 'verbosity', 'check'])

options = Options(listtags=False, listtasks=False, listhosts=False, syntax=False, connection='smart', module_path=None, forks=None, remote_user=user, private_key_file=ssh_key_file, ssh_common_args=None, ssh_extra_args=None, sftp_extra_args=None, scp_extra_args=None, become=True, become_method='sudo', become_user='root', verbosity=None, check=False)



variable_manager.extra_vars = extra_vars
passwords = {}

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

print json.dumps(answer,indent=4)
 
  