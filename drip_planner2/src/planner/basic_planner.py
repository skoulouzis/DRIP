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
from utils.TOSCA_parser import *
import logging



class BasicPlanner:


    def __init__(self, path):
        self.template = ToscaTemplate(path)
        self.tosca_node_types = self.template.nodetemplates[0].type_definition.TOSCA_DEF
        self.all_custom_def = self.template.nodetemplates[0].custom_def
        self.all_nodes = {}
        self.all_nodes.update(self.tosca_node_types.items())
        self.all_nodes.update(self.all_custom_def.items())

        
        
#        capable_node_name = ''
        for node in self.template.nodetemplates:
            node_templates = self.add_reqired_nods(node,None)
#            missing_requirements = self.get_missing_requirements(node)
#            for req in missing_requirements:
#                for key in req:
#                    capable_node = self.get_node_types_by_capability(req[key]['capability'])                
#                    capable_node_type = next(iter(capable_node))
#                
#                if self.node_requered_once(capable_node) and not self.contains_node_type(node_templates,capable_node_type):
#                    capable_node_template = self.node_type_2_node_template(capable_node)
#                    capable_node_name = capable_node_template.name
#                    node_templates.append(capable_node_template)
#                
#                req[next(iter(req))]['node'] = capable_node_name
#                node.requirements.append(req)
#                node_templates.append(node)
        
        if node_templates:
            self.template.nodetemplates = node_templates
        else:
            logging.info('The TOSCA template in: '+path + ' has no requirements' )
        tp = TOSCAParser()
        yaml_str = tp.tosca_template2_yaml(self.template)
        
        logging.info('TOSCA template: \n'+ yaml_str)
#        print(yaml_str)




    def get_missing_requirements(self, node):
        logging.info('Looking for requirements for node: '+node.name )
        def_type = self.all_nodes[node.type]
        def_requirements = []
        if 'requirements' in def_type.keys():
            def_requirements = def_type['requirements']
            logging.info('Found requirements: '+ str(def_requirements)+ ' for node: '+node.name )
        
        missing_requirements = []
        if not node.requirements:
            missing_requirements = def_requirements
        elif def_requirements:
            for def_requirement in def_requirements:
                for key in def_requirement:
                    for node_req in node.requirements:
                        if key not in node_req:
                            missing_requirements.append(def_requirement)
        
        
        #Make sure we have the deffinition. Otherwise we get an error in the recursion 
        if 'derived_from' in def_type.keys() and not def_type['derived_from'] in node.custom_def.keys():
            node.custom_def[def_type['derived_from']] = self.all_custom_def[def_type['derived_from']]
            
        if node.parent_type and node.parent_type.requirements:
            logging.info('Adding to : '+str(node.name) + '  parent requirements from: '+str(node.parent_type.type))
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
        if capable_node_types_list == None:
            return False
        for capable_node_type in capable_node_types_list:
            if isinstance(capable_node_type, NodeTemplate):
                type_name = capable_node_type.type
            elif isinstance(capable_node_type, dict):
                type_name = next(iter(capable_node_type))
            if type_name == node_type:
                return True
        return False
    
    def node_type_2_node_template(self,node_type):
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
        return NodeTemplate(name, nodetemplate_dict,node_type)


    def node_requered_once(self,node):
        for node_type in node:
            if 'capabilities' in node[node_type]:
                capabilities = node[node_type]['capabilities']
                for cap in capabilities:
                    if self.has_capability_max_one_occurrence(capabilities[cap]):
                        return True
        return False
    
    def add_reqired_nods(self,node,node_templates):
        if not node_templates:
            node_templates = []
        
        missing_requirements = self.get_missing_requirements(node)
        if not missing_requirements:
            logging.info('Node: '+node.name + ' of type: '+node.type +' has no requirements')
#            return node_templates
        capable_node_name = ''
        for req in missing_requirements:
            for key in req:
                logging.info('Looking for capability: '+req[key]['capability']+ ' for node: '+node.name )
                capable_node = self.get_node_types_by_capability(req[key]['capability'])
                if capable_node: 
                    capable_node_type = next(iter(capable_node))
                    logging.info('Found : '+str(capable_node_type))
                else: 
                    logging.error('Did not find node with reuired capability: '+str(req[key]['capability']))

            if self.node_requered_once(capable_node) and not self.contains_node_type(node_templates,capable_node_type):
                capable_node_template = self.node_type_2_node_template(capable_node)
                capable_node_name = capable_node_template.name
                node_templates.append(capable_node_template)
                #recursively fulfill all requirements
                self.add_reqired_nods(capable_node_template,node_templates)

            req[next(iter(req))]['node'] = capable_node_name
            node.requirements.append(req)
            if not self.contains_node_type(node_templates,node):
                node_templates.append(node)
        
        return node_templates