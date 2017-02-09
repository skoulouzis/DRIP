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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.commons.types.Parameter;
import nl.uva.sne.drip.commons.types.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author S. Koulouzis
 */
public class Consumer extends DefaultConsumer {

    private final Channel channel;
    private final Planner panner;

    public Consumer(Channel channel) {
        super(channel);
        this.channel = channel;
        this.panner = new Planner();
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                .correlationId(properties.getCorrelationId())
                .build();
        String response = "";

        try {
            String message = new String(body, "UTF-8");
            File[] inputFiles;

            inputFiles = jacksonUnmarshalExample(message);
            panner.plan(inputFiles[0].getAbsolutePath(), inputFiles[1].getAbsolutePath(), "/tmp/out");
            inputFiles = simpleJsonUnmarshalExample(message);
            List<File> files = panner.plan(inputFiles[0].getAbsolutePath(), inputFiles[1].getAbsolutePath(), "/tmp/out");
            response = jacksonMarshalExample(files);
            System.err.println(response);

            response = simpleJsonMarshalExample(files);
            System.err.println(response);

        } catch (RuntimeException | JSONException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
            channel.basicAck(envelope.getDeliveryTag(), false);
        }

    }

    private File[] jacksonUnmarshalExample(String message) throws IOException {
        File[] files = new File[2];
        ObjectMapper mapper = new ObjectMapper();
        Message request = mapper.readValue(message, Message.class);
        //We know that in parameters 0-1 we should have files
        for (int i = 0; i < 2; i++) {
            Map<String, String> fileArg = (java.util.LinkedHashMap) request.getParameters().get(i);

            String fileName = fileArg.get(Parameter.NAME);
            //If not null should be able to use it to download file 
            String url = fileArg.get(Parameter.URL);
            if (url != null) {
                //download
            }

            String encoding = fileArg.get(Parameter.ENCODING);
            String value = fileArg.get(Parameter.VALUE);
            try (PrintWriter out = new PrintWriter(fileName)) {
                out.print(value);
            }
            files[i] = new File(fileName);
        }
        return files;
    }

    private File[] simpleJsonUnmarshalExample(String message) throws JSONException, FileNotFoundException {
        File[] files = new File[2];

        JSONObject jo = new JSONObject(message);
        JSONArray args = jo.getJSONArray("parameters");
        //We know that in parameters 0-2 we should have files
        for (int i = 0; i < 2; i++) {
            JSONObject arg = (JSONObject) args.get(i);
            String fileName = (String) arg.get(Parameter.NAME);
            //If not null should be able to use it to download file

            if (!arg.isNull("url")) {
                String url = (String) arg.get("url");
                //download
            }
            //Otherwise get contents as string 
            String value = (String) arg.get(Parameter.VALUE);
            String encoding = (String) arg.get(Parameter.ENCODING);

            try (PrintWriter out = new PrintWriter(fileName)) {
                out.print(value);
            }
            files[i] = new File(fileName);
        }
        return files;
    }

    private String jacksonMarshalExample(List<File> files) throws UnsupportedEncodingException, IOException {
        Message responseMessage = new Message();

        List parameters = new ArrayList();
        String charset = "UTF-8";
        for (File f : files) {
            Parameter fileParam = new Parameter();
            byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
            fileParam.setValue(new String(bytes, charset));
            fileParam.setEncoding(charset);
            fileParam.setName(f.getName());
            parameters.add(fileParam);

        }
        responseMessage.setParameters(parameters);
        responseMessage.setCreationDate(new Date(System.currentTimeMillis()));

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(responseMessage);
    }

    private String simpleJsonMarshalExample(List<File> files) throws JSONException, IOException {
        JSONObject jo = new JSONObject();
        jo.put("creationDate", new Date(System.currentTimeMillis()));
        List parameters = new ArrayList();
        String charset = "UTF-8";
        for (File f : files) {
            Map<String, String> fileArguments = new HashMap<>();
            fileArguments.put("encoding", charset);
            fileArguments.put("name", f.getName());
            byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
            fileArguments.put("value", new String(bytes, charset));
            parameters.add(fileArguments);
        }
        jo.put("parameters", parameters);
        return jo.toString();
    }

}
