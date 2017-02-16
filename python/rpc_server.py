#!/usr/bin/env python
import pika
import json



connection = pika.BlockingConnection(pika.ConnectionParameters(host='172.17.0.2'))
channel = connection.channel()
channel.queue_declare(queue='planner_queue')



def handleDelivery(message):
    parsed_json = json.loads(message)
    params = parsed_json["parameters"]
    for param in params:
        name = param["name"]
        value = param["value"]
        
    

def on_request(ch, method, props, body):
    handleDelivery(body)

    print(" Message %s" % body)
    response = "AAAAAAAAAAAAAAAAAAAAAA"

    ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id = \
                                                         props.correlation_id),
                     body=str(response))
    ch.basic_ack(delivery_tag = method.delivery_tag)

channel.basic_qos(prefetch_count=1)
channel.basic_consume(on_request, queue='planner_queue')

print(" [x] Awaiting RPC requests")
channel.start_consuming()