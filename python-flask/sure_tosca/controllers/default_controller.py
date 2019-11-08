import connexion
import six

from sure_tosca.models.node_template import NodeTemplate  # noqa: E501
from sure_tosca.models.topology_template import TopologyTemplate  # noqa: E501
from sure_tosca.models.tosca_template import ToscaTemplate  # noqa: E501
from sure_tosca import util


def get_all_ancestor_properties(id, node_name):  # noqa: E501
    """

    Recursively get all requirements all the way to the ROOT including the input node&#39;s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: node_name
    :type node_name: str

    :rtype: List[Dict[str, object]]
    """
    return 'do some magic!'


def get_all_ancestor_types(id, node_name):  # noqa: E501
    """

    Recursively get all requirements all the way to the ROOT including the input node&#39;s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: node_name
    :type node_name: str

    :rtype: List[str]
    """
    return 'do some magic!'


def get_ancestors_requirements(id, node_name):  # noqa: E501
    """

    Recursively get all requirements all the way to the ROOT including the input node&#39;s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: node_name
    :type node_name: str

    :rtype: Dict[str, object]
    """
    return 'do some magic!'


def get_dsl_definitions(id, anchors=None, derived_from=None):  # noqa: E501
    """

    returns the interface types # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param anchors: the anchors the definition is for
    :type anchors: List[str]
    :param derived_from: derived from
    :type derived_from: str

    :rtype: List[Dict[str, object]]
    """
    return 'do some magic!'


def get_imports(id):  # noqa: E501
    """

    returns the interface types # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str

    :rtype: List[Dict[str, object]]
    """
    return 'do some magic!'


def get_node_outputs(id, node_name):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: node_name
    :type node_name: str

    :rtype: List[Dict[str, object]]
    """
    return 'do some magic!'


def get_node_properties(id, node_name):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: node_name
    :type node_name: str

    :rtype: Dict[str, object]
    """
    return 'do some magic!'


def get_node_requirements(id, node_name):  # noqa: E501
    """get_node_requirements

    Returns  the requirements for an input node as described in the template not in the node&#39;s definition  # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: node_name
    :type node_name: str

    :rtype: Dict[str, object]
    """
    return 'do some magic!'


def get_node_templates(id, type_name=None, node_name=None, has_interfaces=None, has_properties=None, has_attributes=None, has_requirements=None, has_capabilities=None, has_artifacts=None):  # noqa: E501
    """get_node_templates

    returns nodes templates in topology # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param type_name: The type
    :type type_name: str
    :param node_name: the name
    :type node_name: str
    :param has_interfaces: filter if has interfaces
    :type has_interfaces: bool
    :param has_properties: filter if has properties
    :type has_properties: bool
    :param has_attributes: filter if has attributes
    :type has_attributes: bool
    :param has_requirements: filter if has requirements
    :type has_requirements: bool
    :param has_capabilities: filter if has capabilities
    :type has_capabilities: bool
    :param has_artifacts: filter if has artifacts
    :type has_artifacts: bool

    :rtype: List[NodeTemplate]
    """
    return 'do some magic!'


def get_node_type_name(id, node_name):  # noqa: E501
    """

     # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: node_name
    :type node_name: str

    :rtype: str
    """
    return 'do some magic!'


def get_parent_type_name(id, node_name):  # noqa: E501
    """

     # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: node_name
    :type node_name: str

    :rtype: str
    """
    return 'do some magic!'


def get_related_nodes(id, node_name):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_name: node_name
    :type node_name: str

    :rtype: List[NodeTemplate]
    """
    return 'do some magic!'


def get_relationship_templates(id, type_name=None, derived_from=None):  # noqa: E501
    """

    returns the interface types # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param type_name: The relationship type
    :type type_name: str
    :param derived_from: derived from
    :type derived_from: str

    :rtype: List[Dict[str, object]]
    """
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


def get_types(id, kind_of_type=None, has_interfaces=None, type_name=None, has_properties=None, has_attributes=None, has_requirements=None, has_capabilities=None, has_artifacts=None, derived_from=None):  # noqa: E501
    """

    returns the interface types # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param kind_of_type: the type we are looking for e.g. capability_types, artifact_types. etc.
    :type kind_of_type: str
    :param has_interfaces: filter if has interfaces
    :type has_interfaces: bool
    :param type_name: The type_name
    :type type_name: str
    :param has_properties: filter if has properties
    :type has_properties: bool
    :param has_attributes: filter if has attributes
    :type has_attributes: bool
    :param has_requirements: filter if has requirements
    :type has_requirements: bool
    :param has_capabilities: filter if has capabilities
    :type has_capabilities: bool
    :param has_artifacts: filter if has artifacts
    :type has_artifacts: bool
    :param derived_from: derived from
    :type derived_from: str

    :rtype: List[Dict[str, object]]
    """
    return 'do some magic!'


def set_node_properties(id, properties, node_name):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param properties: 
    :type properties: 
    :param node_name: node_name
    :type node_name: str

    :rtype: str
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
