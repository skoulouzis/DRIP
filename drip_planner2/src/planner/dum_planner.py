from toscaparser.tosca_template import ToscaTemplate
import toscaparser.utils.yamlparser
import re
import operator
import json


class DumpPlanner:
    service_template_names = ['serviceTemplateOrNodeTypeOrNodeTypeImplementation']
    topology_template_names = ['topologyTemplate']
    node_template_names = ['nodeTemplates']
    requirement_names = ['requirements']
    
    def __init__(self, service_templaete_file_path):
        
        dict_tpl = self.load_file(service_templaete_file_path)
        requirements = self.get_all_requirements(dict_tpl)
        unmet_requirements = self.get_unmet_requirements(requirements)
        print(requirements)
#        yaml_dict_tpl = self.trnsform_to_tosca(yaml_dict_tpl)


        
    def load_file(self,path):
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
        
        
    def trnsform_to_tosca(self,yaml_dict_tpl):        
        try:      
            self.tt = ToscaTemplate(path=None, yaml_dict_tpl=yaml_dict_tpl)
        except Exception as e:
            self.handle_tosca_exeption(e,yaml_dict_tpl)
            
    def handle_tosca_exeption(self,exception,yaml_dict_tpl):  
        print(exception)
        
        
    def get_all_requirements(self,dict_tpl):
        all_requirements = []
        service_templates = self.get_service_template(dict_tpl)
        for service in service_templates:
            topology_template = self.get_topology_template(service)
            node_templates = self.get_node_templates(topology_template)
            for node_template in node_templates:
                requirements = self.get_requirements(node_template)
                if requirements:
                    for requirement in requirements['requirement']:
                        all_requirements.append(requirement)
        return all_requirements
            
        
        
    def get_service_template(self,dict_tpl):  
        return self.find(dict_tpl,self.service_template_names)
    
    def get_topology_template(self,dict_tpl):  
        return self.find(dict_tpl,self.topology_template_names)
    
    def get_node_templates(self,dict_tpl):  
        return self.find(dict_tpl,self.node_template_names)  
    
    def get_requirements(self,dict_tpl):  
        return self.find(dict_tpl,self.requirement_names)     
    
    
    def find(self,dict_tpl,names):
        if dict_tpl:
            for name in names:
                if(name in dict_tpl):
                    return dict_tpl[name]
                
                
    def get_unmet_requirements(self,requirements):
        for requirement in requirement:
            print(requirement)