def get_interface_types(target):
    print(target.node_template.interfaces)
    interface_types = []
    for interface in target.node_template.interfaces:
        interface_types.append(interface)

    return interface_types


def deploy(nodes_pair):
    target = nodes_pair[0]
    source = nodes_pair[1]
    interface_types = get_interface_types(source)
    if 'Kubernetes' in interface_types:
        kubernetes_service.deploy(nodes_pair)
    elif 'Kubernetes' in interface_types:

    print(source)
    print(target)

    return None
