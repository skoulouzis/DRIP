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
package nl.uva.sne.drip.api.event;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
public class DRIPLogger extends StreamHandler implements AutoCloseable {

    private final Connection connection;
    private final Channel channel;
    private static final String EXCHANGE_NAME = "direct_logs";

    public DRIPLogger(String messageBrokerHost) throws IOException, TimeoutException {
        super();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(messageBrokerHost);
        factory.setPort(AMQP.PROTOCOL.PORT);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
    }

    @Override
    public void publish(LogRecord record) {
        try {
            String level = record.getLevel().getName();
            String message = record.getMessage();
            channel.basicPublish(EXCHANGE_NAME, level, null, message.getBytes("UTF-8"));
            super.publish(record);
        } catch (IOException ex) {
            Logger.getLogger(DRIPLogger.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void close() {
        if (channel != null && channel.isOpen()) {
            try {
                channel.close();
            } catch (IOException | TimeoutException ex) {
                Logger.getLogger(DRIPLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connection != null && connection.isOpen()) {
            try {
                connection.close();
            } catch (IOException ex) {
                Logger.getLogger(DRIPLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
