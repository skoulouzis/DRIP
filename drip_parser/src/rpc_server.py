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
import yaml
from transformer.docker_compose_transformer import *
from os.path import expanduser



def init_chanel(args):
    if len(args) > 1:
        rabbitmq_host = args[1]
        queue_name = args[2] #tosca_2_docker_compose_queue
    else:
        rabbitmq_host = '127.0.0.1'
        
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host))
    channel = connection.channel()
    channel.queue_declare(queue=queue_name)
    return channel
    
def start(channel):    
    channel.basic_qos(prefetch_count=1)
    channel.basic_consume(on_request, queue=queue_name)

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
    value = yaml.load(param['value'])
    tosca_file_name = param["name"]
    current_milli_time = lambda: int(round(time.time() * 1000))
    
    try:
        tosca_file_path = tempfile.gettempdir() + "/transformer_files/" + str(current_milli_time()) + "/"
    except NameError:        
        import sys
        tosca_file_path = os.path.dirname(os.path.abspath(sys.argv[0])) + "/transformer_files/" + str(current_milli_time()) + "/"
    
    if not os.path.exists(tosca_file_path):
        os.makedirs(tosca_file_path)    
    with open(tosca_file_path + "/" + tosca_file_name + ".yml", 'w') as outfile:
        outfile.write(str(value))
    
    if queue_name == "tosca_2_docker_compose_queue":
        transformer = DockerComposeTransformer(tosca_file_path + "/" + tosca_file_name + ".yml");
        compose = transformer.getnerate_compose()
    response = {}
    current_milli_time = lambda: int(round(time.time() * 1000))
    response["creationDate"] = current_milli_time()   
    response["parameters"] = []
    
    parameter = {}
    parameter['value'] = str(yaml.dump(compose))
    parameter['name'] = 'docker-compose.yml'
    parameter['encoding'] = 'UTF-8'
    response["parameters"].append(parameter)     
    print ("Output message: %s" % json.dumps(response))         
    return response

def test_local():
    home = expanduser("~")
    transformer = DockerComposeTransformer(home+"/workspace/DRIP/docs/input_tosca_files/BEIAv3.yml")
    compose =  transformer.getnerate_compose()
    print yaml.dump(compose)
    response = {}
    current_milli_time = lambda: int(round(time.time() * 1000))
    response["creationDate"] = current_milli_time()   
    response["parameters"] = []
    
    parameter = {}
    parameter['value'] = str(yaml.dump(compose))
    parameter['name'] = 'docker-compose.yml'
    parameter['encoding'] = 'UTF-8'
    response["parameters"].append(parameter)
    print response

if __name__ == "__main__":
    test_local()
#    print sys.argv
#    channel = init_chanel(sys.argv)
#    global queue_name
#    queue_name = sys.argv[2]
#    start(channel)


#    try:
##        for node in tosca.nodetemplates:
##                print "Name %s Type: %s " %(node.name,node.type)
##    
##        for input in tosca.inputs:
##            print input.name
#
##        for node in tosca.nodetemplates:
##            for relationship, trgt in node.relationships.items():
##                rel_template = trgt.get_relationship_template()
##                for rel in rel_template:
##                    print "source %s Relationship: %s target: %s" %(rel.source.type,rel.type,rel.target.type)
##                    print dir(rel)
#        response = {}
#        current_milli_time = lambda: int(round(time.time() * 1000))
#        response["creationDate"] = current_milli_time()   
#        response["parameters"] = []
#        vm_nodes =  []
#                
#        for node in tosca.nodetemplates:
#            if not node.relationships.items() and 'docker' in node.type.lower():
#                print "1Adding: %s , %s" %(node.name,node.type)
#                vm_nodes.append(node)
##            else:
#            for relationship, trgt in node.relationships.items():
#                if relationship.type == EntityType.HOSTEDON:
#                    rel_template = trgt.get_relationship_template()
#                    for rel in rel_template:
#                        print "2Adding: %s , %s" %(rel.target.name,rel.target.type)                        
##                        print "Name: %s Type: %s " %(node.name, node.type)
#                        vm_nodes.append(rel.target)
#                        
#        
##        if not compute_nodes:
##            for node in tosca.nodetemplates:
###                print dir(node)
##                print "Name: %s Type: %s props: %s"%(node.name,node.type,node.get_properties().keys())         
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
    
    
    
    
        
