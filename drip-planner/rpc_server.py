#!/usr/bin/env python
import pika
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

print "---------"
if len(sys.argv) > 1:
    rabbitmq_host = sys.argv[1]
else:
    rabbitmq_host = '127.0.0.1'


connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host))
channel = connection.channel()
channel.queue_declare(queue='planner_queue')



def handleDelivery(message):
    parsed_json_message = json.loads(message)
    params = parsed_json_message["parameters"]
    param = params[0]
    value = param["value"]
    
    parsed_json_value = json.loads(value)
    topology_template = parsed_json_value['topology_template']
    node_templates = topology_template["node_templates"]
    
    
    json1 = node_templates
    deadline = 0

    for j in json1:
        if "Switch.nodes.Application.Container.Docker." in json1[j]['type'] and "QoS" in str(json1[j]['properties']) and "response_time" in str(json1[j]['properties']):
            response_time = json1[j]['properties']['QoS']['response_time']
            reg_ex = re.search(r'\d+', str(response_time))
            gr = reg_ex.group()
            deadline = int(gr)

    #get the nodes from the json
    nodeDic = {}
    nodeDic1 = {}
    i = 1
    for j in json1:
        if "Switch.nodes.Application.Container.Docker." in json1[j]['type']:
            nodeDic[j] = i
            nodeDic1[i] = j
            i = i + 1

    #get the links from the json
    links = []
    for j in json1:
        if json1[j]['type'] == "Switch.nodes.Application.Connection":
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
    #print wfJson

    #send request to the server
    start = time.time()
    wf = Workflow()
    wf.init(wfJson)
    wf.ic_pcp()
    #print content['workflow']
    #return 
    res = wf.generateJSON()
    
    end = time.time()
    #print (end - start)
    
    # convert the json to the file required
    
    outcontent = {}
    current_milli_time = lambda: int(round(time.time() * 1000))
    outcontent["creationDate"] = current_milli_time()   
    outcontent["parameters"] = []
    
    for key, value in sorted_nodeDic:
        if "docker_image." not in json1[nodeDic1[value]].get('artifacts'):
            keys = json1[nodeDic1[value]].get('artifacts').keys()
            for k in keys:
                docker = json1[nodeDic1[value]].get('artifacts').get(k).get('file')
                par = {}
                res1 = {}
                par["url"] = "null"
                par["encoding"] = "UTF-8"
                name = str(nodeDic1[value])
                res1["name"] = str(nodeDic1[value])
                res1["size"] = res[str(value)]
                res1["docker"] = str(docker)
                par["value"] = res1
                par["attributes"] = "null"
                ##print ("Parameter: %s" % par)
                outcontent["parameters"].append(par)
        else:
            for key, value in sorted_nodeDic:        
                par = {}
                res1 = {}
                par["url"] = "null"
                par["encoding"] = "UTF-8"
                name = str(nodeDic1[value])
                docker = json1[nodeDic1[value]].get('artifacts').get('docker_image').get('file')
                res1["name"] = str(nodeDic1[value])
                res1["size"] = res[str(value)]
                res1["docker"] = str(docker)
                par["value"] = res1
                par["attributes"] = "null"
                ##print ("Parameter: %s" % par)
                outcontent["parameters"].append(par)
    
    print ("Output message: %s" % outcontent)
    return outcontent
    

def on_request(ch, method, props, body):
    response = handleDelivery(body)
    
    ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id = \
                                                         props.correlation_id),
                     body=str(response))
    ch.basic_ack(delivery_tag = method.delivery_tag)






channel.basic_qos(prefetch_count=1)
channel.basic_consume(on_request, queue='planner_queue')

print(" [x] Awaiting RPC requests")
channel.start_consuming()


#f = open("../doc/json_samples/plannerInput2.json","r")
#body=f.read()
#response = handleDelivery(body)

