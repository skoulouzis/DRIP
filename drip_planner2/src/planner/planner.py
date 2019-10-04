import logging
from toscaparser.nodetemplate import NodeTemplate
from toscaparser.tosca_template import ToscaTemplate
import networkx as nx
import operator
from planner.spec_service import *
from utils.tosca import *

from utils import tosca as tosca_util


class Planner:

    def __init__(self, path):
        self.template = ToscaTemplate(path)
        self.tosca_node_types = self.template.nodetemplates[0].type_definition.TOSCA_DEF
        self.all_custom_def = self.template.nodetemplates[0].custom_def
        self.all_nodes = {}
        self.all_nodes.update(self.tosca_node_types.items())
        self.all_nodes.update(self.all_custom_def.items())
        self.required_nodes = []
        props = {'kb_url': "https://host"}
        self.spec_service = SpecService(props)

        # graph = self.build_graph(template.nodetemplates)

    def add_required_nodes_to_template(self, required_nodes):
        for req_node in required_nodes:
            node_template = tosca_util.node_type_2_node_template(req_node)
            self.template.nodetemplates.append(node_template)
        return self.template

    def set_infrastructure_specifications(self, required_nodes):
        required_nodes = self.set_vm_specifications(required_nodes)
        required_nodes = self.set_topology_specifications(required_nodes)
        return required_nodes

    def set_vm_specifications(self, required_nodes):
        for node in required_nodes:
            node_type_name = tosca_util.get_node_type_name(node)
            if node_type_name == 'tosca.nodes.ARTICONF.VM.Compute':
                node_template_props = {'properties': {}}
                node_properties = tosca_util.get_node_properties(node)
                for node_type_prop_key in node_properties:
                    node_template_prop = self.get_node_template_property(node_type_prop_key,
                                                                         node_properties[node_type_prop_key])
                    node_template_props['properties'][node_type_prop_key] = node_template_prop
                node = tosca_util.set_node_properties(node, node_template_props)
                logging.info('node: ' + node_type_name + ' properties: ' + str(node_template_props))
        return required_nodes

    def get_node_template_property(self, prop_key, node_prop_dict):
        prop_value = self.spec_service.get_property(prop_key)
        if prop_value:
            return {prop_key: node_prop_dict}
        else:
            if 'required' in node_prop_dict and 'default' in node_prop_dict:
                return node_prop_dict['default']
            if 'constraints' in node_prop_dict:
                constraints = node_prop_dict['constraints']
                for constraint in constraints:

                    if next(iter(constraint)) == 'greater_or_equal':
                        return constraint[next(iter(constraint))]
            return None

    # Resolve requirements. Go over all nodes and recursively resolve requirements till node has no requirements
    # e.g. docker -> k8s -> cluster -> vm
    def resolve_requirements(self):
        for node in self.template.nodetemplates:
            self.add_required_nodes(node)
        return self.required_nodes

    def add_required_nodes(self, node):
        node_type = tosca_util.get_node_type_name(node)
        missing_requirements = self.get_missing_requirements(node)
        if not missing_requirements:
            logging.debug('Node: ' + node_type + ' has no requirements')
            return
        matching_node = self.find_best_node(missing_requirements)

        # Only add node that is not in node_templates
        matching_node_type_name = next(iter(matching_node))
        # capable_node_template = self.node_type_2_node_template(matching_node)
        if not tosca_util.contains_node_type(self.required_nodes, matching_node_type_name):
            matching_node_type_name = next(iter(matching_node))
            node = self.add_missing_requirements(node, missing_requirements, matching_node_type_name)
            logging.info('Adding: ' + str(matching_node_type_name))
            self.required_nodes.append(matching_node)
        # Find matching nodes for the new node's requirements
        self.add_required_nodes(matching_node)

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
        node_type_name = tosca_util.get_node_type_name(node)
        node_requirements = tosca_util.get_node_requirements(node)
        parent_type_requirements = tosca_util.get_parent_type_requirements(node, self.all_nodes)

        logging.debug('Looking for requirements for node: ' + node_type_name)
        def_type = self.all_nodes[node_type_name]
        def_requirements = []

        if 'requirements' in def_type.keys():
            def_requirements = def_type['requirements']
            logging.debug('Found requirements: ' + str(def_requirements) + ' for node: ' + node_type_name)

        missing_requirements = []

        if not node_requirements:
            missing_requirements = def_requirements
        elif def_requirements:
            for def_requirement in def_requirements:
                for key in def_requirement:
                    for node_req in node_requirements:
                        if key not in node_req:
                            req_name = next(iter(def_requirement))
                            def_requirement[req_name]['node'] = None
                            missing_requirements.append(def_requirement)

        # Make sure we have the definition. Otherwise we get an error in the recursion
        # if 'derived_from' in def_type.keys() and not def_type['derived_from'] in custom_def.keys():
        #     node.custom_def[def_type['derived_from']] = custom_def['derived_from']
        parent_type = tosca_util.get_node_type_name(node)
        if parent_type and parent_type_requirements:
            logging.debug(
                ' Adding to : ' + str(node_type_name) + '  parent requirements from: ' + str(parent_type))
            missing_requirements = missing_requirements + parent_type_requirements
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
        nodes_type_names_with_interface = tosca_util.get_node_types_with_interface(candidate_nodes)
        for type_name in nodes_type_names_with_interface:
            capable_nodes[type_name] = candidate_nodes[type_name]
        return capable_nodes

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

    def add_missing_requirements(self, node, missing_requirements, capable_node_name):
        for req in missing_requirements:
            req[next(iter(req))]['node'] = capable_node_name
            if isinstance(node, NodeTemplate):
                contains_requirement = False
                for node_requirement in node.requirements:
                    if node_requirement == req:
                        contains_requirement = True
                        break
                if not contains_requirement:
                    node.requirements.append(req)
            elif isinstance(node, dict):
                type_name = next(iter(node))
                node_requirements = node[type_name]['requirements']
                contains_requirement = False
                for node_requirement in node_requirements:
                    if node_requirement == req:
                        contains_requirement = True
                        break
                if not contains_requirement:
                    node[type_name]['requirements'].append(req)
        return node
