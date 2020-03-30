# coding: utf-8

"""
    SEMAPHORE

    Semaphore API  # noqa: E501

    OpenAPI spec version: 2.2.0
    
    Generated by: https://github.com/semaphore-api/semaphore-codegen.git
"""


import pprint
import re  # noqa: F401

import six


class EnvironmentRequest(object):
    """NOTE: This class is auto generated by the semaphore code generator program.

    Do not edit the class manually.
    """

    """
    Attributes:
      semaphore_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value is json key in definition.
    """
    semaphore_types = {
        'name': 'str',
        'project_id': 'int',
        'password': 'str',
        'json': 'str'
    }

    attribute_map = {
        'name': 'name',
        'project_id': 'project_id',
        'password': 'password',
        'json': 'json'
    }

    def __init__(self, name=None, project_id=None, password=None, json=None):  # noqa: E501
        """EnvironmentRequest - a model defined in Swagger"""  # noqa: E501

        self._name = None
        self._project_id = None
        self._password = None
        self._json = None
        self.discriminator = None

        if name is not None:
            self.name = name
        if project_id is not None:
            self.project_id = project_id
        if password is not None:
            self.password = password
        if json is not None:
            self.json = json

    @property
    def name(self):
        """Gets the name of this EnvironmentRequest.  # noqa: E501


        :return: The name of this EnvironmentRequest.  # noqa: E501
        :rtype: str
        """
        return self._name

    @name.setter
    def name(self, name):
        """Sets the name of this EnvironmentRequest.


        :param name: The name of this EnvironmentRequest.  # noqa: E501
        :type: str
        """

        self._name = name

    @property
    def project_id(self):
        """Gets the project_id of this EnvironmentRequest.  # noqa: E501


        :return: The project_id of this EnvironmentRequest.  # noqa: E501
        :rtype: int
        """
        return self._project_id

    @project_id.setter
    def project_id(self, project_id):
        """Sets the project_id of this EnvironmentRequest.


        :param project_id: The project_id of this EnvironmentRequest.  # noqa: E501
        :type: int
        """
        if project_id is not None and project_id < 1:  # noqa: E501
            raise ValueError("Invalid value for `project_id`, must be a value greater than or equal to `1`")  # noqa: E501

        self._project_id = project_id

    @property
    def password(self):
        """Gets the password of this EnvironmentRequest.  # noqa: E501


        :return: The password of this EnvironmentRequest.  # noqa: E501
        :rtype: str
        """
        return self._password

    @password.setter
    def password(self, password):
        """Sets the password of this EnvironmentRequest.


        :param password: The password of this EnvironmentRequest.  # noqa: E501
        :type: str
        """

        self._password = password

    @property
    def json(self):
        """Gets the json of this EnvironmentRequest.  # noqa: E501


        :return: The json of this EnvironmentRequest.  # noqa: E501
        :rtype: str
        """
        return self._json

    @json.setter
    def json(self, json):
        """Sets the json of this EnvironmentRequest.


        :param json: The json of this EnvironmentRequest.  # noqa: E501
        :type: str
        """

        self._json = json

    def to_dict(self):
        """Returns the model properties as a dict"""
        result = {}

        for attr, _ in six.iteritems(self.semaphore_types):
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
        if issubclass(EnvironmentRequest, dict):
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
        if not isinstance(other, EnvironmentRequest):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
