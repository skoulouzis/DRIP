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
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.commons.types.Parameter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * This is an example of a Message consumer
 *
 *
 * @author H. Zhou
 */
public class Consumer extends DefaultConsumer {

    private final Channel channel;
    private final String jarFilePath = "/root/SWITCH/bin/ProvisioningCore.jar";

    public class topologyElement {

        String topologyName = "";
        String outputFilePath = "";
    }

    public Consumer(Channel channel) {
        super(channel);
        this.channel = channel;
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
            String tempInputDirPath = System.getProperty("java.io.tmpdir") + File.separator + "Input-" + Long.toString(System.nanoTime()) + File.separator;
            File tempInputDir = new File(tempInputDirPath);
            if (!(tempInputDir.mkdirs())) {
                throw new FileNotFoundException("Could not create input directory: " + tempInputDir.getAbsolutePath());
            }

            ArrayList topologyInfoArray;
            topologyInfoArray = invokeProvisioner(message, tempInputDirPath);
            response = generateResponse(topologyInfoArray);

        } catch (IOException | JSONException ex) {
            response = ex.getMessage();
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //We send the response back. No need to change anything here 
            channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
            channel.basicAck(envelope.getDeliveryTag(), false);
        }

    }

    ////If the provisioner jar file is successfully invoked, the returned value should be a set of output file paths which are expected.
    ////If there are some errors or some information missing with this message, the returned value will be null.
    ////The input dir path contains '/'
    private ArrayList<topologyElement> invokeProvisioner(String message, String tempInputDirPath) throws IOException, JSONException {
        //Use the Jackson API to convert json to Object 
        JSONObject jo = new JSONObject(message);
        JSONArray parameters = jo.getJSONArray("parameters");

        //Create tmp input files 
        File ec2ConfFile = null;
        File geniConfFile = null;
        //loop through the parameters in a message to find the input files
        String logDir = "null", mainTopologyPath = "null", sshKeyFilePath = "null", scriptPath = "null";
        ArrayList<topologyElement> topologyInfoArray = new ArrayList();
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            String name = (String) param.get(Parameter.NAME);

            if (name.equals("ec2.conf")) {
                try {
                    ec2ConfFile = new File(tempInputDirPath + "ec2.Conf");
                    if (ec2ConfFile.createNewFile()) {
                        PrintWriter out = new PrintWriter(ec2ConfFile);
                        out.print(param.get(Parameter.VALUE));
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
                    return null;
                }
            } else if (name.equals("geni.conf")) {
                try {
                    geniConfFile = new File(tempInputDirPath + "geni.Conf");
                    if (geniConfFile.createNewFile()) {
                        PrintWriter out = new PrintWriter(geniConfFile);
                        out.print(param.get(Parameter.VALUE));
                    } else {
                        return null;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            } else if (name.equals("topology")) {
                JSONObject attribute_level = param.getJSONObject("attributes");
                int fileLevel = Integer.valueOf((String) attribute_level.get("level"));
                if (fileLevel == 0) /////if the file level is 0, it means that this is the top level description
                {
                    try {
                        File topologyFile = new File(tempInputDirPath + "topology_main");
                        if (topologyFile.createNewFile()) {
                            PrintWriter out = new PrintWriter(geniConfFile);
                            out.print(param.get(Parameter.VALUE));
                            mainTopologyPath = topologyFile.getAbsolutePath();
                        } else {
                            return null;
                        }
                    } catch (IOException e) {
                        Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
                        return null;
                    }
                } else if (fileLevel == 1) {    ////this means that this file is the low level detailed description
                    String fileName = (String) attribute_level.get("filename");   ////This file name does not contain suffix of '.yml' for example
                    try {
                        File topologyFile = new File(tempInputDirPath + fileName + ".yml");
                        String outputFilePath = tempInputDirPath + fileName + "_provisioned.yml";
                        if (topologyFile.createNewFile()) {
                            PrintWriter out = new PrintWriter(geniConfFile);
                            out.print(param.get(Parameter.VALUE));
                            topologyElement x = new topologyElement();
                            x.topologyName = fileName;
                            x.outputFilePath = outputFilePath;
                            topologyInfoArray.add(x);

                        } else {
                            return null;
                        }
                    } catch (IOException e) {
                        Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
                        return null;
                    }
                }
            } else if (name.equals("logdir")) {
                logDir = (String) param.get(Parameter.VALUE);
            } else if (name.equals("sshkey")) {
                sshKeyFilePath = (String) param.get(Parameter.VALUE);
            } else if (name.equals("guiscript")) {
                scriptPath = (String) param.get(Parameter.VALUE);
            } else {
                return null;
            }
        }

        File curDir = new File(tempInputDirPath);
        String[] ls = curDir.list();
        for (int i = 0; i < ls.length; i++) {
            if (ls[i].contains(".")) {
                String[] fileTypes = ls[i].split("\\.");
                if (fileTypes.length > 0) {
                    int lastIndex = fileTypes.length - 1;
                    String fileType = fileTypes[lastIndex];
                    if (fileType.equals("yml")) {
                        String toscaFile = curDir + ls[i];
                        if (!sshKeyFilePath.equals("null")) {
                            changeKeyFilePath(toscaFile, sshKeyFilePath);
                        }

                        if (!scriptPath.equals("null")) {
                            changeGUIScriptFilePath(toscaFile, scriptPath);
                        }

                    }
                }
            }
        }

        if (ec2ConfFile == null && geniConfFile == null) {
            return null;
        }
        if (mainTopologyPath.equals("null")) {
            return null;
        }
        String ec2ConfFilePath = "null";
        String geniConfFilePath = "null";
        if (ec2ConfFile != null) {
            ec2ConfFilePath = ec2ConfFile.getAbsolutePath();
        }
        if (geniConfFile != null) {
            geniConfFilePath = geniConfFile.getAbsolutePath();
        }
        if (logDir.equals("null")) {
            logDir = "/tmp/";
        }

        String cmd = "java -jar " + jarFilePath + " ec2=" + ec2ConfFilePath + " exogeni=" + geniConfFilePath + " logDir=" + logDir + " topology=" + mainTopologyPath;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
        }

        topologyElement x = new topologyElement();
        x.topologyName = "kubernetes";
        x.outputFilePath = tempInputDirPath + "file_kubernetes";
        topologyInfoArray.add(x);

        return topologyInfoArray;
    }

    ////Change the key file path in the tosca file. 
    ////Because the user needs to upload their public key file into the server file system. 
    private void changeKeyFilePath(String toscaFilePath, String newKeyFilePath) {
        File toscaFile = new File(toscaFilePath);
        String fileContent = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(toscaFile));
            String line = "";
            while ((line = in.readLine()) != null) {
                if (line.contains("publicKeyPath")) {
                    fileContent += ("publicKeyPath: " + newKeyFilePath + "\n");
                } else {
                    fileContent += (line + "\n");
                }
            }
            in.close();

            FileWriter fw = new FileWriter(toscaFilePath, false);
            fw.write(fileContent);
            fw.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void changeGUIScriptFilePath(String toscaFilePath, String newScriptPath) {
        File toscaFile = new File(toscaFilePath);
        String fileContent = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(toscaFile));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("script")) {
                    int index = line.indexOf("script:");
                    String prefix = line.substring(0, index + 7);
                    fileContent += (prefix + " " + newScriptPath + "\n");
                } else {
                    fileContent += (line + "\n");
                }
            }
            in.close();

            FileWriter fw = new FileWriter(toscaFilePath, false);
            fw.write(fileContent);
            fw.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private String generateResponse(ArrayList<topologyElement> outputs) throws JSONException, IOException {
        //Use the JSONObject API to convert Object (Message) to json
        JSONObject jo = new JSONObject();
        jo.put("creationDate", (System.currentTimeMillis()));
        List parameters = new ArrayList();
        String charset = "UTF-8";
        if (outputs == null) {
            Map<String, String> fileArguments = new HashMap<>();
            fileArguments.put("encoding", charset);
            fileArguments.put("name", "ERROR");
            fileArguments.put("value", "Some error with input messages!");
            parameters.add(fileArguments);
        } else {
            for (int i = 0; i < outputs.size(); i++) {
                Map<String, String> fileArguments = new HashMap<>();
                fileArguments.put("encoding", charset);
                File f = new File(outputs.get(i).outputFilePath);
                if (f.exists()) {
                    fileArguments.put("name", outputs.get(i).topologyName);
                    byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
                    fileArguments.put("value", new String(bytes, charset));
                    parameters.add(fileArguments);

                } else {
                    fileArguments.put("name", outputs.get(i).topologyName);
                    fileArguments.put("value", "ERROR::There is no output for topology " + outputs.get(i).topologyName);
                    parameters.add(fileArguments);
                }

            }
        }
        jo.put("parameters", parameters);
        return jo.toString();
    }

}
