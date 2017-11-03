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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import nl.uva.sne.drip.api.dao.CloudCredentialsDao;
import nl.uva.sne.drip.commons.utils.DRIPLogRecordFactory;
import nl.uva.sne.drip.drip.commons.data.v1.external.DRIPLogRecord;
import nl.uva.sne.drip.drip.commons.data.v1.external.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
public class DRIPLogHandler extends StreamHandler implements AutoCloseable {

    private final Connection connection;
    private final Channel channel;

    private final String qeueName;
    private final ObjectMapper mapper;

    public DRIPLogHandler(String messageBrokerHost) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(messageBrokerHost);
        factory.setPort(AMQP.PROTOCOL.PORT);
        //factory.setUsername("guest");
        //factory.setPassword("pass");

        connection = factory.newConnection();
        channel = connection.createChannel();

        this.qeueName = "log_qeue";

        this.mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    @Override
    public void publish(LogRecord record) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String owner = user.getUsername();

            DRIPLogRecord dripLog = DRIPLogRecordFactory.create(record);
            dripLog.setOwner(owner);
            String jsonInString = mapper.writeValueAsString(dripLog);

//            channel.basicPublish(qeueName, owner, null, jsonInString.getBytes());
//            channel.basicPublish(qeueName, owner, MessageProperties.PERSISTENT_TEXT_PLAIN, jsonInString.getBytes("UTF-8"));
            String qeueNameUser = qeueName + "_" + owner;
            channel.queueDeclare(qeueNameUser, true, false, false, null);
            
            channel.basicPublish("", qeueNameUser, MessageProperties.PERSISTENT_TEXT_PLAIN, jsonInString.getBytes("UTF-8"));

            close();
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DRIPLogHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DRIPLogHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void close() {
        super.close();
        if (channel != null && channel.isOpen()) {
            try {
                channel.close();
                if (connection != null && connection.isOpen()) {
                    connection.close();
                }
            } catch (IOException | TimeoutException ex) {
                Logger.getLogger(DRIPLogHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
