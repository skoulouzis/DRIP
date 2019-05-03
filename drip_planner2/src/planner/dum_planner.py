import json
import operator
import pdb
import re
from toscaparser.tosca_template import ToscaTemplate
import toscaparser.utils.yamlparser
import urllib
import urllib.parse

class DumpPlanner:
    service_template_names = ['serviceTemplateOrNodeTypeOrNodeTypeImplementation']
    topology_template_names = ['topologyTemplate']
    node_template_names = ['nodeTemplates']
    requirement_names = ['requirements']
    type_names = ['type']
    relationships_names = ['relationshipTemplates']
    service_templates = None
    topology_templates = None
    node_templates = None
    requirements = {}
    
    def __init__(self, tosca_reposetory_api_base_url, namespace, servicetemplate_id):
        #        dict_tpl = self.load_file(service_templaete_file_path)
        self.tosca_reposetory_api_base_url = tosca_reposetory_api_base_url
        servicetemplate_url = tosca_reposetory_api_base_url + "/servicetemplates/" + namespace + "/" + servicetemplate_id + "/"
        header = {'accept': 'application/json'}
        req = urllib.request.Request(url=servicetemplate_url, headers=header, method='GET')
        res = urllib.request.urlopen(req, timeout=5)   
        res_body = res.read()
        dict_tpl = json.loads(res_body.decode("utf-8"))
        
        service_templates = self.get_service_template(dict_tpl)
        requirements = self.get_all_requirements(service_templates)
        relationships = self.get_all_relationships(dict_tpl)
        unmet_requirements = {}
        for node_name in requirements:
            relationship = self.get_relationship_of_source_node(node_name, relationships)
            if relationship:
                for requirement in requirements[node_name]:
                    if (not self.target_meets_requirement(relationship['targetElement']['ref'], requirement)):
                        unmet_requirements[node_name] = requirement
        
#        unmet_requirements = self.get_unmet_requirements(requirements)
#        print(unmet_requirements)
#        yaml_dict_tpl = self.trnsform_to_tosca(yaml_dict_tpl)


        
    def load_file(self, path):
        is_json = True
        with open(path) as f:
            try:            
                dict_tpl = json.load(f)
            except Exception as e:
                if (not isinstance(e, json.decoder.JSONDecodeError)):
                    raise e
                else:
                    is_json = False
        if is_json:       
            return dict_tpl
        else:
            return toscaparser.utils.yamlparser.load_yaml(path)
        
        
    def trnsform_to_tosca(self, yaml_dict_tpl):        
        try:      
            self.tt = ToscaTemplate(path=None, yaml_dict_tpl=yaml_dict_tpl)
        except Exception as e:
            self.handle_tosca_exeption(e, yaml_dict_tpl)
            
    def handle_tosca_exeption(self, exception, yaml_dict_tpl):  
        print(exception)
        
    def target_meets_requirement(self, target_relationship_element, requirement):
        node = self.get_node(target_relationship_element)
        if 'capabilities' in node:
            print(node['capabilities'])
        else:
            supertypes = self.get_super_types(node['type'], None)
            print(supertypes)
        
    def get_relationship_of_source_node(self, source_id, relationships):
        for rel in relationships:
            if(rel['sourceElement']['ref'] == source_id):
                return rel


    def get_node(self, node_type_id):
        for node in self.node_templates:
            if node['id'] == node_type_id:
                return node
        
    def get_all_requirements(self, service_templates):
        if (not self.requirements):
            for service in service_templates:
                topology_template = self.get_topology_template(service)
                node_templates = self.get_node_templates(topology_template)
                for node_template in node_templates:
                    node_type = node_template['type']
                    all_requirements = self.get_super_types_requirements(node_type, None)
                    print(all_requirements)
                    id = node_template['id']
                    req = self.get_requirements(node_template)
                    if(req):
                        for r in req['requirement']:
                            all_requirements.append(r['type'])
                    self.requirements[id] = all_requirements
        return self.requirements
            
    
    def get_unmet_requirements(self, requirements):
        for requirement in requirements:
            requirement_type = self.get_requirement_type(requirement)
            print(requirement_type) 
            
    def get_all_relationships(self, dict_tpl):
        all_relationships = []
        service_templates = self.get_service_template(dict_tpl)
        for service in service_templates:
            topology_template = self.get_topology_template(service)
            relationships = self.get_relationships(topology_template)
            for rel in relationships:
                all_relationships.append(rel)
        return all_relationships
                
    def get_super_types(self, component_type, supertypes):            
        if (supertypes == None):
            supertypes  = []
        regex = r"\{(.*?)\}"
        matches = re.finditer(regex, component_type, re.MULTILINE | re.DOTALL)
        namespace = next(matches).group(1)
        id = component_type.replace("{" + namespace + "}", "")
        header = {'accept': 'application/json'}
        #winery needs it double percent-encoded 
        encoded_namespace  = urllib.parse.quote(namespace, safe='')
        encoded_namespace = urllib.parse.quote(encoded_namespace, safe='')
        type_name = namespace.rsplit('/', 1)[-1]
        servicetemplate_url = self.tosca_reposetory_api_base_url + "/" + type_name + "/" + encoded_namespace + "/" + id + "/"
        req = urllib.request.Request(url=servicetemplate_url, headers=header, method='GET')
        res = urllib.request.urlopen(req, timeout=5)   
        res_body = res.read()
        component = json.loads(res_body.decode("utf-8"))

        if component:
            comp = component['serviceTemplateOrNodeTypeOrNodeTypeImplementation'][0]
            supertypes.append(comp)
            if 'derivedFrom' in comp:
                supertypes = self.get_super_types(comp['derivedFrom']['typeRef'], supertypes)
            else:
                return supertypes
        
                
    def get_super_types_requirements(self, component_type, requirements):
        if (requirements == None):
            requirements = []
        regex = r"\{(.*?)\}"
        matches = re.finditer(regex, component_type, re.MULTILINE | re.DOTALL)
        namespace = next(matches).group(1)
        id = component_type.replace("{" + namespace + "}", "")
        header = {'accept': 'application/json'}
        #winery needs it double percent-encoded 
        encoded_namespace  = urllib.parse.quote(namespace, safe='')
        encoded_namespace = urllib.parse.quote(encoded_namespace, safe='')
        
        type_name = namespace.rsplit('/', 1)[-1]
        
        servicetemplate_url = self.tosca_reposetory_api_base_url + "/" + type_name + "/" + encoded_namespace + "/" + id + "/"
        req = urllib.request.Request(url=servicetemplate_url, headers=header, method='GET')
        res = urllib.request.urlopen(req, timeout=5)   
        res_body = res.read()
        component = json.loads(res_body.decode("utf-8"))
        for c in component['serviceTemplateOrNodeTypeOrNodeTypeImplementation']:
            if 'requirementDefinitions' in c and 'requirementDefinition' in c['requirementDefinitions']:
                for req in c['requirementDefinitions']['requirementDefinition']:
                    requirements.append(req['requirementType'])
            if 'derivedFrom' in c and c['derivedFrom'] and c['derivedFrom']['type']:
                self.get_super_types_requirements(c['derivedFrom']['type'], requirements)
        return requirements
        
        
    def get_service_template(self, dict_tpl): 
        if(not self.service_templates):
            self.service_templates = self.find(dict_tpl, self.service_template_names)
        return self.service_templates
    
    def get_topology_template(self, dict_tpl):  
        if (not self.topology_templates):
            self.topology_templates = self.find(dict_tpl, self.topology_template_names)
        return self.topology_templates
    
    def get_node_templates(self, dict_tpl):
        if (not self.node_templates):
            self.node_templates = self.find(dict_tpl, self.node_template_names)  
        return self.node_templates
    
    def get_requirements(self, dict_tpl):  
        return self.find(dict_tpl, self.requirement_names)   
    
    def get_relationships(self, dict_tpl):  
        return self.find(dict_tpl, self.relationships_names)
    
    def get_requirement_type(self, dict_req):
        return self.find(dict_req, self.type_names)
    
    def find(self, dict_tpl, names):
        if dict_tpl:
            for name in names:
                if(name in dict_tpl):
                    return dict_tpl[name]