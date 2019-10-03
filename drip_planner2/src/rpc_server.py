# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

import json
import logging
import os
import os.path
from builtins import print
from planner.basic_planner import *
from planner.planner import *
import pika
import sys
import tempfile
import time
import logging
import base64

logger = logging.getLogger(__name__)
# if not getattr(logger, 'handler_set', None):
    # logger.setLevel(logging.INFO)
    # h = logging.StreamHandler()
    # formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    # h.setFormatter(formatter)
    # logger.addHandler(h)
    # logger.handler_set = True


def init_chanel(args):
    global rabbitmq_host
    if len(args) > 1:
        rabbitmq_host = args[1]
        queue_name = args[2]  # planner_queue
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
                     properties=pika.BasicProperties(correlation_id=
                                                     props.correlation_id),
                     body=str(response))
    ch.basic_ack(delivery_tag=method.delivery_tag)


def handle_delivery(message):
    logger.info("Got: "+str(message))
    try:
        message = message.decode()
    except (UnicodeDecodeError, AttributeError):
        pass
    parsed_json_message = json.loads(message)
    params = parsed_json_message["parameters"]
    owner = parsed_json_message['owner']
    tosca_value = {}
    tosca_file_name = ''
    max_vms = -1
    for param in params:
        value = param['value']
        name = param["name"]
        if name == 'tosca_input':
            tosca_value = json.loads(value)
            tosca_file_name = name
        if name == 'max_vm':
            max_vms = int(value)

    current_milli_time = lambda: int(round(time.time() * 1000))

    #rabbit = DRIPLoggingHandler(host=rabbitmq_host, port=5672, user=owner)
    #logger.addHandler(rabbit)

    try:
        tosca_file_path = tempfile.gettempdir() + "/planner_files/" + str(current_milli_time()) + "/"
    except NameError:
        import sys
        tosca_file_path = os.path.dirname(os.path.abspath(sys.argv[0])) + "/planner_files/" + str(
            current_milli_time()) + "/"

    if not os.path.exists(tosca_file_path):
        os.makedirs(tosca_file_path)
    with open(tosca_file_path + "/" + tosca_file_name + ".yml", 'w') as outfile:
        outfile.write(json.dumps(tosca_value))

    response = {}
    current_milli_time = lambda: int(round(time.time() * 1000))
    response["creationDate"] = current_milli_time()
    response["parameters"] = []
    if queue_name == "planner_queue":
        planner = BasicPlanner(tosca_file_path + "/" + tosca_file_name + ".yml")
        plan = planner.get_plan()
        parameter = {}
        encodedBytes = base64.b64encode(plan.encode("utf-8"))
        encodedStr = str(encodedBytes, "utf-8")
        parameter['value'] = encodedStr
        parameter['name'] = 'tosca_plan'
        parameter['encoding'] = 'UTF-8'
        response["parameters"].append(parameter)
    logger.info("Returning plan")
    logger.info("Output message:" + json.dumps(response))
    return json.dumps(response)
    

if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO)
    if (sys.argv[1] == "test_local"):
        #        home = expanduser("~")
        #        tosca_reposetory_api_base_url = "http://localhost:8080/winery"
        #        namespace = "http%253A%252F%252Fsne.uva.nl%252Fservicetemplates"
        #        servicetemplate_id = "wordpress_w1-wip1"
        #        planner = WineryPlanner(tosca_reposetory_api_base_url,namespace,servicetemplate_id)
        tosca_file_path = "../../TOSCA/application_example.yaml"
        # planner = BasicPlanner(tosca_file_path)
        planner = Planner(tosca_file_path)
        planner.resolve_requirements()
        planner.set_infrastructure_specifications()
        template = planner.get_tosca_template()
        # logger.info("template ----: \n" + template)
    else:
        logger.info("Input args: " + sys.argv[0] + ' ' + sys.argv[1] + ' ' + sys.argv[2])
        channel = init_chanel(sys.argv)
        global queue_name
        queue_name = sys.argv[2]
        start(channel)
