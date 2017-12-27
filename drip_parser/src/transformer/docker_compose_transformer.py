
from toscaparser import *
from toscaparser.tosca_template import ToscaTemplate
import toscaparser.utils.yamlparser
import yaml
import logging


logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True

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
        
        
    def getnerate_compose(self,version):
#        if self.tt:
#            return self.analize_tosca()
#        else:
            return self.analyze_yaml(version)

    def get_node_types(self):
        return self.yaml_dict_tpl['node_types']
    
    def get_docker_types(self):
        docker_types = set([])
        node_types = self.get_node_types()
        for node_type_key in node_types:
            if node_types[node_type_key] and isinstance(node_types[node_type_key],dict) and 'derived_from' in node_types[node_type_key].keys():
                if node_types[node_type_key]['derived_from'] == self.DOCKER_TYPE:
                    docker_types.add(node_type_key)
        return docker_types
    
    def get_node_templates(self):
        return self.yaml_dict_tpl['topology_template']['node_templates']
    
    def get_artifacts(self,node):
        if 'artifacts' in node:
            return node['artifacts']
        
    def get_properties(self,node):
        if 'properties' in node:
            return node['properties']
    
    def get_enviroment_vars(self,properties):
        environments = {}
        for prop in properties:
            if prop == 'Environment_variables' or prop == 'Live_variables' or prop =='Environment':
                for var in properties[prop]:
                    if 'Environment' in properties[prop]:
                        for key in properties[prop]['Environment']:
                            environment ={}
                            environment[str(key)] = str(properties[prop]['Environment'][key])
                            environments.update(environment)
                    else:
                        environment ={}
                        environment[str(var)] = str(properties[prop][var])
                        environments.update(environment)
#                        environments.append(str(var)+"="+str(properties[prop][var]))
#            if properties[prop] and not isinstance(properties[prop],dict):
#                environment.append(prop+"="+str(properties[prop]))
        return environments
    
    
    
    def get_port_map(self,properties):
        port_maps = []
        if 'ports_mapping' in properties:
            ports_mappings = properties['ports_mapping']
            if ports_mappings and not isinstance(ports_mappings,str):
                for port_map_key in ports_mappings:
                    port_map = ''
                    if isinstance(ports_mappings,dict):                       
                        if 'host_port' in ports_mappings[port_map_key]:
                            host_port =  ports_mappings[port_map_key]['host_port']
                            if not isinstance(host_port, (int, long, float, complex)) and '$' in host_port:
                                host_port_var =  host_port.replace('${','').replace('}','')
                                host_port = properties[host_port_var]

                        if  'container_port' in ports_mappings[port_map_key]:
                            container_port =  ports_mappings[port_map_key]['container_port']
                            if not isinstance(container_port, (int, long, float, complex)) and '$' in container_port:
                                container_port_var =  container_port.replace('${','').replace('}','')
                                container_port = properties[container_port_var]
#                            port_map[host_port] = container_port
                            port_map = str(host_port)+':'+str(container_port)
                        port_maps.append(port_map)
                    elif isinstance(ports_mappings,list):      
                        for mapping in ports_mappings:
                            host_port = mapping.split(":")[0]
                            container_port = mapping.split(":")[1]
#                            port_map[host_port] = container_port      
                            port_map = str(host_port)+':'+str(container_port)   
                            port_maps.append(port_map)
            elif isinstance(ports_mappings,str):
                host_port = ports_mappings.split(":")[0] 
                container_port = ports_mappings.split(":")[1]
                port_map = str(host_port)+':'+str(container_port)  
                port_maps.append(port_map)
        if 'in_ports' in properties:
            ports_mappings = properties['in_ports']
            for port_map_key in ports_mappings:
                port_map = {}
                if 'host_port' in ports_mappings[port_map_key]:
                    host_port =  ports_mappings[port_map_key]['host_port']
                container_port = None
                if 'container_port' in ports_mappings[port_map_key]:
                    container_port =  ports_mappings[port_map_key]['container_port']
                if 'protocol' in ports_mappings[port_map_key]:
                    protocol =  ports_mappings[port_map_key]['protocol']
                    if protocol:
                        container_port=container_port+'/'+protocol
                if container_port:
#                    port_map[host_port] = container_port    
                    port_map = str(host_port)+':'+str(container_port)   
                    port_maps.append(port_map)
        if 'out_ports' in properties:
            ports_mappings = properties['out_ports']
            for port_map_key in ports_mappings:       
                port_map = {}
                if 'host_port' in ports_mappings[port_map_key] and 'container_port' in ports_mappings[port_map_key]:
                    host_port =  ports_mappings[port_map_key]['host_port']
                    container_port =  ports_mappings[port_map_key]['container_port']
                    if 'protocol' in ports_mappings[port_map_key]:
                        protocol =  ports_mappings[port_map_key]['protocol']
                        if protocol:
                            container_port=container_port+'/'+protocol
#                    port_map[host_port] = container_port
                    port_map = str(host_port)+':'+str(container_port)
                    port_maps.append(port_map)
        return port_maps
    
    def get_requirements(self,node):
        if 'requirements' in node:
            return node['requirements']
        
    def get_volumes(self,requirements):
        if requirements:
            volumes = []
            for req in requirements:
                if 'volume' in req:
                    vol = {}
                    name = req['volume']['name']
                    path = req['volume']['link']
                    vol[name]=path
                    volumes.append(vol)
            return volumes
    
    def analyze_yaml(self,version):
        docker_types  = self.get_docker_types()
        node_templates =  self.get_node_templates() 
        services = {}
        services['version'] = version
        services['services'] = {}
        all_volumes = []
        for node_template_key in node_templates:
            for docker_type in docker_types:
                if isinstance(node_templates[node_template_key],dict) and 'type' in node_templates[node_template_key] and docker_type in node_templates[node_template_key]['type']:
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
                    if environment:
                        service['environment'] = environment
                    port_maps = self.get_port_map(properties)
                    if port_maps:
                        service['ports'] = port_maps
                    
                    requirements = self.get_requirements(node_templates[node_template_key])
                    volumes = self.get_volumes(requirements)
                    if volumes:
                        service['volumes'] = volumes
                        for vol in volumes:
                            volume_id = {}
                            volume_id[next(iter(vol))] = None
                            all_volumes.append(volume_id)
                    services['services'][node_template_key] = service
                    break
        if all_volumes:
            services['volumes'] = all_volumes
        return services
