# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from sure_tosca.models.node_template import NodeTemplate  # noqa: E501
from sure_tosca.models.topology_template import TopologyTemplate  # noqa: E501
from sure_tosca.models.tosca_template import ToscaTemplate  # noqa: E501
from sure_tosca.test import BaseTestCase


class TestDefaultController(BaseTestCase):
    """DefaultController integration test stubs"""

    def test_get_all_ancestor_properties(self):
        """Test case for get_all_ancestor_properties

        
        """
        nodeTemplate = NodeTemplate()
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/ancestors_properties'.format(id='id_example'),
            method='GET',
            data=json.dumps(nodeTemplate),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_all_ancestor_types(self):
        """Test case for get_all_ancestor_types

        
        """
        nodeTemplate = NodeTemplate()
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/ancestors_types'.format(id='id_example'),
            method='GET',
            data=json.dumps(nodeTemplate),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_ancestors_requirements(self):
        """Test case for get_ancestors_requirements

        
        """
        nodeTemplate = NodeTemplate()
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/ancestors_requirements'.format(id='id_example'),
            method='GET',
            data=json.dumps(nodeTemplate),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_interface_types(self):
        """Test case for get_interface_types

        
        """
        query_string = [('interface_type', 'interface_type_example'),
                        ('derived_from', 'derived_from_example'),
                        ('operations', 'operations_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/interface_types'.format(id='id_example'),
            method='GET',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_outputs(self):
        """Test case for get_node_outputs

        
        """
        nodeTemplate = NodeTemplate()
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/outputs'.format(id='id_example'),
            method='GET',
            data=json.dumps(nodeTemplate),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_properties(self):
        """Test case for get_node_properties

        
        """
        nodeTemplate = NodeTemplate()
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/properties'.format(id='id_example'),
            method='GET',
            data=json.dumps(nodeTemplate),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_requirements(self):
        """Test case for get_node_requirements

        
        """
        nodeTemplate = NodeTemplate()
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/requirements'.format(id='id_example'),
            method='GET',
            data=json.dumps(nodeTemplate),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_templates(self):
        """Test case for get_node_templates

        
        """
        query_string = [('node_name', 'node_name_example'),
                        ('node_type', 'node_type_example'),
                        ('has_interface', true),
                        ('has_requirements', true),
                        ('has_artifacts', true),
                        ('has_properties', true)]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates'.format(id='id_example'),
            method='GET',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_type_name(self):
        """Test case for get_node_type_name

        
        """
        nodeTemplate = NodeTemplate()
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/type_name'.format(id='id_example'),
            method='GET',
            data=json.dumps(nodeTemplate),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_parent_type_name(self):
        """Test case for get_parent_type_name

        
        """
        nodeTemplate = NodeTemplate()
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/parent_type_name'.format(id='id_example'),
            method='GET',
            data=json.dumps(nodeTemplate),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_related_nodes(self):
        """Test case for get_related_nodes

        
        """
        nodeTemplate = NodeTemplate()
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/related'.format(id='id_example'),
            method='GET',
            data=json.dumps(nodeTemplate),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_topology_template(self):
        """Test case for get_topology_template

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template'.format(id='id_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_tosca_template(self):
        """Test case for get_tosca_template

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}'.format(id='id_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_set_node_properties(self):
        """Test case for set_node_properties

        
        """
        properties = None
        query_string = [('node_name', 'node_name_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/properties'.format(id='id_example'),
            method='PUT',
            data=json.dumps(properties),
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_upload_tosca_template(self):
        """Test case for upload_tosca_template

        upload a tosca template description file
        """
        data = dict(file=(BytesIO(b'some file data'), 'file.txt'))
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template',
            method='POST',
            data=data,
            content_type='multipart/form-data')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
