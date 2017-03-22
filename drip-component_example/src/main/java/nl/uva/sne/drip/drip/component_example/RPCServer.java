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
package nl.uva.sne.drip.drip.component_example;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 * This class is responsible for receiving and sending message to the DRIP
 * manager via RabbitMQ.
 *
 * @author S. Koulouzis
 */
public class RPCServer {

    /**
     * The name of the queue to send and receive massages
     */
    private static final String RPC_QUEUE_NAME = "planner_queue";
    /**
     * The IP or host name of the RabbitMQ server
     */
    private static String HOST = "127.0.0.1";

    public static void main(String[] args) {
        if (args.length > 1 && args[0].equals("test")) {
            try {
                Consumer c = new Consumer();
                byte[] encoded = Files.readAllBytes(Paths.get(args[1]));
                String response = c.invokeComponent(new String(encoded, "UTF-8"));
                Logger.getLogger(RPCServer.class.getName()).log(Level.INFO, MessageFormat.format("Response: {0}", response));
            } catch (IOException | JSONException ex) {
                Logger.getLogger(RPCServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Properties prop = new Properties();
            if (args.length >= 1 && !args[0].equals("test")) {
                try {
                    prop.load(new FileInputStream(args[0]));
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

    }

    private static void start() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPassword("guest");
        factory.setUsername("guest");
        factory.setPort(AMQP.PROTOCOL.PORT);
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
