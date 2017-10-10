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
    try:
#        tosca = ToscaTemplate('/home/alogo/workspace/DRIP/docs/input_tosca_files/tosca_MOG_input.yaml')       
        tosca = ToscaTemplate('/home/alogo/workspace/DRIP/docs/input_tosca_files/input.yaml')
#        for node in tosca.nodetemplates:
#                print "Name %s Type: %s " %(node.name,node.type)
#    
#        for input in tosca.inputs:
#            print input.name

#        for node in tosca.nodetemplates:
#            for relationship, trgt in node.relationships.items():
#                rel_template = trgt.get_relationship_template()
#                for rel in rel_template:
#                    print "source %s Relationship: %s target: %s" %(rel.source.type,rel.type,rel.target.type)
#                    print dir(rel)
        response = {}
        current_milli_time = lambda: int(round(time.time() * 1000))
        response["creationDate"] = current_milli_time()   
        response["parameters"] = []
        
        compute_nodes =  []
                
        for node in tosca.nodetemplates:
#            print dir(node)
            print "Name: %s Type: %s props: %s"%(node.name,node.type,node.get_properties().keys())
            for relationship, trgt in node.relationships.items():
                if relationship.type == EntityType.HOSTEDON:
                    rel_template = trgt.get_relationship_template()
                    for rel in rel_template:
                        compute_nodes.append(rel.target)

        for compute_node in compute_nodes:
            result = {}
            parameter = {}
            result['name'] = node.name
            result['size'] = 'Medium'
            result['docker'] = compute_node.get_properties()['dockers'].value
#            print dir(compute_node.get_properties()['dockers'] )
#            print "dockers. Name: %s Type: %s Value: %s" % (compute_node.get_properties()['dockers'].name, compute_node.get_properties()['dockers'].type, compute_node.get_properties()['dockers'].value )
            parameter['value'] = str(json.dumps(result))
            parameter['attributes'] = 'null'
            parameter["url"] = "null"
            parameter["encoding"] = "UTF-8"
            response["parameters"].append(parameter)           
#            print "Name: %s Type: %s properties: %s" %(compute_node.name,compute_node.type,compute_node.get_properties().keys())

        print ("Output message: %s" % json.dumps(response))

    except AttributeError as e:
        z = e
        print z
    
    
    
    
        
