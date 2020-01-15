def get_dockers(tosca_template_json):
    pass


def write_k8s_files(dockers):
    pass


def run(tosca_template_json):
    dockers = get_dockers(tosca_template_json)
    tmp_dir = write_k8s_files(dockers)
    return None