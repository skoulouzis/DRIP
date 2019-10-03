import logging
from toscaparser.nodetemplate import NodeTemplate
from toscaparser.tosca_template import ToscaTemplate
import networkx as nx
from src.utils.TOSCA_parser import TOSCAParser
import operator
from planner.spec_service import *


class Planner:
    node_type_key_names_to_remove = ['capabilities', 'requirements', 'derived_from']

    def __init__(self, path):
        self.template = ToscaTemplate(path)
        self.tosca_node_types = self.template.nodetemplates[0].type_definition.TOSCA_DEF
        self.all_custom_def = self.template.nodetemplates[0].custom_def
        self.all_nodes = {}
        self.all_nodes.update(self.tosca_node_types.items())
        self.all_nodes.update(self.all_custom_def.items())
        self.required_nodes = []
        props = {}
        props['kb_url'] = "https://host"
        self.spec_service = SpecService(props)

        # graph = self.build_graph(template.nodetemplates)

    def set_infrastructure_specifications(self):
        self.set_vm_specifications()

    def set_vm_specifications(self):
        for node in self.template.nodetemplates:
            if node.type == 'tosca.nodes.ARTICONF.VM.Compute':
                props = {}
                username_prop = node.get_property_value('user_name')
                vm_username_val = self.spec_service.get_vm_username()

                props['properties'] = self.set_prop(prop_key,prop_value)
                if vm_username_val:
                    props['properties'] = {'user_name': vm_username_val}
                else:
                    if username_prop['required'] == 'true' and username_prop['default']:
                        vm_username_val = username_prop['default']
                        props['properties'] = {'user_name': vm_username_val}

                node._properties = props
                logging.info(str(node._properties))
                # for prop in node.get_properties_objects():
                #     logging.info(prop.name + ' : ' + str(prop.value))

    def set_prop(self,prop_key,prop_value):

    # Resolve requirements. Go over all nodes and recursively resolve requirements till node has no requirements
    # e.g. docker -> k8s -> cluster -> vm
    def resolve_requirements(self):
        for node in self.template.nodetemplates:
            self.add_required_nodes(node)
        self.template.nodetemplates.extend(self.required_nodes)

    def get_tosca_template(self):
        tp = TOSCAParser()
        yaml_str = tp.tosca_template2_yaml(self.template)
        return yaml_str

    def add_required_nodes(self, node):
        missing_requirements = self.get_missing_requirements(node)
        if not missing_requirements:
            logging.debug('Node: ' + node.type + ' has no requirements')
            return
        matching_node = self.find_best_node(missing_requirements)

        # Only add node that is not in node_templates
        matching_node_type_name = next(iter(matching_node))
        capable_node_template = self.node_type_2_node_template(matching_node)
        if not self.contains_node_type(self.required_nodes, matching_node_type_name):
            node = self.add_missing_requirements(node, missing_requirements, capable_node_template.name)
            logging.info('Adding: ' + str(capable_node_template.type))
            self.required_nodes.append(capable_node_template)
        # Find matching nodes for the new node's requirements
        self.add_required_nodes(capable_node_template)

    def build_graph(self, nodetemplates):
        graph = nx.Graph()
        for node in nodetemplates:
            graph.add_node(node.name)
            for req in node.requirements:
                req_name = next(iter(req))
                trg_node_name = req[req_name]['node']
                for key in req[req_name]:
                    if key != 'node':
                        dict_req = req[req_name][key]
                        if isinstance(dict_req, dict):
                            graph.add_edge(node.name, trg_node_name, key=key, attr_dict=dict_req)
                        elif isinstance(dict_req, str):
                            graph.add_edge(node.name, trg_node_name, key=key, attr_dict={'type': dict_req})
        print("Nodes: " + str(graph.nodes()))
        print("Edges: " + str(graph.edges()))
        print("Edges: " + str(graph.edges(data=True)))

        return graph

    def get_missing_requirements(self, node):
        logging.debug('Looking for requirements for node: ' + node.type)
        def_type = self.all_nodes[node.type]
        def_requirements = []
        if 'requirements' in def_type.keys():
            def_requirements = def_type['requirements']
            logging.debug('Found requirements: ' + str(def_requirements) + ' for node: ' + node.type)

        missing_requirements = []
        if not node.requirements:
            missing_requirements = def_requirements
        elif def_requirements:
            for def_requirement in def_requirements:
                for key in def_requirement:
                    for node_req in node.requirements:
                        if key not in node_req:
                            req_name = next(iter(def_requirement))
                            def_requirement[req_name]['node'] = None
                            missing_requirements.append(def_requirement)

        # Make sure we have the definition. Otherwise we get an error in the recursion
        if 'derived_from' in def_type.keys() and not def_type['derived_from'] in node.custom_def.keys():
            node.custom_def[def_type['derived_from']] = self.all_custom_def[def_type['derived_from']]

        if node.parent_type and node.parent_type.requirements:
            logging.debug(
                ' Adding to : ' + str(node.name) + '  parent requirements from: ' + str(node.parent_type.type))
            missing_requirements = missing_requirements + node.parent_type.requirements
            logging.debug('  missing_requirements: ' + str(missing_requirements))
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
        for node in candidate_nodes:
            candidate_child_nodes.update(self.get_child_nodes(node))

        candidate_nodes.update(candidate_child_nodes)
        capable_nodes = {}
        # Only return the nodes that have interfaces. This means that they are not "abstract"
        nodes_type_names_with_interface = self.get_node_types_with_interface(candidate_nodes)
        for type_name in nodes_type_names_with_interface:
            capable_nodes[type_name] = candidate_nodes[type_name]
        return capable_nodes

    def contains_node_type(self, node_types_list, node_type_name):
        if node_types_list is None:
            return False
        for node_type in node_types_list:
            if isinstance(node_type, NodeTemplate):
                type_name = node_type.type
            elif isinstance(node_type, dict):
                type_name = next(iter(node_type))
            if type_name == node_type_name:
                return True
        return False

    def node_type_2_node_template(self, node_type):
        nodetemplate_dict = {}
        type_name = next(iter(node_type))
        node_type_array = type_name.split(".")
        name = node_type_array[len(node_type_array) - 1].lower()
        nodetemplate_dict[name] = node_type[next(iter(node_type))].copy()
        nodetemplate_dict[name]['type'] = type_name

        for name_to_remove in self.node_type_key_names_to_remove:
            if name_to_remove in nodetemplate_dict[name]:
                nodetemplate_dict[name].pop(name_to_remove)

        if 'type' in node_type[next(iter(node_type))]:
            node_type[next(iter(node_type))].pop('type')
        return NodeTemplate(name, nodetemplate_dict, node_type)

    def find_best_node(self, missing_requirements):
        matching_nodes = {}
        number_of_matching_requirement = {}
        # Loop requirements to find nodes per requirement
        for req in missing_requirements:
            for key in req:
                capability = req[key]['capability']
                logging.info('Looking for nodes with capability: ' + capability)
                # Find all nodes in the definitions that have the capability: capability
                capable_nodes = self.get_node_types_by_capability(capability)
                if capable_nodes:
                    # Add number of matching capabilities for each node.
                    # Try to score matching_nodes to return one. The more requirements a node meets the better
                    for node_type in capable_nodes:
                        matching_requirement_count = 1
                        if node_type not in number_of_matching_requirement:
                            number_of_matching_requirement[node_type] = matching_requirement_count
                        else:
                            matching_requirement_count = number_of_matching_requirement[node_type]
                            matching_requirement_count += 1

                        number_of_matching_requirement[node_type] = matching_requirement_count
                        logging.info('  Found: ' + str(node_type))
                    matching_nodes.update(capable_nodes)
                else:
                    logging.error('Did not find node with required capability: ' + str(req[key]['capability']))
                    return None
        # if we only found 1 return it
        if len(matching_nodes) == 1:
            return matching_nodes

        sorted_number_of_matching_requirement = sorted(number_of_matching_requirement.items(),
                                                       key=operator.itemgetter(1))
        index = len(sorted_number_of_matching_requirement) - 1
        winner_type = next(iter(sorted_number_of_matching_requirement[index]))
        return {winner_type: matching_nodes[winner_type]}

    def get_child_nodes(self, parent_node_type_name):
        child_nodes = {}
        for tosca_node_type in self.all_nodes:
            if tosca_node_type.startswith('tosca.nodes') and 'derived_from' in self.all_nodes[tosca_node_type]:
                if parent_node_type_name == self.all_nodes[tosca_node_type]['derived_from']:
                    child_nodes[tosca_node_type] = self.all_nodes[tosca_node_type]
        return child_nodes

    def get_node_types_with_interface(self, nodes):
        node_types_with_interface = []
        for node_name in nodes:
            if 'interfaces' in nodes[node_name].keys() and 'tosca.nodes.Root' != node_name:
                node_types_with_interface.append(node_name)
        return node_types_with_interface

    def add_missing_requirements(self, node, missing_requirements, capable_node_name):
        for req in missing_requirements:
            req[next(iter(req))]['node'] = capable_node_name
            node.requirements.append(req)
        return node
