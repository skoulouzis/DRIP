/*
 * Copyright 2017 S. Koulouzis, Wang Junchao, Huan Zhou, Yang Hu 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.uva.sne.drip.drip.planner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

/**
 *
 * @author S. Koulouzis
 */
public class Consumer extends DefaultConsumer {

    private static final String RPC_QUEUE_NAME = "planner_queue";
    private static final String HOST = "172.17.0.2";
    private final Channel channel;

    public Consumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        String response = "";

        try {
            String message = new String(body, "UTF-8");
            System.out.println(" [.] fib(" + message + ")");

            response += message;
        } catch (RuntimeException e) {
            System.out.println(" [.] " + e.toString());
        } finally {
            channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));

            channel.basicAck(envelope.getDeliveryTag(), false);
        }

    }

}
