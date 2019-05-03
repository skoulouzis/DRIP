# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

import json
import logging
import os
import os.path
from os.path import expanduser
import pika
from winery.service import *
import sys
import tempfile
import time

if __name__ == "__main__":
    tosca_reposetory_api_base_url = "http://localhost:8080/winery"
    namespace = "http%253A%252F%252Fsne.uva.nl%252Fservicetemplates"
    servicetemplate_id = "wordpress_w1-wip1"
    repo = Service(tosca_reposetory_api_base_url)
    servicetemplates = repo.get_servicetemplates(namespace,servicetemplate_id)
    
    tt=  repo.get_topology_template(servicetemplates)
    node_templates =  repo.get_node_templates(tt)
    
    parents = repo.get_parents(node_templates[1])
    requrements=[]
    requrements_types = set()
    node_type = repo.get_object(node_templates[1]['type'])['serviceTemplateOrNodeTypeOrNodeTypeImplementation'][0]
    
    child_requrements = repo.get_requirements(node_type)
    if child_requrements:
        for c_req in child_requrements:
            if not c_req['requirementType'] in requrements_types:
                requrements.append(c_req)
                requrements_types.add(c_req['requirementType'])
    
    for parent in parents:
        parent_requrements = repo.get_requirements(parent)
        for p_req in parent_requrements:
            if not p_req['requirementType'] in requrements_types:
                requrements.append(p_req)
                requrements_types.add(p_req['requirementType'])
    
    
    
#    print(requrements)

    capabilities=[]
    capabilities_types = set()
    child_capabilities = repo.get_capabilities(node_type)
    if child_capabilities:
        for c_cap in child_capabilities:
            if not c_cap['capabilityType'] in capabilities_types:
                capabilities.append(c_cap)
                capabilities_types.add(c_cap['capabilityType'])
    
    for parent in parents:
        parent_capabilities = repo.get_capabilities(parent)
        if(parent_capabilities):
            for p_cap in parent_capabilities:
                if not p_cap['capabilityType'] in capabilities_types:
                    capabilities.append(p_cap)
                    capabilities_types.add(p_cap['capabilityType'])
                
    print(capabilities_types)
        
    
    
    
        
