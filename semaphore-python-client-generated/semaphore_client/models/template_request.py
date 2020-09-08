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


class TemplateRequest(object):
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
        'ssh_key_id': 'int',
        'project_id': 'int',
        'inventory_id': 'int',
        'repository_id': 'int',
        'environment_id': 'int',
        'alias': 'str',
        'playbook': 'str',
        'arguments': 'str',
        'override_args': 'bool'
    }

    attribute_map = {
        'ssh_key_id': 'ssh_key_id',
        'project_id': 'project_id',
        'inventory_id': 'inventory_id',
        'repository_id': 'repository_id',
        'environment_id': 'environment_id',
        'alias': 'alias',
        'playbook': 'playbook',
        'arguments': 'arguments',
        'override_args': 'override_args'
    }

    def __init__(self, ssh_key_id=None, project_id=None, inventory_id=None, repository_id=None, environment_id=None, alias=None, playbook=None, arguments=None, override_args=None):  # noqa: E501
        """TemplateRequest - a model defined in Swagger"""  # noqa: E501
        self._ssh_key_id = None
        self._project_id = None
        self._inventory_id = None
        self._repository_id = None
        self._environment_id = None
        self._alias = None
        self._playbook = None
        self._arguments = None
        self._override_args = None
        self.discriminator = None
        if ssh_key_id is not None:
            self.ssh_key_id = ssh_key_id
        if project_id is not None:
            self.project_id = project_id
        if inventory_id is not None:
            self.inventory_id = inventory_id
        if repository_id is not None:
            self.repository_id = repository_id
        if environment_id is not None:
            self.environment_id = environment_id
        if alias is not None:
            self.alias = alias
        if playbook is not None:
            self.playbook = playbook
        if arguments is not None:
            self.arguments = arguments
        if override_args is not None:
            self.override_args = override_args

    @property
    def ssh_key_id(self):
        """Gets the ssh_key_id of this TemplateRequest.  # noqa: E501


        :return: The ssh_key_id of this TemplateRequest.  # noqa: E501
        :rtype: int
        """
        return self._ssh_key_id

    @ssh_key_id.setter
    def ssh_key_id(self, ssh_key_id):
        """Sets the ssh_key_id of this TemplateRequest.


        :param ssh_key_id: The ssh_key_id of this TemplateRequest.  # noqa: E501
        :type: int
        """

        self._ssh_key_id = ssh_key_id

    @property
    def project_id(self):
        """Gets the project_id of this TemplateRequest.  # noqa: E501


        :return: The project_id of this TemplateRequest.  # noqa: E501
        :rtype: int
        """
        return self._project_id

    @project_id.setter
    def project_id(self, project_id):
        """Sets the project_id of this TemplateRequest.


        :param project_id: The project_id of this TemplateRequest.  # noqa: E501
        :type: int
        """

        self._project_id = project_id

    @property
    def inventory_id(self):
        """Gets the inventory_id of this TemplateRequest.  # noqa: E501


        :return: The inventory_id of this TemplateRequest.  # noqa: E501
        :rtype: int
        """
        return self._inventory_id

    @inventory_id.setter
    def inventory_id(self, inventory_id):
        """Sets the inventory_id of this TemplateRequest.


        :param inventory_id: The inventory_id of this TemplateRequest.  # noqa: E501
        :type: int
        """

        self._inventory_id = inventory_id

    @property
    def repository_id(self):
        """Gets the repository_id of this TemplateRequest.  # noqa: E501


        :return: The repository_id of this TemplateRequest.  # noqa: E501
        :rtype: int
        """
        return self._repository_id

    @repository_id.setter
    def repository_id(self, repository_id):
        """Sets the repository_id of this TemplateRequest.


        :param repository_id: The repository_id of this TemplateRequest.  # noqa: E501
        :type: int
        """

        self._repository_id = repository_id

    @property
    def environment_id(self):
        """Gets the environment_id of this TemplateRequest.  # noqa: E501


        :return: The environment_id of this TemplateRequest.  # noqa: E501
        :rtype: int
        """
        return self._environment_id

    @environment_id.setter
    def environment_id(self, environment_id):
        """Sets the environment_id of this TemplateRequest.


        :param environment_id: The environment_id of this TemplateRequest.  # noqa: E501
        :type: int
        """

        self._environment_id = environment_id

    @property
    def alias(self):
        """Gets the alias of this TemplateRequest.  # noqa: E501


        :return: The alias of this TemplateRequest.  # noqa: E501
        :rtype: str
        """
        return self._alias

    @alias.setter
    def alias(self, alias):
        """Sets the alias of this TemplateRequest.


        :param alias: The alias of this TemplateRequest.  # noqa: E501
        :type: str
        """

        self._alias = alias

    @property
    def playbook(self):
        """Gets the playbook of this TemplateRequest.  # noqa: E501


        :return: The playbook of this TemplateRequest.  # noqa: E501
        :rtype: str
        """
        return self._playbook

    @playbook.setter
    def playbook(self, playbook):
        """Sets the playbook of this TemplateRequest.


        :param playbook: The playbook of this TemplateRequest.  # noqa: E501
        :type: str
        """

        self._playbook = playbook

    @property
    def arguments(self):
        """Gets the arguments of this TemplateRequest.  # noqa: E501


        :return: The arguments of this TemplateRequest.  # noqa: E501
        :rtype: str
        """
        return self._arguments

    @arguments.setter
    def arguments(self, arguments):
        """Sets the arguments of this TemplateRequest.


        :param arguments: The arguments of this TemplateRequest.  # noqa: E501
        :type: str
        """

        self._arguments = arguments

    @property
    def override_args(self):
        """Gets the override_args of this TemplateRequest.  # noqa: E501


        :return: The override_args of this TemplateRequest.  # noqa: E501
        :rtype: bool
        """
        return self._override_args

    @override_args.setter
    def override_args(self, override_args):
        """Sets the override_args of this TemplateRequest.


        :param override_args: The override_args of this TemplateRequest.  # noqa: E501
        :type: bool
        """

        self._override_args = override_args

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
        if issubclass(TemplateRequest, dict):
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
        if not isinstance(other, TemplateRequest):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
