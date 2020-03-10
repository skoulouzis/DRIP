import copy
import json
import logging
import os
import os.path
import tempfile
import time
import unittest

from service import k8s_service, tosca, ansible_service
from tower_cli import get_resource
from tower_cli.exceptions import Found
from tower_cli.conf import settings


class TestDeployer(unittest.TestCase):


    def test_parse_token(self):
        tosca_path = "../../ansible_playbooks/"
        example_ansible_output_file_path = tosca_path + '/example_ansible_output.out'
        if not os.path.exists(example_ansible_output_file_path):
            tosca_path = "../ansible_playbooks/"
            example_ansible_output_file_path = tosca_path + '/example_ansible_output.out'

        with open(example_ansible_output_file_path, 'r') as file:
            out = file.read()
        token = ansible_service.parse_dashboard_tokens(out)

    def test(self):
        logger = logging.getLogger(__name__)
        tosca_path = "../../TOSCA/"
        input_tosca_file_path = tosca_path + '/message_example_provisioned.json'
        if not os.path.exists(input_tosca_file_path):
            tosca_path = "../TOSCA/"
            input_tosca_file_path = tosca_path + '/message_example_provisioned.json'

        with open(input_tosca_file_path, 'r') as stream:
            parsed_json_message = json.load(stream)

        print(parsed_json_message)

        # settings.runtime_values()
        # with settings.runtime_values(host='localhost',username='admin', password='password'):
        #     try:
        #         res = get_resource('organization')
        #         new_org = res.create(name='foo', description='bar', fail_on_found=True)
        #     except Found:
        #         print('This organization already exists.')
        # assert isinstance(new_org, dict)
        # print(new_org['id'])


if __name__ == '__main__':
    import unittest

    unittest.main()
