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
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Consumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    public Consumer() {
        super(null);
        this.channel = null;
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
            response = invokeComponent(message);
        } catch (JSONException | IOException ex) {
            response = ex.getMessage();
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //We send the response back. No need to change anything here 
            channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
    }

    /**
     * This method is extracts the parammeters from incoming JSON message.
     *
     * @param message
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public String invokeComponent(String message) throws JSONException, IOException {
        JSONObject messageJson = new JSONObject(message);
        Date creationDate = getMessageCreationDate(messageJson);
        Logger.getLogger(Consumer.class.getName()).log(Level.INFO, "Message was created at: {0}", creationDate);

        JSONArray parameters = messageJson.getJSONArray("parameters");
        int input = getInputInteger(parameters);

        File inputTextFile = getInputFile(parameters, System.getProperty("java.io.tmpdir") + File.separator + "delete-me.txt");

        File inputBinFile = getInputImageFile(parameters, System.getProperty("java.io.tmpdir"));

        ExamplePOJO book = getExamplePOJO(parameters);

        Component component = new Component(input, inputTextFile, inputBinFile, book);
        String response;
        try {
            ExampleResult result = component.run();
            response = createMessage(result);
        } catch (Exception ex) {
            response = ex.getMessage();
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    private Date getMessageCreationDate(JSONObject messageJson) throws JSONException {
        return new Date(messageJson.getLong("creationDate"));
    }

    private Integer getInputInteger(JSONArray parameters) throws JSONException {
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            if (param.has("name") && param.getString("name").equals("input")) {
                return param.getInt("value");
            }
        }
        return null;
    }

    private File getInputFile(JSONArray parameters, String filePath) throws JSONException, IOException {
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            if (param.has("name") && param.getString("name").equals("input-file")) {
                OpenOption[] options = new OpenOption[1];
                options[0] = StandardOpenOption.CREATE_NEW;
                String value = param.getString("value");
                String encoding = param.getString("encoding");

                File file = new File(filePath);
                file.delete();
                byte[] bytes = null;
                if (encoding.equals("UTF-8")) {
                    bytes = value.getBytes();
                }
                if (encoding.equals("Base64")) {
                    bytes = Base64.getDecoder().decode(value);
                }
                Files.write(Paths.get(file.getAbsolutePath()), bytes, options);
                return file;
            }
        }
        return null;
    }

    private File getInputImageFile(JSONArray parameters, String filePath) throws IOException, JSONException {
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            if (param.has("name") && param.getString("name").equals("input_image")) {
                OpenOption[] options = new OpenOption[1];
                options[0] = StandardOpenOption.CREATE_NEW;
                String value = param.getString("value");
                String encoding = param.getString("encoding");
                JSONObject attributes = param.getJSONObject("attributes");
                String fileName = attributes.getString("filename");
                File file = new File(filePath + File.separator + fileName);
                file.delete();
                byte[] bytes = null;
                if (encoding.equals("UTF-8")) {
                    bytes = value.getBytes();
                }
                if (encoding.equals("Base64")) {
                    bytes = Base64.getDecoder().decode(value);
                }
                Files.write(Paths.get(file.getAbsolutePath()), bytes, options);
                return file;
            }
        }
        return null;

    }

    private ExamplePOJO getExamplePOJO(JSONArray parameters) throws JSONException, MalformedURLException, IOException {
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            if (param.has("name") && param.getString("name").equals("text")) {
                URL url = new URL(param.getString("url"));

                String content = getContent(url);

                JSONObject attributes = param.getJSONObject("attributes");
                String author = attributes.getString("Author");

                String translator = attributes.getString("Translator");

                String language = attributes.getString("Language");
                ExamplePOJO book = new ExamplePOJO(author, content, translator, language);

                return book;
            }
        }
        return null;

    }

    private String getContent(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        StringBuilder cont;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            cont = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                cont.append(inputLine);
            }
        }
        return cont.toString();
    }

    private String createMessage(ExampleResult result) throws JSONException, IOException {
        JSONObject message = new JSONObject();
        message.put("creationDate", (System.currentTimeMillis()));
        List parameters = new ArrayList();
        //JSON objects must be added as values with "" and escape all ". For the 
        //message it's just a string
        JSONObject bookJSON = examplePOJOtoJson(result.book);
        String bookJSONString = bookJSON.toString().replaceAll("\"", "\\\"");
        bookJSONString = "\"" + bookJSONString + "\"";
        Map<String, String> bookParam = new HashMap<>();
        bookParam.put("name", "book");
        bookParam.put("value", bookJSONString);
        bookParam.put("encoding", "UTF-8");
        parameters.add(bookParam);

        //Binarry files must be encoded as base64
        byte[] bytes = Files.readAllBytes(Paths.get(result.inputBinFile.getAbsolutePath()));
        byte[] encoded = Base64.getEncoder().encode(bytes);
        String cont = new String(encoded, "US-ASCII");
        Map<String, String> binFileParam = new HashMap<>();
        binFileParam.put("name", "image");
        binFileParam.put("value", cont);
        binFileParam.put("encoding", "Base64");
        parameters.add(binFileParam);

        //Text files canh be encoded as to UTF-8
        bytes = Files.readAllBytes(Paths.get(result.inputTextFile.getAbsolutePath()));
        cont = new String(bytes, "UTF-8");
        Map<String, String> textFileParam = new HashMap<>();
        textFileParam.put("name", "text");
        textFileParam.put("value", cont);
        textFileParam.put("encoding", "UTF-8");
        parameters.add(textFileParam);

        Map<String, String> intParam = new HashMap<>();
        intParam.put("name", "output");
        intParam.put("value", String.valueOf(result.output));
        parameters.add(intParam);

        message.put("parameters", parameters);
        return message.toString();
    }

    private JSONObject examplePOJOtoJson(ExamplePOJO book) throws JSONException {
        JSONObject bookJSON = new JSONObject();
        bookJSON.append("Author", book.getAuthor());
        bookJSON.append("Content", book.getContent());
        bookJSON.append("Language", book.getLanguage());
        bookJSON.append("Translator", book.getTranslator());
        return bookJSON;
    }
}
