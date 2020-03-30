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


class OneProject(object):
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
        'repository': 'str',
        'status': 'str',
        'revision': 'str',
        'branch': 'str',
        'owner': 'User',
        'notes': 'str',
        'readme_content': 'str',
        'execute_view_data': 'Data'
    }

    attribute_map = {
        'id': 'id',
        'name': 'name',
        'repository': 'repository',
        'status': 'status',
        'revision': 'revision',
        'branch': 'branch',
        'owner': 'owner',
        'notes': 'notes',
        'readme_content': 'readme_content',
        'execute_view_data': 'execute_view_data'
    }

    def __init__(self, id=None, name=None, repository='MANUAL', status=None, revision=None, branch=None, owner=None, notes=None, readme_content=None, execute_view_data=None):  # noqa: E501
        """OneProject - a model defined in Swagger"""  # noqa: E501

        self._id = None
        self._name = None
        self._repository = None
        self._status = None
        self._revision = None
        self._branch = None
        self._owner = None
        self._notes = None
        self._readme_content = None
        self._execute_view_data = None
        self.discriminator = None

        if id is not None:
            self.id = id
        if name is not None:
            self.name = name
        if repository is not None:
            self.repository = repository
        if status is not None:
            self.status = status
        if revision is not None:
            self.revision = revision
        if branch is not None:
            self.branch = branch
        if owner is not None:
            self.owner = owner
        if notes is not None:
            self.notes = notes
        if readme_content is not None:
            self.readme_content = readme_content
        if execute_view_data is not None:
            self.execute_view_data = execute_view_data

    @property
    def id(self):
        """Gets the id of this OneProject.  # noqa: E501


        :return: The id of this OneProject.  # noqa: E501
        :rtype: int
        """
        return self._id

    @id.setter
    def id(self, id):
        """Sets the id of this OneProject.


        :param id: The id of this OneProject.  # noqa: E501
        :type: int
        """

        self._id = id

    @property
    def name(self):
        """Gets the name of this OneProject.  # noqa: E501


        :return: The name of this OneProject.  # noqa: E501
        :rtype: str
        """
        return self._name

    @name.setter
    def name(self, name):
        """Sets the name of this OneProject.


        :param name: The name of this OneProject.  # noqa: E501
        :type: str
        """
        if name is not None and len(name) > 512:
            raise ValueError("Invalid value for `name`, length must be less than or equal to `512`")  # noqa: E501
        if name is not None and len(name) < 1:
            raise ValueError("Invalid value for `name`, length must be greater than or equal to `1`")  # noqa: E501

        self._name = name

    @property
    def repository(self):
        """Gets the repository of this OneProject.  # noqa: E501


        :return: The repository of this OneProject.  # noqa: E501
        :rtype: str
        """
        return self._repository

    @repository.setter
    def repository(self, repository):
        """Sets the repository of this OneProject.


        :param repository: The repository of this OneProject.  # noqa: E501
        :type: str
        """
        if repository is not None and len(repository) < 1:
            raise ValueError("Invalid value for `repository`, length must be greater than or equal to `1`")  # noqa: E501

        self._repository = repository

    @property
    def status(self):
        """Gets the status of this OneProject.  # noqa: E501


        :return: The status of this OneProject.  # noqa: E501
        :rtype: str
        """
        return self._status

    @status.setter
    def status(self, status):
        """Sets the status of this OneProject.


        :param status: The status of this OneProject.  # noqa: E501
        :type: str
        """
        allowed_values = ["NEW", "ERROR", "OK", "WAIT_SYNC", "SYNC"]  # noqa: E501
        if status not in allowed_values:
            raise ValueError(
                "Invalid value for `status` ({0}), must be one of {1}"  # noqa: E501
                .format(status, allowed_values)
            )

        self._status = status

    @property
    def revision(self):
        """Gets the revision of this OneProject.  # noqa: E501


        :return: The revision of this OneProject.  # noqa: E501
        :rtype: str
        """
        return self._revision

    @revision.setter
    def revision(self, revision):
        """Sets the revision of this OneProject.


        :param revision: The revision of this OneProject.  # noqa: E501
        :type: str
        """

        self._revision = revision

    @property
    def branch(self):
        """Gets the branch of this OneProject.  # noqa: E501


        :return: The branch of this OneProject.  # noqa: E501
        :rtype: str
        """
        return self._branch

    @branch.setter
    def branch(self, branch):
        """Sets the branch of this OneProject.


        :param branch: The branch of this OneProject.  # noqa: E501
        :type: str
        """

        self._branch = branch

    @property
    def owner(self):
        """Gets the owner of this OneProject.  # noqa: E501


        :return: The owner of this OneProject.  # noqa: E501
        :rtype: User
        """
        return self._owner

    @owner.setter
    def owner(self, owner):
        """Sets the owner of this OneProject.


        :param owner: The owner of this OneProject.  # noqa: E501
        :type: User
        """

        self._owner = owner

    @property
    def notes(self):
        """Gets the notes of this OneProject.  # noqa: E501


        :return: The notes of this OneProject.  # noqa: E501
        :rtype: str
        """
        return self._notes

    @notes.setter
    def notes(self, notes):
        """Sets the notes of this OneProject.


        :param notes: The notes of this OneProject.  # noqa: E501
        :type: str
        """

        self._notes = notes

    @property
    def readme_content(self):
        """Gets the readme_content of this OneProject.  # noqa: E501


        :return: The readme_content of this OneProject.  # noqa: E501
        :rtype: str
        """
        return self._readme_content

    @readme_content.setter
    def readme_content(self, readme_content):
        """Sets the readme_content of this OneProject.


        :param readme_content: The readme_content of this OneProject.  # noqa: E501
        :type: str
        """

        self._readme_content = readme_content

    @property
    def execute_view_data(self):
        """Gets the execute_view_data of this OneProject.  # noqa: E501


        :return: The execute_view_data of this OneProject.  # noqa: E501
        :rtype: Data
        """
        return self._execute_view_data

    @execute_view_data.setter
    def execute_view_data(self, execute_view_data):
        """Sets the execute_view_data of this OneProject.


        :param execute_view_data: The execute_view_data of this OneProject.  # noqa: E501
        :type: Data
        """

        self._execute_view_data = execute_view_data

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
        if issubclass(OneProject, dict):
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
        if not isinstance(other, OneProject):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
