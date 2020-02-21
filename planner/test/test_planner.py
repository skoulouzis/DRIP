import copy
import json
import logging
import os
import os.path
import tempfile
import time
import unittest

import yaml
from toscaparser.tosca_template import ToscaTemplate

from planner.planner import Planner
from service.spec_service import SpecService
from util import tosca_helper


def get_tosca_files_path(file_name):
    tosca_path = "../../TOSCA/"
    input_tosca_file_path = tosca_path + file_name
    if not os.path.exists(input_tosca_file_path):
        tosca_path = "../TOSCA/"
        input_tosca_file_path = tosca_path + file_name
    return input_tosca_file_path


class MyTestCase(unittest.TestCase):

    # def test_planner1(self):
    #     logger = logging.getLogger(__name__)
    #
    #     input_tosca_file_path = get_tosca_files_path('application_example_updated.yaml')
    #
    #     self.assertEqual(True, os.path.exists(input_tosca_file_path),
    #                      "Input TOSCA file: " + input_tosca_file_path + " not found")
    #
    #     conf = {'url': "http://host"}
    #     spec_service = SpecService(conf)
    #     test_planner = Planner(input_tosca_file_path, spec_service)
    #     test_tosca_template = test_planner.resolve_requirements()
    #     test_tosca_template = test_planner.set_infrastructure_specifications()
    #     template_dict = tosca_helper.get_tosca_template_2_topology_template_dictionary(test_tosca_template)
    #     logger.info("template ----: \n" + yaml.dump(template_dict))
    #     ToscaTemplate(yaml_dict_tpl=copy.deepcopy(template_dict))
    #
    #     test_response = {'toscaTemplate': template_dict}
    #
    #     response = {'toscaTemplate': template_dict}
    #     output_current_milli_time = int(round(time.time() * 1000))
    #     response["creationDate"] = output_current_milli_time
    #     response["parameters"] = []
    #     print("Output message:" + json.dumps(response))
    #     self.assertEqual(True, True)



    def test_planner2(self):
        logger = logging.getLogger(__name__)

        input_tosca_file_path = get_tosca_files_path('lifeWatch_vre1.yaml')

        self.assertEqual(True, os.path.exists(input_tosca_file_path),
                         "Input TOSCA file: " + input_tosca_file_path + " not found")

        conf = {'url': "http://host"}
        spec_service = SpecService(conf)
        test_planner = Planner(input_tosca_file_path, spec_service)
        test_tosca_template = test_planner.resolve_requirements()
        test_tosca_template = test_planner.set_infrastructure_specifications()
        template_dict = tosca_helper.get_tosca_template_2_topology_template_dictionary(test_tosca_template)
        logger.info("template ----: \n" + yaml.dump(template_dict))
        ToscaTemplate(yaml_dict_tpl=copy.deepcopy(template_dict))

        test_response = {'toscaTemplate': template_dict}

        response = {'toscaTemplate': template_dict}
        output_current_milli_time = int(round(time.time() * 1000))
        response["creationDate"] = output_current_milli_time
        response["parameters"] = []
        print("Output message:" + json.dumps(response))
        self.assertEqual(True, True)
