import json
import operator
import pdb
import re
from toscaparser.tosca_template import ToscaTemplate
import toscaparser.utils.yamlparser
import urllib
import urllib.parse

class Service:
    service_template_names = ['serviceTemplateOrNodeTypeOrNodeTypeImplementation']
    topology_template_names = ['topologyTemplate']
    node_template_names = ['nodeTemplates']
    requirement_deff_names = ['requirementDefinitions']
    capability_deff_names = ['capabilityDefinitions']
    type_names = ['type']
    relationships_names = ['relationshipTemplates']
    service_templates = None
    topology_templates = []
    node_templates = []
    
    def __init__(self, tosca_reposetory_api_base_url):
        self.tosca_reposetory_api_base_url = tosca_reposetory_api_base_url

    def get_servicetemplates(self,namespace, servicetemplate_id):
        servicetemplate_url = self.tosca_reposetory_api_base_url + "/servicetemplates/" + namespace + "/" + servicetemplate_id + "/"
        header = {'accept': 'application/json'}
        req = urllib.request.Request(url=servicetemplate_url, headers=header, method='GET')
        res = urllib.request.urlopen(req, timeout=5)   
        res_body = res.read()
        self.dict_tpl = json.loads(res_body.decode("utf-8"))
        return self.dict_tpl
    
    
    def get_topology_template(self, dict_tpl): 
        if (not self.topology_templates):
            self.topology_templates = self.find(dict_tpl['serviceTemplateOrNodeTypeOrNodeTypeImplementation'][0], self.topology_template_names)
        return self.topology_templates
    
    def get_node_templates(self, topology_templates):
        if (not self.node_templates):
            nodes = self.find(topology_templates,self.node_template_names)
            self.node_templates = nodes
        return self.node_templates
    
    def get_parents(self,template):
        node_type = template['type']
        node = self.get_object(node_type)['serviceTemplateOrNodeTypeOrNodeTypeImplementation'][0]
        return self.get_type_parents(node,None)
        
    def get_type_parents(self,type,parents):
        if 'derivedFrom' in type:
            type_str = type['derivedFrom']['typeRef']
            parent = self.get_object(type_str)['serviceTemplateOrNodeTypeOrNodeTypeImplementation'][0]
            if(parents == None):
                parents = []
            parents.append(parent)
            self.get_type_parents(parent,parents)
        
        return parents
        
        
    def get_requirements(self,node_type):
        requirements = self.find(node_type,self.requirement_deff_names)
        if requirements:
            return requirements['requirementDefinition']
        
    def get_all_requirements(self,node_template):
        parents = self.get_parents(node_template)
        node_type = self.get_object(node_template['type'])['serviceTemplateOrNodeTypeOrNodeTypeImplementation'][0]
        requrements=[]
        requrements_types = set()
        child_requrements = self.get_requirements(node_type)
        if child_requrements:
            for c_req in child_requrements:
                if not c_req['requirementType'] in requrements_types:
                    requrements.append(c_req)
                    requrements_types.add(c_req['requirementType'])
        for parent in parents:
            parent_requrements = self.get_requirements(parent)
            for p_req in parent_requrements:
                if not p_req['requirementType'] in requrements_types:
                    requrements.append(p_req)
                    requrements_types.add(p_req['requirementType'])
        return requrements
    
    def get_all_capabilities(self,node_template):
        parents = self.get_parents(node_template)
        node_type = self.get_object(node_template['type'])['serviceTemplateOrNodeTypeOrNodeTypeImplementation'][0]
        child_capabilities = self.get_capabilities(node_type)
        capabilities=[]
        capabilities_types = set()
        
        if child_capabilities:
            for c_cap in child_capabilities:
                if not c_cap['capabilityType'] in capabilities_types:
                    capabilities.append(c_cap)
                    capabilities_types.add(c_cap['capabilityType'])        
                    
        for parent in parents:
            parent_capabilities = self.get_capabilities(parent)
            if(parent_capabilities):
                for p_cap in parent_capabilities:
                    if not p_cap['capabilityType'] in capabilities_types:
                        capabilities.append(p_cap)
                        capabilities_types.add(p_cap['capabilityType'])                    
        return capabilities
        
    def get_capabilities(self,node_type):
        requirements = self.find(node_type,self.capability_deff_names)
        if requirements:
            return requirements['capabilityDefinition']
        
        
        
    def get_object(self,type_str):
        regex = r"\{(.*?)\}"
        matches = re.finditer(regex, type_str, re.MULTILINE | re.DOTALL)
        namespace = next(matches).group(1)
        id = type_str.replace("{" + namespace + "}", "")
        header = {'accept': 'application/json'}
        #winery needs it double percent-encoded 
        encoded_namespace  = urllib.parse.quote(namespace, safe='')
        encoded_namespace = urllib.parse.quote(encoded_namespace, safe='')
        type_name = namespace.rsplit('/', 1)[-1]
        servicetemplate_url = self.tosca_reposetory_api_base_url + "/" + type_name + "/" + encoded_namespace + "/" + id + "/"
        req = urllib.request.Request(url=servicetemplate_url, headers=header, method='GET')
        res = urllib.request.urlopen(req, timeout=5)   
        res_body = res.read()
        return json.loads(res_body.decode("utf-8"))        
        
    def find(self, dict_obj, names):
        if dict_obj:
            for name in names:
                if(name in dict_obj):
                    return dict_obj[name]        

        

  