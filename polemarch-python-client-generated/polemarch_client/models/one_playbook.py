# coding: utf-8

"""
    Polemarch

     ### Polemarch is ansible based service for orchestration infrastructure.  * [Documentation](http://polemarch.readthedocs.io/) * [Issue Tracker](https://gitlab.com/vstconsulting/polemarch/issues) * [Source Code](https://gitlab.com/vstconsulting/polemarch)    # noqa: E501

    OpenAPI spec version: v2
    
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""


import pprint
import re  # noqa: F401

import six


class OnePlaybook(object):
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
        'id': 'int',
        'name': 'str',
        'playbook': 'str'
    }

    attribute_map = {
        'id': 'id',
        'name': 'name',
        'playbook': 'playbook'
    }

    def __init__(self, id=None, name=None, playbook=None):  # noqa: E501
        """OnePlaybook - a model defined in Swagger"""  # noqa: E501

        self._id = None
        self._name = None
        self._playbook = None
        self.discriminator = None

        if id is not None:
            self.id = id
        if name is not None:
            self.name = name
        if playbook is not None:
            self.playbook = playbook

    @property
    def id(self):
        """Gets the id of this OnePlaybook.  # noqa: E501


        :return: The id of this OnePlaybook.  # noqa: E501
        :rtype: int
        """
        return self._id

    @id.setter
    def id(self, id):
        """Sets the id of this OnePlaybook.


        :param id: The id of this OnePlaybook.  # noqa: E501
        :type: int
        """

        self._id = id

    @property
    def name(self):
        """Gets the name of this OnePlaybook.  # noqa: E501


        :return: The name of this OnePlaybook.  # noqa: E501
        :rtype: str
        """
        return self._name

    @name.setter
    def name(self, name):
        """Sets the name of this OnePlaybook.


        :param name: The name of this OnePlaybook.  # noqa: E501
        :type: str
        """
        if name is not None and len(name) > 251:
            raise ValueError("Invalid value for `name`, length must be less than or equal to `251`")  # noqa: E501
        if name is not None and len(name) < 1:
            raise ValueError("Invalid value for `name`, length must be greater than or equal to `1`")  # noqa: E501

        self._name = name

    @property
    def playbook(self):
        """Gets the playbook of this OnePlaybook.  # noqa: E501


        :return: The playbook of this OnePlaybook.  # noqa: E501
        :rtype: str
        """
        return self._playbook

    @playbook.setter
    def playbook(self, playbook):
        """Sets the playbook of this OnePlaybook.


        :param playbook: The playbook of this OnePlaybook.  # noqa: E501
        :type: str
        """
        if playbook is not None and len(playbook) < 1:
            raise ValueError("Invalid value for `playbook`, length must be greater than or equal to `1`")  # noqa: E501

        self._playbook = playbook

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
        if issubclass(OnePlaybook, dict):
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
        if not isinstance(other, OnePlaybook):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
