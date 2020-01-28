def get_dockers(tosca_template_json):
    dockers = []
    node_templates = tosca_template_json['topology_template']['node_templates']
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.Container.Application.Docker':
            dockers.append(node_templates[node_name])
    return dockers


def write_k8s_files(dockers):

    pass


def run(tosca_template_json):
    dockers = get_dockers(tosca_template_json)
    tmp_dir = write_k8s_files(dockers)
    return tmp_dir