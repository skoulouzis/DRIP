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
import com.rabbitmq.client.DefaultConsumer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author S. Koulouzis
 */
public class RPCServer {

    private static Properties prop;

    public static void main(String[] argv) throws MalformedURLException {
        prop = new Properties();
        if (argv.length >= 1) {
            try {
                prop.load(new FileInputStream(argv[0]));
            } catch (IOException ex) {
                Logger.getLogger(RPCServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String resourceName = "application.properies";
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                prop.load(resourceStream);
            } catch (IOException ex) {
                Logger.getLogger(RPCServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        start();
    }

    private static void start() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(prop.getProperty("message.broker.host"));
        factory.setPassword(prop.getProperty("message.broker.username"));
        factory.setUsername(prop.getProperty("message.broker.password"));
        factory.setPort(AMQP.PROTOCOL.PORT);
        Logger.getLogger(RPCServer.class.getName()).log(Level.INFO, "Connected to: {0}", prop.getProperty("message.broker.host"));
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            //We define the queue name 
            channel.queueDeclare(prop.getProperty("message.broker.queue.provisioner", "provisioner"), false, false, false, null);
            DefaultConsumer c;
            c = new nl.uva.sne.drip.drip.provisioner.Consumer(channel, prop.getProperty("message.broker.host"));

            //Start listening for messages 
            channel.basicConsume(prop.getProperty("message.broker.queue.provisioner", "provisioner"), false, c);

            //Block so we don't close the channel
            while (true) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RPCServer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(RPCServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
