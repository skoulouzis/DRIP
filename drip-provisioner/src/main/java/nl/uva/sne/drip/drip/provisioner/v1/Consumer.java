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
package nl.uva.sne.drip.drip.provisioner.v1;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.KeyPair;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.drip.commons.data.internal.Message;
import nl.uva.sne.drip.drip.commons.data.internal.MessageParameter;
import nl.uva.sne.drip.drip.provisioner.utils.MessageParsing;
import nl.uva.sne.drip.drip.provisioner.utils.PropertyValues;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
//import org.globus.myproxy.MyProxyException;
import org.ietf.jgss.GSSException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import provisioning.credential.Credential;
import provisioning.credential.EC2Credential;
import provisioning.credential.EGICredential;
import provisioning.credential.SSHKeyPair;
import provisioning.credential.UserCredential;
import provisioning.database.EC2.EC2Database;
import provisioning.database.EGI.EGIDatabase;
import provisioning.database.UserDatabase;
import provisioning.engine.TEngine.TEngine;
import provisioning.request.RecoverRequest;
import provisioning.request.ScalingRequest;
import topologyAnalysis.TopologyAnalysisMain;
import topologyAnalysis.dataStructure.SubTopologyInfo;
import topologyAnalysis.dataStructure.VM;

/**
 *
 * This is a provision Message consumer
 *
 *
 * @author H. Zhou, S. Koulouzis
 */
public class Consumer extends DefaultConsumer {

    private final Channel channel;
//    Map<String, String> em = new HashMap<>();

    public Consumer(Channel channel) throws IOException {
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
            //The queue only moves bytes so we need to convert them to string 
            String message = new String(body, "UTF-8");

            String tempInputDirPath = System.getProperty("java.io.tmpdir") + File.separator + "Input-" + Long.toString(System.nanoTime()) + File.separator;
            File tempInputDir = new File(tempInputDirPath);
            if (!(tempInputDir.mkdirs())) {
                throw new FileNotFoundException("Could not create input directory: " + tempInputDir.getAbsolutePath());
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            response = mapper.writeValueAsString(invokeProvisioner(message, tempInputDirPath));

        } catch (Throwable ex) {
            try {
                response = generateExeptionResponse(ex);
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex1) {
                response = "{\"creationDate\": " + System.currentTimeMillis()
                        + ",\"parameters\": [{\"url\": null,\"encoding\": UTF-8,"
                        + "\"value\": \"" + ex.getMessage() + "\",\"name\": \""
                        + ex.getClass().getName() + "\",\"attributes\": null}]}";
            }
        } finally {
            Logger.getLogger(Consumer.class.getName()).log(Level.INFO, "Sending Response: {0}", response);
            //We send the response back. No need to change anything here 
            channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
            channel.basicAck(envelope.getDeliveryTag(), false);
        }

    }

    private Message invokeProvisioner(String messageStr, String tempInputDirPath) throws IOException, JSONException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        Message massage = mapper.readValue(messageStr, Message.class);
        List<MessageParameter> params = massage.getParameters();
        for (MessageParameter mp : params) {
            String name = mp.getName();
            String value = mp.getValue();
            if (name.equals("action")) {
                switch (value) {
                    case "start_topology":
                        JSONObject jo = new JSONObject(messageStr);
                        return startTopology(jo.getJSONArray("parameters"), tempInputDirPath);
                    case "kill_topology":
                        jo = new JSONObject(messageStr);
                        return killTopology(jo.getJSONArray("parameters"), tempInputDirPath);
                    case "scale_topology_up":
                        jo = new JSONObject(messageStr);
                        return scaleTopologyUp(jo.getJSONArray("parameters"), tempInputDirPath);
                    case "scale_topology_down":
                        jo = new JSONObject(messageStr);
                        return scaleTopologyDown(jo.getJSONArray("parameters"), tempInputDirPath);
                }
            }
        }
        return null;
    }

    private Message startTopology(JSONArray parameters, String tempInputDirPath) throws Exception {
        Logger.getLogger(Consumer.class.getName()).log(Level.INFO, "Starting topology");
        TEngine tEngine = new TEngine();
        TopologyAnalysisMain tam = null;
        UserCredential userCredential = new UserCredential();
        UserDatabase userDatabase = new UserDatabase();
        try {
            File topologyFile = MessageParsing.getTopologies(parameters, tempInputDirPath, 0).get(0);
            File mainTopologyFile = new File(tempInputDirPath + "topology_main.yml");
            FileUtils.moveFile(topologyFile, mainTopologyFile);
            String topTopologyLoadingPath = mainTopologyFile.getAbsolutePath();
            List<File> topologyFiles = MessageParsing.getTopologies(parameters, tempInputDirPath, 1);
//            for (File lowLevelTopologyFile : topologyFiles) {
//                File secondaryTopologyFile = new File(tempInputDirPath + File.separator + lowLevelTopologyFile.getName() + ".yml");
//                FileUtils.moveFile(lowLevelTopologyFile, secondaryTopologyFile);
//            }
            Logger.getLogger(Consumer.class.getName()).log(Level.INFO, "Saved topology file");
            Map<String, Object> map = Converter.ymlStream2Map(new FileInputStream(topTopologyLoadingPath));
            String userPublicKeyName = ((String) map.get("publicKeyPath"));
            if (userPublicKeyName != null) {
                userPublicKeyName = userPublicKeyName.split("@")[1].replaceAll("\"", "");
            } else if (userPublicKeyName == null) {
                userPublicKeyName = "id_rsa.pub";
                map.put("publicKeyPath", "name@" + userPublicKeyName);
                String cont = Converter.map2YmlString(map);
                MessageParsing.writeValueToFile(cont, new File(topTopologyLoadingPath));
            }
            Logger.getLogger(Consumer.class.getName()).log(Level.INFO, "Generated id_rsa.pub");
            String userPrivateName = FilenameUtils.removeExtension(userPublicKeyName);
            List<File> sshKeys = MessageParsing.getSSHKeys(parameters, tempInputDirPath, userPublicKeyName, "user_ssh_key");
            if (sshKeys == null || sshKeys.isEmpty()) {
                JSch jsch = new JSch();
                KeyPair kpair = KeyPair.genKeyPair(jsch, KeyPair.RSA);
                kpair.writePrivateKey(tempInputDirPath + File.separator + userPrivateName);
                kpair.writePublicKey(tempInputDirPath + File.separator + userPublicKeyName, "auto generated user accees keys");
                kpair.dispose();
            }

            tam = new TopologyAnalysisMain(topTopologyLoadingPath);
            if (!tam.fullLoadWholeTopology()) {
                throw new Exception("Could not load topology");
            }

            userCredential = getUserCredential(parameters, tempInputDirPath);
            ArrayList<SSHKeyPair> sshKeyPairs = userCredential.
                    loadSSHKeyPairFromFile(tempInputDirPath);
            if (sshKeyPairs == null) {
                throw new NullPointerException("ssh key pairs are null");
            }
            if (sshKeyPairs.isEmpty()) {
                throw new IOException("No ssh key pair is loaded!");
            } else if (!userCredential.initial(sshKeyPairs, tam.wholeTopology)) {
                throw new IOException("ssh key pair initilaziation error");
            }
            Logger.getLogger(Consumer.class.getName()).log(Level.INFO, "Generated ssh keys");
            userDatabase = getUserDB();

            tEngine.provisionAll(tam.wholeTopology, userCredential, userDatabase);
            Logger.getLogger(Consumer.class.getName()).log(Level.INFO, "Provisioned resources");
            return buildTopologuResponse(tam, tempInputDirPath, userPublicKeyName, userPrivateName);

        } catch (Throwable ex) {
            if (tam != null) {
                tEngine.deleteAll(tam.wholeTopology, userCredential, userDatabase);
            }
            throw ex;
        } finally {
//            if (tam != null) {
//                tEngine.deleteAll(tam.wholeTopology, userCredential, userDatabase);
//            }
        }
    }

    private String generateExeptionResponse(Throwable ex) throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("creationDate", (System.currentTimeMillis()));
        List parameters = new ArrayList();
        String charset = "UTF-8";

        Map<String, String> errorArgument = new HashMap<>();
        errorArgument.put("encoding", charset);
        errorArgument.put("name", ex.getClass().getName());
        errorArgument.put("value", ex.getMessage());
        parameters.add(errorArgument);

        jo.put("parameters", parameters);
        return jo.toString();
    }

    private Message killTopology(JSONArray parameters, String tempInputDirPath) throws Exception {
        TEngine tEngine = new TEngine();
        TopologyAnalysisMain tam;

        File topologyFile = MessageParsing.getTopologies(parameters, tempInputDirPath, 0).get(0);
        File mainTopologyFile = new File(tempInputDirPath + "topology_main.yml");
        String topTopologyLoadingPath = mainTopologyFile.getAbsolutePath();

        List<File> topologyFiles = MessageParsing.getTopologies(parameters, tempInputDirPath, 1);
//        for (File lowLevelTopologyFile : topologyFiles) {
//            File secondaryTopologyFile = new File(tempInputDirPath + File.separator + lowLevelTopologyFile.getName() + ".yml");
//            FileUtils.moveFile(lowLevelTopologyFile, secondaryTopologyFile);
//        }

        File clusterDir = new File(tempInputDirPath + File.separator + "clusterKeyPair");
        clusterDir.mkdir();
        List<File> public_deployer_key = MessageParsing.getSSHKeys(parameters, clusterDir.getAbsolutePath(), "id_rsa.pub", "public_deployer_key");
        List<File> private_deployer_key = MessageParsing.getSSHKeys(parameters, clusterDir.getAbsolutePath(), "id_rsa", "private_deployer_key");

        Map<String, Object> map = MessageParsing.ymlStream2Map(new FileInputStream(topTopologyLoadingPath));
        String userPublicKeyName = ((String) map.get("publicKeyPath")).split("@")[1].replaceAll("\"", "");
        String userPrivateName = FilenameUtils.removeExtension(userPublicKeyName);

        List<File> public_user_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, userPublicKeyName, "public_user_key");
        List<File> private_user_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, "id_rsa", "private_user_key");
        FileUtils.moveFile(private_user_key.get(0), new File(private_user_key.get(0).getParent() + File.separator + userPrivateName));

        List<File> public_cloud_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, "name.pub", "public_cloud_key");
        List<File> private_cloud_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, "id_rsa", "private_cloud_key");

        UserCredential userCredential = getUserCredential(parameters, tempInputDirPath);
        UserDatabase userDatabase = getUserDB();

        tam = new TopologyAnalysisMain(topTopologyLoadingPath);
        if (!tam.fullLoadWholeTopology()) {
            throw new Exception("sth wrong!");
        }

        ArrayList<SSHKeyPair> sshKeyPairs = userCredential.
                loadSSHKeyPairFromFile(tempInputDirPath);
        if (sshKeyPairs == null) {
            throw new NullPointerException("ssh key pairs are null");
        }
        if (sshKeyPairs.isEmpty()) {
            throw new IOException("No ssh key pair is loaded!");
        } else if (!userCredential.initial(sshKeyPairs, tam.wholeTopology)) {
            throw new IOException("ssh key pair initilaziation error");
        }
        Message response = new Message();
        try {
            tEngine.deleteAll(tam.wholeTopology, userCredential, userDatabase);
        } catch (Throwable ex) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

            return mapper.readValue(generateExeptionResponse(ex), Message.class
            );
        }
        MessageParameter param = new MessageParameter();
        param.setName("topology_killed");
        param.setValue("true");
        List<MessageParameter> messageParameters = new ArrayList<>();
        messageParameters.add(param);
        response.setParameters(messageParameters);
        response.setCreationDate(System.currentTimeMillis());
        return response;
    }

    private Message scaleTopologyUp(JSONArray parameters, String tempInputDirPath) throws IOException, JSONException, Exception {
        TEngine tEngine = new TEngine();
        TopologyAnalysisMain tam;

        File topologyFile = MessageParsing.getTopologies(parameters, tempInputDirPath, 0).get(0);
        File mainTopologyFile = new File(tempInputDirPath + "topology_main.yml");
        String topTopologyLoadingPath = mainTopologyFile.getAbsolutePath();

        List<File> topologyFiles = MessageParsing.getTopologies(parameters, tempInputDirPath, 1);

        File clusterDir = new File(tempInputDirPath + File.separator + "clusterKeyPair");
        clusterDir.mkdir();
        List<File> public_deployer_key = MessageParsing.getSSHKeys(parameters, clusterDir.getAbsolutePath(), "id_rsa.pub", "public_deployer_key");
        List<File> private_deployer_key = MessageParsing.getSSHKeys(parameters, clusterDir.getAbsolutePath(), "id_rsa", "private_deployer_key");

        Map<String, Object> map = MessageParsing.ymlStream2Map(new FileInputStream(topTopologyLoadingPath));
        String userPublicKeyName = ((String) map.get("publicKeyPath")).split("@")[1].replaceAll("\"", "");
        String userPrivateName = FilenameUtils.removeExtension(userPublicKeyName);

        List<File> public_user_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, userPublicKeyName, "public_user_key");
        List<File> private_user_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, "id_rsa", "private_user_key");
        FileUtils.moveFile(private_user_key.get(0), new File(private_user_key.get(0).getParent() + File.separator + userPrivateName));

        List<File> public_cloud_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, "name.pub", "public_cloud_key");
        List<File> private_cloud_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, "id_rsa", "private_cloud_key");

        UserCredential userCredential = getUserCredential(parameters, tempInputDirPath);
        UserDatabase userDatabase = getUserDB();

        tam = new TopologyAnalysisMain(topTopologyLoadingPath);
        if (!tam.fullLoadWholeTopology()) {
            throw new Exception("sth wrong!");
        }

        ArrayList<SSHKeyPair> sshKeyPairs = userCredential.
                loadSSHKeyPairFromFile(tempInputDirPath);
        if (sshKeyPairs == null) {
            throw new NullPointerException("ssh key pairs are null");
        }
        if (sshKeyPairs.isEmpty()) {
            throw new IOException("No ssh key pair is loaded!");
        } else if (!userCredential.initial(sshKeyPairs, tam.wholeTopology)) {
            throw new IOException("ssh key pair initilaziation error");
        }
        Message response = new Message();
        MessageParameter scaleInfo = MessageParsing.getScaleInfo(parameters);
        String nameOfScalingTopology = scaleInfo.getValue();
        String cloudProvider = scaleInfo.getAttributes().get("cloud_provider");
        String cloudDomain = scaleInfo.getAttributes().get("domain");
        Integer numOfInst = Integer.valueOf(scaleInfo.getAttributes().get("number_of_instances"));
        try {
            ArrayList<ScalingRequest> scalDCs = new ArrayList<>();
            for (int i = 0; i < numOfInst; i++) {
                ScalingRequest scalReq = new ScalingRequest();
                scalReq.cloudProvider = cloudProvider;
                scalReq.domain = cloudDomain;
                scalReq.satisfied = false;
                scalDCs.add(scalReq);
            }

            tEngine.autoScal(tam.wholeTopology, userCredential, userDatabase, nameOfScalingTopology, true, scalDCs);
            return buildTopologuResponse(tam, tempInputDirPath, null, null);
        } catch (Throwable ex) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

            return mapper.readValue(generateExeptionResponse(ex), Message.class
            );
        }

    }

    private Message scaleTopologyDown(JSONArray parameters, String tempInputDirPath) throws IOException, JSONException, Exception {
        TEngine tEngine = new TEngine();
        TopologyAnalysisMain tam;

        File topologyFile = MessageParsing.getTopologies(parameters, tempInputDirPath, 0).get(0);
        File mainTopologyFile = new File(tempInputDirPath + "topology_main.yml");
        String topTopologyLoadingPath = mainTopologyFile.getAbsolutePath();

        List<File> topologyFiles = MessageParsing.getTopologies(parameters, tempInputDirPath, 1);

        File clusterDir = new File(tempInputDirPath + File.separator + "clusterKeyPair");
        clusterDir.mkdir();
        List<File> public_deployer_key = MessageParsing.getSSHKeys(parameters, clusterDir.getAbsolutePath(), "id_rsa.pub", "public_deployer_key");
        List<File> private_deployer_key = MessageParsing.getSSHKeys(parameters, clusterDir.getAbsolutePath(), "id_rsa", "private_deployer_key");

        Map<String, Object> map = MessageParsing.ymlStream2Map(new FileInputStream(topTopologyLoadingPath));
        String userPublicKeyName = ((String) map.get("publicKeyPath")).split("@")[1].replaceAll("\"", "");
        String userPrivateName = FilenameUtils.removeExtension(userPublicKeyName);

        List<File> public_user_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, userPublicKeyName, "public_user_key");
        List<File> private_user_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, "id_rsa", "private_user_key");
        FileUtils.moveFile(private_user_key.get(0), new File(private_user_key.get(0).getParent() + File.separator + userPrivateName));

        List<File> public_cloud_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, "name.pub", "public_cloud_key");
        List<File> private_cloud_key = MessageParsing.getSSHKeys(parameters, tempInputDirPath + File.separator, "id_rsa", "private_cloud_key");

        UserCredential userCredential = getUserCredential(parameters, tempInputDirPath);
        UserDatabase userDatabase = getUserDB();

        tam = new TopologyAnalysisMain(topTopologyLoadingPath);
        if (!tam.fullLoadWholeTopology()) {
            throw new Exception("sth wrong!");
        }

        ArrayList<SSHKeyPair> sshKeyPairs = userCredential.
                loadSSHKeyPairFromFile(tempInputDirPath);
        if (sshKeyPairs == null) {
            throw new NullPointerException("ssh key pairs are null");
        }
        if (sshKeyPairs.isEmpty()) {
            throw new IOException("No ssh key pair is loaded!");
        } else if (!userCredential.initial(sshKeyPairs, tam.wholeTopology)) {
            throw new IOException("ssh key pair initilaziation error");
        }
        Message response = new Message();
        MessageParameter scaleInfo = MessageParsing.getScaleInfo(parameters);
        String nameOfScalingTopology = scaleInfo.getValue();
        String cloudProvider = scaleInfo.getAttributes().get("cloud_provider");
        String cloudDomain = scaleInfo.getAttributes().get("domain");
        Integer numOfInst = Integer.valueOf(scaleInfo.getAttributes().get("number_of_instances"));
        try {
            ArrayList<ScalingRequest> scalDCs = new ArrayList<>();
            for (int i = 0; i < numOfInst; i++) {
                ScalingRequest scalReq = new ScalingRequest();
                scalReq.cloudProvider = cloudProvider;
                scalReq.domain = cloudDomain;
                scalReq.satisfied = false;
                scalDCs.add(scalReq);
            }

            tEngine.autoScal(tam.wholeTopology, userCredential, userDatabase, nameOfScalingTopology, false, scalDCs);

            return buildTopologuResponse(tam, tempInputDirPath, null, null);
        } catch (Throwable ex) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

            return mapper.readValue(generateExeptionResponse(ex), Message.class
            );
        }

    }

    private UserDatabase getUserDB() {
        UserDatabase userDatabase = new UserDatabase();
        EGIDatabase egiDatabase = new EGIDatabase();
        egiDatabase.loadDomainInfoFromFile(PropertyValues.DOMAIN_INFO_PATH + File.separator + "EGI_Domain_Info");
        EC2Database ec2Database = new EC2Database();
        ec2Database.loadDomainInfoFromFile(PropertyValues.DOMAIN_INFO_PATH + File.separator + "domains");
        ec2Database.loadAmiFromFile(PropertyValues.DOMAIN_INFO_PATH + File.separator + "OS_Domain_AMI");
        if (userDatabase.databases == null) {
            userDatabase.databases = new HashMap<>();
        }
        userDatabase.databases.put("ec2", ec2Database);
        userDatabase.databases.put("egi", egiDatabase);
        return userDatabase;
    }

    private UserCredential getUserCredential(JSONArray parameters, String tempInputDirPath) throws JSONException, IOException, FileNotFoundException, CertificateEncodingException, GSSException {
        UserCredential userCredential = new UserCredential();
        List<Credential> credentials = MessageParsing.getCloudCredentials(parameters, tempInputDirPath);
        for (Credential cred : credentials) {
            ////Initial credentials and ssh key pairs
            if (userCredential.cloudAccess == null) {
                userCredential.cloudAccess = new HashMap<>();
            }
            if (cred instanceof EC2Credential) {
                userCredential.cloudAccess.put("ec2", cred);
            }
            if (cred instanceof EGICredential) {
                userCredential.cloudAccess.put("egi", cred);
            }
        }
        return userCredential;
    }

    private Message buildTopologuResponse(TopologyAnalysisMain tam,
            String tempInputDirPath, String userPublicKeyName, String userPrivateName) throws IOException, Exception {
        String topologyUserName = tam.wholeTopology.userName;

        String charset = "UTF-8";
        List<MessageParameter> responseParameters = new ArrayList<>();
        MessageParameter param = new MessageParameter();
        param.setEncoding(charset);
        String fileName = tam.wholeTopology.loadingPath;
        if (!fileName.endsWith(".yml")) {
            fileName += ".yml";
        }
        File f = new File(fileName);
        if (f.exists()) {
            param.setName(FilenameUtils.removeExtension(FilenameUtils.getBaseName(fileName)));
            byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
            param.setValue(new String(bytes, charset));
            responseParameters.add(param);
        } else {
            param.setName(FilenameUtils.removeExtension(FilenameUtils.getBaseName(fileName)));
            param.setValue("ERROR::There is no output for topology topology_main");
            responseParameters.add(param);
        }

        for (SubTopologyInfo sub : tam.wholeTopology.topologies) {
            param = new MessageParameter();
            param.setEncoding(charset);
            fileName = tempInputDirPath + File.separator + sub.topology;
            if (!fileName.endsWith(".yml")) {
                fileName += ".yml";
            }
            f = new File(fileName);
            if (f.exists()) {
                param.setName(sub.topology);
                byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
                param.setValue(new String(bytes, charset));
                responseParameters.add(param);
            } else {
                param.setName(sub.topology);
                param.setValue("ERROR::There is no output for topology " + sub.topology);
                responseParameters.add(param);
            }
        }

        if (userPublicKeyName != null) {
            param = new MessageParameter();
            param.setEncoding(charset);
            param.setName("public_user_key");
            byte[] bytes = Files.readAllBytes(Paths.get(tempInputDirPath + File.separator + userPublicKeyName));
            param.setValue(new String(bytes, charset));
            Map<String, String> attributes = new HashMap<>();
            attributes.put("name", userPublicKeyName);
            param.setAttributes(attributes);
            responseParameters.add(param);
        }

        if (userPrivateName != null) {
            param = new MessageParameter();
            param.setEncoding(charset);
            param.setName("private_user_key");
            byte[] bytes = Files.readAllBytes(Paths.get(tempInputDirPath + File.separator + userPrivateName));
            param.setValue(new String(bytes, charset));
            Map<String, String> attributes = new HashMap<>();
            attributes.put("name", userPrivateName);
//            attributes.put("username",  sub.userName);
            param.setAttributes(attributes);
            responseParameters.add(param);

            param = new MessageParameter();
            param.setEncoding(charset);
            param.setName("private_deployer_key");
            bytes = Files.readAllBytes(Paths.get(tempInputDirPath + File.separator + "clusterKeyPair" + File.separator + "id_rsa"));
            param.setValue(new String(bytes, charset));
            attributes = new HashMap<>();
            attributes.put("name", "id_rsa");
            param.setAttributes(attributes);
            responseParameters.add(param);

            param = new MessageParameter();
            param.setEncoding(charset);
            param.setName("public_deployer_key");
            bytes = Files.readAllBytes(Paths.get(tempInputDirPath + File.separator + "clusterKeyPair" + File.separator + "id_rsa.pub"));
            param.setValue(new String(bytes, charset));
            attributes = new HashMap<>();
            attributes.put("name", "id_rsa.pub");
            param.setAttributes(attributes);
            responseParameters.add(param);

        }

        File dir = new File(tempInputDirPath);
        for (File d : dir.listFiles()) {
            if (d.isDirectory() && !d.getName().equals("clusterKeyPair")) {
                param = new MessageParameter();
                param.setEncoding(charset);
                param.setName("public_cloud_key");
                File publicKey = new File(d.getAbsolutePath() + File.separator + "name.pub");
                if (!publicKey.exists()) {
                    publicKey = new File(d.getAbsolutePath() + File.separator + "id_rsa.pub");
                }
                byte[] bytes = Files.readAllBytes(Paths.get(publicKey.getAbsolutePath()));
                param.setValue(new String(bytes, charset));
                Map<String, String> attributes = new HashMap<>();
                attributes.put("name", publicKey.getName());
                attributes.put("key_pair_id", d.getName());
                param.setAttributes(attributes);
                responseParameters.add(param);

                param = new MessageParameter();
                param.setEncoding(charset);
                param.setName("private_cloud_key");
                bytes = Files.readAllBytes(Paths.get(d.getAbsolutePath() + File.separator + "id_rsa"));
                param.setValue(new String(bytes, charset));
                attributes = new HashMap<>();
                attributes.put("name", "id_rsa");
                attributes.put("key_pair_id", d.getName());
                param.setAttributes(attributes);
                responseParameters.add(param);
            }

        }

        param = new MessageParameter();
        param.setEncoding(charset);
        param.setName("deploy_parameters");
        String paramValue = "";
        for (SubTopologyInfo sub : tam.wholeTopology.topologies) {
            ArrayList<VM> vms = sub.subTopology.getVMsinSubClass();
            for (VM vm : vms) {
                if (vm != null && sub.status.equals("running")) {
                    paramValue += vm.publicAddress + " ";
                    paramValue += sub.userName + " ";
//                        paramValue += tempInputDirPath + File.separator + sub.subTopology.accessKeyPair.SSHKeyPairId + File.separator + "id_rsa";
                    paramValue += vm.role + "\n";
                } else if (vm == null || !sub.status.equals("running")) {
                    throw new Exception("A VM failed to start. Deleteing all topology");
                }
            }
//            String accessKeyPath = tempInputDirPath + File.separator + sub.subTopology.accessKeyPair.SSHKeyPairId + File.separator + "id_rsa";
//            System.err.println("accessKeyPath: " + accessKeyPath);
        }
        param.setValue(paramValue);
        responseParameters.add(param);

        Message response = new Message();
        response.setCreationDate(System.currentTimeMillis());
        response.setParameters(responseParameters);

        return response;

    }
}
