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


class CreateUser(object):
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
        'username': 'str',
        'is_active': 'bool',
        'is_staff': 'bool',
        'first_name': 'str',
        'last_name': 'str',
        'email': 'str',
        'password': 'str',
        'password2': 'str'
    }

    attribute_map = {
        'id': 'id',
        'username': 'username',
        'is_active': 'is_active',
        'is_staff': 'is_staff',
        'first_name': 'first_name',
        'last_name': 'last_name',
        'email': 'email',
        'password': 'password',
        'password2': 'password2'
    }

    def __init__(self, id=None, username=None, is_active=True, is_staff=False, first_name=None, last_name=None, email=None, password=None, password2=None):  # noqa: E501
        """CreateUser - a model defined in Swagger"""  # noqa: E501

        self._id = None
        self._username = None
        self._is_active = None
        self._is_staff = None
        self._first_name = None
        self._last_name = None
        self._email = None
        self._password = None
        self._password2 = None
        self.discriminator = None

        if id is not None:
            self.id = id
        self.username = username
        if is_active is not None:
            self.is_active = is_active
        if is_staff is not None:
            self.is_staff = is_staff
        if first_name is not None:
            self.first_name = first_name
        if last_name is not None:
            self.last_name = last_name
        if email is not None:
            self.email = email
        self.password = password
        self.password2 = password2

    @property
    def id(self):
        """Gets the id of this CreateUser.  # noqa: E501


        :return: The id of this CreateUser.  # noqa: E501
        :rtype: int
        """
        return self._id

    @id.setter
    def id(self, id):
        """Sets the id of this CreateUser.


        :param id: The id of this CreateUser.  # noqa: E501
        :type: int
        """

        self._id = id

    @property
    def username(self):
        """Gets the username of this CreateUser.  # noqa: E501

        Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.  # noqa: E501

        :return: The username of this CreateUser.  # noqa: E501
        :rtype: str
        """
        return self._username

    @username.setter
    def username(self, username):
        """Sets the username of this CreateUser.

        Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.  # noqa: E501

        :param username: The username of this CreateUser.  # noqa: E501
        :type: str
        """
        if username is None:
            raise ValueError("Invalid value for `username`, must not be `None`")  # noqa: E501
        if username is not None and len(username) > 150:
            raise ValueError("Invalid value for `username`, length must be less than or equal to `150`")  # noqa: E501
        if username is not None and len(username) < 1:
            raise ValueError("Invalid value for `username`, length must be greater than or equal to `1`")  # noqa: E501
        if username is not None: # and not re.search(r'^[\\w.@+-]+$', username):  # noqa: E501
            raise ValueError(r"Invalid value for `username`, must be a follow pattern or equal to `/^[\\w.@+-]+$/`")  # noqa: E501

        self._username = username

    @property
    def is_active(self):
        """Gets the is_active of this CreateUser.  # noqa: E501


        :return: The is_active of this CreateUser.  # noqa: E501
        :rtype: bool
        """
        return self._is_active

    @is_active.setter
    def is_active(self, is_active):
        """Sets the is_active of this CreateUser.


        :param is_active: The is_active of this CreateUser.  # noqa: E501
        :type: bool
        """

        self._is_active = is_active

    @property
    def is_staff(self):
        """Gets the is_staff of this CreateUser.  # noqa: E501


        :return: The is_staff of this CreateUser.  # noqa: E501
        :rtype: bool
        """
        return self._is_staff

    @is_staff.setter
    def is_staff(self, is_staff):
        """Sets the is_staff of this CreateUser.


        :param is_staff: The is_staff of this CreateUser.  # noqa: E501
        :type: bool
        """

        self._is_staff = is_staff

    @property
    def first_name(self):
        """Gets the first_name of this CreateUser.  # noqa: E501


        :return: The first_name of this CreateUser.  # noqa: E501
        :rtype: str
        """
        return self._first_name

    @first_name.setter
    def first_name(self, first_name):
        """Sets the first_name of this CreateUser.


        :param first_name: The first_name of this CreateUser.  # noqa: E501
        :type: str
        """
        if first_name is not None and len(first_name) > 30:
            raise ValueError("Invalid value for `first_name`, length must be less than or equal to `30`")  # noqa: E501

        self._first_name = first_name

    @property
    def last_name(self):
        """Gets the last_name of this CreateUser.  # noqa: E501


        :return: The last_name of this CreateUser.  # noqa: E501
        :rtype: str
        """
        return self._last_name

    @last_name.setter
    def last_name(self, last_name):
        """Sets the last_name of this CreateUser.


        :param last_name: The last_name of this CreateUser.  # noqa: E501
        :type: str
        """
        if last_name is not None and len(last_name) > 150:
            raise ValueError("Invalid value for `last_name`, length must be less than or equal to `150`")  # noqa: E501

        self._last_name = last_name

    @property
    def email(self):
        """Gets the email of this CreateUser.  # noqa: E501


        :return: The email of this CreateUser.  # noqa: E501
        :rtype: str
        """
        return self._email

    @email.setter
    def email(self, email):
        """Sets the email of this CreateUser.


        :param email: The email of this CreateUser.  # noqa: E501
        :type: str
        """
        if email is not None and len(email) < 1:
            raise ValueError("Invalid value for `email`, length must be greater than or equal to `1`")  # noqa: E501

        self._email = email

    @property
    def password(self):
        """Gets the password of this CreateUser.  # noqa: E501


        :return: The password of this CreateUser.  # noqa: E501
        :rtype: str
        """
        return self._password

    @password.setter
    def password(self, password):
        """Sets the password of this CreateUser.


        :param password: The password of this CreateUser.  # noqa: E501
        :type: str
        """
        if password is None:
            raise ValueError("Invalid value for `password`, must not be `None`")  # noqa: E501
        if password is not None and len(password) < 1:
            raise ValueError("Invalid value for `password`, length must be greater than or equal to `1`")  # noqa: E501

        self._password = password

    @property
    def password2(self):
        """Gets the password2 of this CreateUser.  # noqa: E501


        :return: The password2 of this CreateUser.  # noqa: E501
        :rtype: str
        """
        return self._password2

    @password2.setter
    def password2(self, password2):
        """Sets the password2 of this CreateUser.


        :param password2: The password2 of this CreateUser.  # noqa: E501
        :type: str
        """
        if password2 is None:
            raise ValueError("Invalid value for `password2`, must not be `None`")  # noqa: E501
        if password2 is not None and len(password2) < 1:
            raise ValueError("Invalid value for `password2`, length must be greater than or equal to `1`")  # noqa: E501

        self._password2 = password2

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
        if issubclass(CreateUser, dict):
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
        if not isinstance(other, CreateUser):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
