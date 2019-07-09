import json
import operator
import pdb
import re
from toscaparser.tosca_template import ToscaTemplate
from toscaparser.tosca_template import TopologyTemplate
from toscaparser.elements.nodetype import NodeType
from toscaparser.nodetemplate import NodeTemplate
from toscaparser.utils import yamlparser
import urllib
import urllib.parse
import sys
import pdb
import names
import yaml


# TOSCA template key names
SECTIONS = (DEFINITION_VERSION, DEFAULT_NAMESPACE, TEMPLATE_NAME,
            TOPOLOGY_TEMPLATE, TEMPLATE_AUTHOR, TEMPLATE_VERSION,
            DESCRIPTION, IMPORTS, DSL_DEFINITIONS, NODE_TYPES,
            RELATIONSHIP_TYPES, RELATIONSHIP_TEMPLATES,
            CAPABILITY_TYPES, ARTIFACT_TYPES, DATA_TYPES, INTERFACE_TYPES,
            POLICY_TYPES, GROUP_TYPES, REPOSITORIES,INPUTS,NODE_TEMPLATES,
            OUTPUTS,GROUPS,SUBSTITUION_MAPPINGS,POLICIES,TYPE,REQUIREMENTS,
            ARTIFACTS,PROPERTIES,INTERFACES) = \
           ('tosca_definitions_version', 'tosca_default_namespace',
            'template_name', 'tosca_template', 'template_author',
            'template_version', 'description', 'imports', 'dsl_definitions',
            'node_types', 'relationship_types', 'relationship_templates',
            'capability_types', 'artifact_types', 'data_types',
            'interface_types', 'policy_types', 'group_types', 'repositories',
            'inputs','node_templates','outputs','groups','substitution_mappings',
            'policies','type','requirements','artifacts','properties','interfaces')
                
class TOSCAParser:
    
    def tosca_template2_yaml(self, tosca_template):     
        topology_dict = {}
        topology_dict[DEFINITION_VERSION] = tosca_template.version
        topology_dict[IMPORTS] = tosca_template._tpl_imports()
        topology_dict[DESCRIPTION] = tosca_template.description
        topology_dict[TOPOLOGY_TEMPLATE] = {}
        topology_dict[TOPOLOGY_TEMPLATE][NODE_TEMPLATES] = {}
        node_templates = tosca_template.nodetemplates
        for node_template in node_templates:
            node_template_dict = self.get_node_template_dict(node_template)
            topology_dict[TOPOLOGY_TEMPLATE][NODE_TEMPLATES][node_template.name] = node_template_dict
            
        # If we don't add this then dump uses references for the same dictionary entries i.e. '&id001'
        yaml.Dumper.ignore_aliases = lambda *args : True
        return (yaml.dump(topology_dict,default_flow_style=False))
        
        
    def get_node_template_dict(self,node_template):
        node_template_dict = {}
        node_template_dict[TYPE] = node_template.type
#        node_template_dict[REQUIREMENTS] = {}
        if node_template.requirements:
            node_template_dict[REQUIREMENTS] = node_template.requirements
#        if node_template.interfaces:
#            interfaces = {}
#            for interface in node_template.interfaces:
#                interfaces[interface.type] = {}
#                interfaces[interface.type][interface.name] = interface.implementation
        
#        print( node_template.templates[node_template.name] )
        if ARTIFACTS in node_template.templates[node_template.name].keys():
            node_template_dict[ARTIFACTS] = node_template.templates[node_template.name][ARTIFACTS]
        if PROPERTIES in node_template.templates[node_template.name].keys():
            node_template_dict[PROPERTIES] = node_template.templates[node_template.name][PROPERTIES]
        if INTERFACES in node_template.templates[node_template.name].keys():
            node_template_dict[INTERFACES] = node_template.templates[node_template.name][INTERFACES]            
#        print(dir(node_template))
#        print(node_template.templates)
        
            
        return node_template_dict