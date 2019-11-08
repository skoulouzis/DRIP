import copy

from toscaparser.nodetemplate import NodeTemplate
from toscaparser.properties import Property

import networkx as nx
import logging

from service.specification_analyzer import SpecificationAnalyzer
from util import tosca_helper


class SimpleAnalyzer(SpecificationAnalyzer):

    def __init__(self, tosca_template):
        super(SimpleAnalyzer, self).__init__(tosca_template)

    def set_relationship_occurrences(self):
        return_nodes = []
        # nodes_with_occurrences_in_requirements = tosca_util.get_nodes_with_occurrences_in_requirements(
        # self.tosca_template.nodetemplates)
        orchestrator_nodes = tosca_helper.get_nodes_by_type('tosca.nodes.ARTICONF.Orchestrator',
                                                            self.tosca_template.nodetemplates, self.all_node_types,
                                                            self.all_custom_def)

        if 'properties' in orchestrator_nodes[0].entity_tpl:
            if 'min_masters_num' in orchestrator_nodes[0].entity_tpl['properties']:
                min_masters_num = orchestrator_nodes[0].entity_tpl['properties']['min_masters_num']
            if 'min_workers_num' in orchestrator_nodes[0].entity_tpl['properties']:
                workers_num = orchestrator_nodes[0].entity_tpl['properties']['min_workers_num']
        else:
            min_masters_num = orchestrator_nodes[0].get_property_value('min_masters_num')
            workers_num = orchestrator_nodes[0].get_property_value('min_workers_num')

        topology_nodes = tosca_helper.get_nodes_by_type('tosca.nodes.ARTICONF.VM.topology',
                                                        self.tosca_template.nodetemplates, self.all_node_types,
                                                        self.all_custom_def)

        # for requirement in topology_nodes[0].requirements:
        #     requirement_dict = requirement[next(iter(requirement))]
        #     if requirement_dict['capability'] == 'tosca.capabilities.ARTICONF.VM':
        #         requirement_dict['occurrences'] = min_num_of_vm

        vm_nodes = tosca_helper.get_nodes_by_type('tosca.nodes.ARTICONF.VM.Compute',
                                                  self.tosca_template.nodetemplates, self.all_node_types,
                                                  self.all_custom_def)
        if vm_nodes:
            for i in range(len(vm_nodes), min_masters_num):
                old_vm_name = vm_nodes[0].name
                new_vm = copy.deepcopy(vm_nodes[0])
                new_vm_name = new_vm.name + '_' + str(i)
                new_vm.name = new_vm_name
                templates = new_vm.templates.pop(old_vm_name)
                new_vm.templates[new_vm_name] = templates

                return_nodes.append(new_vm)
                for requirement in topology_nodes[0].requirements:
                    requirement_key = next(iter(requirement))
                    requirement_value = requirement[requirement_key]
                    if requirement_value['capability'] == 'tosca.capabilities.ARTICONF.VM':
                        new_requirement = copy.deepcopy(requirement)
                        new_requirement[requirement_key]['node'] = new_vm.name
                        topology_nodes[0].requirements.append(new_requirement)
                        return_nodes.append(topology_nodes[0])
                        break

            for i in range(len(vm_nodes), workers_num + 1):
                old_vm_name = vm_nodes[0].name
                new_vm = copy.deepcopy(vm_nodes[0])
                new_vm_name = new_vm.name + '_' + str(i)
                new_vm.name = new_vm_name
                templates = new_vm.templates.pop(old_vm_name)
                new_vm.templates[new_vm_name] = templates

                return_nodes.append(new_vm)
                for requirement in topology_nodes[0].requirements:
                    requirement_key = next(iter(requirement))
                    requirement_value = requirement[requirement_key]
                    if requirement_value['capability'] == 'tosca.capabilities.ARTICONF.VM':
                        new_requirement = copy.deepcopy(requirement)
                        new_requirement[requirement_key]['node'] = new_vm.name
                        topology_nodes[0].requirements.append(new_requirement)
                        return_nodes.append(topology_nodes[0])
                        break

        return return_nodes

    def set_node_specifications(self):
        nodes_to_implement_policies = self.get_nodes_to_implement_policy()
        affected_nodes = []
        for node_name in nodes_to_implement_policies:
            policies = nodes_to_implement_policies[node_name]
            affected_node = self.set_specs(node_name, policies, self.tosca_template.nodetemplates)
            if affected_node:
                affected_nodes.append(affected_node)

        return affected_nodes

    def get_nodes_to_implement_policy(self):
        nodes_to_implement_policies = {}

        for policy in self.tosca_template.policies:
            for target in policy.targets:
                for leaf in self.leaf_nodes:
                    for affected_node_name in (nx.shortest_path(self.g, source=target, target=leaf)):
                        if affected_node_name not in nodes_to_implement_policies:
                            policy_list = []
                            nodes_to_implement_policies[affected_node_name] = policy_list
                        policy_list = nodes_to_implement_policies[affected_node_name]
                        policy_list.append(policy.type)
                        nodes_to_implement_policies[affected_node_name] = policy_list
        return nodes_to_implement_policies

    def set_node_properties_for_policy(self, affected_node, policies):
        logging.info('Setting properties for: ' + str(affected_node.type))

        # ancestors_types = tosca_helper.get_all_ancestors_types(affected_node, self.all_node_types, self.all_custom_def)
        # if 'tosca.nodes.ARTICONF.Orchestrator' in ancestors_types:
        #     logging.info('Do Something')
        ancestors_properties = tosca_helper.get_all_ancestors_properties(affected_node, self.all_node_types,
                                                                         self.all_custom_def)

        default_properties = {}
        for ancestors_property in ancestors_properties:
            default_property = self.get_defult_value(ancestors_property)
            if default_property:
                default_properties[next(iter(default_property))] = default_property[next(iter(default_property))]

        if default_properties:
            for default_property in default_properties:
                affected_node.get_properties_objects().append(default_property)

            if 'properties' in affected_node.templates[next(iter(affected_node.templates))]:
                for prop_name in affected_node.templates[next(iter(affected_node.templates))]['properties']:
                    if 'required' not in affected_node.templates[next(iter(affected_node.templates))]['properties'][
                        prop_name] and 'type' not in \
                            affected_node.templates[next(iter(affected_node.templates))]['properties'][prop_name]:
                        default_properties[prop_name] = \
                            affected_node.templates[next(iter(affected_node.templates))]['properties'][prop_name]

            affected_node.templates[next(iter(affected_node.templates))]['properties'] = default_properties

            return affected_node
        else:
            return None

    def set_specs(self, node_name, policies, nodes_in_template):
        logging.info('node_name: ' + str(node_name) + ' will implement policies: ' + str(len(policies)))
        for node in nodes_in_template:
            if node.name == node_name:
                affected_node = node
                break

        logging.info('node: ' + str(affected_node.type) + ' will implement policies: ' + str(len(policies)))
        affected_node = self.set_node_properties_for_policy(affected_node, policies)

        return affected_node

    def get_defult_value(self, node_property):
        if node_property and node_property.required and isinstance(node_property.value, dict) and 'required' in \
                node_property.value and 'type' in node_property.value:
            if node_property.default:
                return {node_property.name: node_property.default}
            if node_property.constraints:
                for constraint in node_property.constraints:
                    print(constraint)
        if node_property and node_property.required and node_property.value:
            return {node_property.name: node_property.value}
        return None
