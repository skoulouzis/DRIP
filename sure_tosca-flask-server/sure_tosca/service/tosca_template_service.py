import os
import sys
import uuid

import yaml
from tinydb.database import Document
from tinydb.middlewares import CachingMiddleware
from toscaparser.functions import GetAttribute
from typing import re

from sure_tosca.models.base_model_ import Model
from sure_tosca.models.node_template import NodeTemplate as NodeTemplateModel
from sure_tosca.models.topology_template import TopologyTemplate  as TopologyTemplateModel
from sure_tosca.models.tosca_template import ToscaTemplate  as ToscaTemplateModel
from sure_tosca import util
from tinydb import TinyDB, Query
import tempfile
from toscaparser.nodetemplate import NodeTemplate
from toscaparser.tosca_template import ToscaTemplate
from toscaparser.topology_template import TopologyTemplate
from toscaparser.parameters import Output
from tinydb.storages import MemoryStorage
from functools import reduce
from sure_tosca.service import tosca_helper
import logging

# db = TinyDB(storage=CachingMiddleware(MemoryStorage))
db_dir_path = tempfile.gettempdir()
tosca_templates_db_file_path = os.path.join(db_dir_path, "tosca_templates.json")
tosca_templates_db = TinyDB(tosca_templates_db_file_path)
# tosca_templates_db = TinyDB(storage=CachingMiddleware(MemoryStorage))

node_template_db = TinyDB(storage=CachingMiddleware(MemoryStorage))
dsl_definitions_db = TinyDB(storage=CachingMiddleware(MemoryStorage))
relationship_template_db = TinyDB(storage=CachingMiddleware(MemoryStorage))
interface_types_db = TinyDB(storage=CachingMiddleware(MemoryStorage))

model_id_names = ['id']
root_key = 'root_key'


def query_db(queries, db=None):
    if queries:
        query = reduce(lambda a, b: a & b, queries)
        results = db.search(query)
    else:
        results = db.all()
    if results:
        updated_results = []
        for res in results:
            if root_key in res:
                key = res.pop(root_key)
                node = {key: res}
            else:
                node = res
            updated_results.append(node)
        return updated_results
    return 'Not Found', 404


def get_tosca_template_model_by_id(id):
    tosca_template_dict = get_tosca_template_dict_by_id(id)
    if tosca_template_dict:
        get_tosca_template(tosca_template_dict)
        return ToscaTemplateModel.from_dict(tosca_template_dict)
    return 'Not Found', 404


def get_tosca_template(tosca_template_dict):
    return ToscaTemplate(yaml_dict_tpl=tosca_template_dict)


def get_tosca_template_dict_by_id(id):
    query = Query()
    tosca_template_list_dict = tosca_templates_db.search(query.id == id)
    if tosca_template_list_dict:
        tosca_template_dict = tosca_template_list_dict[0]
        for id_name in model_id_names:
            if id_name in tosca_template_dict:
                tosca_template_dict.pop(id_name)
                break
        return tosca_template_dict
    return 'Not Found', 404


def deTOSCAfy_topology_template(dictionary):
    # outputs out of nowhere is  instantiated  as GetAttribute
    if 'outputs' in dictionary['topology_template']:
        outputs = dictionary['topology_template']['outputs']
        for output_name in outputs:
            output = outputs[output_name]
            if isinstance(output['value'], GetAttribute):
                args = output['value'].args
                assert isinstance(args, list)
                output['value'] = {'get_attribute': args}
    return dictionary


def save(file):
    try:
        # tosca_template_file_path = os.path.join(db_dir_path, file.filename)
        dictionary = yaml.safe_load(file.stream)

        tosca_template = ToscaTemplate(yaml_dict_tpl=dictionary)

        dictionary = deTOSCAfy_topology_template(dictionary)

        tosca_template_model = ToscaTemplateModel.from_dict(dictionary)
        doc_id = str(uuid.uuid4())
        tosca_template_dict_with_id = {model_id_names[0]: doc_id}

        tosca_template_dict_with_id.update(tosca_template_model.to_dict())
        tosca_templates_db.insert(tosca_template_dict_with_id)
        # tosca_templates_db.close()
        return doc_id
    except Exception as e:
        logging.error(str(e))
        return str(e), 400


def get_interface_types(id, interface_type=None):
    if len(interface_types_db) <= 1:
        interface_types = get_tosca_template_model_by_id(id).interface_types
        if interface_types:
            for interface_type_name in interface_types:
                interface = {root_key: interface_type_name}
                interface.update(interface_types[interface_type_name])
    queries = []
    if interface_type:
        query = Query()
        queries.append(query._node_nme == interface_type)

    return query_db(queries, db=interface_types_db)


def get_node_templates(id, type_name=None, node_name=None, has_interfaces=None, has_properties=None,
                       has_attributes=None,
                       has_requirements=None, has_capabilities=None, has_artifacts=None, derived_from=None):
    # tmp_db.purge()

    if len(node_template_db) <= 1:
        tosca_template_model = get_tosca_template_model_by_id(id)
        object_list = tosca_template_model.topology_template.node_templates
        # tosca_template = get_tosca_template(tosca_template_model.to_dict())
        # tosca_node_types = tosca_template.nodetemplates[0].type_definition.TOSCA_DEF
        # all_custom_def = tosca_template.nodetemplates[0].custom_def
        # object_list.update(tosca_node_types)
        # object_list.update(all_custom_def)
        if object_list:
            for key in object_list:
                node = {root_key: key}
                if isinstance(object_list[key], dict):
                    node.update(object_list[key])
                elif isinstance(object_list[key], Model):
                    node.update(object_list[key].to_dict())
                node_template_db.insert(node)

    queries = []
    if node_name:
        if isinstance(node_name, list):
            for n in node_name:
                query = Query()
                queries.append(query.root_key == n)
        elif isinstance(node_name, str):
            query = Query()
            queries.append(query.root_key == node_name)
    if type_name:
        query = Query()
        queries.append(query.type == type_name)
    if derived_from:
        query = Query()
        queries.append(query.derived_from == derived_from)
    if has_properties:
        query = Query()
        prop = None
        queries.append(query.properties != prop)
    if has_interfaces:
        query = Query()
        prop = None
        queries.append(query.interfaces != prop)
    if has_attributes:
        query = Query()
        prop = None
        queries.append(query.attributes != prop)
    if has_requirements:
        query = Query()
        prop = None
        queries.append(query.requirements != prop)
    if has_capabilities:
        query = Query()
        prop = None
        queries.append(query.capabilities != prop)
    if has_artifacts:
        query = Query()
        prop = None
        queries.append(query.artifacts != prop)

    query_results = query_db(queries, db=node_template_db)
    return query_results


def get_tosca_template_get_dsl_definitions(id, anchors, derived_from):
    # interface_types_db.purge()
    if len(dsl_definitions_db) <= 1:
        object_list = get_tosca_template_model_by_id(id).dsl_definitions
        if object_list:
            for key in object_list:
                node = {key: object_list[key]}
                interface_types_db.insert(node)

    queries = []
    if derived_from:
        query = Query()
        queries.append(query.derived_from == derived_from)
    if anchors:
        for anchor in anchors:
            query = Query()
            queries.append(query.anchor == anchor)

    return query_db(queries, db=dsl_definitions_db)


def get_relationship_templates(id, type_name=None, derived_from=None):
    if len(relationship_template_db) <= 1:
        object_list = get_tosca_template_model_by_id(id).relationship_templates
        if object_list:
            for key in object_list:
                node = {key: object_list[key]}
                relationship_template_db.insert(node)

    queries = []
    if derived_from:
        query = Query()
        queries.append(query.derived_from == derived_from)
    if type_name:
        query = Query()
        queries.append(query.type == type_name)

    return query_db(queries, db=relationship_template_db)


def node_dict_2_node_template(id, node_name):
    tosca_template_dict = get_tosca_template_dict_by_id(id)
    tosca_template = get_tosca_template(tosca_template_dict)
    tosca_node_types = tosca_template.nodetemplates[0].type_definition.TOSCA_DEF
    all_custom_def = tosca_template.nodetemplates[0].custom_def
    all_node_types = {}
    all_node_types.update(tosca_node_types.items())
    all_node_types.update(all_custom_def.items())

    node_template_dict = get_node_templates(id, node_name=node_name)[0]
    node_template_dict = {node_name: node_template_dict}
    if isinstance(node_template_dict, dict):
        the_node = tosca_helper.node_dict_2_node_template(node_template_dict, all_node_types)

        return the_node, all_node_types, all_custom_def
    return None


def get_all_ancestors_requirements(id, node_root_key):
    the_node, all_node_types, all_custom_def = node_dict_2_node_template(id, node_root_key)
    parent_requirements = tosca_helper.get_all_ancestors_requirements(the_node, all_node_types, all_custom_def)
    return parent_requirements


def get_all_ancestor_properties(id, node_root_key):
    the_node, all_node_types, all_custom_def = node_dict_2_node_template(id, node_root_key)
    properties = tosca_helper.get_all_ancestors_properties(the_node, all_node_types, all_custom_def)
    properties_list = []
    for prop in properties:
        prop_dict = {prop.name: prop.value}
        properties_list.append(prop_dict)
    return properties_list


def get_all_ancestor_types(id, node_root_key):
    try:
        the_node, all_node_types, all_custom_def = node_dict_2_node_template(id, node_root_key)
    except Exception as e:
        return 'Not Found', 404
    all_ancestor_types = tosca_helper.get_all_ancestors_types(the_node, all_node_types, all_custom_def)
    return all_ancestor_types


def get_parent_type_name(id, node_root_key):
    try:
        the_node, all_node_types, all_custom_def = node_dict_2_node_template(id, node_root_key)
    except Exception as e:
        return 'Not Found', 404
    return tosca_helper.get_parent_type(the_node)


def get_node_outputs(id, node_nme):
    matching_outputs = {}
    matching_output_names = []
    tosca_template_dict = get_tosca_template_dict_by_id(id)
    tosca_template = get_tosca_template(tosca_template_dict)
    outputs = tosca_template.topology_template.outputs
    for output in outputs:
        if node_nme == output.value.node_template_name:
            matching_output_names.append(output.name)

    tosca_template_model = ToscaTemplateModel.from_dict(deTOSCAfy_topology_template(tosca_template_dict))

    for matching_output_name in matching_output_names:
        matching_outputs[matching_output_name] = tosca_template_model.topology_template.outputs[matching_output_name]

    return matching_outputs


def get_node_properties(id, node_name):
    node_template_dict = get_node_templates(id, node_name=node_name)[0]
    if node_name in node_template_dict:
        node_template_dict = node_template_dict[node_name]

    properties = NodeTemplateModel.from_dict(node_template_dict).properties
    if properties:
        return properties
    return 'Not Found', 404


def get_node_requirements(id, node_name):
    node_template_dict = get_node_templates(id, node_name=node_name)[0]
    if node_name in node_template_dict:
        node_template_dict = node_template_dict[node_name]

    requirements = NodeTemplateModel.from_dict(node_template_dict).requirements
    if requirements:
        return requirements
    return 'Not Found', 404


def get_related_nodes(id, node_name):
    tosca_template_dict = get_tosca_template_dict_by_id(id)
    tosca_template = get_tosca_template(tosca_template_dict)
    related_node_names = []
    for node_template in tosca_template.topology_template.nodetemplates:
        if node_template.name == node_name:
            related_nodes = node_template.related_nodes
            for related_node in related_nodes:
                related_node_names.append(related_node.name)
    related_nodes = []
    for name in related_node_names:
        related_node = get_node_templates(id, node_name=name)
        related_nodes.append(related_node)

    return related_nodes


def get_node_type_name(id, node_name):
    node_template_dict = get_node_templates(id, node_name=node_name)[0]
    if node_name in node_template_dict:
        node_template_dict = node_template_dict[node_name]

    type_name = NodeTemplateModel.from_dict(node_template_dict).type
    if type_name:
        return type_name
    return 'Not Found', 404


def set_node_properties(id, properties, node_name):
    node_template_dict = get_node_templates(id, node_name=node_name)[0]
    if node_name in node_template_dict:
        node_template_dict = node_template_dict[node_name]
    return None