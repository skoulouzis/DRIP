import os
import tempfile
from unittest import TestCase

import requests
from six import BytesIO
from werkzeug.datastructures import FileStorage
import logging

from sure_tosca.models import ToscaTemplateModel
from sure_tosca.service import tosca_template_service

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
h = logging.StreamHandler()
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
h.setFormatter(formatter)
logger.addHandler(h)
logger.handler_set = True
base_url = 'https://raw.githubusercontent.com/qcdis-sdia/sdia-tosca/master/examples/'

class Test(TestCase):
    def test_upload(self):

        file_names = ['application_example_outputs.yaml','application_example_2_topologies.yaml','lifeWatch_vre1.yaml',
                      'application_example_updated.yaml','compute.yaml','application_example_provisioned.yaml','topology.yaml',
                      'kubernetes.yaml','TIC.yaml']

        for file_name in file_names:
            logger.info("Testing : " + str(file_name))
            doc_id = tosca_template_service.save(self.upload_file(base_url+file_name))
            logger.info("doc_id : " + str(doc_id))
            self.assertTrue (doc_id >= 0)

    def test_get_tosca_template_model_by_id(self):
        doc_id = tosca_template_service.save(self.upload_file('application_example_updated.yaml'))
        tosca_template_dict = tosca_template_service.get_tosca_template_dict_by_id(doc_id)
        tosca_template_model = ToscaTemplateModel.from_dict(tosca_template_dict)

        self.assertIsNotNone(tosca_template_model)
        self.assertIsNotNone(tosca_template_model.topology_template)
        self.assertIsNotNone(tosca_template_model.topology_template.node_templates)


    def test_get_tosca_template_model_by_id(self):
        doc_id = tosca_template_service.save(self.upload_file(base_url+'application_example_updated.yaml'))
        tosca_template_dict = tosca_template_service.get_tosca_template_dict_by_id(doc_id)
        tosca_template_model = ToscaTemplateModel.from_dict(tosca_template_dict)

        self.assertIsNotNone(tosca_template_model)
        self.assertIsNotNone(tosca_template_model.topology_template)
        self.assertIsNotNone(tosca_template_model.topology_template.node_templates)


    def upload_file(self, url):
        input_tosca_file_path = self.get_remote_tosca_file(url)
        dir_path = os.path.dirname(os.path.realpath(__file__))
        self.assertEqual(True, os.path.exists(input_tosca_file_path),
                         'Starting from: ' + dir_path + ' Input TOSCA file: ' + input_tosca_file_path + ' not found')
        file = open(input_tosca_file_path, "r")
        # with open(input_tosca_file_path, 'r') as file:
        #     contents = file.read()
        # byte_contents = bytes(contents, 'utf8')

        with open(input_tosca_file_path, 'r') as file:
            contents = file.read()
        byte_contents = bytes(contents, 'utf8')
        # data = dict(file=(BytesIO(byte_contents), input_tosca_file_path))

        return FileStorage(stream=BytesIO(byte_contents), filename=input_tosca_file_path, name=input_tosca_file_path,
                           content_type=None,
                           content_length=None, headers=None)


    def get_remote_tosca_file(self, url):
        tosca = requests.get(url)
        input_tosca_file_path = os.path.join(tempfile.gettempdir(),'test_tosca_file.yaml')
        open( input_tosca_file_path, 'wb').write(tosca.content)
        return input_tosca_file_path
