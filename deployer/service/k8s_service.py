import yaml

yaml.Dumper.ignore_aliases = lambda *args: True


def get_dockers(tosca_template_json):
    dockers = []
    node_templates = tosca_template_json['topology_template']['node_templates']
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.Container.Application.Docker':
            docker = {node_name: node_templates[node_name]}
            dockers.append(docker)
    return dockers


def get_k8s_definitions(dockers):
    k8s_services = []
    k8s_deployments = []
    definitions = {}
    for docker in dockers:
        docker_name = next(iter(docker))
        k8s_service = {'apiVersion': 'v1', 'kind': 'Service'}
        labels = {'app': docker_name}
        metadata = {'labels': labels, 'name': docker_name}
        k8s_service['metadata'] = metadata
        spec = {'type': 'NodePort'}
        docker_ports = docker[docker_name]['properties']['ports'][0].split(':')
        ports = {'port': docker_ports[1], 'nodePort': docker_ports[0]}
        spec['ports'] = ports
        app = {'app': docker_name}
        spec['selector'] = app
        k8s_service['spec'] = spec
        # print(yaml.safe_dump(k8s_service))
        k8s_services.append(k8s_service)
        # -----------------------------------------------------------
        deployment = {'apiVersion': 'apps/v1', 'kind': 'Deployment', 'metadata': metadata}

        match_labels = {'app': docker_name}
        selector = {'matchLabels': match_labels}
        spec = {'selector': selector, 'replicas': 1}

        labels['app'] = docker_name
        metadata = {'labels': labels}
        template = {'metadata': metadata}

        containers = []
        container = {'image': docker[docker_name]['artifacts']['image']['file'], 'name': docker_name}
        ports = {'containerPort': docker_ports[1]}
        container['ports'] = ports
        containers.append(container)
        template_spec = {'containers': containers}
        template['spec'] = template_spec

        spec['template'] = template

        deployment['spec'] = spec
        k8s_deployments.append(deployment)
        # print(yaml.dump(deployment))
    definitions['services'] = k8s_services
    definitions['deployments'] = k8s_deployments
    return definitions


def write_ansible_k8s_files(tosca_template_json, tmp_path):
    dockers = get_dockers(tosca_template_json)
    k8s_definitions = get_k8s_definitions(dockers)
    services = k8s_definitions['services']
    deployments = k8s_definitions['deployments']
    i = 0
    tasks = []
    for services_def in services:
        task = {'name': 'Create a Service object' + str(i)}
        k8s = {'state': 'present', 'definition': services_def}
        task['k8s'] = k8s
        i += 1
        tasks.append(task)

    i = 0
    for deployments_def in deployments:
        task = {'name': 'Create a deployment object' + str(i)}
        k8s = {'state': 'present', 'definition': deployments_def}
        task['k8s'] = k8s
        i += 1
        tasks.append(task)

    ansible_playbook = []
    plays = {'hosts': 'k8-master', 'tasks': tasks}
    ansible_playbook.append(plays)
    print(yaml.safe_dump(ansible_playbook))
    ansible_playbook_path = tmp_path + '/' + 'k8s_playbook.yml'

    with open(ansible_playbook_path, 'w') as file:
        documents = yaml.dump(ansible_playbook, file)
    return ansible_playbook_path
