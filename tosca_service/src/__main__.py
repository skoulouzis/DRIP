# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

import json
import logging
import os
import os.path
from os.path import expanduser
import pika
import sys
import tempfile
import time
from winery.service import *

if __name__ == "__main__":
    tosca_reposetory_api_base_url = "http://localhost:8080/winery"
    namespace = "http%253A%252F%252Fsne.uva.nl%252Fservicetemplates"
    servicetemplate_id = "wordpress_w1-wip1"
    repo = Service(tosca_reposetory_api_base_url)
    servicetemplates = repo.get_servicetemplates(namespace, servicetemplate_id)
    
    tt = repo.get_topology_template(servicetemplates)
    node_templates = repo.get_node_templates(tt)
    
    relationships = repo.get_relationships(tt)
    
    print(relationships)
    
    
#    requrements = repo.get_all_requirements(node_templates[1])
    
#    unmet_requrements = repo.get_unmet_requrements(node_templates[1], tt)
    
#    capabilities = repo.get_all_capabilities(node_templates[1])
#    print(capabilities)
        
    
    
    
        
