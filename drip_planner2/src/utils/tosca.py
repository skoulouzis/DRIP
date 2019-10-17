from toscaparser.nodetemplate import NodeTemplate

from utils.TOSCA_parser import TOSCAParser
import yaml
import logging

node_type_key_names_to_remove = ['capabilities', 'derived_from']


def get_node_type_name(node):
    if isinstance(node, NodeTemplate):
        node_type = node.type
    elif isinstance(node, dict):
        node_type = next(iter(node))
    return node_type


def get_node_requirements(node):
    if isinstance(node, NodeTemplate):
        node_requirements = node.requirements
    elif isinstance(node, dict):
        node_type_name = get_node_type_name(node)
        if 'requirements' not in node[node_type_name]:
            node[node_type_name]['requirements'] = {}
        node_requirements = node[node_type_name]['requirements']
    return node_requirements


def get_parent_type(node):
    if isinstance(node, NodeTemplate):
        parent_type = node.parent_type
    elif isinstance(node, dict):
        parent_type = node[next(iter(node))]['derived_from']
    return parent_type


def get_node_type_requirements(type_name, all_nodes):
    def_type = all_nodes[type_name]
    if 'requirements' in def_type.keys():
        return def_type['requirements']
    return None


def get_parent_type_requirements(node, all_nodes):
    if isinstance(node, NodeTemplate):

        if node.parent_type and node.parent_type.requirements:
            parent_type_requirements = node.parent_type.requirements
        else:
            parent_type_requirements = {}
    elif isinstance(node, dict):
        node_type_name = get_node_type_name(node)
        parent_type_requirements = get_node_type_requirements(node_type_name, all_nodes)
    return parent_type_requirements


def get_node_types_with_interface(nodes):
    node_types_with_interface = []
    for node_name in nodes:
        if 'interfaces' in nodes[node_name].keys() and 'tosca.nodes.Root' != node_name:
            node_types_with_interface.append(node_name)
    return node_types_with_interface


def node_type_2_node_template(node_type):
    nodetemplate_dict = {}
    type_name = next(iter(node_type))
    node_type_array = type_name.split(".")
    name = node_type_array[len(node_type_array) - 1].lower()
    nodetemplate_dict[name] = node_type[next(iter(node_type))].copy()
    nodetemplate_dict[name]['type'] = type_name

    for name_to_remove in node_type_key_names_to_remove:
        if name_to_remove in nodetemplate_dict[name]:
            nodetemplate_dict[name].pop(name_to_remove)

    if 'type' in node_type[next(iter(node_type))]:
        node_type[next(iter(node_type))].pop('type')
    return NodeTemplate(name, nodetemplate_dict, node_type)


def get_tosca_template_2_topology_template(template):
    tp = TOSCAParser()
    yaml_str = tp.tosca_template2_yaml(template)
    tosca_template_dict = yaml.load(yaml_str, Loader=yaml.FullLoader)
    tosca_template = tosca_template_dict['tosca_template']
    tosca_template_dict.pop('tosca_template')
    tosca_template_dict['topology_template'] = tosca_template

    if template.policies and 'policies' not in tosca_template_dict['topology_template']:
        policies_list = []
        for policy in template.policies:
            policies_list.append(policy.entity_tpl)
        tosca_template_dict['topology_template']['policies'] = policies_list
    return yaml.dump(tosca_template_dict)


def contains_node_type(node_types_list, node_type_name):
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


def get_node_properties(node):
    node_type_name = get_node_type_name(node)
    return node[node_type_name]['properties']


def set_node_properties(node, properties):
    node_type_name = get_node_type_name(node)
    node[node_type_name]['properties'] = properties
    return node


def get_all_ancestors_types(child_node):
    logging.info('child_node: ' + str(child_node.type))
    parent_type = get_parent_type(child_node)
    logging.info('child_node.parent_type: ' + str(parent_type))
    return None
