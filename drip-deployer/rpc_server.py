#!/usr/bin/env python
import pika
import json
import os
import time

from vm_info import VmInfo
import docker_kubernetes
import docker_engine
import docker_swarm
import control_agent
import sys, argparse
from threading import Thread
from time import sleep

print sys.argv
if len(sys.argv) > 1:
    rabbitmq_host = sys.argv[1]
else:
    rabbitmq_host = '127.0.0.1'
    

connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host))
channel = connection.channel()
channel.queue_declare(queue='deployer_queue')

path = os.path.dirname(os.path.abspath(__file__)) + "/"


def threaded_function(args):
    while True:
        #print "processing data events"
        connection.process_data_events()
        sleep(30)

def handleDelivery(message):
    parsed_json = json.loads(message)
    params = parsed_json["parameters"]  
    node_num = 0
    vm_list = []
    for param in params:
        name = param["name"]
        if name == "cluster":
            cluster_type = param["value"]
        else:
            value = param["value"]
            ip = param["attributes"]["IP"]
            user = param["attributes"]["user"]
            role = param["attributes"]["role"]
            node_num += 1
            key = path + "%d.txt" % (node_num)
            fo = open(key, "w")
            fo.write(value)
            fo.close()
            vm = VmInfo(ip, user, key, role)
            vm_list.append(vm)

    if cluster_type == "kubernetes":
        ret = docker_kubernetes.run(vm_list)
        return ret
    elif cluster_type == "swarm":
        ret = docker_engine.run(vm_list)
        if "ERROR" in ret: return ret
        ret = docker_swarm.run(vm_list)
        if "ERROR" in ret: return ret
        ret1 = control_agent.run(vm_list)
        if "ERROR" in ret1: ret = ret1
        return ret
    else:
        return "ERROR: invalid cluster"
    

def on_request(ch, method, props, body):
    ret = handleDelivery(body)
    print ret
    if "ERROR" in ret:
        res_name = "error"
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

    print "Response: %s " % response
    
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

print(" [x] Awaiting RPC requests")
channel.start_consuming()
thread.stop()