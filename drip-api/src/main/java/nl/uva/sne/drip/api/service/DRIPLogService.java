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
package nl.uva.sne.drip.api.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import nl.uva.sne.drip.drip.commons.data.v1.external.DRIPLogRecord;
import nl.uva.sne.drip.drip.commons.data.v1.external.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class DRIPLogService {

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    private final String qeueName = "log_qeue";
    private ObjectMapper mapper;
    private ConnectionFactory factory;

    public DRIPLogRecord get() throws IOException, TimeoutException {
        Channel channel = null;
        if (factory == null) {
            this.factory = new ConnectionFactory();
            factory.setHost(messageBrokerHost);
            factory.setPort(AMQP.PROTOCOL.PORT);
        }
        if (this.mapper == null) {
            this.mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        }

        try (Connection connection = factory.newConnection()) {
            channel = connection.createChannel();

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String owner = user.getUsername();
            String qeueNameUser = qeueName + "_" + owner;
            channel.queueDeclare(qeueNameUser, true, false, false, null);

            GetResponse response = channel.basicGet(qeueNameUser, true);
            if (response != null) {
                String message = new String(response.getBody(), "UTF-8");
                return mapper.readValue(message, DRIPLogRecord.class);
            }

        }
        return null;
    }

}
