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


class ExecuteResponse(object):
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
        'detail': 'str',
        'history_id': 'int',
        'executor': 'int'
    }

    attribute_map = {
        'detail': 'detail',
        'history_id': 'history_id',
        'executor': 'executor'
    }

    def __init__(self, detail=None, history_id=None, executor=None):  # noqa: E501
        """ExecuteResponse - a model defined in Swagger"""  # noqa: E501

        self._detail = None
        self._history_id = None
        self._executor = None
        self.discriminator = None

        self.detail = detail
        if history_id is not None:
            self.history_id = history_id
        if executor is not None:
            self.executor = executor

    @property
    def detail(self):
        """Gets the detail of this ExecuteResponse.  # noqa: E501


        :return: The detail of this ExecuteResponse.  # noqa: E501
        :rtype: str
        """
        return self._detail

    @detail.setter
    def detail(self, detail):
        """Sets the detail of this ExecuteResponse.


        :param detail: The detail of this ExecuteResponse.  # noqa: E501
        :type: str
        """
        if detail is None:
            raise ValueError("Invalid value for `detail`, must not be `None`")  # noqa: E501
        if detail is not None and len(detail) < 1:
            raise ValueError("Invalid value for `detail`, length must be greater than or equal to `1`")  # noqa: E501

        self._detail = detail

    @property
    def history_id(self):
        """Gets the history_id of this ExecuteResponse.  # noqa: E501


        :return: The history_id of this ExecuteResponse.  # noqa: E501
        :rtype: int
        """
        return self._history_id

    @history_id.setter
    def history_id(self, history_id):
        """Sets the history_id of this ExecuteResponse.


        :param history_id: The history_id of this ExecuteResponse.  # noqa: E501
        :type: int
        """

        self._history_id = history_id

    @property
    def executor(self):
        """Gets the executor of this ExecuteResponse.  # noqa: E501


        :return: The executor of this ExecuteResponse.  # noqa: E501
        :rtype: int
        """
        return self._executor

    @executor.setter
    def executor(self, executor):
        """Sets the executor of this ExecuteResponse.


        :param executor: The executor of this ExecuteResponse.  # noqa: E501
        :type: int
        """

        self._executor = executor

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
        if issubclass(ExecuteResponse, dict):
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
        if not isinstance(other, ExecuteResponse):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
