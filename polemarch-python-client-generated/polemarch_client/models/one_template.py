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


class OneTemplate(object):
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
        'notes': 'str',
        'kind': 'str',
        'data': 'Data',
        'options': 'Data',
        'options_list': 'list[str]'
    }

    attribute_map = {
        'id': 'id',
        'name': 'name',
        'notes': 'notes',
        'kind': 'kind',
        'data': 'data',
        'options': 'options',
        'options_list': 'options_list'
    }

    def __init__(self, id=None, name=None, notes=None, kind='Task', data=None, options=None, options_list=None):  # noqa: E501
        """OneTemplate - a model defined in Swagger"""  # noqa: E501

        self._id = None
        self._name = None
        self._notes = None
        self._kind = None
        self._data = None
        self._options = None
        self._options_list = None
        self.discriminator = None

        if id is not None:
            self.id = id
        self.name = name
        if notes is not None:
            self.notes = notes
        if kind is not None:
            self.kind = kind
        self.data = data
        if options is not None:
            self.options = options
        if options_list is not None:
            self.options_list = options_list

    @property
    def id(self):
        """Gets the id of this OneTemplate.  # noqa: E501


        :return: The id of this OneTemplate.  # noqa: E501
        :rtype: int
        """
        return self._id

    @id.setter
    def id(self, id):
        """Sets the id of this OneTemplate.


        :param id: The id of this OneTemplate.  # noqa: E501
        :type: int
        """

        self._id = id

    @property
    def name(self):
        """Gets the name of this OneTemplate.  # noqa: E501


        :return: The name of this OneTemplate.  # noqa: E501
        :rtype: str
        """
        return self._name

    @name.setter
    def name(self, name):
        """Sets the name of this OneTemplate.


        :param name: The name of this OneTemplate.  # noqa: E501
        :type: str
        """
        if name is None:
            raise ValueError("Invalid value for `name`, must not be `None`")  # noqa: E501
        if name is not None and len(name) > 512:
            raise ValueError("Invalid value for `name`, length must be less than or equal to `512`")  # noqa: E501
        if name is not None and len(name) < 1:
            raise ValueError("Invalid value for `name`, length must be greater than or equal to `1`")  # noqa: E501

        self._name = name

    @property
    def notes(self):
        """Gets the notes of this OneTemplate.  # noqa: E501


        :return: The notes of this OneTemplate.  # noqa: E501
        :rtype: str
        """
        return self._notes

    @notes.setter
    def notes(self, notes):
        """Sets the notes of this OneTemplate.


        :param notes: The notes of this OneTemplate.  # noqa: E501
        :type: str
        """

        self._notes = notes

    @property
    def kind(self):
        """Gets the kind of this OneTemplate.  # noqa: E501


        :return: The kind of this OneTemplate.  # noqa: E501
        :rtype: str
        """
        return self._kind

    @kind.setter
    def kind(self, kind):
        """Sets the kind of this OneTemplate.


        :param kind: The kind of this OneTemplate.  # noqa: E501
        :type: str
        """
        allowed_values = ["Task", "Module"]  # noqa: E501
        if kind not in allowed_values:
            raise ValueError(
                "Invalid value for `kind` ({0}), must be one of {1}"  # noqa: E501
                .format(kind, allowed_values)
            )

        self._kind = kind

    @property
    def data(self):
        """Gets the data of this OneTemplate.  # noqa: E501


        :return: The data of this OneTemplate.  # noqa: E501
        :rtype: Data
        """
        return self._data

    @data.setter
    def data(self, data):
        """Sets the data of this OneTemplate.


        :param data: The data of this OneTemplate.  # noqa: E501
        :type: Data
        """
        if data is None:
            raise ValueError("Invalid value for `data`, must not be `None`")  # noqa: E501

        self._data = data

    @property
    def options(self):
        """Gets the options of this OneTemplate.  # noqa: E501


        :return: The options of this OneTemplate.  # noqa: E501
        :rtype: Data
        """
        return self._options

    @options.setter
    def options(self, options):
        """Sets the options of this OneTemplate.


        :param options: The options of this OneTemplate.  # noqa: E501
        :type: Data
        """

        self._options = options

    @property
    def options_list(self):
        """Gets the options_list of this OneTemplate.  # noqa: E501


        :return: The options_list of this OneTemplate.  # noqa: E501
        :rtype: list[str]
        """
        return self._options_list

    @options_list.setter
    def options_list(self, options_list):
        """Sets the options_list of this OneTemplate.


        :param options_list: The options_list of this OneTemplate.  # noqa: E501
        :type: list[str]
        """

        self._options_list = options_list

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
        if issubclass(OneTemplate, dict):
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
        if not isinstance(other, OneTemplate):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
