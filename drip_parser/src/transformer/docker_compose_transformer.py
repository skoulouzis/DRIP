
from toscaparser import *
from toscaparser.tosca_template import ToscaTemplate
import toscaparser.utils.yamlparser

class DockerComposeTransformer:
    
    def __init__(self, tosca_file_path):
        yaml_dict_tpl = toscaparser.utils.yamlparser.load_yaml(tosca_file_path)
        self.tt = ToscaTemplate(path=None, yaml_dict_tpl=yaml_dict_tpl)
        self.DOCKER_TYPE = 'Switch.nodes.Application.Container.Docker'
        
        
    def getnerate_compose(self):
        dockers = []
        print dir(self.tt.topology_template)
        print dir(self.tt.outputs)
        print dir(self.tt.nested_tosca_tpls_with_topology)
        print dir(self.tt.nested_tosca_templates_with_topology)
        print dir(self.tt.inputs)
        print dir(self.tt.input_path)
        print dir(self.tt.graph)
        for node in self.tt.nodetemplates:
            if node.parent_type.type == self.DOCKER_TYPE:
                dockers.append(node)
                print "Name %s Type: %s Parent: %s" %(node.name,node.type,node.parent_type.type)        


        
        
#        topology_template = parsed_json_value['topology_template']
#        node_templates = topology_template["node_templates"]
#    
#        response = {}
#        current_milli_time = lambda: int(round(time.time() * 1000))
#        response["creationDate"] = current_milli_time()   
#        response["parameters"] = []
#
#        for nodes in node_templates:
#            if "Switch.nodes.Application.Container.Docker." in node_templates[nodes]['type']:
#                node_keys = node_templates[nodes].keys()     
#                if 'artifacts' in node_keys:
#                    artifact_key = next(iter(node_templates[nodes]['artifacts']))
#                    artifact_keys = node_templates[nodes]['artifacts'][artifact_key].keys()   
#                    if 'file' in artifact_keys:
#                        docker = node_templates[nodes]['artifacts'][artifact_key]['file']
#                    elif 'docker_image' in artifact_keys:
#                        docker = node_templates[nodes]['artifacts']['docker_image']['file']
#                    result = {}
#                    parameter = {}
#                    result['name'] = nodes
#                    result['size'] = 'Medium'
#                    result['docker'] = docker
#                    parameter['value'] = str(json.dumps(result))
#                    parameter['attributes'] = 'null'
#                    parameter["url"] = "null"
#                    parameter["encoding"] = "UTF-8"
#                    response["parameters"].append(parameter)
#        print ("Output message: %s" % json.dumps(response))
#        return json.dumps(response)