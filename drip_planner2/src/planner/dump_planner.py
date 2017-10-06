from toscaparser import *
from toscaparser.tosca_template import ToscaTemplate
import json
import time

class DumpPlanner:
    

    def __init__(self,tosca):
        self.tosca = tosca
        self.tt = ToscaTemplate()
        
    def plan(self):
        self.tt
        
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