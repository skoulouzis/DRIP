# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO
import os

from sure_tosca.models.node_template import NodeTemplateModel  # noqa: E501
from sure_tosca.models.topology_template import TopologyTemplateModel  # noqa: E501
from sure_tosca.models.tosca_template import ToscaTemplateModel  # noqa: E501
from sure_tosca.test import BaseTestCase


class TestDefaultController(BaseTestCase):
    """DefaultController integration test stubs"""

    def test_get_all_ancestor_properties(self):
        """Test case for get_all_ancestor_properties


        """
        id_example = self.upload_2_topologies_file()

        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/ancestors_properties'.format(
                id=id_example, node_name='compute'),
            method='GET')
        self.assertEqual(response.status_code, 200)
        self.assertTrue(response.is_json)
        self.assertIsInstance(response.json, list)


    def test_get_all_ancestor_types(self):
        """Test case for get_all_ancestor_types


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/ancestors_types'.format(
                id=id_example, node_name='compute'),
            method='GET')
        self.assertEqual(response.status_code, 200)
        self.assertTrue(response.is_json)
        self.assertIsInstance(response.json, list)

    def test_get_ancestors_requirements(self):
        """Test case for get_ancestors_requirements


        """
        id_example = self.upload_2_topologies_file()

        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/ancestors_requirements'.format(
                id=id_example, node_name='kubernetes'),
            method='GET')
        self.assertEqual(response.status_code, 200)
        self.assertTrue(response.is_json)
        self.assertIsInstance(response.json, list)

    def test_get_dsl_definitions(self):
        """Test case for get_dsl_definitions


        """
        # query_string = [('anchors', 'anchors_example'), ('derived_from', 'derived_from_example')]
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/dsl_definitions'.format(id=id_example),
            method='GET')
        self.assertTrue(response.is_json)

    def test_get_imports(self):
        """Test case for get_imports


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/imports'.format(id=id_example),
            method='GET')
        self.assertTrue(response.is_json)

    def test_get_node_outputs(self):
        """Test case for get_node_outputs


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/outputs'.format(
                id=id_example, node_name='compute'),
            method='GET')
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, dict)

    def test_get_node_properties(self):
        """Test case for get_node_properties


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/properties'.format(
                id=id_example, node_name='compute'),
            method='GET')
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, dict)

    def test_get_node_requirements(self):
        """Test case for get_node_requirements


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/requirements'.format(
                id=id_example, node_name='kubernetes'),
            method='GET')
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, list)

    def test_get_node_templates(self):
        """Test case for get_node_templates


        """
        id_example = self.upload_2_topologies_file()
        query_string = [('type_name', None),
                        ('node_name', 'compute'),
                        ('has_interfaces', True),
                        ('has_properties', None),
                        ('has_attributes', None),
                        ('has_requirements', None),
                        ('has_capabilities', None),
                        ('has_artifacts', None)]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates'.format(id=id_example),
            method='GET',
            query_string=query_string)
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, list)

        query_string = [('type_name', None),
                        ('node_name', None),
                        ('has_interfaces', None),
                        ('has_properties', None),
                        ('has_attributes', None),
                        ('has_requirements', None),
                        ('has_capabilities', None),
                        ('has_artifacts', None)]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates'.format(id=id_example),
            method='GET',
            query_string=query_string)
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, list)

        query_string = [('type_name', 'tosca.nodes.ARTICONF.Container.Application.Docker'),
                        ('node_name', None),
                        ('has_interfaces', None),
                        ('has_properties', None),
                        ('has_attributes', None),
                        ('has_requirements', None),
                        ('has_capabilities', None),
                        ('has_artifacts', None)]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates'.format(id=id_example),
            method='GET',
            query_string=query_string)
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, list)

    def test_get_node_type_name(self):
        """Test case for get_node_type_name


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/type_name'.format(
                id=id_example, node_name='compute'),
            method='GET')
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, str)

    def test_get_parent_type_name(self):
        """Test case for get_parent_type_name


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/derived_from'.format(
                id=id_example, node_name='kubernetes'),
            method='GET')
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, str)

    def test_get_related_nodes(self):
        """Test case for get_related_nodes


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/related'.format(
                id=id_example, node_name='mysql'),
            method='GET')
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, list)

    def test_get_relationship_templates(self):
        """Test case for get_relationship_templates


        """
        id_example = self.upload_2_topologies_file()
        query_string = [('type_name', None),
                        ('derived_from', None)]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/relationship_templates'.format(id=id_example),
            method='GET',
            query_string=query_string)
        self.assertTrue(response.is_json)

    def test_get_topology_template(self):
        """Test case for get_topology_template


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template'.format(id=id_example),
            method='GET')
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, dict)

    def test_get_tosca_template(self):
        """Test case for get_tosca_template


        """
        id_example = self.upload_2_topologies_file()
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}'.format(id=id_example),
            method='GET')
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, dict)

    def test_get_types(self):
        """Test case for get_types


        """
        id_example = self.upload_2_topologies_file()
        query_string = [('kind_of_type', 'interface_types'),
                        ('has_interfaces', None),
                        ('type_name', 'tosca.interfaces.ARTICONF.CloudsStorm'),
                        ('has_properties', None),
                        ('has_attributes', None),
                        ('has_requirements', None),
                        ('has_capabilities', None),
                        ('has_artifacts', None),
                        ('derived_from', None)]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/types'.format(id=id_example),
            method='GET',
            query_string=query_string)
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, list)
        query_string = [('kind_of_type', 'interface_types'),
                        ('has_interfaces', None),
                        ('type_name', None),
                        ('has_properties', None),
                        ('has_attributes', None),
                        ('has_requirements', None),
                        ('has_capabilities', None),
                        ('has_artifacts', None),
                        ('derived_from', 'tosca.interfaces.node.lifecycle.Standard')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/types'.format(id=id_example),
            method='GET',
            query_string=query_string)
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, list)

    def test_set_node_properties(self):
        """Test case for set_node_properties


        """
        id_example = self.upload_2_topologies_file()
        properties = {'properties': {'cpu_frequency': '2 GHz'}}
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates/{node_name}/properties'.format(
                id=id_example, node_name='compute'),
            method='PUT',
            data=json.dumps(properties),
            content_type='application/json')
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, list)

    def upload_2_topologies_file(self):
        tosca_path = "../../../TOSCA/"
        file_name = 'application_example_2_topologies.yaml'  # 'application_example_updated.yaml'  # 'application_example_2_topologies.yaml'
        input_tosca_file_path = tosca_path + '/' + file_name
        if not os.path.exists(input_tosca_file_path):
            tosca_path = "../TOSCA/"
            input_tosca_file_path = tosca_path + '/' + file_name

        dir_path = os.path.dirname(os.path.realpath(__file__))
        self.assertEqual(True, os.path.exists(input_tosca_file_path),
                         'Starting from: ' + dir_path + ' Input TOSCA file: ' + input_tosca_file_path + ' not found')

        with open(input_tosca_file_path, 'r') as file:
            contents = file.read()
        byte_contents = bytes(contents, 'utf8')
        data = dict(file=(BytesIO(byte_contents), input_tosca_file_path))
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template',
            method='POST',
            data=data,
            content_type='multipart/form-data')
        file_id = response.data.decode('utf-8').replace('\n', '')
        return file_id

    def upload_application_example_file(self):
        tosca_path = "../../../TOSCA/"
        file_name = 'application_example_updated.yaml'  # 'application_example_updated.yaml'  # 'application_example_2_topologies.yaml'
        input_tosca_file_path = tosca_path + '/' + file_name
        if not os.path.exists(input_tosca_file_path):
            tosca_path = "../TOSCA/"
            input_tosca_file_path = tosca_path + '/' + file_name

        dir_path = os.path.dirname(os.path.realpath(__file__))
        self.assertEqual(True, os.path.exists(input_tosca_file_path),
                         'Starting from: ' + dir_path + ' Input TOSCA file: ' + input_tosca_file_path + ' not found')

        with open(input_tosca_file_path, 'r') as file:
            contents = file.read()
        byte_contents = bytes(contents, 'utf8')
        data = dict(file=(BytesIO(byte_contents), input_tosca_file_path))
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template',
            method='POST',
            data=data,
            content_type='multipart/form-data')
        file_id = response.data.decode('utf-8').replace('\n', '')
        return file_id

    def test_get_node_templates2(self):
        """Test case for get_node_templates


        """
        id_example = self.upload_application_example_file()

        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/topology_template/node_templates'.format(id=id_example),
            method='GET',
            query_string=None)
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, list)
        self.assertTrue(response.json)

    def test_get_default_interface(self):
        """Test case for get_default_interface


        """
        id_example = self.upload_2_topologies_file()
        query_string = [('instance_name', 'instance_name_example'),
                        ('operation_name', 'provision')]
        response = self.client.open(
            '/tosca-sure/1.0.0/tosca_template/{id}/interface/{interface_type}/default'.format(id=id_example,
                                                                                              interface_type='tosca.interfaces.ARTICONF.CloudsStorm'),
            method='GET',
            query_string=query_string)
        self.assertTrue(response.is_json)
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json, dict)


if __name__ == '__main__':
    import unittest

    unittest.main()
