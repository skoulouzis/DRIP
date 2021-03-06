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


class InfoType(object):
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
        'version': 'str',
        'update_body': 'str',
        'update': 'InfoTypeUpdate'
    }

    attribute_map = {
        'version': 'version',
        'update_body': 'updateBody',
        'update': 'update'
    }

    def __init__(self, version=None, update_body=None, update=None):  # noqa: E501
        """InfoType - a model defined in Swagger"""  # noqa: E501
        self._version = None
        self._update_body = None
        self._update = None
        self.discriminator = None
        if version is not None:
            self.version = version
        if update_body is not None:
            self.update_body = update_body
        if update is not None:
            self.update = update

    @property
    def version(self):
        """Gets the version of this InfoType.  # noqa: E501


        :return: The version of this InfoType.  # noqa: E501
        :rtype: str
        """
        return self._version

    @version.setter
    def version(self, version):
        """Sets the version of this InfoType.


        :param version: The version of this InfoType.  # noqa: E501
        :type: str
        """

        self._version = version

    @property
    def update_body(self):
        """Gets the update_body of this InfoType.  # noqa: E501


        :return: The update_body of this InfoType.  # noqa: E501
        :rtype: str
        """
        return self._update_body

    @update_body.setter
    def update_body(self, update_body):
        """Sets the update_body of this InfoType.


        :param update_body: The update_body of this InfoType.  # noqa: E501
        :type: str
        """

        self._update_body = update_body

    @property
    def update(self):
        """Gets the update of this InfoType.  # noqa: E501


        :return: The update of this InfoType.  # noqa: E501
        :rtype: InfoTypeUpdate
        """
        return self._update

    @update.setter
    def update(self, update):
        """Sets the update of this InfoType.


        :param update: The update of this InfoType.  # noqa: E501
        :type: InfoTypeUpdate
        """

        self._update = update

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
        if issubclass(InfoType, dict):
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
        if not isinstance(other, InfoType):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
