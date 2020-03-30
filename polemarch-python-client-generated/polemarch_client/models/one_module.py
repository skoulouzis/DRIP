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


class OneModule(object):
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
        'path': 'str',
        'name': 'str',
        'data': 'Data'
    }

    attribute_map = {
        'id': 'id',
        'path': 'path',
        'name': 'name',
        'data': 'data'
    }

    def __init__(self, id=None, path=None, name=None, data=None):  # noqa: E501
        """OneModule - a model defined in Swagger"""  # noqa: E501

        self._id = None
        self._path = None
        self._name = None
        self._data = None
        self.discriminator = None

        if id is not None:
            self.id = id
        self.path = path
        if name is not None:
            self.name = name
        self.data = data

    @property
    def id(self):
        """Gets the id of this OneModule.  # noqa: E501


        :return: The id of this OneModule.  # noqa: E501
        :rtype: int
        """
        return self._id

    @id.setter
    def id(self, id):
        """Sets the id of this OneModule.


        :param id: The id of this OneModule.  # noqa: E501
        :type: int
        """

        self._id = id

    @property
    def path(self):
        """Gets the path of this OneModule.  # noqa: E501


        :return: The path of this OneModule.  # noqa: E501
        :rtype: str
        """
        return self._path

    @path.setter
    def path(self, path):
        """Sets the path of this OneModule.


        :param path: The path of this OneModule.  # noqa: E501
        :type: str
        """
        if path is None:
            raise ValueError("Invalid value for `path`, must not be `None`")  # noqa: E501
        if path is not None and len(path) > 1024:
            raise ValueError("Invalid value for `path`, length must be less than or equal to `1024`")  # noqa: E501
        if path is not None and len(path) < 1:
            raise ValueError("Invalid value for `path`, length must be greater than or equal to `1`")  # noqa: E501

        self._path = path

    @property
    def name(self):
        """Gets the name of this OneModule.  # noqa: E501


        :return: The name of this OneModule.  # noqa: E501
        :rtype: str
        """
        return self._name

    @name.setter
    def name(self, name):
        """Sets the name of this OneModule.


        :param name: The name of this OneModule.  # noqa: E501
        :type: str
        """

        self._name = name

    @property
    def data(self):
        """Gets the data of this OneModule.  # noqa: E501


        :return: The data of this OneModule.  # noqa: E501
        :rtype: Data
        """
        return self._data

    @data.setter
    def data(self, data):
        """Sets the data of this OneModule.


        :param data: The data of this OneModule.  # noqa: E501
        :type: Data
        """
        if data is None:
            raise ValueError("Invalid value for `data`, must not be `None`")  # noqa: E501

        self._data = data

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
        if issubclass(OneModule, dict):
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
        if not isinstance(other, OneModule):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
