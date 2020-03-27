from sure_tosca_client import Configuration, ApiClient
from sure_tosca_client.api import default_api


class ToscaHelper:

    def __init__(self, sure_tosca_base_url, tosca_template_path):
        self.sure_tosca_base_url = sure_tosca_base_url
        self.tosca_template_path = tosca_template_path
        self.tosca_client = self.init_sure_tosca_client(sure_tosca_base_url)
        self.doc_id = self.upload_tosca_template(tosca_template_path)


    def upload_tosca_template(self, file_path):
        file_id = self.tosca_client.upload_tosca_template(file_path)
        return file_id


    def init_sure_tosca_client(self,sure_tosca_base_path):
        configuration = Configuration()
        configuration.host = sure_tosca_base_path
        api_client = ApiClient(configuration=configuration)
        api = default_api.DefaultApi(api_client=api_client)
        return api

    def get_interface_types(target):
        print(target.node_template.interfaces)
        interface_types = []
        for interface in target.node_template.interfaces:
            interface_types.append(interface)

        return interface_types

    def get_application_nodes(self):
        return self.tosca_client.get_node_templates(self.doc_id, type_name='tosca.nodes.ARTICONF.Application')

    def get_deployment_node_pairs(self):
        nodes_to_deploy = self.get_application_nodes()
        nodes_pairs = []
        for node in nodes_to_deploy:
            related_nodes = self.tosca_client.get_related_nodes(self.doc_id,node.name)
            for related_node in related_nodes:
                pair = (related_node, node)
                nodes_pairs.append(pair)

        return nodes_pairs


def get_interface_types(node):
    interface_type_names = []
    for interface in node.node_template.interfaces:
        interface_type_names.append(interface)
    return interface_type_names