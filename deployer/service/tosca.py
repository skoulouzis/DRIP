import logging

logger = logging.getLogger(__name__)


def get_interfaces(tosca_template_dict):
    node_templates = tosca_template_dict['topology_template']['node_templates']
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.docker.Orchestrator.Kubernetes':
            logger.info("Returning interfaces from tosca_template: " + str(node_templates[node_name]['interfaces']))
            return node_templates[node_name]['interfaces']


def get_vms(tosca_template_json):
    node_templates = tosca_template_json['topology_template']['node_templates']
    vms = {}
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.VM.Compute':
            vms[node_name] = node_templates[node_name]
    logger.info("Returning VMs from tosca_template: " + str(vms))
    return vms


def add_tokens(tokens, tosca_template_dict):
    node_templates = tosca_template_dict['topology_template']['node_templates']
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.docker.Orchestrator.Kubernetes':
            creds = []
            for token_name in tokens:
                cred = {'token_type': 'k8s_token', 'token': tokens[token_name], 'user': token_name}
                creds.append(cred)
            if 'attributes' not in node_templates[node_name]:
                node_templates[node_name]['attributes'] = {}
            attributes = node_templates[node_name]['attributes']
            attributes['tokens'] = creds
    return tosca_template_dict


def add_dashboard_url(dashboard_url, tosca_template_dict):
    node_templates = tosca_template_dict['topology_template']['node_templates']
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.docker.Orchestrator.Kubernetes':
            if 'attributes' not in node_templates[node_name]:
                node_templates[node_name]['attributes'] = {}
            attributes = node_templates[node_name]['attributes']
            attributes['dashboard_url'] = dashboard_url
    return tosca_template_dict


def add_service_url(serviceurls_url, tosca_template_dict):
    node_templates = tosca_template_dict['topology_template']['node_templates']
    for node_name in node_templates:
        if node_templates[node_name]['type'] == '"tosca.nodes.ARTICONF.Container.Application.Docker':
            if 'attributes' not in node_templates[node_name]:
                node_templates[node_name]['attributes'] = {}
            attributes = node_templates[node_name]['attributes']
            attributes['service_url'] = serviceurls_url[node_name]
    return tosca_template_dict
