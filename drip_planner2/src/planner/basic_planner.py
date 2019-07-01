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
        
        for node in self.template.nodetemplates:
            missing_requirements = self.get_missing_requirements(node)
            print(missing_requirements)
            for req in missing_requirements:
                node.requirements.append(req)
            
#            print(node.get_properties().keys())
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
  