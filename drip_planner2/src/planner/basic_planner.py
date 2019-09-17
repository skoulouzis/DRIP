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


def get_cpu_frequency():
    return '2.9 GHz'


def get_num_cpus():
    return 1


def get_cloud_domain():
    return 'UvA (Amsterdam, The Netherlands) XO Rack'


def get_cloud_provider():
    return 'ExoGeni'


def get_disk_size():
    return '25000 MB'


def get_mem_size():
    return '3000 MB'


def get_os_distribution():
    return 'Ubuntu 16.04'


def set_vm_properties(node_template_dict):
    node_template_dict['properties'].pop('host_name')
    node_template_dict['properties']['host_name'] = 'vm'
    node_template_dict['properties'].pop('num_cpus')
    node_template_dict['properties']['num_cpus'] = get_num_cpus()
    node_template_dict['properties'].pop('cpu_frequency')
    node_template_dict['properties']['cpu_frequency'] = get_cpu_frequency()
    node_template_dict['properties'].pop('disk_size')
    node_template_dict['properties']['disk_size'] = get_disk_size()
    node_template_dict['properties']['mem_size'] = get_mem_size()
    node_template_dict['properties'].pop('user_name')
    node_template_dict['properties']['user_name'] = 'vm_user'
    node_template_dict['properties']['os'] = get_os_distribution()
    return node_template_dict


def set_topology_properties(node_template_dict):
    node_template_dict['properties'].pop('provider')
    node_template_dict['properties']['provider'] = get_cloud_provider()
    node_template_dict['properties'].pop('domain')
    node_template_dict['properties']['domain'] = get_cloud_domain()
    node_template_dict['properties'].pop('name')
    node_template_dict['properties']['name'] = 'name'
    return node_template_dict


def fill_in_properties(nodetemplate_dict):
    if 'properties' in nodetemplate_dict:
        if 'type' in nodetemplate_dict and nodetemplate_dict['type'] == 'tosca.nodes.ARTICONF.VM.topology':
            nodetemplate_dict = set_topology_properties(nodetemplate_dict)
        if 'type' in nodetemplate_dict and nodetemplate_dict['type'] == 'tosca.nodes.ARTICONF.VM.Compute':
            nodetemplate_dict = set_vm_properties(nodetemplate_dict)
    return nodetemplate_dict


def fix_occurrences(node_templates):
    # Replace  'occurrences': [1, 'UNBOUNDED']  with 'occurrences': 1
    for node in node_templates:
        for req in node.requirements:
            if 'occurrences' in req[next(iter(req))]:
                req[next(iter(req))].pop('occurrences')
    return node_templates


def node_type_2_node_template(node_type):
    nodetemplate_dict = {}
    type_name = next(iter(node_type))
    node_type_array = type_name.split(".")
    name = names.get_first_name().lower() + "_" + node_type_array[len(node_type_array) - 1].lower()
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
    if 'properties' in nodetemplate_dict[name]:
        nodetemplate_dict[name] = fill_in_properties(nodetemplate_dict[name])
    if 'type' in node_type[next(iter(node_type))]:
        node_type[next(iter(node_type))].pop('type')
    return NodeTemplate(name, nodetemplate_dict, node_type)


def contains_node_type(capable_node_types_list, node_type):
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


def get_requirement_occurrences(req):
    if 'occurrences' in req:
        return req['occurrences']
    return None


def fix_duplicate_vm_names(yaml_str):
    topology_dict = yaml.load(yaml_str)
    node_templates = topology_dict['tosca_template']['node_templates']
    vm_names = []
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.VM.Compute':
            vm_names.append(node_name)

    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.VM.topology':
            i = 0
            for req in node_templates[node_name]['requirements']:
                req['vm']['node'] = vm_names[i]
                i += 1
    return yaml.dump(topology_dict)


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
            node_templates = self.add_reqired_nods(node, None)

        if node_templates:
            node_templates = fix_occurrences(node_templates)
            self.template.nodetemplates = node_templates
        else:
            logging.info('The TOSCA template in: ' + path + ' has no requirements')
        tp = TOSCAParser()
        yaml_str = tp.tosca_template2_yaml(self.template)
        yaml_str = fix_duplicate_vm_names(yaml_str)
        yaml_str = yaml_str.replace('tosca_definitions_version: tosca_simple_yaml_1_0', '')
        yaml_str = yaml_str.replace('description: TOSCA example', '')
        yaml_str = yaml_str.replace('tosca_template', 'topology_template')
        self.formatted_yaml_str = 'tosca_definitions_version: tosca_simple_yaml_1_0\nrepositories:\n docker_hub: https://hub.docker.com/\n' + yaml_str
        logging.info('TOSCA template: \n' + self.formatted_yaml_str)

    def get_plan(self):
        return self.formatted_yaml_str

    def get_missing_requirements(self, node):
        logging.info('Looking for requirements for node: ' + node.name)
        def_type = self.all_nodes[node.type]
        def_requirements = []
        if 'requirements' in def_type.keys():
            def_requirements = def_type['requirements']
            logging.info('Found requirements: ' + str(def_requirements) + ' for node: ' + node.name)

        missing_requirements = []
        if not node.requirements:
            missing_requirements = def_requirements
        elif def_requirements:
            for def_requirement in def_requirements:
                for key in def_requirement:
                    for node_req in node.requirements:
                        if key not in node_req:
                            missing_requirements.append(def_requirement)

        # Make sure we have the deffinition. Otherwise we get an error in the recursion
        if 'derived_from' in def_type.keys() and not def_type['derived_from'] in node.custom_def.keys():
            node.custom_def[def_type['derived_from']] = self.all_custom_def[def_type['derived_from']]

        if node.parent_type and node.parent_type.requirements:
            logging.info('Adding to : ' + str(node.name) + '  parent requirements from: ' + str(node.parent_type.type))
            missing_requirements = missing_requirements + node.parent_type.requirements
            logging.info('  missing_requirements: ' + str(missing_requirements))
        return missing_requirements

    def get_node_types_by_capability(self, cap):
        candidate_nodes = {}
        for tosca_node_type in self.all_nodes:
            if tosca_node_type.startswith('tosca.nodes') and 'capabilities' in self.all_nodes[tosca_node_type]:
                logging.debug('      Node: ' + str(tosca_node_type))
                for caps in self.all_nodes[tosca_node_type]['capabilities']:
                    logging.debug('          ' + str(
                        self.all_nodes[tosca_node_type]['capabilities'][caps]['type']) + ' == ' + cap)
                    if self.all_nodes[tosca_node_type]['capabilities'][caps]['type'] == cap:
                        candidate_nodes[tosca_node_type] = self.all_nodes[tosca_node_type]
                        logging.debug('          candidate_node: ' + str(tosca_node_type))

        candidate_child_nodes = {}
        for tosca_node_type in self.all_nodes:
            if tosca_node_type.startswith('tosca.nodes') and 'derived_from' in self.all_nodes[tosca_node_type]:
                candidate_child_node = (self.all_nodes[tosca_node_type])
                for candidate_node_name in candidate_nodes:
                    if candidate_child_node['derived_from'] == candidate_node_name:
                        candidate_child_nodes[tosca_node_type] = self.all_nodes[tosca_node_type]
                        candidate_child_nodes[tosca_node_type] = self.copy_capabilities_with_one_occurrences(
                            candidate_nodes[candidate_node_name]['capabilities'], candidate_child_node)

        candidate_nodes.update(candidate_child_nodes)
        capable_nodes = {}

        # Only return the nodes that have interfaces. This means that they are not "abstract"
        for candidate_node_name in candidate_nodes:
            if 'interfaces' in candidate_nodes[
                candidate_node_name].keys() and 'tosca.nodes.Root' != candidate_node_name:
                capable_nodes[candidate_node_name] = candidate_nodes[candidate_node_name]
        return capable_nodes

    def copy_capabilities_with_one_occurrences(self, parent_capabilities, candidate_child_node):
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

    def has_capability_max_one_occurrence(self, capability):
        if 'occurrences' in capability and capability['occurrences'][1] == 1:
            return True
        else:
            return False

    def get_optimal_num_of_occurrences(self, node_type, min_max_occurrences):
        max_occurrences = -1
        min_occurrences = -1
        if min_max_occurrences:
            if isinstance(min_max_occurrences[1], int):
                max_occurrences = int(min_max_occurrences[1])
            if isinstance(min_max_occurrences[0], int):
                min_occurrences = int(min_max_occurrences[0])
            if max_occurrences and max_occurrences > -1:
                return max_occurrences
            if max_occurrences and max_occurrences <= -1 and min_max_occurrences[
                1] == 'UNBOUNDED' and node_type == 'tosca.nodes.ARTICONF.VM.Compute':
                return 2
        else:
            return 1

    def add_reqired_nods(self, node, node_templates):
        if not node_templates:
            node_templates = []
        missing_requirements = self.get_missing_requirements(node)
        if not missing_requirements:
            logging.info('Node: ' + node.name + ' of type: ' + node.type + ' has no requirements')
        #            return node_templates
        capable_node_name = ''
        min_max_occurrences = []
        for req in missing_requirements:
            for key in req:
                min_max_occurrences = get_requirement_occurrences(req[key])
                logging.info('Looking for capability: ' + req[key]['capability'] + ' for node: ' + node.name)
                capable_node = self.get_node_types_by_capability(req[key]['capability'])
                if capable_node:
                    capable_node_type = next(iter(capable_node))
                    logging.info('Found: ' + str(capable_node_type))
                else:
                    logging.error('Did not find node with reuired capability: ' + str(req[key]['capability']))

            occurrences = self.get_optimal_num_of_occurrences(capable_node_type, min_max_occurrences)
            if not contains_node_type(node_templates, capable_node_type) and occurrences == 1:
                capable_node_template = node_type_2_node_template(capable_node)
                capable_node_name = capable_node_template.name
                node_templates.append(capable_node_template)
                # recursively fulfill all requirements
                self.add_reqired_nods(capable_node_template, node_templates)
            elif occurrences > 1:
                logging.info('Creating: ' + str(occurrences) + ' occurrences of ' + capable_node_type)
                for x in range(0, occurrences):
                    capable_node_template = node_type_2_node_template(capable_node)
                    capable_node_name = capable_node_template.name
                    logging.info('Adding : ' + str(capable_node_template.name))
                    node_templates.append(capable_node_template)
                # recursively fulfill all requirements
                self.add_reqired_nods(capable_node_template, node_templates)

            for x in range(0, occurrences):
                req[next(iter(req))]['node'] = capable_node_name
                node.requirements.append(req)

            if not contains_node_type(node_templates, node):
                node_templates.append(node)

        return node_templates
