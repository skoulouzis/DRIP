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
        # owner = parsed_json_message['owner']
        tosca_file_name = 'tosca_template'
        tosca_template_dict = parsed_json_message['toscaTemplate']

        tosca_interfaces = tosca.get_interfaces(tosca_template_dict)
        tmp_path = tempfile.mkdtemp()
        vms = tosca.get_vms(tosca_template_dict)
        inventory_path = ansible_service.write_inventory_file(tmp_path, vms)
        paths = ansible_service.write_playbooks_from_tosca_interface(tosca_interfaces, tmp_path)

        tokens = {}
        for playbook_path in paths:
            out, err = ansible_service.run(inventory_path, playbook_path)
            api_key, join_token, discovery_token_ca_cert_hash = ansible_service.parse_api_tokens(out.decode("utf-8"))
            if api_key:
                tokens['api_key'] = api_key
            if join_token:
                tokens['join_token'] = join_token
            if discovery_token_ca_cert_hash:
                tokens['discovery_token_ca_cert_hash'] = discovery_token_ca_cert_hash

        ansible_playbook_path = k8s_service.write_ansible_k8s_files(tosca_template_dict, tmp_path)
        out, err = ansible_service.run(inventory_path, ansible_playbook_path)
        dashboard_token = ansible_service.parse_dashboard_tokens(out.decode("utf-8"))

        tokens['dashboard_token'] = dashboard_token

        tosca_template_dict = tosca.add_tokens(tokens, tosca_template_dict)

        tosca_template_dict = tosca.add_dashboard_url(k8s_service.get_dashboard_url(vms), tosca_template_dict)

        response = {'toscaTemplate': tosca_template_dict}
        output_current_milli_time = int(round(time.time() * 1000))
        response["creationDate"] = output_current_milli_time
        logger.info("Returning Deployment")
        logger.info("Output message:" + json.dumps(response))


if __name__ == '__main__':
    import unittest

    unittest.main()
