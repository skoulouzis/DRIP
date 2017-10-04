# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

import argparse
import getopt
import json
import networkx as nx
import numpy as np
import operator
import os
import pika
import random
import re
import sys
import time
from toscaparser import *
from toscaparser.tosca_template import ToscaTemplate



def init_chanel(args):
    if len(args) > 1:
        rabbitmq_host = args[1]#sys.argv[1]
    else:
        rabbitmq_host = '127.0.0.1'
        
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host))
    channel = connection.channel()
    channel.queue_declare(queue='planner_queue')
    return channel
    
def start(channel):    
    channel.basic_qos(prefetch_count=1)
    channel.basic_consume(on_request, queue='planner_queue')

    print(" [x] Awaiting RPC requests")
    channel.start_consuming()
    
def on_request(ch, method, props, body):
    response = handle_delivery(body)
    
    ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id=\
                     props.correlation_id),
                     body=str(response))
    ch.basic_ack(delivery_tag=method.delivery_tag)   
    
    
def handle_delivery(message):
    parsed_json_message = json.loads(message)
    params = parsed_json_message["parameters"]
    param = params[0]
    value = param["value"]
    
    parsed_json_value = json.loads(value)
    topology_template = parsed_json_value['topology_template']
    node_templates = topology_template["node_templates"]
    
    response = {}
    current_milli_time = lambda: int(round(time.time() * 1000))
    response["creationDate"] = current_milli_time()   
    response["parameters"] = []
    
    for nodes in node_templates:
        if "Switch.nodes.Application.Container.Docker." in node_templates[nodes]['type']:
            node_keys = node_templates[nodes].keys()     
            if 'artifacts' in node_keys:
                artifact_key = next(iter(node_templates[nodes]['artifacts']))
                artifact_keys = node_templates[nodes]['artifacts'][artifact_key].keys()   
                if 'file' in artifact_keys:
                    docker = node_templates[nodes]['artifacts'][artifact_key]['file']
                elif 'docker_image' in artifact_keys:
                    docker = node_templates[nodes]['artifacts']['docker_image']['file']
                result = {}
                parameter = {}
                result['name'] = nodes
                result['size'] = 'Medium'
                result['docker'] = docker
                parameter['value'] = str(json.dumps(result))
                parameter['attributes'] = 'null'
                parameter["url"] = "null"
                parameter["encoding"] = "UTF-8"
                response["parameters"].append(parameter)
    print ("Output message: %s" % json.dumps(response))            
    return json.dumps(response)

if __name__ == "__main__":
    print sys.argv
    channel = init_chanel(sys.argv)
    start(channel)
    
    
    
        
