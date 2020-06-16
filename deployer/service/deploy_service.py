import json
import logging

from service import tosca_helper
from service.ansible_service import AnsibleService

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
        for vm in vms:
            if vm.node_template.attributes['role'] == 'master':
                self.master_ip = vm.node_template.attributes['public_ip']
                break

    def deploy(self,nodes_pair):
        target = nodes_pair[0]
        source = nodes_pair[1]
        interface_types = tosca_helper.get_interface_types(source)
        if interface_types:
            ansible_service = AnsibleService(self.semaphore_base_url, self.semaphore_username, self.semaphore_password)
            env_vars = self.get_env_vars(nodes_pair)
            if 'Standard' in interface_types:
                task_outputs =  ansible_service.execute(nodes_pair, 'Standard', self.vms, env_vars=env_vars)
                nodes_pair = self.set_attributes(task_outputs,nodes_pair)
            if 'Kubernetes' in interface_types:
                task_outputs = ansible_service.execute(nodes_pair, 'Kubernetes', self.vms, env_vars=env_vars)
                nodes_pair = self.set_attributes(task_outputs,nodes_pair)

        return nodes_pair

    def get_env_vars(self, nodes_pair):
        target = nodes_pair[0]
        source = nodes_pair[1]
        env_vars = {'K8s_NAMESPACE': 'default'}
        if source.node_template.type == 'tosca.nodes.QC.Container.Application.Docker':
            env_vars['DOCKER_IMAGE'] = source.node_template.artifacts['image']['file']
            env_vars['DOCKER_SERVICE_NAME'] = source.name
            env_vars['CONTAINER_PORT'] = source.node_template.properties['ports'][0].split(':')[1]
        return env_vars

    def set_attributes(self, task_outputs,nodes_pair):
        target = nodes_pair[0]
        source = nodes_pair[1]
        if source.node_template.type == 'tosca.nodes.QC.docker.Orchestrator.Kubernetes':
            source = self.set_kubernetes_attributes(source=source,task_outputs=task_outputs)
            lst = list(nodes_pair)
            lst[1] = source
            nodes_pair = tuple(lst)
        return nodes_pair



    def parse_ansible_var(self,var_name, output_array):
        index = 0
        start_index = -1
        end_index = -1
        for out in output_array:
            index += 1
            if 'TASK' in out.output or 'PLAY RECAP' in out.output:
                if start_index > -1:
                    end_index = index - 1
                    break
            if start_index <=-1 and '"' + var_name + '":' in out.output:
                start_index = index - 1
        if start_index <= -1:
            return None
        ansible_var = output_array[start_index:end_index]
        json_ansible_var = '{'
        for item in ansible_var:
            json_ansible_var = json_ansible_var + item.output
        logger.info('found '+var_name +': '+ str(json_ansible_var))
        return json.loads(json_ansible_var)

    def get_dashboard_token(self, k8s_dashboard_token):
        k8s_dashboard_token = k8s_dashboard_token['k8s_dashboard_token']
        # if 'resources' in k8s_secrets:
        #     return self.get_secret_from_k8s_info(k8s_secrets)
        if 'stdout' in k8s_dashboard_token:
            return self.get_secret_from_stdout(k8s_dashboard_token)


    def get_service_port(self, k8s_services, service_name,port_type):
        resources = k8s_services['k8s_services']['resources']
        for resource in resources:
            name = resource['metadata']['name']
            if name == service_name:
                ports = resource['spec']['ports']
                for port in ports:
                    if port_type in port:
                        return port[port_type]
        return None

    def get_secret_from_k8s_info(self, k8s_secrets):
        resources = k8s_secrets['resources']
        for resource in resources:
            metadata = resource['metadata']
            if 'admin-user-token' in  metadata['name']:
                dashboard_token = resource['data']['token']
                logger.info('found dashboard_token: ' + str(dashboard_token))
                return resource['data']['token']
        return None

    def get_secret_from_stdout(self, k8s_dashboard_token):
        return k8s_dashboard_token['stdout'].replace('token:     ', '')

    def set_kubernetes_attributes(self, source=None,task_outputs=None):
        attributes = source.node_template.attributes
        if 'tokens' not in attributes:
            tokens = []
            attributes['tokens'] = tokens
        else:
            tokens = attributes['tokens']

        if 'dashboard_url' not in source.node_template.attributes:
            dashboard_url = ''
            attributes['dashboard_url'] = tokens
        else:
            dashboard_url = attributes['dashboard_url']

        k8s_dashboard_token = None
        k8s_services = None
        for task_output_key in task_outputs:
            task_output = task_outputs[task_output_key]
            if not k8s_dashboard_token:
                k8s_dashboard_token = self.parse_ansible_var('k8s_dashboard_token', task_output)
            if not k8s_services:
                k8s_services = self.parse_ansible_var('k8s_services', task_output)
            if k8s_services and k8s_dashboard_token:
                credential = {'token_type': 'k8s_dashboard_token'}
                credential['token'] = self.get_dashboard_token(k8s_dashboard_token)
                tokens.append(credential)

                service_port = self.get_service_port(k8s_services, 'kubernetes-dashboard', 'nodePort')
                dashboard_url = 'https://' + self.master_ip + ':' + str(service_port)
                attributes['dashboard_url'] = dashboard_url
                logger.info('source.node_template.attributes: ' + str(attributes))
                return source
