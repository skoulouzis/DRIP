import json
import logging
import os
import os.path
import tempfile
import time
import unittest

from src.planner.planner import *
from src.planner.spec_service import SpecService
from src.utils import tosca_helper as tosca_util


class MyTestCase(unittest.TestCase):
    def test_something(self):
        logger = logging.getLogger(__name__)
        tosca_path = "../../../TOSCA/"
        input_tosca_file_path = tosca_path + '/application_example_2_topologies.yaml'

        dir_path = os.path.dirname(os.path.realpath(__file__))
        print(dir_path)

        self.assertEqual(True, os.path.exists(input_tosca_file_path),
                         "Input TOSCA file: " + input_tosca_file_path + " not found")

        conf = {'url': "http://host"}
        spec_service = SpecService(conf)
        test_planner = Planner(input_tosca_file_path, spec_service)
        test_tosca_template = test_planner.resolve_requirements()
        test_tosca_template = test_planner.set_infrastructure_specifications()
        template_dict = tosca_util.get_tosca_template_2_topology_template_dictionary(test_tosca_template)
        logger.info("template ----: \n" + yaml.dump(template_dict))

        try:
            tosca_folder_path = os.path.join(tempfile.gettempdir(), tosca_path)
        except NameError:
            import sys

            tosca_folder_path = os.path.dirname(os.path.abspath(sys.argv[0])) + os.path.join(tempfile.gettempdir(),
                                                                                             tosca_path)
        tosca_file_name = 'tosca_template'
        input_tosca_file_path = tosca_path + '/application_example_output.yaml'

        with open(input_tosca_file_path, 'w') as outfile:
            outfile.write(yaml.dump(template_dict))

        ToscaTemplate(input_tosca_file_path)

        test_response = {'toscaTemplate': template_dict}

        response = {'toscaTemplate': template_dict}
        output_current_milli_time = int(round(time.time() * 1000))
        response["creationDate"] = output_current_milli_time
        response["parameters"] = []
        print("Output message:" + json.dumps(response))
        self.assertEqual(True, True)
