#!/usr/bin/env python
import pika
import json
import os
import time
from vm_info import VmInfo
import docker_kubernetes
import docker_engine
import docker_swarm
import docker_compose
import docker_service
import docker_check
import control_agent
import ansible_playbook
import sys, argparse
from threading import Thread
from time import sleep
import os.path
import logging


if len(sys.argv) > 1:
    rabbitmq_host = sys.argv[1]
else:
    rabbitmq_host = '127.0.0.1'
    
logger = logging.getLogger('rpc_server')
logger.setLevel(logging.DEBUG)
ch = logging.StreamHandler()
ch.setLevel(logging.DEBUG)
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
ch.setFormatter(formatter)
logger.addHandler(ch)

fh = logging.FileHandler('deployer.log')
fh.setLevel(logging.DEBUG)
fh.setFormatter(formatter)
logger.addHandler(fh)


connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host))
channel = connection.channel()
channel.queue_declare(queue='deployer_queue')



done = False

def threaded_function(args):
    while not done:
        connection.process_data_events()
        sleep(5)

def handleDelivery(message):
    parsed_json = json.loads(message)
    params = parsed_json["parameters"]  
    node_num = 0
    vm_list = []
    current_milli_time = lambda: int(round(time.time() * 1000))
    try:
        path = os.path.dirname(os.path.abspath(__file__)) + "/deployer_files/"+str(current_milli_time()) + "/"
    except NameError:        
        import sys
        path = os.path.dirname(os.path.abspath(sys.argv[0])) + "/deployer_files/"+str(current_milli_time()) + "/"
        
    if not os.path.exists(path):
        os.makedirs(path)
        
    for param in params:
        name = param["name"]
        if name == "cluster":
            manager_type = param["value"]
        elif name == "credential":
            value = param["value"]
            ip = param["attributes"]["IP"]
            user = param["attributes"]["user"]
            role = param["attributes"]["role"]
            node_num += 1
            key = path + "%d.txt" % (node_num)
             
            fo = open(key, "w")
            fo.write(value)
            fo.close()
            parentDir = os.path.dirname(os.path.abspath(key))
            os.chmod(parentDir, 0o700)
            os.chmod(key, 0o600)   
            
            vm = VmInfo(ip, user, key, role)
            vm_list.append(vm)
        elif name == "playbook":
            value = param["value"]
            playbook = path + "playbook.yml"
            fo = open(playbook, "w")
            fo.write(value)
            fo.close()
        elif name == "composer":
            value = param["value"]
            compose_file = path + "docker-compose.yml"
            if not param["attributes"] == None and not param["attributes"]["name"] == None : 
                compose_name = param["attributes"]["name"]
            else:
                current_milli_time = lambda: int(round(time.time() * 1000))
                compose_name = "service_"+str(current_milli_time())
            fo = open(compose_file, "w")
            fo.write(value)
            fo.close()     
        elif name == "scale":
            name_of_deployment = param["value"]
            name_of_service = param["attributes"]["service"]
            number_of_containers = param["attributes"]["number_of_containers"]
        elif name == "swarm_info":
            compose_name = param["attributes"]["name"]
            

    if manager_type == "kubernetes":
        ret = docker_kubernetes.run(vm_list)
        return ret
    elif manager_type == "swarm":
        ret = docker_engine.run(vm_list)
        if "ERROR" in ret: return ret
        ret = docker_swarm.run(vm_list)
        if "ERROR" in ret: return ret
        ret = docker_compose.run(vm_list, compose_file, compose_name)
        return ret
    elif manager_type == "ansible":
        ret = ansible_playbook.run(vm_list,playbook)
        return ret
    elif manager_type == "scale":
        ret = docker_service.run(vm_list, name_of_deployment, name_of_service, number_of_containers)
        return ret
    elif manager_type == "swarm_info":
        ret = docker_check.run(vm_list, compose_name)
        ret = '"'+json.dumps(ret)+'"'
        return ret
    else:
        return "ERROR: invalid cluster"
    

def on_request(ch, method, props, body):
    ret = handleDelivery(body)
    
    parsed_json = json.loads(body)
    params = parsed_json["parameters"]
    
    for param in params:
        name = param["name"]
        if name == "cluster":
            manager_type = param["value"]
            break
    
            
    if "ERROR" in ret:
        res_name = "error"
    elif manager_type == "ansible":
        res_name = "ansible_output"
    elif manager_type == "scale":
        res_name = "scale_status"
    elif manager_type == "swarm_info":
        res_name = "swarm_info"
    else:
        res_name = "credential"
            
    response = {}
    outcontent = {}
    current_milli_time = lambda: int(round(time.time() * 1000))
    response["creationDate"] = current_milli_time()
    response["parameters"] = []
    par = {}
    par["url"] = "null"
    par["encoding"] = "UTF-8"
    par["name"] = res_name
    par["value"] = ret
    par["attributes"] = "null"
    response["parameters"].append(par)

    response = json.dumps(response)
    logger.info("Response: " + response)
    
    
    ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id = \
                                                         props.correlation_id),
                     body=str(response))
    ch.basic_ack(delivery_tag = method.delivery_tag)

channel.basic_qos(prefetch_count=1)
channel.basic_consume(on_request, queue='deployer_queue')


thread = Thread(target = threaded_function, args = (1, ))
thread.start()

logger.info("Awaiting RPC requests")



try:
    channel.start_consuming()
except KeyboardInterrupt:
    #thread.stop()
    done = True
    thread.join()
    logger.info("Threads successfully closed")
