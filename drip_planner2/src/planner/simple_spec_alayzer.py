from src.utils import tosca as tosca_util
from src.planner.specification_analyzer import SpecificationAnalyzer
import networkx as nx
import logging


def set_node_properties_for_policy(affected_node, policy):
    ancestors_types = tosca_util.get_all_ancestors_types(affected_node)

    if affected_node.type == 'tosca.nodes.ARTICONF.Orchestrator':
        if policy.type == 'tosca.policies.ARTICONF.Performance.CPU':
            logging.info('Do placement')
    return affected_node


def set_specs(node_name, policy, nodes_in_template):
    for node in nodes_in_template:
        if node.name == node_name:
            affected_node = node
            break

    logging.info('node to implement policy: ' + str(affected_node.type))
    affected_node = set_node_properties_for_policy(affected_node, policy)

    return affected_node


class SimpleAnalyzer(SpecificationAnalyzer):

    def __init__(self, required_nodes, tosca_template):
        super(SimpleAnalyzer, self).__init__(required_nodes, tosca_template)

    def set_node_specifications(self):
        # nx.draw(g, with_labels=True)
        # plt.savefig("/tmp/graph.png")
        # plt.show()
        nodes_to_implement_policy = self.get_nodes_to_implement_policy()

        for node_name in nodes_to_implement_policy:
            policy = nodes_to_implement_policy[node_name]
            affected_node = set_specs(node_name, policy, self.nodes_in_template)

        return self.required_nodes

    def get_nodes_to_implement_policy(self):
        nodes_to_implement_policy = {}
        for policy in self.tosca_template.policies:
            for target in policy.targets:
                for leaf in self.leaf_nodes:
                    for affected_node_name in (nx.shortest_path(self.g, source=target, target=leaf)):
                        if affected_node_name not in nodes_to_implement_policy:
                            policy_list = []
                            nodes_to_implement_policy[affected_node_name] = policy_list
                        policy_list = nodes_to_implement_policy[affected_node_name]
                        policy_list.append(policy)
                        nodes_to_implement_policy[affected_node_name] = policy_list
        return nodes_to_implement_policy

    def build_graph(self, node_templates):
        graph = nx.DiGraph()
        for node in node_templates:
            graph.add_node(node.name, attr_dict=node.entity_tpl)
            for req in node.requirements:
                req_name = next(iter(req))
                req_node_name = req[req_name]['node']
                if 'type' in req[req_name]['relationship']:
                    relationship_type = req[req_name]['relationship']['type']
                else:
                    relationship_type = req[req_name]['relationship']
                graph.add_edge(node.name, req_node_name, relationship=relationship_type)
        return graph
