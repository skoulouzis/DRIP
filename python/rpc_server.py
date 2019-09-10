#!/usr/bin/env python
import pika
import random
import time
import json



connection = pika.BlockingConnection(pika.ConnectionParameters(host='172.17.0.3'))
channel = connection.channel()
channel.queue_declare(queue='planner_queue')


def on_request(ch, method, props, body):
    parsed_message = json.loads(body)
    
    response = consume_message(parsed_message)
    
    
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