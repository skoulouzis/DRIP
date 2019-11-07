import logging
from toscaparser.nodetemplate import NodeTemplate
from toscaparser.tosca_template import ToscaTemplate
from toscaparser.topology_template import TopologyTemplate

import operator

from src.planner.simple_spec_alayzer import SimpleAnalyzer
from src.utils.tosca_helper import *
from src.planner.specification_analyzer import *
from src.utils import tosca_helper as tosca_util
import matplotlib.pyplot as plt


class Planner:

    def __init__(self, path, spec_service):
        self.path = path
        self.tosca_template = ToscaTemplate(path)
        self.tosca_node_types = self.tosca_template.nodetemplates[0].type_definition.TOSCA_DEF
        self.all_custom_def = self.tosca_template.nodetemplates[0].custom_def
        self.all_node_types = {}
        self.all_node_types.update(self.tosca_node_types.items())
        self.all_node_types.update(self.all_custom_def.items())
        self.required_nodes = []

        self.spec_service = spec_service

    def add_required_nodes_to_template(self, required_nodes):
        for req_node in required_nodes:
            node_template = tosca_util.node_type_2_node_template(req_node, self.all_custom_def)
            self.tosca_template.nodetemplates.append(node_template)
        return self.tosca_template

    def set_infrastructure_specifications(self):
        # Start bottom up and (node without requirements leaf) and find the root of the graph.
        # Get root performance, version requirements and set specs to required node
        specification_analyzer = SimpleAnalyzer(self.tosca_template)
        nodes_with_new_specifications = specification_analyzer.set_node_specifications()

        for new_spec_node in nodes_with_new_specifications:
            for index, node_in_temple in enumerate(self.tosca_template.nodetemplates):
                if new_spec_node.name == node_in_temple.name:
                    self.tosca_template.nodetemplates[index] = new_spec_node
                    break

        specification_analyzer = SimpleAnalyzer(self.tosca_template)
        nodes_with_new_relationship_occurrences = specification_analyzer.set_relationship_occurrences()

        added_node_names = []
        for new_spec_occurrences in nodes_with_new_relationship_occurrences:
            for index, node_in_temple in enumerate(self.tosca_template.nodetemplates):
                if new_spec_occurrences.name == node_in_temple.name:
                    added_node_names.append(new_spec_occurrences.name)
                    self.tosca_template.nodetemplates[index] = new_spec_occurrences
                    break

        for new_spec_occurrences in nodes_with_new_relationship_occurrences:
            if new_spec_occurrences.name not in added_node_names:
                self.tosca_template.nodetemplates.append(new_spec_occurrences)
        return self.tosca_template

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

    def resolve_requirements(self):
        """ Resolve requirements. Go over all nodes and recursively resolve requirements till node has no
        requirements  e.g. docker -> k8s -> cluster -> vm """
        for node in self.tosca_template.nodetemplates:
            self.add_required_nodes(node)
        return self.add_required_nodes_to_template(self.required_nodes)

    def add_required_nodes(self, node):
        """Adds the required nodes in self.required_nodes for an input node."""
        if isinstance(node, NodeTemplate):
            logging.info('Resolving requirements for: ' + node.name)
        elif isinstance(node, dict):
            logging.info('Resolving requirements for: ' + str(next(iter(node))))
        # Get all requirements for node.
        all_requirements = self.get_all_requirements(node)
        if not all_requirements:
            logging.debug('Node: ' + tosca_util.get_node_type_name(node) + ' has no requirements')
            return
        matching_node = self.find_best_node_for_requirements(all_requirements)

        # Only add node that is not in node_templates
        matching_node_type_name = next(iter(matching_node))
        matching_node_template = tosca_util.node_type_2_node_template(matching_node, self.all_custom_def)

        # Add the requirements to the node we analyzed. e.g. docker needed host now we added the type and name of host
        node = self.add_requirements(node, all_requirements, matching_node_template.name)
        if not tosca_util.contains_node_type(self.required_nodes, matching_node_type_name):
            logging.info('  Adding: ' + str(matching_node_template.name))
            self.required_nodes.append(matching_node)
        # Find matching nodes for the new node's requirements
        self.add_required_nodes(matching_node)

    def get_all_requirements(self, node):
        """Returns  all requirements for an input node including all parents requirements"""

        node_type_name = tosca_util.get_node_type_name(node)
        logging.info('      Looking for requirements for node: ' + node_type_name)
        # Get the requirements for this node from its definition e.g. docker: hostedOn k8s
        def_type = self.all_node_types[node_type_name]
        all_requirements = []
        if 'requirements' in def_type.keys():
            all_requirements = def_type['requirements']
            logging.info('      Found requirements: ' + str(all_requirements) + ' for node: ' + node_type_name)

        # Get the requirements for this node from the template. e.g. wordpress: connectsTo mysql
        # node_requirements =  tosca_util.get_node_requirements(node)
        # if node_requirements:
        #     all_requirements += node_requirements

        # Put them all together
        parent_requirements = tosca_util.get_ancestors_requirements(node, self.all_node_types, self.all_custom_def)
        parent_type = tosca_util.get_node_type_name(node)
        if parent_type and parent_requirements:
            logging.info(
                '       Adding to : ' + str(node_type_name) + '  parent requirements from: ' + str(parent_type))
            if not all_requirements:
                all_requirements += parent_requirements
            else:
                for all_requirement in all_requirements:
                    for parent_requirement in parent_requirements:
                        all_requirement_key =  next(iter(all_requirement))
                        parent_requirement_key = next(iter(parent_requirement))
                        if all_requirement_key != parent_requirement_key and all_requirement[all_requirement_key]['capability'] != parent_requirement[parent_requirement_key]['capability']:
                            all_requirements.append(parent_requirement)

            logging.debug('      all_requirements: ' + str(all_requirements))
        return all_requirements

    def get_node_types_by_capability(self, cap):
        """Returns  all nodes that have the  capability: cap and have interfaces. This way we  distinguish between
        'abstract' and 'concrete' """
        candidate_nodes = {}
        for tosca_node_type in self.all_node_types:
            if tosca_node_type.startswith('tosca.nodes') and 'capabilities' in self.all_node_types[tosca_node_type]:
                logging.debug('      Node: ' + str(tosca_node_type))
                for caps in self.all_node_types[tosca_node_type]['capabilities']:
                    logging.debug('          ' + str(
                        self.all_node_types[tosca_node_type]['capabilities'][caps]['type']) + ' == ' + cap)
                    if self.all_node_types[tosca_node_type]['capabilities'][caps]['type'] == cap:
                        candidate_nodes[tosca_node_type] = self.all_node_types[tosca_node_type]
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

    def find_best_node_for_requirements(self, all_requirements):
        """Returns  the 'best' node for a set of requirements. Here we count the number of requiremets that the node
        can cover and return the one which covers the most """
        matching_nodes = {}
        number_of_matching_requirement = {}
        # Loop requirements to find nodes per requirement
        for req in all_requirements:
            if 'capability' in req[next(iter(req))]:
                capability = req[next(iter(req))]['capability']
                logging.info('  Looking for nodes with capability: ' + capability)
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
                        logging.info('      Found: ' + str(node_type))
                    matching_nodes.update(capable_nodes)
                else:
                    logging.error('Did not find any node with required capability: ' + str(capability))
                    raise Exception('Did not find any node with required capability: ' + str(capability))
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
        for tosca_node_type in self.all_node_types:
            if tosca_node_type.startswith('tosca.nodes') and 'derived_from' in self.all_node_types[tosca_node_type]:
                if parent_node_type_name == self.all_node_types[tosca_node_type]['derived_from']:
                    child_nodes[tosca_node_type] = self.all_node_types[tosca_node_type]
        return child_nodes

    def add_requirements(self, node, missing_requirements, capable_node_name):
        """Add the requirements to the node """
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
                if 'requirements' not in node[type_name]:
                    node[type_name]['requirements'] = []
                node_requirements = node[type_name]['requirements']
                contains_requirement = False
                for node_requirement in node_requirements:
                    if node_requirement == req:
                        contains_requirement = True
                        break
                if not contains_requirement:
                    node[type_name]['requirements'].append(req)
        return node
