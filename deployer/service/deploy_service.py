import json

from service import tosca_helper, ansible_service
from service.ansible_service import AnsibleService
import logging

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True

class DeployService:

    def __init__(self, polemarch_base_url=None,polemarch_username=None,polemarch_password=None,
                 semaphore_base_url=None,semaphore_username=None,semaphore_password=None, vms=None):
        # self.polemarch_base_url = polemarch_base_url
        # self.polemarch_username=polemarch_username
        # self.polemarch_password = polemarch_password
        self.semaphore_base_url = semaphore_base_url
        self.semaphore_username = semaphore_username
        self.semaphore_password = semaphore_password
        self.vms = vms

    def deploy(self,nodes_pair):
        target = nodes_pair[0]
        source = nodes_pair[1]
        logger.info('target: ' + str(target) + ' source: ' + str(source))
        interface_types = tosca_helper.get_interface_types(source)
        if interface_types:
            ansible_service = AnsibleService(self.semaphore_base_url, self.semaphore_username, self.semaphore_password)
            env_vars = self.get_env_vars(nodes_pair)
            if 'Standard' in interface_types:
                task_outputs =  ansible_service.execute(nodes_pair, 'Standard', self.vms, env_vars=env_vars)
                self.set_attributes(task_outputs,nodes_pair)
            if 'Kubernetes' in interface_types:
                task_outputs = ansible_service.execute(nodes_pair, 'Kubernetes', self.vms, env_vars=env_vars)
                self.set_attributes(task_outputs,nodes_pair)

        return None

    def get_env_vars(self, nodes_pair):
        target = nodes_pair[0]
        source = nodes_pair[1]
        if source.node_template.type == 'tosca.nodes.QC.Container.Application.Docker':
            env_vars = {'DOCKER_IMAGE':source.node_template.artifacts['image']['file']}
            env_vars['DOCKER_SERVICE_NAME'] = source.name
            env_vars['CONTAINER_PORT'] = source.node_template.properties['ports'][0].split(':')[1]
            env_vars['K8s_NAMESPACE'] = 'default'
            return env_vars
        return None

    def set_attributes(self, task_outputs,nodes_pair):
        target = nodes_pair[0]
        source = nodes_pair[1]
        if source.node_template.type == 'tosca.nodes.QC.docker.Orchestrator.Kubernetes':
            if 'tokens' not in source.node_template.attributes:
                tokens = []
                source.node_template.attributes['tokens'] = tokens
            else:
                tokens = source.node_template.attributes['tokens']

            k8s_secrets = None
            k8s_services = None
            for task_output_key in task_outputs:
                task_output = task_outputs[task_output_key]
                if not k8s_secrets:
                    k8s_secrets = self.parse_ansible_var('k8s_secrets',task_output)
                if not k8s_services:
                    k8s_services = self.parse_ansible_var('k8s_services', task_output)
                if k8s_services and k8s_secrets:
                    break


            credential = {'token_type':'k8s_dashboard_token'}
            credential['token'] = self.get_dashboard_token(k8s_secrets)
            tokens.append(credential)

    def parse_ansible_var(self,var_name, output_array):
        index = 0
        start_index = -1
        end_index = -1
        for out in output_array:
            index += 1
            if 'TASK' in out or 'PLAY RECAP' in out:
                if start_index > -1:
                    end_index = index - 1
                    break
            if '"' + var_name + '":' in out:
                start_index = index - 1
        if start_index <= -1:
            return None
        ansible_var = output_array[start_index:end_index]
        json_ansible_var = '{'
        for item in ansible_var:
            json_ansible_var = json_ansible_var + item
        return json.loads(json_ansible_var)

    def get_dashboard_token(self, k8s_secrets):
        print(k8s_secrets)
        return None