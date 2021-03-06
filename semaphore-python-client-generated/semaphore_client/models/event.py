# coding: utf-8

"""
    SEMAPHORE

    Semaphore API  # noqa: E501

    OpenAPI spec version: 2.2.0-oas3
    
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""

import pprint
import re  # noqa: F401

import six


class Event(object):
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
        'project_id': 'int',
        'object_id': 'int',
        'object_type': 'str',
        'description': 'str'
    }

    attribute_map = {
        'project_id': 'project_id',
        'object_id': 'object_id',
        'object_type': 'object_type',
        'description': 'description'
    }

    def __init__(self, project_id=None, object_id=None, object_type=None, description=None):  # noqa: E501
        """Event - a model defined in Swagger"""  # noqa: E501
        self._project_id = None
        self._object_id = None
        self._object_type = None
        self._description = None
        self.discriminator = None
        if project_id is not None:
            self.project_id = project_id
        if object_id is not None:
            self.object_id = object_id
        if object_type is not None:
            self.object_type = object_type
        if description is not None:
            self.description = description

    @property
    def project_id(self):
        """Gets the project_id of this Event.  # noqa: E501


        :return: The project_id of this Event.  # noqa: E501
        :rtype: int
        """
        return self._project_id

    @project_id.setter
    def project_id(self, project_id):
        """Sets the project_id of this Event.


        :param project_id: The project_id of this Event.  # noqa: E501
        :type: int
        """

        self._project_id = project_id

    @property
    def object_id(self):
        """Gets the object_id of this Event.  # noqa: E501


        :return: The object_id of this Event.  # noqa: E501
        :rtype: int
        """
        return self._object_id

    @object_id.setter
    def object_id(self, object_id):
        """Sets the object_id of this Event.


        :param object_id: The object_id of this Event.  # noqa: E501
        :type: int
        """

        self._object_id = object_id

    @property
    def object_type(self):
        """Gets the object_type of this Event.  # noqa: E501


        :return: The object_type of this Event.  # noqa: E501
        :rtype: str
        """
        return self._object_type

    @object_type.setter
    def object_type(self, object_type):
        """Sets the object_type of this Event.


        :param object_type: The object_type of this Event.  # noqa: E501
        :type: str
        """

        self._object_type = object_type

    @property
    def description(self):
        """Gets the description of this Event.  # noqa: E501


        :return: The description of this Event.  # noqa: E501
        :rtype: str
        """
        return self._description

    @description.setter
    def description(self, description):
        """Sets the description of this Event.


        :param description: The description of this Event.  # noqa: E501
        :type: str
        """

        self._description = description

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
        if issubclass(Event, dict):
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
        if not isinstance(other, Event):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
