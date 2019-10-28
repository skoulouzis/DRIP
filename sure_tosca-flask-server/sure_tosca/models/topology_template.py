# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from sure_tosca.models.base_model_ import Model
from sure_tosca import util


class TopologyTemplate(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, items=None, additional_properties=None):  # noqa: E501
        """TopologyTemplate - a model defined in Swagger

        :param items: The items of this TopologyTemplate.  # noqa: E501
        :type items: object
        :param additional_properties: The additional_properties of this TopologyTemplate.  # noqa: E501
        :type additional_properties: object
        """
        self.swagger_types = {
            'items': object,
            'additional_properties': object
        }

        self.attribute_map = {
            'items': 'items',
            'additional_properties': 'additionalProperties'
        }

        self._items = items
        self._additional_properties = additional_properties

    @classmethod
    def from_dict(cls, dikt):
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The TopologyTemplate of this TopologyTemplate.  # noqa: E501
        :rtype: TopologyTemplate
        """
        return util.deserialize_model(dikt, cls)

    @property
    def items(self):
        """Gets the items of this TopologyTemplate.


        :return: The items of this TopologyTemplate.
        :rtype: object
        """
        return self._items

    @items.setter
    def items(self, items):
        """Sets the items of this TopologyTemplate.


        :param items: The items of this TopologyTemplate.
        :type items: object
        """

        self._items = items

    @property
    def additional_properties(self):
        """Gets the additional_properties of this TopologyTemplate.


        :return: The additional_properties of this TopologyTemplate.
        :rtype: object
        """
        return self._additional_properties

    @additional_properties.setter
    def additional_properties(self, additional_properties):
        """Sets the additional_properties of this TopologyTemplate.


        :param additional_properties: The additional_properties of this TopologyTemplate.
        :type additional_properties: object
        """

        self._additional_properties = additional_properties
