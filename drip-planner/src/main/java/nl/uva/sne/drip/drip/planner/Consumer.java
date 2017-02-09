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
            File tempDir = new File(System.getProperty("java.io.tmpdir") + File.separator + this.getClass().getSimpleName() + "-" + Long.toString(System.nanoTime()));
            if (!(tempDir.mkdirs())) {
                throw new FileNotFoundException("Could not create output directory: " + tempDir.getAbsolutePath());
            }

            inputFiles = jacksonUnmarshalExample(message);
            panner.plan(inputFiles[0].getAbsolutePath(), inputFiles[1].getAbsolutePath(), tempDir.getAbsolutePath());
            inputFiles = simpleJsonUnmarshalExample(message);

            List<File> files = panner.plan(inputFiles[0].getAbsolutePath(), inputFiles[1].getAbsolutePath(), tempDir.getAbsolutePath());
            response = jacksonMarshalExample(files);
            System.err.println(response);

            response = simpleJsonMarshalExample(files);
            System.err.println(response);

        } catch (JSONException | FileNotFoundException ex) {
            response = ex.getMessage();
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
        List<Parameter> params = request.getParameters();
        File inputFile = File.createTempFile("input-", Long.toString(System.nanoTime()));
        File exampleFile = File.createTempFile("example-", Long.toString(System.nanoTime()));
        for (Parameter param : params) {
            if (param.getName().equals("input")) {
                try (PrintWriter out = new PrintWriter(inputFile)) {
                    out.print(param.getValue());
                }
                files[0] = inputFile;
            }
            if (param.getName().equals("example")) {
                try (PrintWriter out = new PrintWriter(exampleFile)) {
                    out.print(param.getValue());
                }
                files[1] = exampleFile;
            }
        }
        return files;
    }

    private File[] simpleJsonUnmarshalExample(String message) throws JSONException, FileNotFoundException, IOException {
        File[] files = new File[2];

        JSONObject jo = new JSONObject(message);
        JSONArray parameters = jo.getJSONArray("parameters");
        File inputFile = File.createTempFile("input-", Long.toString(System.nanoTime()));
        File exampleFile = File.createTempFile("example-", Long.toString(System.nanoTime()));
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            String name = (String) param.get(Parameter.NAME);
            if (name.equals("input")) {
                try (PrintWriter out = new PrintWriter(inputFile)) {
                    out.print(param.get(Parameter.VALUE));
                }
                files[0] = inputFile;
            }
            if (name.equals("example")) {
                try (PrintWriter out = new PrintWriter(exampleFile)) {
                    out.print(param.get(Parameter.VALUE));
                }
                files[1] = exampleFile;
            }
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
        responseMessage.setCreationDate((System.currentTimeMillis()));

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(responseMessage);
    }

    private String simpleJsonMarshalExample(List<File> files) throws JSONException, IOException {
        JSONObject jo = new JSONObject();
        jo.put("creationDate", (System.currentTimeMillis()));
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
