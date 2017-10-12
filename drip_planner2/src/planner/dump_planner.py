
from toscaparser import *
from toscaparser.tosca_template import ToscaTemplate
import toscaparser.utils.yamlparser

class DumpPlanner:
    
    def __init__(self, tosca_file_path):
        self.yaml_dict_tpl = toscaparser.utils.yamlparser.load_yaml(tosca_file_path)
        self.errors = []
        self.warnings = []
        self.tt = None
        try:
            self.tt = ToscaTemplate(path=None, yaml_dict_tpl=self.yaml_dict_tpl)
        except:
            self.warnings.append("Not a valid tosca file")
            
        self.DOCKER_TYPE = 'Switch.nodes.Application.Container.Docker'
        self.COMPUTE_TYPE = 'Switch.nodes.Compute'
        self.HW_HOST_TYPE = 'Switch.datatypes.hw.host'
        self.HOSTED_NODE_TYPE = [self.DOCKER_TYPE, self.COMPUTE_TYPE]
        
        
    def get_docker_types(self):
        docker_types = set([])
        node_types = self.get_node_types()
        for node_type_key in node_types:
            if node_types[node_type_key] and 'derived_from' in node_types[node_type_key].keys():
                if node_types[node_type_key]['derived_from'] == self.DOCKER_TYPE:
                    docker_types.add(node_type_key)
        return  docker_types
    

    def get_node_types(self):
        return self.yaml_dict_tpl['node_types']
    
    
    def get_node_templates(self):
        return self.yaml_dict_tpl['topology_template']['node_templates']
    
    def get_artifacts(self, node):
        if 'artifacts' in node:
            return node['artifacts']
        
    def get_properties(self, node):
        if 'properties' in node:
            return node['properties']
    
    def get_enviroment_vars(self, properties):
        environment = []
        for prop in properties:
            if properties[prop] and not isinstance(properties[prop], dict):
                environment.append(prop + "=" + str(properties[prop]))
        return environment
    
    def get_port_map(self, properties):
        if 'ports_mapping' in properties:
            ports_mappings = properties['ports_mapping']
            
            port_maps = []
            for port_map_key in ports_mappings:            
                host_port = ports_mappings[port_map_key]['host_port']
                if not isinstance(host_port, (int, long, float, complex)):
                    host_port_var = host_port.replace('${', '').replace('}', '')
                    host_port = properties[host_port_var]

                container_port = ports_mappings[port_map_key]['container_port']
                if not isinstance(container_port, (int, long, float, complex)):
                    container_port_var = container_port.replace('${', '').replace('}', '')
                    container_port = properties[container_port_var]
                port_maps.append(str(host_port) + ':' + str(container_port))
        return port_maps
    
    def get_hosted_nodes(self, node_templates):
        docker_types = self.get_docker_types()
        self.HOSTED_NODE_TYPE  = self.HOSTED_NODE_TYPE + list(docker_types)
        hosted_nodes = []
        for node_key in node_templates:
            for hosted_type in self.HOSTED_NODE_TYPE:
                if node_templates[node_key]['type'] == hosted_type:
                    hosted_node = node_templates[node_key]
                    hosted_node['id'] = node_key
                    hosted_nodes.append(hosted_node)
                    break
        return hosted_nodes
    
    def plan(self):
        node_templates = self.get_node_templates() 
        hosted_nodes = self.get_hosted_nodes(node_templates)
        vms = []
        for node in hosted_nodes:
            vm = {}
            vm['name'] = node['id']
            vm['type'] = self.COMPUTE_TYPE
            
            for req in node['requirements']:
                vm['host'] = req['host']['node_filter']['capabilities']['host']
                vm['OStype'] = req['host']['node_filter']['capabilities']['os']['distribution'] + " " + str(req['host']['node_filter']['capabilities']['os']['os_version'])
#            vm['nodeType'] = 'medium'
#            vm['dockers']  = 
#            artifacts = self.get_artifacts(node)
#            if artifacts:
#                key =  next(iter(artifacts))
#                docker_file =  artifacts[key]['file']
#                vm['dockers'] = docker_file
#            vm['cloudProvider']
            vms.append(vm)
        print len(vms)
        return vms
