import copy
import json
import logging
import os
import os.path
import tempfile
import time

import yaml
import unittest

import sure_tosca_client


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
        tosca_path = "../../example_messages/"
        input_tosca_file_path = tosca_path + '/message_provision_response.json'
        if not os.path.exists(input_tosca_file_path):
            tosca_path = "../example_messages/"
            input_tosca_file_path = tosca_path + '/message_provision_response.json'

        with open(input_tosca_file_path, 'r') as stream:
            parsed_json_message = json.load(stream)

        print(parsed_json_message)
        # owner = parsed_json_message['owner']
        tosca_file_name = 'tosca_template'
        tosca_template_dict = parsed_json_message['toscaTemplate']

        tmp_path = tempfile.mkdtemp()
        tosca_template_path = tmp_path + os.path.sep + 'toscaTemplate.yml'
        with open(tosca_template_path, 'w') as outfile:
            yaml.dump(tosca_template_dict, outfile, default_flow_style=False)

        tosca_client = init_sure_tosca_client('http://localhost:8081/tosca-sure/1.0.0/')
        doc_id = tosca_client.upload_tosca_template(tosca_template_path)
        print(doc_id)

        # tosca_interfaces = tosca.get_interfaces(tosca_template_dict)
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


def init_sure_tosca_client(sure_tosca_base_path):
    configuration = sure_tosca_client.Configuration()
    sure_tosca_client.configuration.host = sure_tosca_base_path
    api_client = sure_tosca_client.ApiClient(configuration=configuration)
    sure_tosca_client_api = sure_tosca_client.api.default_api.DefaultApi(api_client=api_client)  # noqa: E501
    return sure_tosca_client_api


if __name__ == '__main__':
    import unittest

    unittest.main()
