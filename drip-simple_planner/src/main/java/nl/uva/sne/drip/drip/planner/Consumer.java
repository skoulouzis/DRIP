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
 * This is an example of a Message consumer
 *
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
        //Create the reply properties which tells us where to reply, and which id to use.
        //No need to change anything here 
        AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        String response = "";

        try {
            //The queue only moves bytes so we need to convert them to stting 
            String message = new String(body, "UTF-8");
            //Create a tmp folder to save the output. 
            File[] inputFiles;
            File tempDir = new File(System.getProperty("java.io.tmpdir") + File.separator + this.getClass().getSimpleName() + "-" + Long.toString(System.nanoTime()));
            if (!(tempDir.mkdirs())) {
                throw new FileNotFoundException("Could not create output directory: " + tempDir.getAbsolutePath());
            }
            //We need to extact the call parameters form the json message. 
            inputFiles = jacksonUnmarshalExample(message);
            //Call the method with the extracted parameters 
            List<File> files = panner.plan(inputFiles[0].getAbsolutePath(), inputFiles[1].getAbsolutePath(), tempDir.getAbsolutePath());

            //Here we do the same as above with a different API
            inputFiles = simpleJsonUnmarshalExample(message);
            //Call the method with the extracted parameters  
            files = panner.plan(inputFiles[0].getAbsolutePath(), inputFiles[1].getAbsolutePath(), tempDir.getAbsolutePath());

            
            //Now we need to put the result of the call to a message and respond 
            //Example 1
            response = jacksonMarshalExample(files);

            //Example 2
            response = simpleJsonMarshalExample(files);

        } catch (IOException | JSONException ex) {
            response = ex.getMessage();
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //We send the response back. No need to change anything here 
            channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
            channel.basicAck(envelope.getDeliveryTag(), false);
        }

    }

    private File[] jacksonUnmarshalExample(String message) throws IOException {
        //Use the Jackson API to convert json to Object 
        File[] files = new File[2];
        ObjectMapper mapper = new ObjectMapper();
        Message request = mapper.readValue(message, Message.class);

        List<Parameter> params = request.getParameters();

        //Create tmp input files 
        File inputFile = File.createTempFile("input-", Long.toString(System.nanoTime()));
        File exampleFile = File.createTempFile("example-", Long.toString(System.nanoTime()));
        //loop through the parameters in a message to find the input files
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
        //Return the array with input files 
        return files;
    }

    private File[] simpleJsonUnmarshalExample(String message) throws JSONException, FileNotFoundException, IOException {
         //Use the JSONObject API to convert json to Object (Message)
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
        //Use the jackson API to convert Object (Message) to json
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
        //The creationDate is the only filed that has to be there 
        responseMessage.setCreationDate((System.currentTimeMillis()));

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(responseMessage);
    }

    private String simpleJsonMarshalExample(List<File> files) throws JSONException, IOException {
         //Use the JSONObject API to convert Object (Message) to json
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
