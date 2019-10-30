import connexion
import six

from sure_tosca.models.node_template import NodeTemplate  # noqa: E501
from sure_tosca.models.topology_template import TopologyTemplate  # noqa: E501
from sure_tosca.models.tosca_template import ToscaTemplate  # noqa: E501
from sure_tosca import util
from sure_tosca.service import tosca_template_service


def get_all_ancestor_properties(id, node_root_key):  # noqa: E501
    """

    Recursively get all requirements all the way to the ROOT including the input node&#39;s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: List[Dict[str, object]]
    """
    return tosca_template_service.get_all_ancestor_properties(id, node_root_key)


def get_all_ancestor_types(id, node_root_key):  # noqa: E501
    """

    Recursively get all requirements all the way to the ROOT including the input node&#39;s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: List[str]
    """
    return tosca_template_service.get_all_ancestor_types(id, node_root_key)


def get_ancestors_requirements(id, node_root_key):  # noqa: E501
    """

    Recursively get all requirements all the way to the ROOT including the input node&#39;s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: Dict[str, object]
    """
    return tosca_template_service.get_all_ancestors_requirements(id, node_root_key)


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
    return tosca_template_service.get_tosca_template_model_by_id(id).dsl_definitions


def get_imports(id):  # noqa: E501
    """

    returns the interface types # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str

    :rtype: List[Dict[str, object]]
    """
    return tosca_template_service.get_tosca_template_model_by_id(id).imports


def get_node_outputs(id, node_root_key):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: Dict[str, object]
    """
    return tosca_template_service.get_node_outputs(id, name_key=node_root_key)


def get_node_properties(id, node_root_key):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: Dict[str, object]
    """
    return tosca_template_service.get_node_templates(id, name_key=node_root_key)[0].properties


def get_node_requirements(id, node_root_key):  # noqa: E501
    """get_node_requirements

    Returns  the requirements for an input node as described in the template not in the node&#39;s definition  # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: Dict[str, object]
    """
    return tosca_template_service.get_node_templates(id, name_key=node_root_key)[0].requirements


def get_node_templates(id, type_name=None, name_key=None, has_interfaces=None, has_properties=None, has_attributes=None,
                       has_requirements=None, has_capabilities=None, has_artifacts=None,
                       derived_from=None):  # noqa: E501
    """get_node_templates

    returns nodes templates in topology # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param type_name: The type
    :type type_name: str
    :param name_key: the name key
    :type name_key: str
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
    :param derived_from: derived from
    :type derived_from: str

    :rtype: List[NodeTemplate]
    """
    return tosca_template_service.get_node_templates(id, type_name=type_name, name_key=name_key,
                                                     has_interfaces=has_interfaces, has_properties=has_properties,
                                                     has_attributes=has_attributes, has_requirements=has_requirements,
                                                     has_capabilities=has_capabilities, has_artifacts=has_artifacts,
                                                     derived_from=derived_from)


def get_node_type_name(id, node_root_key):  # noqa: E501
    """

     # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: str
    """
    return tosca_template_service.get_node_type_name(id, node_root_key)


def get_parent_type_name(id, node_root_key):  # noqa: E501
    """

     # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: str
    """
    return tosca_template_service.get_parent_type_name(id, node_root_key)


def get_related_nodes(id, node_root_key):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: List[NodeTemplate]
    """
    return tosca_template_service.get_related_nodes(id, node_root_key)


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
    return tosca_template_service.get_relationship_templates(id, type_name=type_name, derived_from=derived_from)


def get_topology_template(id):  # noqa: E501
    """get_topology_template

    r # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str

    :rtype: TopologyTemplate
    """
    return tosca_template_service.get_tosca_template_dict_by_id(id)


def get_tosca_template(id):  # noqa: E501
    """get_tosca_template

     # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str

    :rtype: ToscaTemplate
    """
    return tosca_template_service.get_tosca_template_model_by_id(id)


def get_types(id, kind_of_type=None, has_interfaces=None, type_name=None, has_properties=None, has_attributes=None,
              has_requirements=None, has_capabilities=None, has_artifacts=None, derived_from=None):  # noqa: E501
    """

    returns the interface types # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param kind_of_type: the type we are looking for e.g. capability_types, artifact_types. etc.
    :type kind_of_type: str
    :param has_interfaces: filter if has interfaces
    :type has_interfaces: bool
    :param type_name: The relationship type
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
    return tosca_template_service.get_types(id, kind_of_type=kind_of_type, has_interfaces=has_interfaces,
                                            type_name=type_name, has_properties=has_properties,
                                            has_attributes=has_attributes, has_requirements=has_requirements,
                                            has_capabilities=has_capabilities, has_artifacts=has_artifacts,
                                            derived_from=derived_from)


def set_node_properties(id, properties, node_root_key):  # noqa: E501
    """

    s # noqa: E501

    :param id: ID of topolog template uplodaed
    :type id: str
    :param properties: 
    :type properties: 
    :param node_root_key: node_root_key
    :type node_root_key: str

    :rtype: Dict[str, object]
    """
    return tosca_template_service.set_node_properties(id, properties, node_root_key)


def upload_tosca_template(file):  # noqa: E501
    """upload a tosca template description file

    upload and validate a tosca template description file # noqa: E501

    :param file: tosca Template description
    :type file: werkzeug.datastructures.FileStorage

    :rtype: str
    """
    return tosca_template_service.save(file)
