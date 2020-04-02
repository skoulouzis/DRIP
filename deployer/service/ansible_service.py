import base64
import logging
from time import sleep

import yaml

from service.semaphore_helper import SemaphoreHelper
yaml.Dumper.ignore_aliases = lambda *args : True

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True


class AnsibleService:

    def __init__(self, semaphore_base_url=None,semaphore_username=None,semaphore_password=None):
        self.semaphore_base_url = semaphore_base_url
        self.semaphore_username = semaphore_username
        self.semaphore_password = semaphore_password
        self.semaphore_helper = SemaphoreHelper(self.semaphore_base_url, self.semaphore_username, self.semaphore_password)



    def execute(self,nodes_pair):
        vms = nodes_pair[0]
        orchestartor = nodes_pair[1]
        desired_state = None
        interfaces = orchestartor.node_template.interfaces
        if 'current_state' in orchestartor.node_template.attributes:
            current_state = orchestartor.node_template.attributes['current_state']
        if 'desired_state' in orchestartor.node_template.attributes:
            desired_state = orchestartor.node_template.attributes['desired_state']

        if desired_state:
            project_id = self.semaphore_helper.create_project(orchestartor.name)
            inventory_contents = yaml.dump( self.build_yml_inventory(vms),default_flow_style=False)
            private_key = self.get_private_key(vms)
            key_id = self.semaphore_helper.create_ssh_key(orchestartor.name, project_id, private_key)
            inventory_id = self.semaphore_helper.create_inventory(orchestartor.name, project_id, key_id,inventory_contents)
            if 'RUNNING' == desired_state:
                standard = interfaces['Standard']
                create = standard['create']
                inputs = create['inputs']
                git_url = inputs['repository']
                playbook_name = inputs['playbook']

                repository_id = self.semaphore_helper.create_repository(orchestartor.name, project_id, key_id, git_url)
                template_id = self.semaphore_helper.create_template(project_id, key_id, inventory_id, repository_id, playbook_name)
                task_id = self.semaphore_helper.execute_task(project_id, template_id, playbook_name)
                for x in range(0, 3):
                    task = self.semaphore_helper.get_task(project_id,task_id)
                    print(task)
                    sleep(0.5)


        pass

    def build_yml_inventory(self, vms):
        # loader = DataLoader()
        # inventory = InventoryManager(loader=loader)
        # variable_manager = VariableManager()
        # with open(r'/home/alogo/Downloads/inventory.yaml') as file:
        #     # The FullLoader parameter handles the conversion from YAML
        #     # scalar values to Python the dictionary format
        #     data = yaml.load(file)

        inventory = {}
        all = {}
        vars = {'ansible_ssh_common_args':'-o StrictHostKeyChecking=no'}
        vars['ansible_ssh_user'] = vms[0].node_template.properties['user_name']
        children = {}
        for vm in vms:
            attributes = vm.node_template.attributes
            role = attributes['role']
            public_ip = attributes['public_ip']

            if role not in children:
                hosts = {}
            else:
                hosts = children[role]
            hosts[public_ip] = vars
            children[role] = hosts
            # inventory.add_group(role)
            # inventory.add_host(public_ip,group=role)
        all['children'] = children
        inventory['all'] = all
        return inventory

    def get_private_key(self, vms):
        private_key = vms[0].node_template.attributes['user_key_pair']['keys']['private_key']
        return base64.b64decode(private_key).decode('utf-8').replace(r'\n', '\n')

