from toscaparser.nodetemplate import NodeTemplate
from toscaparser.properties import Property

from src.utils import tosca as tosca_util
from src.planner.specification_analyzer import SpecificationAnalyzer
import networkx as nx
import logging


class SimpleAnalyzer(SpecificationAnalyzer):

    def __init__(self, tosca_template):
        super(SimpleAnalyzer, self).__init__(tosca_template)

    def set_relationship_occurrences(self):
        return None

    def set_node_specifications(self):
        nodes_to_implement_policies = self.get_nodes_to_implement_policy()
        affected_nodes = []
        for node_name in nodes_to_implement_policies:
            policies = nodes_to_implement_policies[node_name]
            affected_node = self.set_specs(node_name, policies, self.tosca_template.nodetemplates)
            if affected_node:
                affected_nodes.append(affected_node)

        return affected_nodes

    def get_nodes_to_implement_policy(self):
        nodes_to_implement_policies = {}

        for policy in self.tosca_template.policies:
            for target in policy.targets:
                for leaf in self.leaf_nodes:
                    logging.info('From: ' + target + '  to: ' + str(leaf))
                    for affected_node_name in (nx.shortest_path(self.g, source=target, target=leaf)):
                        if affected_node_name not in nodes_to_implement_policies:
                            policy_list = []
                            nodes_to_implement_policies[affected_node_name] = policy_list
                        policy_list = nodes_to_implement_policies[affected_node_name]
                        policy_list.append(policy.type)
                        nodes_to_implement_policies[affected_node_name] = policy_list
        return nodes_to_implement_policies

    def set_node_properties_for_policy(self, affected_node, policies):
        logging.info('Setting properties for: ' + str(affected_node.type))

        ancestors_types = tosca_util.get_all_ancestors_types(affected_node, self.all_node_types, self.all_custom_def)
        # if 'tosca.nodes.ARTICONF.Orchestrator' in ancestors_types:
        #     logging.info('Do Something')
        properties = tosca_util.get_all_ancestors_properties(affected_node, self.all_node_types,
                                                             self.all_custom_def)

        default_properties = {}
        for node_property in properties:
            default_property = self.get_defult_value(node_property)
            if default_property:
                default_properties[next(iter(default_property))] = default_property[next(iter(default_property))]

        if default_properties:
            for default_property in default_properties:
                affected_node.get_properties_objects().append(default_property)

            if 'properties' not in affected_node.templates[next(iter(affected_node.templates))]:
                affected_node.templates[next(iter(affected_node.templates))]['properties'] = default_properties
            else:
                for prop_name in affected_node.templates[next(iter(affected_node.templates))]['properties']:
                    if 'required' in affected_node.templates[next(iter(affected_node.templates))]['properties'][prop_name] and 'type' in affected_node.templates[next(iter(affected_node.templates))]['properties'][prop_name]:
                        # del affected_node.templates[next(iter(affected_node.templates))]['properties'][prop_name]
                        affected_node.templates[next(iter(affected_node.templates))]['properties'][prop_name] = None
                affected_node.templates[next(iter(affected_node.templates))]['properties'].update(default_properties)
            return affected_node
        else:
            return None

    def set_specs(self, node_name, policies, nodes_in_template):
        logging.info('node_name: ' + str(node_name) + ' will implement policies: ' + str(len(policies)))
        for node in nodes_in_template:
            if node.name == node_name:
                affected_node = node
                break

        logging.info('node: ' + str(affected_node.type) + ' will implement policies: ' + str(len(policies)))
        affected_node = self.set_node_properties_for_policy(affected_node, policies)

        return affected_node

    def get_defult_value(self, node_property):
        if isinstance(node_property.value, dict) and 'required' in node_property.value and 'type' in node_property.value:
            if node_property.value['required']:
                default_prop = {}
                if 'default' in node_property.value:
                    if node_property.value['type'] == 'integer':
                        default_prop = int(node_property.value['default'])
                    else:
                        default_prop = str(node_property.value['default'])
                elif 'constraints' in node_property.value:
                    constraints = node_property.value['constraints']
                    for constraint in constraints:
                        for constraint_key in constraint:
                            if 'equal' in constraint_key:
                                if node_property.value['type'] == 'integer':
                                    default_prop = int(constraint[constraint_key])
                                else:
                                    default_prop = str(constraint[constraint_key])
                name = node_property.name
                node_property = None
                node_property = {name: default_prop}
                return node_property
        return None
