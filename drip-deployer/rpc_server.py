#!/usr/bin/env python
import pika
import json
import os


from vm_info import VmInfo
import docker_kubernetes

connection = pika.BlockingConnection(pika.ConnectionParameters(host='172.17.0.3'))
channel = connection.channel()
channel.queue_declare(queue='deployer_queue')

path = os.path.dirname(os.path.abspath(__file__)) + "/"

def handleDelivery(message):
    parsed_json = json.loads(message)
    params = parsed_json["parameters"]  
    node_num = 0
    vm_list = []
    for param in params:
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
    docker_kubernetes.run(vm_list)
    

def on_request(ch, method, props, body):
    handleDelivery(body)

    print(" Message %s" % body)

    kuber_file = open(path + "admin.conf", "r")
    kuber_string = kuber_file.read()
    kuber_file.close()

    response = {}
    outcontent = {}
    current_milli_time = lambda: int(round(time.time() * 1000))
    response["creationDate"] = current_milli_time()
    response["parameters"] = []
    par = {}
    par["url"] = "null"
    par["encoding"] = "UTF-8"
    par["name"] = "credential"
    par["value"] = kuber_string
    par["attributes"] = "null"
    response["parameters"].append(par)

    ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id = \
                                                         props.correlation_id),
                     body=str(response))
    ch.basic_ack(delivery_tag = method.delivery_tag)

channel.basic_qos(prefetch_count=1)
channel.basic_consume(on_request, queue='deployer_queue')

print(" [x] Awaiting RPC requests")
channel.start_consuming()