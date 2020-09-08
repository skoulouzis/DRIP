# coding: utf-8

"""
    tosca-sure

    TOSCA Simple qUeRy sErvice (SURE).   # noqa: E501

    OpenAPI spec version: 1.0.0
    Contact: S.Koulouzis@uva.nl
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""


import pprint
import re  # noqa: F401

import six


class TopologyTemplate(object):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    """
    Attributes:
      swagger_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value is json key in definition.
    """
    swagger_types = {
        'description': 'str',
        'inputs': 'dict(str, str)',
        'node_templates': 'dict(str, NodeTemplate)',
        'relationship_templates': 'dict(str, object)',
        'outputs': 'dict(str, object)',
        'groups': 'dict(str, object)',
        'substitution_mappings': 'dict(str, object)',
        'policies': 'list[dict(str, object)]'
    }

    attribute_map = {
        'description': 'description',
        'inputs': 'inputs',
        'node_templates': 'node_templates',
        'relationship_templates': 'relationship_templates',
        'outputs': 'outputs',
        'groups': 'groups',
        'substitution_mappings': 'substitution_mappings',
        'policies': 'policies'
    }

    def __init__(self, description=None, inputs=None, node_templates=None, relationship_templates=None, outputs=None, groups=None, substitution_mappings=None, policies=None):  # noqa: E501
        """TopologyTemplate - a model defined in Swagger"""  # noqa: E501

        self._description = None
        self._inputs = None
        self._node_templates = None
        self._relationship_templates = None
        self._outputs = None
        self._groups = None
        self._substitution_mappings = None
        self._policies = None
        self.discriminator = None

        if description is not None:
            self.description = description
        if inputs is not None:
            self.inputs = inputs
        if node_templates is not None:
            self.node_templates = node_templates
        if relationship_templates is not None:
            self.relationship_templates = relationship_templates
        if outputs is not None:
            self.outputs = outputs
        if groups is not None:
            self.groups = groups
        if substitution_mappings is not None:
            self.substitution_mappings = substitution_mappings
        if policies is not None:
            self.policies = policies

    @property
    def description(self):
        """Gets the description of this TopologyTemplate.  # noqa: E501


        :return: The description of this TopologyTemplate.  # noqa: E501
        :rtype: str
        """
        return self._description

    @description.setter
    def description(self, description):
        """Sets the description of this TopologyTemplate.


        :param description: The description of this TopologyTemplate.  # noqa: E501
        :type: str
        """

        self._description = description

    @property
    def inputs(self):
        """Gets the inputs of this TopologyTemplate.  # noqa: E501


        :return: The inputs of this TopologyTemplate.  # noqa: E501
        :rtype: dict(str, str)
        """
        return self._inputs

    @inputs.setter
    def inputs(self, inputs):
        """Sets the inputs of this TopologyTemplate.


        :param inputs: The inputs of this TopologyTemplate.  # noqa: E501
        :type: dict(str, str)
        """

        self._inputs = inputs

    @property
    def node_templates(self):
        """Gets the node_templates of this TopologyTemplate.  # noqa: E501


        :return: The node_templates of this TopologyTemplate.  # noqa: E501
        :rtype: dict(str, NodeTemplate)
        """
        return self._node_templates

    @node_templates.setter
    def node_templates(self, node_templates):
        """Sets the node_templates of this TopologyTemplate.


        :param node_templates: The node_templates of this TopologyTemplate.  # noqa: E501
        :type: dict(str, NodeTemplate)
        """

        self._node_templates = node_templates

    @property
    def relationship_templates(self):
        """Gets the relationship_templates of this TopologyTemplate.  # noqa: E501


        :return: The relationship_templates of this TopologyTemplate.  # noqa: E501
        :rtype: dict(str, object)
        """
        return self._relationship_templates

    @relationship_templates.setter
    def relationship_templates(self, relationship_templates):
        """Sets the relationship_templates of this TopologyTemplate.


        :param relationship_templates: The relationship_templates of this TopologyTemplate.  # noqa: E501
        :type: dict(str, object)
        """

        self._relationship_templates = relationship_templates

    @property
    def outputs(self):
        """Gets the outputs of this TopologyTemplate.  # noqa: E501


        :return: The outputs of this TopologyTemplate.  # noqa: E501
        :rtype: dict(str, object)
        """
        return self._outputs

    @outputs.setter
    def outputs(self, outputs):
        """Sets the outputs of this TopologyTemplate.


        :param outputs: The outputs of this TopologyTemplate.  # noqa: E501
        :type: dict(str, object)
        """

        self._outputs = outputs

    @property
    def groups(self):
        """Gets the groups of this TopologyTemplate.  # noqa: E501


        :return: The groups of this TopologyTemplate.  # noqa: E501
        :rtype: dict(str, object)
        """
        return self._groups

    @groups.setter
    def groups(self, groups):
        """Sets the groups of this TopologyTemplate.


        :param groups: The groups of this TopologyTemplate.  # noqa: E501
        :type: dict(str, object)
        """

        self._groups = groups

    @property
    def substitution_mappings(self):
        """Gets the substitution_mappings of this TopologyTemplate.  # noqa: E501


        :return: The substitution_mappings of this TopologyTemplate.  # noqa: E501
        :rtype: dict(str, object)
        """
        return self._substitution_mappings

    @substitution_mappings.setter
    def substitution_mappings(self, substitution_mappings):
        """Sets the substitution_mappings of this TopologyTemplate.


        :param substitution_mappings: The substitution_mappings of this TopologyTemplate.  # noqa: E501
        :type: dict(str, object)
        """

        self._substitution_mappings = substitution_mappings

    @property
    def policies(self):
        """Gets the policies of this TopologyTemplate.  # noqa: E501


        :return: The policies of this TopologyTemplate.  # noqa: E501
        :rtype: list[dict(str, object)]
        """
        return self._policies

    @policies.setter
    def policies(self, policies):
        """Sets the policies of this TopologyTemplate.


        :param policies: The policies of this TopologyTemplate.  # noqa: E501
        :type: list[dict(str, object)]
        """

        self._policies = policies

    def to_dict(self):
        """Returns the model properties as a dict"""
        result = {}

        for attr, _ in six.iteritems(self.swagger_types):
            value = getattr(self, attr)
            if isinstance(value, list):
                result[attr] = list(map(
                    lambda x: x.to_dict() if hasattr(x, "to_dict") else x,
                    value
                ))
            elif hasattr(value, "to_dict"):
                result[attr] = value.to_dict()
            elif isinstance(value, dict):
                result[attr] = dict(map(
                    lambda item: (item[0], item[1].to_dict())
                    if hasattr(item[1], "to_dict") else item,
                    value.items()
                ))
            else:
                result[attr] = value
        if issubclass(TopologyTemplate, dict):
            for key, value in self.items():
                result[key] = value

        return result

    def to_str(self):
        """Returns the string representation of the model"""
        return pprint.pformat(self.to_dict())

    def __repr__(self):
        """For `print` and `pprint`"""
        return self.to_str()

    def __eq__(self, other):
        """Returns true if both objects are equal"""
        if not isinstance(other, TopologyTemplate):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
