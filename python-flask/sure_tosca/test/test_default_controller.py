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
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/ancestors_properties'.format(id='id_example', node_name='node_name_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_all_ancestor_types(self):
        """Test case for get_all_ancestor_types

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/ancestors_types'.format(id='id_example', node_name='node_name_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_ancestors_requirements(self):
        """Test case for get_ancestors_requirements

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/ancestors_requirements'.format(id='id_example', node_name='node_name_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_dsl_definitions(self):
        """Test case for get_dsl_definitions

        
        """
        query_string = [('anchors', 'anchors_example'),
                        ('derived_from', 'derived_from_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/dsl_definitions'.format(id='id_example'),
            method='GET',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_imports(self):
        """Test case for get_imports

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/imports'.format(id='id_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_outputs(self):
        """Test case for get_node_outputs

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/outputs'.format(id='id_example', node_name='node_name_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_properties(self):
        """Test case for get_node_properties

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/properties'.format(id='id_example', node_name='node_name_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_requirements(self):
        """Test case for get_node_requirements

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/requirements'.format(id='id_example', node_name='node_name_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_templates(self):
        """Test case for get_node_templates

        
        """
        query_string = [('type_name', 'type_name_example'),
                        ('node_name', 'node_name_example'),
                        ('has_interfaces', true),
                        ('has_properties', true),
                        ('has_attributes', true),
                        ('has_requirements', true),
                        ('has_capabilities', true),
                        ('has_artifacts', true),
                        ('derived_from', 'derived_from_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates'.format(id='id_example'),
            method='GET',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_node_type_name(self):
        """Test case for get_node_type_name

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/type_name'.format(id='id_example', node_name='node_name_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_parent_type_name(self):
        """Test case for get_parent_type_name

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/derived_from'.format(id='id_example', node_name='node_name_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_related_nodes(self):
        """Test case for get_related_nodes

        
        """
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/related'.format(id='id_example', node_name='node_name_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_relationship_templates(self):
        """Test case for get_relationship_templates

        
        """
        query_string = [('type_name', 'type_name_example'),
                        ('derived_from', 'derived_from_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/relationship_templates'.format(id='id_example'),
            method='GET',
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

    def test_get_types(self):
        """Test case for get_types

        
        """
        query_string = [('kind_of_type', 'kind_of_type_example'),
                        ('has_interfaces', true),
                        ('type_name', 'type_name_example'),
                        ('has_properties', true),
                        ('has_attributes', true),
                        ('has_requirements', true),
                        ('has_capabilities', true),
                        ('has_artifacts', true),
                        ('derived_from', 'derived_from_example')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/types'.format(id='id_example'),
            method='GET',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_set_node_properties(self):
        """Test case for set_node_properties

        
        """
        properties = None
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/properties'.format(id='id_example', node_name='node_name_example'),
            method='PUT',
            data=json.dumps(properties),
            content_type='application/json')
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
