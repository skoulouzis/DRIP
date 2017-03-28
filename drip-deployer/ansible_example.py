#!/usr/bin/env python

import json
from collections import namedtuple
from ansible.parsing.dataloader import DataLoader
from ansible.vars import VariableManager
from ansible.inventory import Inventory
from ansible.playbook.play import Play
from ansible.executor.task_queue_manager import TaskQueueManager
from ansible.plugins.callback import CallbackBase
import ansible.executor.task_queue_manager
import ansible.inventory
import ansible.parsing.dataloader
import ansible.playbook.play
import ansible.plugins.callback
import ansible.vars


class ResultCallback(CallbackBase):
    """A sample callback plugin used for performing an action as results come in

    If you want to collect all results into a single object for processing at
    the end of the execution, look into utilizing the ``json`` callback plugin
    or writing your own custom callback plugin
    """
    def v2_runner_on_ok(self, result, **kwargs):
        """Print a json representation of the result

        This method could store the result in an instance attribute for retrieval later
        """
        host = result._host
        print json.dumps({host.name: result._result})


class Options(object):

    def __init__(self, check=True):
        self.connection = "smart"
        self.module_path = None
        self.forks = None
        self.remote_user = None
        self.private_key_file = None
        self.ssh_common_args = None
        self.ssh_extra_args = None
        self.sftp_extra_args = None
        self.scp_extra_args = None
        self.become = None
        self.become_method = None
        self.become_user = None
        self.verbosity = None
        self.check = check
        super(Options, self).__init__()



options = Options(check=False)
 
# initialize needed objects
variable_manager = ansible.vars.VariableManager()

loader = DataLoader()

# Instantiate our ResultCallback for handling results as they come in
results_callback = ResultCallback()

# create inventory and pass to var manager
inventory = Inventory(loader=loader, variable_manager=variable_manager, host_list='172.17.0.4,172.17.0.5')
variable_manager.set_inventory(inventory)

# create play with tasks
play_source =  dict(
        name = "Ansible Play",
        hosts = 'all',
        gather_facts = 'no',
        tasks = [
            dict(action=dict(module='shell', args='ls'), register='shell_out')
            #,dict(action=dict(module='debug', args=dict(msg='{{shell_out.stdout}}')))
         ]
    )
play = Play().load(play_source, variable_manager=variable_manager, loader=loader)

# actually run it
tqm = None
try:
    tqm = TaskQueueManager(
        inventory=inventory,
        variable_manager=variable_manager,
        loader=loader,
        options=options,
        passwords=None,
        #stdout_callback='json', 
        stdout_callback=results_callback,  # Use our custom callback instead of the ``default`` callback plugin
         #stdout_callback='default', 
        )
    
    result = tqm.run(play)
finally:
    if tqm is not None:
        tqm.cleanup()