
from toscaparser import *
from toscaparser.tosca_template import ToscaTemplate
import toscaparser.utils.yamlparser
import yaml

class DockerComposeTransformer:
    
    def __init__(self, tosca_file_path):
        self.yaml_dict_tpl = toscaparser.utils.yamlparser.load_yaml(tosca_file_path)
        self.errors =[]
        self.warnings = []
        self.tt = None
        try:
            self.tt = ToscaTemplate(path=None, yaml_dict_tpl=self.yaml_dict_tpl)
        except:
            self.warnings.append("Not a valid tosca file")
        self.DOCKER_TYPE = 'Switch.nodes.Application.Container.Docker'
        
        
    def getnerate_compose(self):
#        if self.tt:
#            return self.analize_tosca()
#        else:
            return self.analyze_yaml()

    def get_node_types(self):
        return self.yaml_dict_tpl['node_types']
    
    def get_docker_types(self):
        docker_types = set([])
        node_types = self.get_node_types()
        for node_type_key in node_types:
            if node_types[node_type_key] and isinstance(node_types[node_type_key],dict) and'derived_from' in node_types[node_type_key].keys():
                if node_types[node_type_key]['derived_from'] == self.DOCKER_TYPE:
                    docker_types.add(node_type_key)
        return  docker_types
    
    def get_node_templates(self):
        return self.yaml_dict_tpl['topology_template']['node_templates']
    
    def get_artifacts(self,node):
        if 'artifacts' in node:
            return node['artifacts']
        
    def get_properties(self,node):
        if 'properties' in node:
            return node['properties']
    
    def get_enviroment_vars(self,properties):
        environment = []
        for prop in properties:
            if properties[prop] and not isinstance(properties[prop],dict):
                environment.append(prop+"="+str(properties[prop]))
        return environment
    
    def get_port_map(self,properties):
        port_maps = []
        if 'ports_mapping' in properties:
            ports_mappings = properties['ports_mapping']
            for port_map_key in ports_mappings:            
                host_port =  ports_mappings[port_map_key]['host_port']
                if not isinstance(host_port, (int, long, float, complex)):
                    host_port_var =  host_port.replace('${','').replace('}','')
                    host_port = properties[host_port_var]

                container_port =  ports_mappings[port_map_key]['container_port']
                if not isinstance(container_port, (int, long, float, complex)):
                    container_port_var =  container_port.replace('${','').replace('}','')
                    container_port = properties[container_port_var]
                port_maps.append(str(host_port)+':'+str(container_port))
        return port_maps
    
    
    def analyze_yaml(self):
        docker_types  = self.get_docker_types()
        node_templates =  self.get_node_templates() 
        services = {}
        services['version'] = '2'
        services['services'] = {}
        for node_template_key in node_templates:
            for docker_type in docker_types:
                if docker_type in node_templates[node_template_key]['type']:
                    service = {}
                    artifacts = self.get_artifacts(node_templates[node_template_key])
                    if artifacts:
                        key =  next(iter(artifacts))
                        docker_file =  artifacts[key]['file']
                        if docker_file:
                            service['image'] = docker_file
                    if docker_file and docker_file is not None and '/' in docker_file:
                        container_name = docker_file.split("/")[1]
                        if container_name and ':' in container_name:
                            container_name = container_name.split(':')[0]
                        service ['container_name'] = container_name+"_"+node_template_key
                    
                    properties = self.get_properties(node_templates[node_template_key])
                    environment = self.get_enviroment_vars(properties)
                    service['environment'] = environment
                    
                    port_maps = self.get_port_map(properties)
                    if port_maps:
                        service['ports'] = port_maps
                    services['services'][node_template_key] = service
                    break                
        return services

#    def analize_tosca():
#        dockers = []
#        print dir(self.tt.topology_template)
#        print dir(self.tt.outputs)
#        print dir(self.tt.nested_tosca_tpls_with_topology)
#        print dir(self.tt.nested_tosca_templates_with_topology)
#        print dir(self.tt.inputs)
#        print dir(self.tt.input_path)
#        print dir(self.tt.graph)
        
#        for node in self.tt.nodetemplates:
#            if node.parent_type.type == self.DOCKER_TYPE:
#                dockers.append(node)
#                print dir(node)
#                print "Name %s Type: %s" %(node.name,node.type)        
#                service = {}
#                service['name'] = node.type
#                print dir(node.get_properties_objects())
#                for prop_obj in node.get_properties_objects():
#                    print dir(prop_obj)
#                    print "Name %s Type: %s Val: %s" %(prop_obj.name,prop_obj.type,prop_obj.value)        
#                print (node.templates.keys())
#                docker_file = ""
#                for temp in node.templates:
#                    print "\t template: %s" %(temp)
#                    if 'artifacts' in node.templates[temp]:
#                        key = next(iter(node.templates[temp]['artifacts']))
#                        if 'file' in node.templates[temp]['artifacts'][key]:
#                            docker_file = node.templates[temp]['artifacts'][key]['file']
#                            print "\t\tdocker_file: %s"%(docker_file)
#                            
#                if docker_file:
#                    container_name = docker_file.split("/")[1]
#                    if ':' in container_name:
#                        container_name = container_name.split(':')[0]
#                    print container_name
#                    service ['container_name'] = container_name
#                    print "Name %s Type: %s Val: %s" %(prop_obj.name,prop_obj.type,prop_obj.value)                  
#                service ['container_name'] = 
