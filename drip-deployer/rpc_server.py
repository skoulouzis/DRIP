#!/usr/bin/env python
import base64
import json
import logging
import os
import os.path
# import ansible_playbook
import sys
import time
from threading import Thread
from time import sleep

import pika

# import ansible_playbook
import docker_check
import docker_compose
import docker_engine
import docker_kubernetes
import docker_service
import docker_swarm
from vm_info import VmInfo

global rabbitmq_host
if len(sys.argv) > 1:
    rabbitmq_host = sys.argv[1]
else:
    rabbitmq_host = '127.0.0.1'

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True

connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host))
channel = connection.channel()
channel.queue_declare(queue='deployer_queue')

done = False


def threaded_function(args):
    while not done:
        connection.process_data_events()
        sleep(5)


def handle_delivery(message):
    parsed_json = json.loads(message)
    owner = parsed_json['owner']
    params = parsed_json["parameters"]
    node_num = 0
    vm_list = set()
    current_milli_time = lambda: int(round(time.time() * 1000))
    try:
        path = os.path.dirname(os.path.abspath(__file__)) + "/deployer_files/" + str(current_milli_time()) + "/"
    except NameError:
        import sys
        path = os.path.dirname(os.path.abspath(sys.argv[0])) + "/deployer_files/" + str(current_milli_time()) + "/"

    if not os.path.exists(path):
        os.makedirs(path)

    for param in params:
        name = param["name"]
        if name == "cluster":
            manager_type = param["value"]
        elif name == "credential":
            value = param["value"]
            value = base64.b64decode(value)
            ip = param["attributes"]["IP"]
            user = param["attributes"]["user"]
            role = param["attributes"]["role"]
            node_num += 1
            key = path + "%d.txt" % node_num

            fo = open(key, "w")
            fo.write(value)
            fo.close()
            parentDir = os.path.dirname(os.path.abspath(key))
            os.chmod(parentDir, 0o700)
            os.chmod(key, 0o600)

            vm = VmInfo(ip, user, key, role)
            vm_list.add(vm)
        elif name.startswith('k8s_'):
            value = param["value"]
            value = base64.b64decode(value)
            k8s_folder = path + "/k8s/"
            if not os.path.exists(k8s_folder):
                os.makedirs(k8s_folder)

            deployment_file = k8s_folder + name+".yml"
            fo = open(deployment_file, "w")
            fo.write(value)
            fo.close()
        elif name == "playbook":
            value = param["value"]
            playbook = path + "playbook.yml"
            fo = open(playbook, "w")
            fo.write(value)
            fo.close()
        elif name == "composer":
            value = param["value"]
            compose_file = path + "docker-compose.yml"
            if not param["attributes"] is None and not param["attributes"]["name"] is None:
                compose_name = param["attributes"]["name"]
                docker_login = {}
                if 'docker_login' in param["attributes"]:
                    docker_login['username'] = param["attributes"]["docker_login_username"]
                    docker_login['password'] = param["attributes"]["docker_login_password"]
                    docker_login['registry'] = param["attributes"]["docker_login_registry"]
                    docker_login = param["attributes"]["docker_login"]
            else:
                current_milli_time = lambda: int(round(time.time() * 1000))
                compose_name = "service_" + str(current_milli_time())
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
        ret = docker_kubernetes.run(vm_list, rabbitmq_host, owner)
        ret = docker_kubernetes.deploy(vm_list, k8s_folder)
        return ret
    elif manager_type == "swarm":
        ret = docker_engine.run(vm_list, rabbitmq_host, owner)
        if "ERROR" in ret: return ret
        ret = docker_swarm.run(vm_list, rabbitmq_host, owner)
        if "ERROR" in ret: return ret
        ret = docker_compose.run(vm_list, compose_file, compose_name, rabbitmq_host, owner, docker_login)
        return ret
    elif manager_type == "ansible":
        ret = ansible_playbook.run(vm_list, playbook, rabbitmq_host, owner)
        return ret
    elif manager_type == "scale":
        ret = docker_service.run(vm_list, name_of_deployment, name_of_service, number_of_containers, rabbitmq_host,
                                 owner)
        return ret
    elif manager_type == "swarm_info":
        ret = docker_check.run(vm_list, compose_name, rabbitmq_host, owner)
        ret = '"' + json.dumps(ret) + '"'
        return ret
    else:
        return "ERROR: invalid cluster"


def on_request(ch, method, props, body):
    ret = handle_delivery(body)

    parsed_json = json.loads(body)
    params = parsed_json["parameters"]

    for param in params:
        name = param["name"]
        if name == "cluster":
            manager_type = param["value"]
            break

    if not ret and "ERROR" in ret:
        res_name = "error"
    elif manager_type == "ansible":
        res_name = "ansible_output"
    elif manager_type == "scale":
        res_name = "scale_status"
    elif manager_type == "swarm_info":
        res_name = "swarm_info"
    elif manager_type == "kubernetes":
        res_name = "kubectl_get"
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
    par["value"] = base64.b64encode(ret)
    par["attributes"] = "null"
    response["parameters"].append(par)

    response = json.dumps(response)
    logger.info("Response: " + response)

    ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id= \
                                                         props.correlation_id),
                     body=str(response))
    ch.basic_ack(delivery_tag=method.delivery_tag)


channel.basic_qos(prefetch_count=1)
channel.basic_consume(on_request, queue='deployer_queue')

thread = Thread(target=threaded_function, args=(1,))
thread.start()

logger.info("Awaiting RPC requests")

try:
    channel.start_consuming()
except KeyboardInterrupt:
    # thread.stop()
    done = True
    thread.join()
    logger.info("Threads successfully closed")
