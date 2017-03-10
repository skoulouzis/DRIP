#https://github.com/skoulouzis/DRIP/blob/package/doc/json_samples/kbExampleMessage.json

import networkx as nx
import sys
import numpy as np
import sys, argparse
import operator
import os
from toscaparser import *
from toscaparser.tosca_template import ToscaTemplate
import re
import getopt
from ICPCP import Workflow
import random
import time
import json

def main(argv):
    workflow_file = ""
    
    try:
        opts, args = getopt.getopt(argv,"hw:s:",["workflow=", "SDI="])
    except getopt.GetoptError:
        print 'server.py -w <workflowfile> -s <SDI>'
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print 'server.py -w <workflowfile> -s <SDI>'
            sys.exit()
        elif opt in ("-w", "--workflow"):
            workflow_file = arg
        elif opt in ("-s", "--SDI"):
            SDI_file = arg
    
    data = {}
    #print workflow_file
    with open(workflow_file) as data_file:    
        data = json.load(data_file)     
        #print data         
        
    #path = "input.yaml"
    
    '''
    a_file = os.path.isfile(path)
    tosca = ToscaTemplate(path)
    #print tosca.tpl
    json = tosca.tpl.get('topology_template').get('node_templates')
    #print json
    '''
    json1 = data.get('parameters')[0].get('value').get('topology_template').get('node_templates')
    deadline = 0

    for j in json1:
        #print json[j]
        if not json1[j]['type'] == "Switch.nodes.Application.Connection":
            deadline = int(re.search(r'\d+', json1[j]['properties']['QoS']['response_time']).group())

    #get the nodes from the json
    nodeDic = {}
    nodeDic1 = {}
    
    i = 1
    for j in json1:
        if not json1[j]['type'] == "Switch.nodes.Application.Connection":
            print j, json1[j]
            nodeDic[j] = i
            nodeDic1[i] = j
            i = i + 1

    #get the links from the json
    links = []
    for j in json1:
        if json1[j]['type'] == "Switch.nodes.Application.Connection":
            print json1[j]['properties']['source']['component_name']
            print json1[j]['properties']['target']['component_name']
            link= {}
            link['source'] = nodeDic[json1[j]['properties']['source']['component_name']]
            link['target'] = nodeDic[json1[j]['properties']['target']['component_name']]
            link['weight'] = random.randint(1, 10)
            links.append(link)

    # compose the json as input of the workflow
    wfJson = {}
    wfJson['workflow'] = {}

    nodesList = []
    sorted_nodeDic = sorted(nodeDic.items(), key=operator.itemgetter(1))
    for key, value in sorted_nodeDic:
        v = {}
        v['name'] = value
        nodesList.append(v)
    wfJson['workflow']['nodes'] = nodesList
    wfJson['workflow']['links'] = links
    #print deadline
    
    wfJson['price'] = "5,2,1"
    wfJson['deadline'] = {'2': deadline}

    #generate performance
    performance = {}
    for key, value in sorted_nodeDic:
        performance[str(value)] = "1,2,3"
    wfJson['performance'] = performance
    
    print wfJson

    #send request to the server
    start = time.time()
    wf = Workflow()
    wf.init(wfJson)
    wf.ic_pcp()
    #print content['workflow']
    #return 
    res = wf.generateJSON()
    
    end = time.time()
    print (end - start)
    # convert the json to the file required
    res1 = {}
    for key, value in sorted_nodeDic:
        print value, res[str(value)]
        
        res1_value = {}
        res1_value["size"] = res[str(value)]
        res1_value["docker"] = json1[nodeDic1[value]].get('artifacts').get('docker_image').get('file')
        res1[str(nodeDic1[value])] = res1_value
    print res1
    # generate the json files in the corresponding format as the 
    outcontent = {}
    outcontent["creationDate"] = 1487002029722
    outcontent["parameters"] = []
    par1 = {}
    par1["url"] = "null"
    par1["encoding"] = "UTF-8"
    par1["value"] = res1
    par1["attributes"] = "null"
    outcontent["parameters"].append(par1)
    #print outcontent
    return outcontent
 
    
if __name__ == '__main__':
    main(sys.argv[1:])