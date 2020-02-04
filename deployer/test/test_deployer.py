import copy
import json
import logging
import os
import os.path
import tempfile
import time
import unittest

from service import k8s_service, tosca, ansible_service


class TestDeployer(unittest.TestCase):

    def test(self):
        logger = logging.getLogger(__name__)
        tosca_path = "../../TOSCA/"
        input_tosca_file_path = tosca_path + '/message_example_provisioned.json'
        if not os.path.exists(input_tosca_file_path):
            tosca_path = "../TOSCA/"
            input_tosca_file_path = tosca_path + '/message_example_provisioned.json'

        with open(input_tosca_file_path, 'r') as stream:
            parsed_json_message = json.load(stream)

        # parsed_json_message = json.loads(message)
        owner = parsed_json_message['owner']
        tosca_file_name = 'tosca_template'
        tosca_template_json = parsed_json_message['toscaTemplate']

        interfaces = tosca.get_interfaces(tosca_template_json)
        tmp_path = tempfile.mkdtemp()
        vms = tosca.get_vms(tosca_template_json)
        inventory_path = ansible_service.write_inventory_file(tmp_path, vms)
        paths = ansible_service.write_playbooks_from_tosca_interface(interfaces, tmp_path)
        # for playbook_path in paths:
        #     out,err = ansible_service.run(inventory_path,playbook_path)
        # api_key, join_token, discovery_token_ca_cert_hash = ansible_service.parse_tokens(out.decode("utf-8"))

        ansible_playbook_path = k8s_service.write_ansible_k8s_files(tosca_template_json, tmp_path)
        # out, err = ansible_service.run(inventory_path, ansible_playbook_path)


if __name__ == '__main__':
    import unittest

    unittest.main()
