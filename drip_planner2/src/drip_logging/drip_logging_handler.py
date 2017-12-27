import json
import logging
import pika
from python_logging_rabbitmq import RabbitMQHandler

class DRIPLoggingHandler(RabbitMQHandler):
    
    def __init__(self, host='localhost', port=5672, username=None, password=None, user=None):
        super(DRIPLoggingHandler, self).__init__(host=host, port=port, username=username, password=password)
        self.user = user
    
    def open_connection(self):
        self.sequenceNumber = 0
        """
        Connect to RabbitMQ.
        """
        # Set logger for pika.
        # See if something went wrong connecting to RabbitMQ.
        handler = logging.StreamHandler()
        handler.setFormatter(self.formatter)
        rabbitmq_logger = logging.getLogger('pika')
        rabbitmq_logger.addHandler(handler)
        rabbitmq_logger.propagate = False
        rabbitmq_logger.setLevel(logging.WARNING)

        if not self.connection or self.connection.is_closed:
            self.connection = pika.BlockingConnection(pika.ConnectionParameters(** self.connection_params))

        if not self.channel or self.channel.is_closed:
            self.channel = self.connection.channel()
            
        self.channel.queue_declare(queue='log_qeue_' + self.user, durable=True)

        # Manually remove logger to avoid shutdown message.
        rabbitmq_logger.removeHandler(handler)

    def emit(self, record):
        self.acquire()

        try:
            if not self.connection or self.connection.is_closed or not self.channel or self.channel.is_closed:
                self.open_connection()

            queue='log_qeue_' + self.user
            self.channel.basic_publish(
                                       exchange='',
                                       routing_key=queue,
                                       body=self.format(record),
                                       properties=pika.BasicProperties(
                                       delivery_mode=2)
                                       )
        
            
        except Exception:
            self.channel, self.connection = None, None
            self.handleError(record)
        finally:
            if self.close_after_emit:
                self.close_connection()

        self.release()
        
        
    def format(self, record):
        drip_record = {}
        drip_record['timestamp'] = record.created
        drip_record['owner'] = 'user'
        drip_record['level'] = record.levelname
        drip_record['loggerName'] = record.module
        drip_record['message'] = record.message
        drip_record['millis'] = record.created
        self.sequenceNumber += 1
        drip_record['sequenceNumber'] = self.sequenceNumber
        drip_record['sourceClassName'] = record.module
        drip_record['sourceMethodName'] = record.funcName
        return json.dumps(drip_record)
        