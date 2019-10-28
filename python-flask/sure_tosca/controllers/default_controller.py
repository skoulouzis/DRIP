import connexion
import six

from sure_tosca.models.node_template import NodeTemplate  # noqa: E501
from sure_tosca.models.topology_template import TopologyTemplate  # noqa: E501
from sure_tosca.models.tosca_template import ToscaTemplate  # noqa: E501
from sure_tosca import util


def get_all_ancestor_properties(id, body=None, node_name=None):  # noqa: E501
    """

    Recursively get all requirements all the way to the ROOT including the input node&#39;s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param node_name: The node name
    :type node_name: str

    :rtype: List[Dict[str, object]]
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_all_ancestor_types(id, body=None, node_name=None):  # noqa: E501
    """

    Recursively get all requirements all the way to the ROOT including the input node&#39;s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param node_name: The node name
    :type node_name: str

    :rtype: List[str]
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_ancestors_requirements(id, body=None, node_name=None):  # noqa: E501
    """

    Recursively get all requirements all the way to the ROOT including the input node&#39;s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param node_name: The node name
    :type node_name: str

    :rtype: Dict[str, object]
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_interface_types(id, body=None, interface_type=None):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param interface_type: The interface type
    :type interface_type: str

    :rtype: List[Dict[str, object]]
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_node_outputs(id, body=None, node_name=None):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param node_name: The node name
    :type node_name: str

    :rtype: Dict[str, object]
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_node_properties(id, body=None, node_name=None):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param node_name: The node name
    :type node_name: str

    :rtype: Dict[str, object]
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_node_requirements(id, body=None, node_name=None):  # noqa: E501
    """get_node_requirements

    Returns  the requirements for an input node as described in the template not in the node&#39;s definition  # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param node_name: The node name
    :type node_name: str

    :rtype: Dict[str, object]
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_node_templates(id, node_name=None, node_type=None, has_interface=None):  # noqa: E501
    """get_node_templates

    returns nodes templates in topology # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: filter by node name
    :type node_name: str
    :param node_type: filter by node type
    :type node_type: str
    :param has_interface: filter if node has interface
    :type has_interface: bool

    :rtype: List[NodeTemplate]
    """
    return 'do some magic!'


def get_node_type_name(id, body=None, node_name=None):  # noqa: E501
    """

     # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param node_name: The node name
    :type node_name: str

    :rtype: str
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_parent_type_name(id, body=None, node_name=None):  # noqa: E501
    """

     # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param node_name: The node name
    :type node_name: str

    :rtype: str
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_related_node(id, body=None, node_name=None):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param body: 
    :type body: dict | bytes
    :param node_name: The node name
    :type node_name: str

    :rtype: List[NodeTemplate]
    """
    if connexion.request.is_json:
        body = NodeTemplate.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def get_topology_template(id):  # noqa: E501
    """get_topology_template

    r # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str

    :rtype: TopologyTemplate
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


def set_node_properties(id, properties, node_name):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param properties: 
    :type properties: 
    :param node_name: The node name
    :type node_name: str

    :rtype: Dict[str, object]
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
