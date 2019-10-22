import os
import os.path
import tempfile
import unittest

from planner.planner import *
from planner.spec_service import SpecService
from utils import tosca as tosca_util


class MyTestCase(unittest.TestCase):
    def test_something(self):
        tosca_path = "../../TOSCA/"
        tosca_file_path = tosca_path+'/application_example_updated.yaml'
        conf = {'url': "http://host"}
        spec_service = SpecService(conf)
        test_planner = Planner(tosca_file_path, spec_service)
        tosca_template = test_planner.resolve_requirements()
        tosca_template = test_planner.set_infrastructure_specifications()
        template = tosca_util.get_tosca_template_2_topology_template_dictionary(tosca_template)
        # logger.info("template ----: \n" + yaml.dump(template))

        try:
            tosca_folder_path = os.path.join(tempfile.gettempdir(), tosca_path)
        except NameError:
            import sys

            tosca_folder_path = os.path.dirname(os.path.abspath(sys.argv[0])) + os.path.join(tempfile.gettempdir(),
                                                                                             tosca_path)
        tosca_file_name = 'tosca_template'
        input_tosca_file_path = tosca_path+'/application_example_output.yaml'

        with open(input_tosca_file_path, 'w') as outfile:
            outfile.write(yaml.dump(template))

        ToscaTemplate(input_tosca_file_path)
        self.assertEqual(True, True)
