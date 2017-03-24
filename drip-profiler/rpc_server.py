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
channel.queue_declare(queue='profiler_queue')

path = os.path.dirname(os.path.abspath(__file__)) + "/"


def threaded_function(args):
    while True:
        #print "processing data events"
        connection.process_data_events()
        sleep(30)

def handleDelivery(message):
        

def on_request(ch, method, props, body):
    ret = handleDelivery(body)
    
        
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

print("[x] Awaiting RPC requests")
channel.start_consuming()
thread.stop()