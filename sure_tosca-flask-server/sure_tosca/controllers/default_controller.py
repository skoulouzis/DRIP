import connexion
import six

from sure_tosca.models.node_template import NodeTemplateModel  # noqa: E501
from sure_tosca.models.node_template_map import NodeTemplateMapModel  # noqa: E501
from sure_tosca.models.tosca_template import ToscaTemplateModel  # noqa: E501
from sure_tosca import util


def add_type_definition(definition_map):  # noqa: E501
    """

    s # noqa: E501

    :param definition_map: 
    :type definition_map: 

    :rtype: str
    """
    return 'do some magic!'


def get_node_templates(id, query=None):  # noqa: E501
    """get_node_templates

    returns nodes templates in topology # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param query: The the query
    :type query: str

    :rtype: List[NodeTemplateMap]
    """
    return 'do some magic!'


def get_tosca_template(id):  # noqa: E501
    """get_tosca_template

     # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str

    :rtype: ToscaTemplate
    """
    return 'do some magic!'


def get_type_definition(query=None):  # noqa: E501
    """get_type_definition

    returns types # noqa: E501

    :param query: the query to run on the DB
    :type query: str

    :rtype: List[NodeTemplate]
    """
    return 'do some magic!'


def upload_tosca_template(file):  # noqa: E501
    """upload a tosca template description file

    upload and validate a tosca template description file # noqa: E501

    :param file: tosca Template description
    :type file: werkzeug.datastructures.FileStorage

    :rtype: str
    """
    return 'do some magic!'
