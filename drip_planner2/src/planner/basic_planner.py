import json
import operator
import pdb
import re
from toscaparser.tosca_template import ToscaTemplate
from toscaparser.topology_template import TopologyTemplate
from toscaparser.elements.nodetype import NodeType
from toscaparser.nodetemplate import NodeTemplate
import toscaparser.utils.yamlparser
import urllib
import urllib.parse
import sys
import pdb



class BasicPlanner:

    
    def __init__(self, path):
        self.template = ToscaTemplate(path)
        self.tosca_node_types = self.template.nodetemplates[0].type_definition.TOSCA_DEF
        self.all_custom_def = self.template.nodetemplates[0].custom_def
        self.all_nodes = {}
        self.all_nodes.update(self.tosca_node_types.items())
        self.all_nodes.update(self.all_custom_def.items())
        for node in self.template.nodetemplates:
            capable_nodes = []
            missing_requirements = self.get_missing_requirements(node)
            
            for req in missing_requirements:
                for key in req:
                    capable_nodes.append(self.get_node_types_by_capability(req[key]['capability']))
                node.requirements.append(req)
#            print((node.type_definition._get_node_type_by_cap('tosca.capabilities.ARTICONF.Orchestrator')))
            print('------------------')
#            print(node.get_capabilities().keys)
        
            
            
        
    def get_missing_requirements(self, node):
        def_type = self.template._get_all_custom_defs()[node.type]
        def_requirements = def_type['requirements']
        missing_requirements = []
        if not node.requirements:
            missing_requirements = def_requirements
        else:
            for def_requirement in def_requirements:
                for key in def_requirement:
                    for node_req in node.requirements:
                        if key not in(node_req):
                            missing_requirements.append(def_requirement)
                            break
                                
        if node.parent_type:
            missing_requirements = missing_requirements + node.parent_type.requirements
        return missing_requirements
  
  
    def get_node_types_by_capability(self,cap):
        capable_nodes = []
        for tosca_node in self.all_nodes:
            if 'capabilities' in self.all_nodes[tosca_node]:
                for caps in self.all_nodes[tosca_node]['capabilities']:
                    if self.all_nodes[tosca_node]['capabilities'][caps]['type'] == cap:
                        capable_nodes.append(tosca_node)
        return capable_nodes