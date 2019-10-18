import copy
from itertools import chain

from toscaparser.elements.nodetype import NodeType
from toscaparser.nodetemplate import NodeTemplate

from utils.TOSCA_parser import TOSCAParser
import yaml
import logging

node_type_key_names_to_remove = ['capabilities', 'derived_from']


def get_node_type_name(node):
    """Returns  the requirements for an input node as described in the template not in the node's definition """
    if isinstance(node, NodeTemplate):
        if node.type:
            if node.type and isinstance(node.type, str):
                node_type = node.type
            elif isinstance(node.type, NodeTemplate):
                node_type = node.type.type
        else:
            node_type = None
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
        if node.parent_type:
            parent_type = node.parent_type.type
        else:
            parent_type = None
    elif isinstance(node, dict):
        parent_type = node[next(iter(node))]['derived_from']
    return parent_type


def get_node_type_requirements(type_name, all_nodes):
    def_type = all_nodes[type_name]
    if 'requirements' in def_type.keys():
        return def_type['requirements']
    return None


def get_ancestors_requirements(node, all_nodes, all_custom_def, parent_requirements=None):
    """Recursively get all requirements all the way to the ROOT including the input node's"""
    if not parent_requirements:
        parent_requirements = []
    if isinstance(node, NodeTemplate):
        # If node has parent and parent has requirements add them
        if node.parent_type and node.parent_type.requirements:
            if isinstance(node.parent_type.requirements, dict):
                parent_requirements.append(node.parent_type.requirements)
            elif isinstance(node.parent_type.requirements, list):
                parent_requirements.extend(node.parent_type.requirements)
            # Make parent type to NodeTemplate to continue
            if node.parent_type.type:
                parent_template = node_type_2_node_template({'name': all_nodes[node.parent_type.type]}, all_custom_def)
            if parent_template:
                get_ancestors_requirements(parent_template, all_nodes, parent_requirements)
    elif isinstance(node, dict):
        node_type_name = get_node_type_name(node)
        node_template = node_type_2_node_template({'name': all_nodes[node_type_name]}, all_custom_def)
        get_ancestors_requirements(node_template, all_nodes, all_custom_def, parent_requirements)
    return parent_requirements


def get_node_types_with_interface(nodes):
    node_types_with_interface = []
    for node_name in nodes:
        if 'interfaces' in nodes[node_name].keys() and 'tosca.nodes.Root' != node_name:
            node_types_with_interface.append(node_name)
    return node_types_with_interface


def node_type_2_node_template(node_type, all_custom_def):
    node_template_dict = {}
    type_name = next(iter(node_type))
    node_type_array = type_name.split(".")
    name = node_type_array[len(node_type_array) - 1].lower()
    node_template_dict[name] = node_type[next(iter(node_type))].copy()
    node_template_dict[name]['type'] = type_name

    for name_to_remove in node_type_key_names_to_remove:
        if name_to_remove in node_template_dict[name]:
            node_template_dict[name].pop(name_to_remove)

    if 'type' in node_type[next(iter(node_type))]:
        node_type[next(iter(node_type))].pop('type')

    node_template = NodeTemplate(name, node_template_dict, node_type)
    # For some reason the tosca.nodes.ARTICONF.Orchestrator doesn't  have all definitions so we need to add them
    # manually. We get 'toscaparser.common.exception.InvalidTypeError: Type "tosca.nodes.ARTICONF.Orchestrator"
    # is not a valid type.'
    if len(node_template.custom_def) < len(all_custom_def):
        for def_key in all_custom_def:
            if isinstance(def_key, dict):
                node_template.custom_def.update(def_key)
            else:
                node_template.custom_def[def_key] = all_custom_def[def_key]

    return node_template


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


def get_node_by_type(node_type, all_nodes):
    return all_nodes[node_type]


def get_all_ancestors_types(child_node, all_nodes, all_custom_def, ancestors_types=None):
    if not ancestors_types:
        ancestors_types = [get_node_type_name(child_node)]
    parent_type = get_parent_type(child_node)
    if parent_type:
        ancestors_types.append(parent_type)
        parent_type = node_type_2_node_template({'name': all_nodes[parent_type]}, all_custom_def)
        get_all_ancestors_types(parent_type, all_nodes, all_custom_def,ancestors_types)
    return ancestors_types


def get_all_ancestors_properties(node, all_nodes, all_custom_def, ancestors_properties=None, ancestors_types=None):
    if not ancestors_properties:
        ancestors_properties = []
        ancestors_properties_names = []
        if node.get_properties_objects():
            ancestors_properties.extend(node.get_properties_objects())
    if not ancestors_types:
        ancestors_types = get_all_ancestors_types(node, all_nodes,all_custom_def)

    for ancestors_type in ancestors_types:
        ancestor = node_type_2_node_template({'name': all_nodes[ancestors_type]}, all_custom_def)
        if ancestor.get_properties_objects():
            for ancestor_prop in ancestor.get_properties_objects():
                if ancestor_prop.name not in ancestors_properties_names:
                    ancestors_properties_names.append(ancestor_prop.name)
                    ancestors_properties.append(ancestor_prop)

    return ancestors_properties
