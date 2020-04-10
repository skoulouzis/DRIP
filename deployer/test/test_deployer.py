import copy
import json
import logging
import os
import os.path
import tempfile
import time

import yaml
import re  # noqa: F401
from pathlib import Path
import unittest

import sure_tosca_client
from sure_tosca_client import Configuration, ApiClient
from sure_tosca_client.api import default_api

from service.deploy_service import DeployService
from service.tosca_helper import  ToscaHelper


class TestDeployer(unittest.TestCase):

    # def test_parse_token(self):
    #     tosca_path = "../../ansible_playbooks/"
    #     example_ansible_output_file_path = tosca_path + '/example_ansible_output.out'
    #     if not os.path.exists(example_ansible_output_file_path):
    #         tosca_path = "../ansible_playbooks/"
    #         example_ansible_output_file_path = tosca_path + '/example_ansible_output.out'
    #
    #     with open(example_ansible_output_file_path, 'r') as file:
    #         out = file.read()
    #     token = ansible_service.parse_dashboard_tokens(out)

    def test(self):
        logger = logging.getLogger(__name__)
        message_file_path = str(Path.home()) + '/Downloads/message_deploy_request.json'

        # example_messages_path = "../../example_messages/"
        # message_file_path = example_messages_path + '/message_deploy_request.json'
        # if not os.path.exists(message_file_path):
        #     example_messages_path = "../example_messages/"
        #     message_file_path = example_messages_path + '/message_deploy_request.json'

        with open(message_file_path, 'r') as stream:
            parsed_json_message = json.load(stream)

        # owner = parsed_json_message['owner']
        tosca_file_name = 'tosca_template'
        tosca_template_dict = parsed_json_message['toscaTemplate']

        tmp_path = tempfile.mkdtemp()
        tosca_template_path = tmp_path + os.path.sep + 'toscaTemplate.yml'
        with open(tosca_template_path, 'w') as outfile:
            yaml.dump(tosca_template_dict, outfile, default_flow_style=False)


        sure_tosca_base_url = 'http://localhost:8081/tosca-sure/1.0.0'
        polemarch_base_url='http://localhost:30001/api/v2'
        semaphore_base_url = 'http://localhost:3000/api'
        tosca_service_is_up = ToscaHelper.service_is_up(sure_tosca_base_url)
        semaphore_is_up = ToscaHelper.service_is_up(semaphore_base_url)

        if tosca_service_is_up and semaphore_is_up:
            tosca_helper = ToscaHelper(sure_tosca_base_url,tosca_template_path)
            self.assertIsNotNone(tosca_helper.doc_id)
            nodes_to_deploy = tosca_helper.get_application_nodes()
            self.assertIsNotNone(nodes_to_deploy)
            nodes_pairs = tosca_helper.get_deployment_node_pairs()
            self.assertIsNotNone(nodes_pairs)

            username = 'admin'
            deployService = DeployService(polemarch_base_url=polemarch_base_url,polemarch_username=username,polemarch_password='admin',
                                          semaphore_base_url=semaphore_base_url,semaphore_username=username,semaphore_password='password')
            for node_pair in nodes_pairs:
                deployService.deploy(node_pair)

        # tmp_path = tempfile.mkdtemp()
        # vms = tosca.get_vms(tosca_template_dict)
        # inventory_path = ansible_service.write_inventory_file(tmp_path, vms)
        # paths = ansible_service.write_playbooks_from_tosca_interface(tosca_interfaces, tmp_path)
        #
        # tokens = {}
        # for playbook_path in paths:
        #     out, err = ansible_service.run(inventory_path, playbook_path)
        #     api_key, join_token, discovery_token_ca_cert_hash = ansible_service.parse_api_tokens(out.decode("utf-8"))
        #     if api_key:
        #         tokens['api_key'] = api_key
        #     if join_token:
        #         tokens['join_token'] = join_token
        #     if discovery_token_ca_cert_hash:
        #         tokens['discovery_token_ca_cert_hash'] = discovery_token_ca_cert_hash
        #
        # ansible_playbook_path = k8s_service.write_ansible_k8s_files(tosca_template_dict, tmp_path)
        # out, err = ansible_service.run(inventory_path, ansible_playbook_path)
        # dashboard_token = ansible_service.parse_dashboard_tokens(out.decode("utf-8"))
        #
        # tokens['dashboard_token'] = dashboard_token
        #
        # tosca_template_dict = tosca.add_tokens(tokens, tosca_template_dict)
        #
        # tosca_template_dict = tosca.add_dashboard_url(k8s_service.get_dashboard_url(vms), tosca_template_dict)
        #
        # response = {'toscaTemplate': tosca_template_dict}
        # output_current_milli_time = int(round(time.time() * 1000))
        # response["creationDate"] = output_current_milli_time
        # logger.info("Returning Deployment")
        # logger.info("Output message:" + json.dumps(response))
        # print(json.dumps(response))





    def get_tosca_file(self, file_name):
        tosca_path = "../../TOSCA/"
        input_tosca_file_path = tosca_path + '/' + file_name
        if not os.path.exists(input_tosca_file_path):
            tosca_path = "../TOSCA/"
            input_tosca_file_path = tosca_path + '/' + file_name

        dir_path = os.path.dirname(os.path.realpath(__file__))
        self.assertEqual(True, os.path.exists(input_tosca_file_path),
                         'Starting from: ' + dir_path + ' Input TOSCA file: ' + input_tosca_file_path + ' not found')
        return input_tosca_file_path




if __name__ == '__main__':
    import unittest

    unittest.main()
