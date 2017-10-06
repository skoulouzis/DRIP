# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

import pika
from planner.dump_planner import *
import sys
from toscaparser import *
from toscaparser.tosca_template import ToscaTemplate
from toscaparser.elements.entity_type import EntityType



def init_chanel(args):
    if len(args) > 1:
        rabbitmq_host = args[1]
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
    planner = DumpPlanner();
    response = planner.plan(parsed_json_value);
            
    return response

if __name__ == "__main__":
#    print sys.argv
#    channel = init_chanel(sys.argv)
#    start(channel)
    tosca = ToscaTemplate('/home/alogo/workspace/DRIP/docs/input_tosca_files/tosca_MOG_input.yaml')
#    for node in tosca.nodetemplates:
#        print "Name %s Type: %s " %(node.name,node.type)
#    
#    for input in tosca.inputs:
#        print input.name

#    for node in tosca.nodetemplates:
#        for relationship, trgt in node.relationships.items():
#            rel_template = trgt.get_relationship_template()
#            for rel in rel_template:
#                print "source %s Relationship: %s target: %s" %(rel.source.type,rel.type,rel.target.type)
#                print dir(rel)
    
    for node in tosca.nodetemplates:
        for relationship, trgt in node.relationships.items():
#            print EntityType.HOSTEDON
#            print type(EntityType.HOSTEDON)
#            print relationship.type
#            print type(relationship.type)
            if relationship.type == EntityType.HOSTEDON:
                print relationship.type
    
        
