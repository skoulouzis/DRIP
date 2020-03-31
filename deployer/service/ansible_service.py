import logging
import os

import yaml

from service.semaphore_helper import SemaphoreHelper

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
        if 'current_state' in orchestartor.attributes:
            current_state = orchestartor.attributes['current_state']
        if 'desired_state' in orchestartor.attributes:
            desired_state = orchestartor.attributes['desired_state']

        if desired_state:
            project_id = self.semaphore_helper.create_project(orchestartor.name)
            inventory_contents = self.build_yml_inventory(vms)
            private_key = self.get_private_key(vms)
            key_id = self.semaphore_helper.create_ssh_key(orchestartor.name, project_id, private_key)
            inventory_id = self.semaphore_helper.create_inventory(orchestartor.name, project_id, key_id,inventory_contents)
            if 'RUNNING' == desired_state:
                standard = interfaces['Standard']
                create = standard['create']
                inputs = create['inputs']
                git_url = inputs['repository']
                playbook_name = inputs['playbook']

                # repository_id = self.semaphore_helper.create_repository(orchestartor.name, project_id, key_id, git_url)
                # template_id = self.semaphore_helper.create_template(project_id, key_id, inventory_id, repository_id, playbook_name)
                # task_id = self.semaphore_helper.execute_task(project_id, template_id, playbook_name)

        pass

    def get_path(self,file_name):
        tosca_path = "../../../ansible_playbooks"
        input_tosca_file_path = tosca_path + '/' + file_name
        if not os.path.exists(input_tosca_file_path):
            tosca_path = "../../ansible_playbooks"
            input_tosca_file_path = tosca_path + '/' + file_name
        return input_tosca_file_path
