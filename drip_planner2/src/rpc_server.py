# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

import os
import os.path
import pika
import sys
import tempfile
import time
import json
from planner.dum_planner import *
from os.path import expanduser
import logging

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True
    


def init_chanel(args):
    if len(args) > 1:
        rabbitmq_host = args[1]
        queue_name = args[2] #planner_queue
    else:
        rabbitmq_host = '127.0.0.1'
        
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host))
    channel = connection.channel()
    channel.queue_declare(queue=queue_name)
    return channel
    
def start(channel):    
    channel.basic_qos(prefetch_count=1)
    channel.basic_consume(on_request, queue=queue_name)

    logger.info(" [x] Awaiting RPC requests")
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
    value = json.loads( param['value'])
    tosca_file_name = param["name"]
    current_milli_time = lambda: int(round(time.time() * 1000))
    
    try:
        tosca_file_path = tempfile.gettempdir() + "/planner_files/" + str(current_milli_time()) + "/"
    except NameError:        
        import sys
        tosca_file_path = os.path.dirname(os.path.abspath(sys.argv[0])) + "/planner_files/" + str(current_milli_time()) + "/"
    
    if not os.path.exists(tosca_file_path):
        os.makedirs(tosca_file_path)    
    with open(tosca_file_path + "/" + tosca_file_name + ".yml", 'w') as outfile:
        outfile.write(json.dumps(value))
    
    response = {}
    current_milli_time = lambda: int(round(time.time() * 1000))
    response["creationDate"] = current_milli_time()   
    response["parameters"] = []
    if queue_name == "planner_queue":
        planner = DumpPlanner(tosca_file_path + "/" + tosca_file_name + ".yml");
        vm_nodes = planner.plan()
        for vm in vm_nodes:
            parameter = {}
            parameter['value'] = str(json.dumps(vm))
            parameter['name'] = 'vm'
            parameter['encoding'] = 'UTF-8'
            response["parameters"].append(parameter)         
    
    logger.info ("Output message:" + json.dumps(response))            
    return json.dumps(response)

if __name__ == "__main__":
#    home = expanduser("~")
#    planner = DumpPlanner(home+"/workspace/DRIP/docs/input_tosca_files/BEIA_cardif2.yml")
#    print planner.plan()
    logger.info("Input args: "+sys.argv[0]+' '+sys.argv[1]+' '+sys.argv[2]) 
    channel = init_chanel(sys.argv)
    global queue_name
    queue_name = sys.argv[2]
    start(channel)
    
    
#            
#        for vm in vm_nodes:
#            result = {}
#            parameter = {}
#            result['name'] = vm.name
#            result['size'] = 'Medium'
#            if 'dockers' in vm.get_properties():
#                result['docker'] = vm.get_properties()['dockers'].value
#            elif 'artifacts' in vm.templates[next(iter(vm.templates))]:
#                artifacts = vm.templates[next(iter(vm.templates))]['artifacts']
#                result['docker'] = artifacts['docker_image']['file']
##            print "1st Key: %s" %next(iter(vm.templates))
##            print vm.templates[next(iter(vm.templates))]
#            
##            print dir(compute_node.get_properties()['dockers'] )
##            print "dockers. Name: %s Type: %s Value: %s" % (compute_node.get_properties()['dockers'].name, compute_node.get_properties()['dockers'].type, compute_node.get_properties()['dockers'].value )
#            parameter['value'] = str(json.dumps(result))
#            parameter['attributes'] = 'null'
#            parameter["url"] = "null"
#            parameter["encoding"] = "UTF-8"
#            response["parameters"].append(parameter)           
##            print "Name: %s Type: %s properties: %s" %(vm.name,vm.type,vm.get_properties().keys())
##
#        print ("Output message: %s" % json.dumps(response))
#
#    except AttributeError as e:
#        z = e
#        print z
    
    
    
    
        
