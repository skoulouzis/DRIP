import os
from _ast import mod

import yaml

yaml.Dumper.ignore_aliases = lambda *args: True


def get_templates_directory_path(file_name):
    template_path = "./k8s/"
    template_file_path = template_path + file_name
    if not os.path.exists(template_file_path):
        template_path = "../k8s/"
        template_file_path = template_path + file_name
    return os.path.abspath(template_file_path)


def get_yaml_data(file_name):
    template_file_path = get_templates_directory_path(file_name)
    with open(template_file_path, 'r') as stream:
        data = yaml.safe_load(stream)
    return data


def get_dockers(tosca_template_json):
    dockers = []
    node_templates = tosca_template_json['topology_template']['node_templates']
    for node_name in node_templates:
        if node_templates[node_name]['type'] == 'tosca.nodes.ARTICONF.Container.Application.Docker':
            docker = {node_name: node_templates[node_name]}
            dockers.append(docker)
    return dockers


def create_service_definition(docker_name, docker):
    k8s_service = get_yaml_data('template-service.yaml')
    k8s_service['metadata']['labels']['app'] = docker_name
    k8s_service['metadata']['name'] = docker_name
    docker_ports = docker[docker_name]['properties']['ports'][0].split(':')
    k8s_service['spec']['ports'][0]['port'] = int(docker_ports[1])
    k8s_service['spec']['ports'][0]['nodePort'] = int(docker_ports[0])
    k8s_service['spec']['selector']['app'] = docker_name
    return k8s_service


def create_deployment_definition(docker_name, docker):
    docker_ports = docker[docker_name]['properties']['ports'][0].split(':')
    deployment = get_yaml_data('template-deployment.yaml')
    deployment['metadata']['labels']['app'] = docker_name
    deployment['metadata']['name'] = docker_name
    deployment['spec']['selector']['matchLabels']['app'] = docker_name
    deployment['spec']['template']['metadata']['labels']['app'] = docker_name
    deployment['spec']['template']['spec']['containers'][0]['image'] = docker[docker_name]['artifacts']['image']['file']
    deployment['spec']['template']['spec']['containers'][0]['ports'][0]['containerPort'] = int(docker_ports[1])
    deployment['spec']['template']['spec']['containers'][0]['name'] = docker_name
    if docker[docker_name]['properties'] and 'environment' in docker[docker_name]['properties']:
        env_list = []
        for env in docker[docker_name]['properties']['environment']:
            k8s_env = {'name': env, 'value': docker[docker_name]['properties']['environment'][env]}
            env_list.append(k8s_env)
        deployment['spec']['template']['spec']['containers'][0]['env'] = env_list
    return deployment


def get_k8s_definitions(dockers):
    k8s_services = []
    k8s_deployments = []
    definitions = {}
    for docker in dockers:
        docker_name = next(iter(docker))

        k8s_service = create_service_definition(docker_name, docker)
        k8s_services.append(k8s_service)
        # -----------------------------------------------------------
        deployment = create_deployment_definition(docker_name, docker)
        k8s_deployments.append(deployment)
        # print(yaml.dump(deployment))
    definitions['services'] = k8s_services
    definitions['deployments'] = k8s_deployments
    return definitions


def create_pip_task():
    pip_task = {'name': 'install pip modules'}
    modules = ['setuptools', 'kubernetes', 'openshift']
    pip = {'name': modules}
    pip_task['pip'] = pip
    return pip_task


def create_service_task(i, services_def):
    task = {'name': 'Create a Service object' + str(i)}
    k8s = {'state': 'present', 'definition': services_def}
    task['k8s'] = k8s
    return task


def create_deployment_task(i, deployments_def):
    task = {'name': 'Create a deployment object' + str(i)}
    k8s = {'state': 'present', 'definition': deployments_def}
    task['k8s'] = k8s
    return task


def create_namespace_task():
    task = {'name': 'create namespace'}
    k8s = {'name': 'application', 'api_version': 'v1', 'kind': 'Namespace', 'state': 'present'}
    task['k8s'] = k8s
    return task


def create_dashboard_task(def_src):
    task = {'name': 'create_dashboard'}
    k8s = {'state': 'present', 'src': def_src}
    task['k8s'] = k8s
    return task


def create_admin_dashboard_task():
    admin_service_account_def = get_yaml_data("admin_service_account.yaml")
    task = {'name': 'create_admin_dashboard'}
    k8s = {'state': 'present', 'definition': admin_service_account_def}
    task['k8s'] = k8s
    return task


def create_admin_cluster_role_binding_task():
    admin_cluster_role_binding_def = get_yaml_data("admin_cluster_role_binding.yaml")
    task = {'name': 'create_admin_cluster_role_binding'}
    k8s = {'state': 'present', 'definition': admin_cluster_role_binding_def}
    task['k8s'] = k8s
    return task


def create_copy_task(src, dest):
    copy = {'src': src, 'dest': dest}
    task = {'name': 'copy task src: ' + src + ' dest: ' + dest, 'copy': copy}
    return task


def create_get_admin_token_task():
    task = {'name': 'get token',
            'shell': 'kubectl describe secret $(kubectl get secret | grep admin-user | awk \'{print $1}\')',
            'register': 'dashboard_token'}
    return task


def create_print_admin_token_task():
    var = {'var': 'dashboard_token'}
    task = {'name': 'print token',
            'debug': var}
    return task


def write_ansible_k8s_files(tosca_template_json, tmp_path):
    dockers = get_dockers(tosca_template_json)
    k8s_definitions = get_k8s_definitions(dockers)
    services = k8s_definitions['services']
    deployments = k8s_definitions['deployments']
    i = 0
    tasks = []
    pip_task = create_pip_task()
    tasks.append(pip_task)

    # namespace_task = create_namespace_task()
    # tasks.append(namespace_task)

    def_src = '/tmp/dashboard.yaml'
    copy_task = create_copy_task(get_templates_directory_path('dashboard.yaml'), def_src)
    tasks.append(copy_task)

    dashboard_task = create_dashboard_task(def_src)
    tasks.append(dashboard_task)

    dashboard_admin_task = create_admin_dashboard_task()
    tasks.append(dashboard_admin_task)

    admin_cluster_role_binding_task = create_admin_cluster_role_binding_task()
    tasks.append(admin_cluster_role_binding_task)

    get_admin_token_task = create_get_admin_token_task()
    tasks.append(get_admin_token_task)

    print_admin_token_task = create_print_admin_token_task()
    tasks.append(print_admin_token_task)

    for services_def in services:
        task = create_service_task(i, services_def)
        i += 1
        tasks.append(task)

    i = 0
    for deployments_def in deployments:
        task = create_deployment_task(i, deployments_def)
        i += 1
        tasks.append(task)

    ansible_playbook = []
    plays = {'hosts': 'k8-master', 'tasks': tasks}
    ansible_playbook.append(plays)
    # print(yaml.safe_dump(ansible_playbook))
    ansible_playbook_path = tmp_path + '/' + 'k8s_playbook.yml'

    with open(ansible_playbook_path, 'w') as file:
        documents = yaml.dump(ansible_playbook, file)

    return ansible_playbook_path


def get_dashboard_url(vms):
    dashboard_tasks_path = get_templates_directory_path('dashboard.yaml')
    with open(dashboard_tasks_path, 'r') as stream:
        tasks = list(yaml.load_all(stream))
    for task in tasks:
        if task['kind'] == 'Service' and 'name' in task['metadata'] and task['metadata']['name'] and task['metadata'][
            'name'] == 'kubernetes-dashboard':
            dashboard_port = task['spec']['ports'][0]['nodePort']
    for vm_name in vms:
        attributes = vms[vm_name]['attributes']
        role = attributes['role']
        if role == 'master':
            k8_master = attributes['public_ip']
    url = 'https://' + k8_master + ':' + str(dashboard_port)
    return url
