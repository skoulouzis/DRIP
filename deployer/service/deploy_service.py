from service import tosca_helper
from service.ansible_service import execute


def deploy(nodes_pair):
    target = nodes_pair[0]
    source = nodes_pair[1]

    interface_types = tosca_helper.get_interface_types(source)
    if 'Standard' in interface_types:
        execute(nodes_pair)
    print(source)
    print(target)

    return None
