import json
import operator
import pdb
import re
from toscaparser.tosca_template import ToscaTemplate
from toscaparser.topology_template import TopologyTemplate
from toscaparser.elements.nodetype import NodeType
from toscaparser.nodetemplate import NodeTemplate
from toscaparser.utils import yamlparser
import urllib
import urllib.parse
import sys
import pdb
import names
import yaml
from pdb import set_trace as bp



class BasicPlanner:


    def __init__(self, path):
        self.template = ToscaTemplate(path)
        self.tosca_node_types = self.template.nodetemplates[0].type_definition.TOSCA_DEF
        self.all_custom_def = self.template.nodetemplates[0].custom_def
        self.all_nodes = {}
        self.all_nodes.update(self.tosca_node_types.items())
        self.all_nodes.update(self.all_custom_def.items())

        capable_node_types = []
        node_templates = []
        for node in self.template.nodetemplates:
            missing_requirements = self.get_missing_requirements(node)
            for req in missing_requirements:
                for key in req:
                    capable_nodes = self.get_node_types_by_capability(req[key]['capability'])
                    
                    # remove nodes required once. e.g. kubernetes is hosting all dockers 
                    for node_type in capable_nodes:
                        capable_node = capable_nodes[node_type]
                        for cap in capable_node['capabilities']:
                            capability_type =  capable_node['capabilities'][cap]['type']
                            if capability_type == req[key]['capability'] and self.has_capability_max_one_occurrence(capable_node['capabilities'][cap]) and not self.contains_node_type(capable_node_types,node_type):
                                capable_node_types.append(capable_nodes)
                                break
                            elif capability_type == req[key]['capability'] and not self.has_capability_max_one_occurrence(capable_node['capabilities'][cap]):
                                capable_node_types.append(capable_nodes)
                                break
                node.requirements.append(req)
                node_templates.append(node)


        for node_type in capable_node_types:
            nodetemplate_dict = {}
            type_name = next(iter(node_type))
            node_type_array = type_name.split(".")
            name = names.get_first_name().lower() + "_" + node_type_array[len(node_type_array)-1].lower()
            nodetemplate_dict[name] = node_type[next(iter(node_type))].copy()
            nodetemplate_dict[name]['type'] = type_name
            if 'capabilities' in nodetemplate_dict[name]:
                nodetemplate_dict[name].pop('capabilities')
            if 'requirements' in nodetemplate_dict[name]:
                nodetemplate_dict[name].pop('requirements')
            if 'capabilities' in nodetemplate_dict[name]:
                nodetemplate_dict[name].pop('capabilities')
            if 'derived_from' in nodetemplate_dict[name]:
                nodetemplate_dict[name].pop('derived_from')                
            
            if 'type' in node_type[next(iter(node_type))]:
                node_type[next(iter(node_type))].pop('type')
            
            nodetemplate = NodeTemplate(name, nodetemplate_dict,node_type)
            node_templates.append(nodetemplate)

        self.template.nodetemplates = node_templates
        
        print('------------------')
#            print(node.get_capabilities().keys)




    def get_missing_requirements(self, node):
        def_type = self.all_nodes[node.type]
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
        return candidate_child_node


    def has_capability_max_one_occurrence(self,capability):
        if 'occurrences' in capability and capability['occurrences'][1] == 1:
            return True
        else:
            return False


    def contains_node_type(self,capable_node_types_list,node_type):
        for capable_node_type in capable_node_types_list:
            type_name = next(iter(capable_node_type))
            if type_name == node_type:
                return True
        return False
