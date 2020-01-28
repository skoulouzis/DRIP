def get_interfaces(tosca_template_json):
    node_templates = tosca_template_json['topology_template']['node_templates']
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.Orchestrator.Kubernetes':
            return node_templates[node_name]['interfaces']


def get_vms(tosca_template_json):
    node_templates = tosca_template_json['topology_template']['node_templates']
    vms = {}
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.VM.Compute':
            vms[node_name] = node_templates[node_name]
    return vms
