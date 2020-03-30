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


class PeriodicTaskVariable(object):
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
        'key': 'str',
        'value': 'str'
    }

    attribute_map = {
        'id': 'id',
        'key': 'key',
        'value': 'value'
    }

    def __init__(self, id=None, key=None, value=''):  # noqa: E501
        """PeriodicTaskVariable - a model defined in Swagger"""  # noqa: E501

        self._id = None
        self._key = None
        self._value = None
        self.discriminator = None

        if id is not None:
            self.id = id
        self.key = key
        if value is not None:
            self.value = value

    @property
    def id(self):
        """Gets the id of this PeriodicTaskVariable.  # noqa: E501


        :return: The id of this PeriodicTaskVariable.  # noqa: E501
        :rtype: int
        """
        return self._id

    @id.setter
    def id(self, id):
        """Sets the id of this PeriodicTaskVariable.


        :param id: The id of this PeriodicTaskVariable.  # noqa: E501
        :type: int
        """

        self._id = id

    @property
    def key(self):
        """Gets the key of this PeriodicTaskVariable.  # noqa: E501


        :return: The key of this PeriodicTaskVariable.  # noqa: E501
        :rtype: str
        """
        return self._key

    @key.setter
    def key(self, key):
        """Sets the key of this PeriodicTaskVariable.


        :param key: The key of this PeriodicTaskVariable.  # noqa: E501
        :type: str
        """
        if key is None:
            raise ValueError("Invalid value for `key`, must not be `None`")  # noqa: E501
        if key is not None and len(key) > 512:
            raise ValueError("Invalid value for `key`, length must be less than or equal to `512`")  # noqa: E501
        if key is not None and len(key) < 1:
            raise ValueError("Invalid value for `key`, length must be greater than or equal to `1`")  # noqa: E501

        self._key = key

    @property
    def value(self):
        """Gets the value of this PeriodicTaskVariable.  # noqa: E501


        :return: The value of this PeriodicTaskVariable.  # noqa: E501
        :rtype: str
        """
        return self._value

    @value.setter
    def value(self, value):
        """Sets the value of this PeriodicTaskVariable.


        :param value: The value of this PeriodicTaskVariable.  # noqa: E501
        :type: str
        """

        self._value = value

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
        if issubclass(PeriodicTaskVariable, dict):
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
        if not isinstance(other, PeriodicTaskVariable):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
