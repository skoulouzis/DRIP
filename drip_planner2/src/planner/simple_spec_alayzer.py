from src.utils import tosca as tosca_util
from src.planner.specification_analyzer import SpecificationAnalyzer
import networkx as nx
import logging


class SimpleAnalyzer(SpecificationAnalyzer):

    def __init__(self, tosca_template, required_nodes):
        super(SimpleAnalyzer, self).__init__(tosca_template, required_nodes)

    def set_node_specifications(self):
        nodes_to_implement_policies = self.get_nodes_to_implement_policy()

        for node_name in nodes_to_implement_policies:
            policies = nodes_to_implement_policies[node_name]
            affected_node = self.set_specs(node_name, policies, self.nodes_in_template)

        return self.required_nodes

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
        if 'tosca.nodes.ARTICONF.Orchestrator' in ancestors_types:
            logging.info('Do Something')
            properties = tosca_util.get_all_ancestors_properties(affected_node, self.all_node_types,
                                                                 self.all_custom_def)
            for node_property in properties:
                property_dict = {}
                logging.info('property: ' + str(node_property.name) + ' val: ' + str(node_property.value))

        return affected_node

    def set_specs(self, node_name, policies, nodes_in_template):
        logging.info('node_name: ' + str(node_name) + ' will implement policies: ' + str(len(policies)))
        for node in nodes_in_template:
            if node.name == node_name:
                affected_node = node
                break

        logging.info('node: ' + str(affected_node.type) + ' will implement policies: ' + str(len(policies)))
        affected_node = self.set_node_properties_for_policy(affected_node, policies)

        return affected_node
