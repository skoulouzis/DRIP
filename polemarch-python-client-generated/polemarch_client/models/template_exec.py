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


class TemplateExec(object):
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
        'option': 'str'
    }

    attribute_map = {
        'option': 'option'
    }

    def __init__(self, option=None):  # noqa: E501
        """TemplateExec - a model defined in Swagger"""  # noqa: E501

        self._option = None
        self.discriminator = None

        if option is not None:
            self.option = option

    @property
    def option(self):
        """Gets the option of this TemplateExec.  # noqa: E501

        Option name from template options.  # noqa: E501

        :return: The option of this TemplateExec.  # noqa: E501
        :rtype: str
        """
        return self._option

    @option.setter
    def option(self, option):
        """Sets the option of this TemplateExec.

        Option name from template options.  # noqa: E501

        :param option: The option of this TemplateExec.  # noqa: E501
        :type: str
        """
        if option is not None and len(option) < 0:
            raise ValueError("Invalid value for `option`, length must be greater than or equal to `0`")  # noqa: E501

        self._option = option

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
        if issubclass(TemplateExec, dict):
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
        if not isinstance(other, TemplateExec):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other
