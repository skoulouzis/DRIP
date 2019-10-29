import os
import uuid

import connexion
import six
import yaml
from tinydb.middlewares import CachingMiddleware

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
from operator import and_, or_
from functools import reduce
from tinydb import where

# db = TinyDB(storage=CachingMiddleware(MemoryStorage))
db_dir_path = tempfile.gettempdir()
tosca_templates_db_file_path = os.path.join(db_dir_path, "tosca_templates.json")
tosca_templates_db = TinyDB(tosca_templates_db_file_path, cache_size=30)

node_templates_db = TinyDB(storage=CachingMiddleware(MemoryStorage))

model_id_names = ['id']


def get_tosca_template_model_by_id(id):
    tosca_template_dict = get_tosca_template_dict_by_id(id)
    if tosca_template_dict:
        get_tosca_template_by_id(tosca_template_dict)
        return ToscaTemplateModel.from_dict(tosca_template_dict)
    return 'Not Found', 404


def get_tosca_template_by_id(tosca_template_dict):
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
    tosca_template_file_path = os.path.join(db_dir_path, file.filename)
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
    if interface_types:
        if interface_type:
            filtered_interface_types = []
            for interface in interface_types:
                type_name = next(iter(interface))
                if type_name == interface_type:
                    filtered_interface_types.append({type_name: interface})
            return filtered_interface_types
    return 'Not Found', 404


def get_node_templates(id, name=None, node_type=None, has_interface=None):
    node_templates_db.purge()
    node_templates = get_tosca_template_model_by_id(id).topology_template.node_templates
    if node_templates:
        for node_template_name in node_templates:
            node = {'name': node_template_name}
            node.update(node_templates[node_template_name].to_dict())
            node_templates_db.insert(node)

        queries = []
        if name:
            query = Query()
            queries.append(query.name == name)
        if node_type:
            query = Query()
            queries.append(query.type == node_type)
        if has_interface:
            query = Query()
            interface = None
            queries.append(query.interfaces != interface)

        query = reduce(lambda a, b: a & b, queries)
        query_results = node_templates_db.search(query)
        if not query_results:
            return 'Not Found', 404
        results = []
        for res in query_results:
            name = res.pop('name')
            node = {name: res}
            if has_interface and 'interfaces' in res and res['interfaces'] is not None:
                results.append(node)
            elif not has_interface:
                results.append(node)
        return results
    return 'Not Found', 404
