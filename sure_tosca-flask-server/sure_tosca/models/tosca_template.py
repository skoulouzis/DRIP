# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from sure_tosca.models.base_model_ import Model
from sure_tosca import util
from sure_tosca.models.topology_template import TopologyTemplateModel


class ToscaTemplateModel(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, tosca_definitions_version=None, tosca_default_namespace=None, template_name=None, topology_template=None, template_author=None, template_version=None, description=None, imports=None, dsl_definitions=None, node_types=None, relationship_types=None, relationship_templates=None, capability_types=None, artifact_types=None, data_types=None, interface_types=None, policy_types=None, group_types=None, repositories=None):  # noqa: E501
        """ToscaTemplate - a model defined in Swagger

        :param tosca_definitions_version: The tosca_definitions_version of this ToscaTemplate.  # noqa: E501
        :type tosca_definitions_version: str
        :param tosca_default_namespace: The tosca_default_namespace of this ToscaTemplate.  # noqa: E501
        :type tosca_default_namespace: str
        :param template_name: The template_name of this ToscaTemplate.  # noqa: E501
        :type template_name: str
        :param topology_template: The topology_template of this ToscaTemplate.  # noqa: E501
        :type topology_template: TopologyTemplate
        :param template_author: The template_author of this ToscaTemplate.  # noqa: E501
        :type template_author: str
        :param template_version: The template_version of this ToscaTemplate.  # noqa: E501
        :type template_version: str
        :param description: The description of this ToscaTemplate.  # noqa: E501
        :type description: str
        :param imports: The imports of this ToscaTemplate.  # noqa: E501
        :type imports: List[Dict[str, object]]
        :param dsl_definitions: The dsl_definitions of this ToscaTemplate.  # noqa: E501
        :type dsl_definitions: Dict[str, object]
        :param node_types: The node_types of this ToscaTemplate.  # noqa: E501
        :type node_types: Dict[str, object]
        :param relationship_types: The relationship_types of this ToscaTemplate.  # noqa: E501
        :type relationship_types: Dict[str, object]
        :param relationship_templates: The relationship_templates of this ToscaTemplate.  # noqa: E501
        :type relationship_templates: Dict[str, object]
        :param capability_types: The capability_types of this ToscaTemplate.  # noqa: E501
        :type capability_types: Dict[str, object]
        :param artifact_types: The artifact_types of this ToscaTemplate.  # noqa: E501
        :type artifact_types: Dict[str, object]
        :param data_types: The data_types of this ToscaTemplate.  # noqa: E501
        :type data_types: Dict[str, object]
        :param interface_types: The interface_types of this ToscaTemplate.  # noqa: E501
        :type interface_types: Dict[str, object]
        :param policy_types: The policy_types of this ToscaTemplate.  # noqa: E501
        :type policy_types: Dict[str, object]
        :param group_types: The group_types of this ToscaTemplate.  # noqa: E501
        :type group_types: Dict[str, object]
        :param repositories: The repositories of this ToscaTemplate.  # noqa: E501
        :type repositories: Dict[str, object]
        """
        self.swagger_types = {
            'tosca_definitions_version': str,
            'tosca_default_namespace': str,
            'template_name': str,
            'topology_template': TopologyTemplateModel,
            'template_author': str,
            'template_version': str,
            'description': str,
            'imports': List[Dict[str, object]],
            'dsl_definitions': Dict[str, object],
            'node_types': Dict[str, object],
            'relationship_types': Dict[str, object],
            'relationship_templates': Dict[str, object],
            'capability_types': Dict[str, object],
            'artifact_types': Dict[str, object],
            'data_types': Dict[str, object],
            'interface_types': Dict[str, object],
            'policy_types': Dict[str, object],
            'group_types': Dict[str, object],
            'repositories': Dict[str, object]
        }

        self.attribute_map = {
            'tosca_definitions_version': 'tosca_definitions_version',
            'tosca_default_namespace': 'tosca_default_namespace',
            'template_name': 'template_name',
            'topology_template': 'topology_template',
            'template_author': 'template_author',
            'template_version': 'template_version',
            'description': 'description',
            'imports': 'imports',
            'dsl_definitions': 'dsl_definitions',
            'node_types': 'node_types',
            'relationship_types': 'relationship_types',
            'relationship_templates': 'relationship_templates',
            'capability_types': 'capability_types',
            'artifact_types': 'artifact_types',
            'data_types': 'data_types',
            'interface_types': 'interface_types',
            'policy_types': 'policy_types',
            'group_types': 'group_types',
            'repositories': 'repositories'
        }

        self._tosca_definitions_version = tosca_definitions_version
        self._tosca_default_namespace = tosca_default_namespace
        self._template_name = template_name
        self._topology_template = topology_template
        self._template_author = template_author
        self._template_version = template_version
        self._description = description
        self._imports = imports
        self._dsl_definitions = dsl_definitions
        self._node_types = node_types
        self._relationship_types = relationship_types
        self._relationship_templates = relationship_templates
        self._capability_types = capability_types
        self._artifact_types = artifact_types
        self._data_types = data_types
        self._interface_types = interface_types
        self._policy_types = policy_types
        self._group_types = group_types
        self._repositories = repositories

    @classmethod
    def from_dict(cls, dikt):
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The ToscaTemplate of this ToscaTemplate.  # noqa: E501
        :rtype: ToscaTemplate
        """
        return util.deserialize_model(dikt, cls)

    @property
    def tosca_definitions_version(self):
        """Gets the tosca_definitions_version of this ToscaTemplate.


        :return: The tosca_definitions_version of this ToscaTemplate.
        :rtype: str
        """
        return self._tosca_definitions_version

    @tosca_definitions_version.setter
    def tosca_definitions_version(self, tosca_definitions_version):
        """Sets the tosca_definitions_version of this ToscaTemplate.


        :param tosca_definitions_version: The tosca_definitions_version of this ToscaTemplate.
        :type tosca_definitions_version: str
        """

        self._tosca_definitions_version = tosca_definitions_version

    @property
    def tosca_default_namespace(self):
        """Gets the tosca_default_namespace of this ToscaTemplate.


        :return: The tosca_default_namespace of this ToscaTemplate.
        :rtype: str
        """
        return self._tosca_default_namespace

    @tosca_default_namespace.setter
    def tosca_default_namespace(self, tosca_default_namespace):
        """Sets the tosca_default_namespace of this ToscaTemplate.


        :param tosca_default_namespace: The tosca_default_namespace of this ToscaTemplate.
        :type tosca_default_namespace: str
        """

        self._tosca_default_namespace = tosca_default_namespace

    @property
    def template_name(self):
        """Gets the template_name of this ToscaTemplate.


        :return: The template_name of this ToscaTemplate.
        :rtype: str
        """
        return self._template_name

    @template_name.setter
    def template_name(self, template_name):
        """Sets the template_name of this ToscaTemplate.


        :param template_name: The template_name of this ToscaTemplate.
        :type template_name: str
        """

        self._template_name = template_name

    @property
    def topology_template(self):
        """Gets the topology_template of this ToscaTemplate.


        :return: The topology_template of this ToscaTemplate.
        :rtype: TopologyTemplate
        """
        return self._topology_template

    @topology_template.setter
    def topology_template(self, topology_template):
        """Sets the topology_template of this ToscaTemplate.


        :param topology_template: The topology_template of this ToscaTemplate.
        :type topology_template: TopologyTemplate
        """

        self._topology_template = topology_template

    @property
    def template_author(self):
        """Gets the template_author of this ToscaTemplate.


        :return: The template_author of this ToscaTemplate.
        :rtype: str
        """
        return self._template_author

    @template_author.setter
    def template_author(self, template_author):
        """Sets the template_author of this ToscaTemplate.


        :param template_author: The template_author of this ToscaTemplate.
        :type template_author: str
        """

        self._template_author = template_author

    @property
    def template_version(self):
        """Gets the template_version of this ToscaTemplate.


        :return: The template_version of this ToscaTemplate.
        :rtype: str
        """
        return self._template_version

    @template_version.setter
    def template_version(self, template_version):
        """Sets the template_version of this ToscaTemplate.


        :param template_version: The template_version of this ToscaTemplate.
        :type template_version: str
        """

        self._template_version = template_version

    @property
    def description(self):
        """Gets the description of this ToscaTemplate.


        :return: The description of this ToscaTemplate.
        :rtype: str
        """
        return self._description

    @description.setter
    def description(self, description):
        """Sets the description of this ToscaTemplate.


        :param description: The description of this ToscaTemplate.
        :type description: str
        """

        self._description = description

    @property
    def imports(self):
        """Gets the imports of this ToscaTemplate.


        :return: The imports of this ToscaTemplate.
        :rtype: List[Dict[str, object]]
        """
        return self._imports

    @imports.setter
    def imports(self, imports):
        """Sets the imports of this ToscaTemplate.


        :param imports: The imports of this ToscaTemplate.
        :type imports: List[Dict[str, object]]
        """

        self._imports = imports

    @property
    def dsl_definitions(self):
        """Gets the dsl_definitions of this ToscaTemplate.


        :return: The dsl_definitions of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._dsl_definitions

    @dsl_definitions.setter
    def dsl_definitions(self, dsl_definitions):
        """Sets the dsl_definitions of this ToscaTemplate.


        :param dsl_definitions: The dsl_definitions of this ToscaTemplate.
        :type dsl_definitions: Dict[str, object]
        """

        self._dsl_definitions = dsl_definitions

    @property
    def node_types(self):
        """Gets the node_types of this ToscaTemplate.


        :return: The node_types of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._node_types

    @node_types.setter
    def node_types(self, node_types):
        """Sets the node_types of this ToscaTemplate.


        :param node_types: The node_types of this ToscaTemplate.
        :type node_types: Dict[str, object]
        """

        self._node_types = node_types

    @property
    def relationship_types(self):
        """Gets the relationship_types of this ToscaTemplate.


        :return: The relationship_types of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._relationship_types

    @relationship_types.setter
    def relationship_types(self, relationship_types):
        """Sets the relationship_types of this ToscaTemplate.


        :param relationship_types: The relationship_types of this ToscaTemplate.
        :type relationship_types: Dict[str, object]
        """

        self._relationship_types = relationship_types

    @property
    def relationship_templates(self):
        """Gets the relationship_templates of this ToscaTemplate.


        :return: The relationship_templates of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._relationship_templates

    @relationship_templates.setter
    def relationship_templates(self, relationship_templates):
        """Sets the relationship_templates of this ToscaTemplate.


        :param relationship_templates: The relationship_templates of this ToscaTemplate.
        :type relationship_templates: Dict[str, object]
        """

        self._relationship_templates = relationship_templates

    @property
    def capability_types(self):
        """Gets the capability_types of this ToscaTemplate.


        :return: The capability_types of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._capability_types

    @capability_types.setter
    def capability_types(self, capability_types):
        """Sets the capability_types of this ToscaTemplate.


        :param capability_types: The capability_types of this ToscaTemplate.
        :type capability_types: Dict[str, object]
        """

        self._capability_types = capability_types

    @property
    def artifact_types(self):
        """Gets the artifact_types of this ToscaTemplate.


        :return: The artifact_types of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._artifact_types

    @artifact_types.setter
    def artifact_types(self, artifact_types):
        """Sets the artifact_types of this ToscaTemplate.


        :param artifact_types: The artifact_types of this ToscaTemplate.
        :type artifact_types: Dict[str, object]
        """

        self._artifact_types = artifact_types

    @property
    def data_types(self):
        """Gets the data_types of this ToscaTemplate.


        :return: The data_types of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._data_types

    @data_types.setter
    def data_types(self, data_types):
        """Sets the data_types of this ToscaTemplate.


        :param data_types: The data_types of this ToscaTemplate.
        :type data_types: Dict[str, object]
        """

        self._data_types = data_types

    @property
    def interface_types(self):
        """Gets the interface_types of this ToscaTemplate.


        :return: The interface_types of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._interface_types

    @interface_types.setter
    def interface_types(self, interface_types):
        """Sets the interface_types of this ToscaTemplate.


        :param interface_types: The interface_types of this ToscaTemplate.
        :type interface_types: Dict[str, object]
        """

        self._interface_types = interface_types

    @property
    def policy_types(self):
        """Gets the policy_types of this ToscaTemplate.


        :return: The policy_types of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._policy_types

    @policy_types.setter
    def policy_types(self, policy_types):
        """Sets the policy_types of this ToscaTemplate.


        :param policy_types: The policy_types of this ToscaTemplate.
        :type policy_types: Dict[str, object]
        """

        self._policy_types = policy_types

    @property
    def group_types(self):
        """Gets the group_types of this ToscaTemplate.


        :return: The group_types of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._group_types

    @group_types.setter
    def group_types(self, group_types):
        """Sets the group_types of this ToscaTemplate.


        :param group_types: The group_types of this ToscaTemplate.
        :type group_types: Dict[str, object]
        """

        self._group_types = group_types

    @property
    def repositories(self):
        """Gets the repositories of this ToscaTemplate.


        :return: The repositories of this ToscaTemplate.
        :rtype: Dict[str, object]
        """
        return self._repositories

    @repositories.setter
    def repositories(self, repositories):
        """Sets the repositories of this ToscaTemplate.


        :param repositories: The repositories of this ToscaTemplate.
        :type repositories: Dict[str, object]
        """

        self._repositories = repositories
