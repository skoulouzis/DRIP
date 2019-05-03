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
        return requirements['requirementDefinition']
        
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

        

  