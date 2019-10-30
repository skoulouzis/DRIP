import os
import uuid

import yaml
from tinydb.middlewares import CachingMiddleware
from typing import re

from sure_tosca.models.node_template import NodeTemplate as NodeTemplateModel
from sure_tosca.models.topology_template import TopologyTemplate  as TopologyTemplateModel
from sure_tosca.models.tosca_template import ToscaTemplate  as ToscaTemplateModel
from sure_tosca import util
from tinydb import TinyDB, Query
import tempfile
from toscaparser.nodetemplate import NodeTemplate
from toscaparser.tosca_template import ToscaTemplate
from toscaparser.topology_template import TopologyTemplate
from tinydb.storages import MemoryStorage
from functools import reduce
from sure_tosca.service import tosca_helper

# db = TinyDB(storage=CachingMiddleware(MemoryStorage))
db_dir_path = tempfile.gettempdir()
tosca_templates_db_file_path = os.path.join(db_dir_path, "tosca_templates.json")
tosca_templates_db = TinyDB(tosca_templates_db_file_path)
# tosca_templates_db = TinyDB(storage=CachingMiddleware(MemoryStorage))

tmp_db = TinyDB(storage=CachingMiddleware(MemoryStorage))
# interface_types_db = TinyDB(storage=CachingMiddleware(MemoryStorage))

model_id_names = ['id']
root_key = 'root_key'


def query_tmp_db(queries):
    if queries:
        query = reduce(lambda a, b: a & b, queries)
        results = tmp_db.search(query)
    else:
        results = tmp_db.all()
    if results:
        updated_results = []
        for res in results:
            key = res.pop(root_key)
            node = {key: res}
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


def save(file):
    # tosca_template_file_path = os.path.join(db_dir_path, file.filename)
    tosca_template_dict = yaml.safe_load(file.stream)
    tosca_template_model = ToscaTemplateModel.from_dict(tosca_template_dict)
    tosca_template = ToscaTemplate(yaml_dict_tpl=tosca_template_dict)

    doc_id = str(uuid.uuid4())
    tosca_template_dict_with_id = {model_id_names[0]: doc_id}

    tosca_template_dict_with_id.update(tosca_template_model.to_dict())
    tosca_templates_db.insert(tosca_template_dict_with_id)
    # tosca_templates_db.close()
    return doc_id


def get_interface_types(id, interface_type=None):
    interface_types = get_tosca_template_model_by_id(id).interface_types
    tmp_db.purge()
    if interface_types:
        for interface_type_name in interface_types:
            interface = {root_key: interface_type_name}
            interface.update(interface_types[interface_type_name])
    queries = []
    if interface_type:
        query = Query()
        queries.append(query._name_key == interface_type)

    return query_tmp_db(queries)


def get_node_templates(id, type_name=None, name_key=None, has_interfaces=None, has_properties=None, has_attributes=None,
                       has_requirements=None, has_capabilities=None, has_artifacts=None, derived_from=None):
    tmp_db.purge()
    object_list = get_tosca_template_model_by_id(id).topology_template.node_templates
    if object_list:
        for key in object_list:
            node = {root_key: key}
            node.update(object_list[key].to_dict())
            tmp_db.insert(node)

        queries = []
        if name_key:
            query = Query()
            queries.append(query.root_key == name_key)
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

    query_results = query_tmp_db(queries)
    return query_results


def get_tosca_template_get_dsl_definitions(id, anchors, derived_from):
    tmp_db.purge()
    object_list = get_tosca_template_model_by_id(id).dsl_definitions
    if object_list:
        for key in object_list:
            node = {key: object_list[key]}
            tmp_db.insert(node)

    queries = []
    if derived_from:
        query = Query()
        queries.append(query.derived_from == derived_from)
    if anchors:
        for anchor in anchors:
            query = Query()

    return query_tmp_db(queries)


def get_relationship_templates(id, type_name, derived_from):
    tmp_db.purge()
    object_list = get_tosca_template_model_by_id(id).relationship_templates
    if object_list:
        for key in object_list:
            node = {key: object_list[key]}
            tmp_db.insert(node)

    queries = []
    if derived_from:
        query = Query()
        queries.append(query.derived_from == derived_from)
    if type_name:
        query = Query()
        queries.append(query.type == type_name)

    return query_tmp_db(queries)


def find_node_template(id, node_root_key):
    tosca_template_dict = get_tosca_template_dict_by_id(id)
    tosca_template = get_tosca_template(tosca_template_dict)
    tosca_node_types = tosca_template.nodetemplates[0].type_definition.TOSCA_DEF
    all_custom_def = tosca_template.nodetemplates[0].custom_def
    all_node_types = {}
    all_node_types.update(tosca_node_types.items())
    all_node_types.update(all_custom_def.items())

    the_node = None
    for node in tosca_template.nodetemplates:
        if node.name == node_root_key:
            the_node = node
            break
    if not the_node:
        template = all_node_types[node_root_key]
        the_node = tosca_helper.node_type_2_node_template({node_root_key: template}, all_custom_def)
    return the_node, all_node_types, all_custom_def


def get_all_ancestors_requirements(id, node_root_key):
    the_node, all_node_types, all_custom_def = find_node_template(id, node_root_key)
    parent_requirements = tosca_helper.get_all_ancestors_requirements(the_node, all_node_types, all_custom_def)
    return parent_requirements


def get_all_ancestor_properties(id, node_root_key):
    the_node, all_node_types, all_custom_def = find_node_template(id, node_root_key)
    properties = tosca_helper.get_all_ancestors_properties(the_node, all_node_types, all_custom_def)
    properties_list = []
    for prop in properties:
        prop_dict = {prop.name: prop.value}
        properties_list.append(prop_dict)
    return properties_list


def get_all_ancestor_types(id, node_root_key):
    the_node, all_node_types, all_custom_def = find_node_template(id, node_root_key)
    all_ancestor_types = tosca_helper.get_all_ancestors_types(the_node, all_node_types, all_custom_def)
    return all_ancestor_types


def get_parent_type_name(id, node_root_key):
    the_node, all_node_types, all_custom_def = find_node_template(id, node_root_key)
    return tosca_helper.get_parent_type(the_node)


def get_node_outputs(id, name_key):
    outputs = get_tosca_template_model_by_id(id).topology_template.outputs
    return None