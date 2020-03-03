# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from sure_tosca.models.node_template import NodeTemplateModel  # noqa: E501
from sure_tosca.models.node_template_map import NodeTemplateMapModel  # noqa: E501
from sure_tosca.models.tosca_template import ToscaTemplateModel  # noqa: E501
from sure_tosca.test import BaseTestCase

import werkzeug
werkzeug.cached_property = werkzeug.utils.cached_property


class TestDefaultController(BaseTestCase):
    """DefaultController integration test stubs"""

    def test_add_type_definition(self):
        """Test case for add_type_definition

        
        """
        definition_map = None
        response = self.client.open(
            '/tosca-sure/1.2.0/tosca_types',
            method='POST',
            data=json.dumps(definition_map),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_templates(self):
        """Test case for get_node_templates

        
        """
        query_string = [('query', 'query_example')]
        response = self.client.open(
            '/tosca-sure/1.2.0/tosca_template/{id}/topology_template/node_templates'.format(id='id_example'),
            method='GET',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_tosca_template(self):
        """Test case for get_tosca_template

        
        """
        response = self.client.open(
            '/tosca-sure/1.2.0/tosca_template/{id}'.format(id='id_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_type_definition(self):
        """Test case for get_type_definition

        
        """
        query_string = [('query', 'query_example')]
        response = self.client.open(
            '/tosca-sure/1.2.0/tosca_types',
            method='GET',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_upload_tosca_template(self):
        """Test case for upload_tosca_template

        upload a tosca template description file
        """
        data = dict(file=(BytesIO(b'some file data'), 'file.txt'))
        response = self.client.open(
            '/tosca-sure/1.2.0/tosca_template',
            method='POST',
            data=data,
            content_type='multipart/form-data')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
