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



    def execute(self,nodes_pair,operation):
        vms = nodes_pair[0]
        orchestartor = nodes_pair[1]
        interfaces = orchestartor.node_template.interfaces
        for interface in interfaces:
            if interface == 'Standard':
                standard = interfaces['Standard']

        git_url = 'https://github.com/skoulouzis/playbooks.git'
        private_key = '-----BEGIN RSA PRIVATE KEY-----6qFrczm3VYELw0Flw06Cf2Bza8rAVFnFqWpZJHLh7LFMt/U5ocn4df45NrE4UXo+hGoK7xWb/A' \
              'zudkwDkSexIAUHx/yPsHXK0gIxGtpsAXzV+7Y+5bI4gsN+WAJgOASFi6YHJU1YuAkLkPk5Gqb5UGZn7DoS9cGFQKvLCxBQIDAQABAoIBA' \
              'GXPM7ugfC-----END RSA PRIVATE KEY-----'
        path = self.get_path('inventory.yaml')
        with open(path, 'r') as stream:
            data = yaml.load(stream)

        inventory_contents = yaml.dump(data)
        playbook_name = 'k8s_install.yml'
        semaphore_helper = SemaphoreHelper(self.semaphore_base_url,self.semaphore_username,self.semaphore_password)

        project_id = semaphore_helper.create_project(orchestartor.name)
        key_id = semaphore_helper.create_ssh_key(orchestartor.name,project_id,private_key)
        inventory_id = semaphore_helper.create_inventory(orchestartor.name, project_id, key_id, inventory_contents)
        repository_id = semaphore_helper.create_repository(orchestartor.name,project_id,key_id,git_url)

        template_id = semaphore_helper.create_template(project_id,key_id,inventory_id,repository_id,playbook_name)
        task_id = semaphore_helper.execute_task(project_id, template_id, playbook_name)
        pass

    def get_path(self,file_name):
        tosca_path = "../../../ansible_playbooks"
        input_tosca_file_path = tosca_path + '/' + file_name
        if not os.path.exists(input_tosca_file_path):
            tosca_path = "../../ansible_playbooks"
            input_tosca_file_path = tosca_path + '/' + file_name
        return input_tosca_file_path