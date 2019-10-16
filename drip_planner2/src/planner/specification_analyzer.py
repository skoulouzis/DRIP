from abc import abstractmethod, ABCMeta
from src.utils import tosca as tosca_util


class SpecificationAnalyzer(metaclass=ABCMeta):

    def __init__(self, required_nodes, tosca_template):
        self.tosca_template = tosca_template
        self.tosca_node_types = self.tosca_template.nodetemplates[0].type_definition.TOSCA_DEF
        self.all_custom_def = self.tosca_template.nodetemplates[0].custom_def
        self.all_node_types = {}
        self.all_node_types.update(self.tosca_node_types.items())
        self.all_node_types.update(self.all_custom_def.items())

        self.required_nodes = required_nodes
        self.nodes_in_template = []
        for req_node in required_nodes:
            node_template = tosca_util.node_type_2_node_template(req_node)
            self.nodes_in_template.append(node_template)
        self.nodes_in_template += tosca_template.nodetemplates
        self.g = self.build_graph(self.nodes_in_template)

        self.root_nodes = []
        self.leaf_nodes = []
        for node_name, degree in self.g.in_degree():
            if degree == 0:
                self.root_nodes.append(node_name)
        for node_name, degree in self.g.out_degree():
            if degree == 0:
                self.leaf_nodes.append(node_name)

    @abstractmethod
    def set_node_specifications(self):
        raise NotImplementedError('Must implement upload in subclasses')
