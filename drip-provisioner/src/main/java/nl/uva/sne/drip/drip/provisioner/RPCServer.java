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
package nl.uva.sne.drip.drip.provisioner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author H. Zhou
 */
public class RPCServer {

    private static final String RPC_QUEUE_NAME = "provisioner_queue";
    private static String HOST = "127.0.0.1";

    public static void main(String[] argv) {
        Properties prop = new Properties();
        if (argv.length >= 1) {
            try {
                prop.load(new FileInputStream(argv[0]));
            } catch (IOException ex) {
                Logger.getLogger(RPCServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String resourceName = "provisioner.properies";
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                prop.load(resourceStream);
            } catch (IOException ex) {
                Logger.getLogger(RPCServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        HOST = prop.getProperty("rabbitmq.host", "127.0.0.1");
        Logger.getLogger(RPCServer.class.getName()).log(Level.INFO, MessageFormat.format("rabbitmq.host: {0}", HOST));
        start();
    }

    private static void start() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPassword("guest");
        factory.setUsername("guest");
        factory.setPort(AMQP.PROTOCOL.PORT);
        Logger.getLogger(RPCServer.class.getName()).log(Level.INFO, "Connected to: {0}", HOST);
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            //We define the queue name 
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            //Set our own customized consummer 
            Consumer c = new Consumer(channel);
            //Start listening for messages 
            channel.basicConsume(RPC_QUEUE_NAME, false, c);

            //Block so we don't close the channel
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException _ignore) {
                }
            }

        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(RPCServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
