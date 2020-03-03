import json
import logging
import os
import tempfile
import time
import uuid
from builtins import print
from functools import reduce
import copy

import yaml
from tinydb import TinyDB, Query
from tinydb.middlewares import CachingMiddleware
from tinydb.storages import MemoryStorage
from toscaparser.functions import GetAttribute
from toscaparser.tosca_template import ToscaTemplate

from sure_tosca.models.base_model_ import Model
from sure_tosca.models.node_template import NodeTemplateModel as NodeTemplateModel
from sure_tosca.models.node_template_map import NodeTemplateMapModel
from sure_tosca.models.tosca_template import ToscaTemplateModel  as ToscaTemplateModel

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
h = logging.StreamHandler()
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
h.setFormatter(formatter)
logger.addHandler(h)
logger.handler_set = True

root_key = 'root_key'


def get_tosca_template_model_by_id(id):
    tosca_template_dict = get_tosca_template_dict_by_id(id)
    tosca_template_model = ToscaTemplateModel.from_dict(tosca_template_dict)
    # tosca_template_dict = deTOSCAfy_topology_template(tosca_template_dict)
    if tosca_template_dict:
        get_tosca_template(tosca_template_dict)
        return tosca_template_model
    return None


def get_tosca_template(tosca_template_dict):
    start = time.time()
    logger.info("Checking  ToscaTemplate validity")
    tt = ToscaTemplate(yaml_dict_tpl=copy.deepcopy(tosca_template_dict))
    end = time.time()
    elapsed = end - start
    logger.info("Time elapsed: " + str(elapsed))
    return tt


def get_tosca_template_dict_by_id(id):
    tosca_template_dict = tosca_templates_db.get(doc_id=int(id))
    return tosca_template_dict


def save(file):
    # try:
    # tosca_template_file_path = os.path.join(db_dir_path, file.filename)
    start = time.time()
    logger.info("Got request for tosca template")
    dictionary = yaml.safe_load(file.stream)
    # dictionary = yaml.load(file.stream)
    logger.info("tosca template: \n" + str(yaml.dump(dictionary)))
    # print(yaml.dump(dictionary))
    tosca_template = get_tosca_template(dictionary)
    # all_custom_def = tosca_template.nodetemplates[0].custom_def
    tosca_template_model = ToscaTemplateModel.from_dict(dictionary)
    doc_id = tosca_templates_db.insert(dictionary)
    # tosca_templates_db.close()
    logger.info("Returning doc_id: " + str(doc_id))
    return doc_id


def change_to_node_template_model(query_results):
    res = []
    for node_template in query_results:
        # copy.deepcopy()
        node_template_map = NodeTemplateMapModel()
        name = next(iter(node_template))
        node_template_map.name = name
        node_template = node_template[name]
        node_template_map.node_template = NodeTemplateModel.from_dict(node_template)
        res.append(node_template_map)
    return res


def get_node_templates(id, query=None):
    return change_to_node_template_model(query_results)


def node_dict_2_node_template(id, node_name):
    tosca_template_dict = get_tosca_template_dict_by_id(id)
    if tosca_template_dict is None:
        return None
    tosca_template = get_tosca_template(tosca_template_dict)
    tosca_node_types = tosca_template.nodetemplates[0].type_definition.TOSCA_DEF
    all_custom_def = tosca_template.nodetemplates[0].custom_def
    all_node_types = {}
    all_node_types.update(tosca_node_types.items())
    all_node_types.update(all_custom_def.items())

    node_template_map_dict = get_node_templates(id, node_name=node_name)[0]
    the_node = tosca_helper.node_dict_2_node_template(node_template_map_dict.name,
                                                      node_template_map_dict.node_template.to_dict(), all_node_types)
    return the_node, all_node_types, all_custom_def





def get_all_ancestor_properties(id, node_root_key):
    try:
        the_node, all_node_types, all_custom_def = node_dict_2_node_template(id, node_root_key)
    except Exception as e:
        return None
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
        return None
    all_ancestor_types = tosca_helper.get_all_ancestors_types(the_node, all_node_types, all_custom_def)
    return all_ancestor_types


def get_parent_type_name(id, node_root_key):
    try:
        the_node, all_node_types, all_custom_def = node_dict_2_node_template(id, node_root_key)
    except Exception as e:
        return None
    return tosca_helper.get_node_template_parent_type(the_node)


def get_node_outputs(id, node_name):
    matching_outputs = {}
    matching_output_names = []
    tosca_template_dict = get_tosca_template_dict_by_id(id)
    tosca_template = get_tosca_template(tosca_template_dict)
    outputs = tosca_template.topology_template.outputs
    if not outputs:
        return None
    for output in outputs:
        if node_name == output.value.node_template_name:
            matching_output_names.append(output.name)

    # tosca_template_dict = deTOSCAfy_topology_template(tosca_template_dict)
    tosca_template_model = ToscaTemplateModel.from_dict(tosca_template_dict)
    for matching_output_name in matching_output_names:
        matching_outputs[matching_output_name] = tosca_template_model.topology_template.outputs[matching_output_name]

    return matching_outputs


def get_node_properties(id, node_name):
    node_template_map = get_node_templates(id, node_name=node_name)[0]

    properties = node_template_map.node_template.properties
    if properties:
        return properties
    return None


def get_node_requirements(id, node_name):
    node_template_map = get_node_templates(id, node_name=node_name)[0]
    requirements = node_template_map.node_template.requirements
    if requirements:
        return requirements
    return None


def get_node_attributes(id, node_name):
    node_template_map = get_node_templates(id, node_name=node_name)[0]
    attributes = node_template_map.node_template.attributes
    if attributes:
        return attributes
    return None