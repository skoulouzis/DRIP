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
        
        capable_node_types = []
        for node in self.template.nodetemplates:
            missing_requirements = self.get_missing_requirements(node)
            
            for req in missing_requirements:
                for key in req:
                    capable_nodes = self.get_node_types_by_capability(req[key]['capability'])
                    
                    capable_node_types.append(capable_nodes)
                    
                    for node_type in capable_nodes:
                        capable_node = capable_nodes[node_type]
                        for cap in capable_node['capabilities']:
                            capability_type =  capable_node['capabilities'][cap]['type']
                            if capability_type == req[key]['capability'] and self.has_capability_max_one_occurrence(capable_node['capabilities'][cap]):
                                for capable_node_type in capable_node_types:
                                    print(capable_node_type)
                                    
#                        print(req[key]['capability'])
#                        print(capable_nodes[node_type]['capabilities'])
#                       if self.has_capability_max_one_occurrence(capable_nodes[node_type]['capabilities']):
#                           capable_nodes_dict[node_type] = capable_nodes[node_type]
                           
                        
#                    for capable_node in capable_nodes:
#                        print(capable_node)
#                    capable_nodes.append(capable_nodes)
                    
                node.requirements.append(req)
#            print(node.requirements)
#            print(capable_node_types)
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
        candidate_nodes = {}
        for tosca_node_type in self.all_nodes:
            if tosca_node_type.startswith('tosca.nodes') and 'capabilities' in self.all_nodes[tosca_node_type]:
                for caps in self.all_nodes[tosca_node_type]['capabilities']:
                    if self.all_nodes[tosca_node_type]['capabilities'][caps]['type'] == cap:
                        candidate_nodes[tosca_node_type] = self.all_nodes[tosca_node_type]
        
        candidate_child_nodes = {}
        for tosca_node_type in self.all_nodes:
            if tosca_node_type.startswith('tosca.nodes') and 'derived_from' in self.all_nodes[tosca_node_type]:
                candidate_child_node = (self.all_nodes[tosca_node_type])
                for candidate_node_name in candidate_nodes:
                    if candidate_child_node['derived_from'] == candidate_node_name:
                        candidate_child_nodes[tosca_node_type] = self.all_nodes[tosca_node_type]
                        candidate_child_nodes[tosca_node_type] = self.copy_capabilities_with_one_occurrences(candidate_nodes[candidate_node_name]['capabilities'],candidate_child_node)
        
        candidate_nodes.update(candidate_child_nodes)
        capable_nodes = {}
        
        #Only return the nodes that have interfaces. This means that they are not "abstract" 
        for candidate_node_name in candidate_nodes:
            if 'interfaces' in candidate_nodes[candidate_node_name].keys():
                capable_nodes[candidate_node_name] = candidate_nodes[candidate_node_name]
        return capable_nodes
    
    
    def copy_capabilities_with_one_occurrences(self,parent_capabilities,candidate_child_node):
        inherited_capabilities = []
        if not 'capabilities' in candidate_child_node.keys():
            candidate_child_node['capabilities'] = {}
            
        for capability in parent_capabilities: 
            inherited_capability = parent_capabilities[capability]
            if self.has_capability_max_one_occurrence(inherited_capability):
                inherited_capabilities.append(parent_capabilities)
                for key in parent_capabilities:
                    candidate_child_node['capabilities'][key] = parent_capabilities[key]
#                    candidate_child_node['capabilities'][key] = inherited_capability[key]                
#                print(inherited_capability)
        
        

#        for inherited_capability in inherited_capabilities:
#            for key in inherited_capability:
#                candidate_child_node['capabilities'][key] = inherited_capability[key]
        
        return candidate_child_node
    
    
    def has_capability_max_one_occurrence(self,capability):
        if 'occurrences' in capability and capability['occurrences'][1] == 1:
            return True
        else:
            return False