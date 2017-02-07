/*
 * Copyright 2017 S. Koulouzis.
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
package nl.uva.sne.drip.rpc;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author S. Koulouzis.
 */
class DRIPComponent {

    private Connection connection;
    private Channel channel;

    private void connect(String messageBrokerHost) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(messageBrokerHost);

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void close() throws IOException {
        connection.close();
    }

    /**
     * @return the channel
     */
    public Channel getChannel(String messageBrokerHost) throws IOException, TimeoutException {
        if (channel == null) {
            connect(messageBrokerHost);
        }
        return channel;
    }

}
