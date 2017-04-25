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

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import commonTool.CommonTool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.drip.provisioner.utils.MessageParsing;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import provisioning.credential.EC2Credential;
import provisioning.credential.EGICredential;
import provisioning.credential.SSHKeyPair;
import provisioning.credential.UserCredential;
import provisioning.database.EGI.EGIDatabase;
import provisioning.database.UserDatabase;
import provisioning.engine.TEngine.TEngine;
import topologyAnalysis.TopologyAnalysisMain;

/**
 *
 * This is an example of a Message consumer
 *
 *
 * @author H. Zhou, S. Koulouzis
 */
public class Consumer extends DefaultConsumer {

    private final Channel channel;
    Map<String, String> em = new HashMap<>();

    public Consumer(Channel channel) throws IOException {
        super(channel);
        this.channel = channel;
        em.put("Virginia", "ec2.us-east-1.amazonaws.com");
        em.put("California", "ec2.us-west-1.amazonaws.com");
        em.put("Oregon", "ec2.us-west-2.amazonaws.com");
        em.put("Mumbai", "ec2.ap-south-1.amazonaws.com");
        em.put("Singapore", "ec2.ap-southeast-1.amazonaws.com");
        em.put("Seoul", "ec2.ap-northeast-2.amazonaws.com");
        em.put("Sydney", "ec2.ap-southeast-2.amazonaws.com");
        em.put("Tokyo", "ec2.ap-northeast-1.amazonaws.com");
        em.put("Frankfurt", "ec2.eu-central-1.amazonaws.com");
        em.put("Ireland", "ec2.eu-west-1.amazonaws.com");
        em.put("Paulo", "ec2.sa-east-1.amazonaws.com");
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

            String tempInputDirPath = System.getProperty("java.io.tmpdir") + File.separator + "Input-" + Long.toString(System.nanoTime()) + File.separator;
            File tempInputDir = new File(tempInputDirPath);
            if (!(tempInputDir.mkdirs())) {
                throw new FileNotFoundException("Could not create input directory: " + tempInputDir.getAbsolutePath());
            }

            invokeProvisioner(message, tempInputDirPath);
            TopologyAnalysisMain topLevelTopology = null;
            response = generateResponse(topLevelTopology);

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

    private void invokeProvisioner(String message, String tempInputDirPath) throws IOException, JSONException, Exception {
        //Use the Jackson API to convert json to Object 
        JSONObject jo = new JSONObject(message);
        JSONArray parameters = jo.getJSONArray("parameters");
        startTopology(parameters, tempInputDirPath);
    }

    private void startTopology(JSONArray parameters, String tempInputDirPath) throws Exception {

        String topTopologyLoadingPath = "ES/EGI/zh_all_test.yml";

        File topologyFile = MessageParsing.getTopology(parameters, tempInputDirPath, 0);
        File mainTopologyFile = new File(tempInputDirPath + "topology_main");
        FileUtils.moveFile(topologyFile, mainTopologyFile);
        topTopologyLoadingPath = mainTopologyFile.getAbsolutePath();
//load 2nd level in same folder 
        topologyFile = MessageParsing.getTopology(parameters, tempInputDirPath, 1);
        File secondaryTopologyFile = new File(tempInputDirPath + File.separator + topologyFile.getName() + ".yml");
        String outputFilePath = tempInputDirPath + File.separator + topologyFile.getName() + "_provisioned.yml";
        FileUtils.moveFile(topologyFile, secondaryTopologyFile);

        System.exit(-1);

        String currentDir = CommonTool.getPathDir(topTopologyLoadingPath);

        TopologyAnalysisMain tam = new TopologyAnalysisMain(topTopologyLoadingPath);
        if (!tam.fullLoadWholeTopology()) {
            throw new Exception("sth wrong!");
        }

        ////Initial credentials and ssh key pairs
        UserCredential userCredential = new UserCredential();

        EC2Credential ec2Credential = new EC2Credential();
        ec2Credential.accessKey = "23332323wer";
        ec2Credential.secretKey = "fdfsdffdfsdf";

        EGICredential egiCredential = new EGICredential();
        egiCredential.proxyFilePath = "/tmp/x509up_u0";
        egiCredential.trustedCertPath = "/etc/grid-security/certificates";
        if (userCredential.cloudAccess == null) {
            userCredential.cloudAccess = new HashMap<>();
        }
        userCredential.cloudAccess.put("ec2", ec2Credential);
        userCredential.cloudAccess.put("egi", egiCredential);
        ArrayList<SSHKeyPair> sshKeyPairs = userCredential.loadSSHKeyPairFromFile(currentDir);
        if (sshKeyPairs == null) {
            throw new NullPointerException("ssh key pairs are null");
        }
        if (sshKeyPairs.isEmpty()) {
            throw new IOException("No ssh key pair is loaded!");
        } else if (!userCredential.initial(sshKeyPairs, tam.wholeTopology)) {
            throw new IOException("ssh key pair initilaziation error");
        }

        ///Initial Database
        UserDatabase userDatabase = new UserDatabase();
        EGIDatabase egiDatabase = new EGIDatabase();
        egiDatabase.loadDomainInfoFromFile(currentDir + "EGI_Domain_Info");
        /*EC2Database ec2Database = new EC2Database();
		ec2Database.loadDomainFromFile(currentDir+"domains");
		ec2Database.loadAmiFromFile(currentDir+"OS_Domain_AMI");*/
        if (userDatabase.databases == null) {
            userDatabase.databases = new HashMap<>();
        }
        //userDatabase.databases.put("ec2", ec2Database);
        userDatabase.databases.put("egi", egiDatabase);

        /*ProvisionRequest pq = new ProvisionRequest();
		pq.topologyName = "ec2_zh_b";
		ArrayList<ProvisionRequest> provisionReqs = new ArrayList<ProvisionRequest>();
		provisionReqs.add(pq);*/
        TEngine tEngine = new TEngine();
        //tEngine.provision(tam.wholeTopology, userCredential, userDatabase, provisionReqs);
        tEngine.provisionAll(tam.wholeTopology, userCredential, userDatabase);

        //For deployment parameters
        String userName = tam.wholeTopology.topologies.get(0).subTopology.getVMsinSubClass().get(0).defaultSSHAccount;
        String ip = tam.wholeTopology.topologies.get(0).subTopology.getVMsinSubClass().get(0).publicAddress;
        String vmRole = tam.wholeTopology.topologies.get(0).subTopology.getVMsinSubClass().get(0).role;
        String curDir = CommonTool.getPathDir(tam.wholeTopology.loadingPath);
        String accessKeyPath = curDir + File.separator + tam.wholeTopology.topologies.get(0).subTopology.accessKeyPair.SSHKeyPairId + File.separator + "id_rsa";

    }

    private String generateResponse(TopologyAnalysisMain topMain) throws JSONException, IOException {
        //Use the JSONObject API to convert Object (Message) to json
        JSONObject jo = new JSONObject();
        jo.put("creationDate", (System.currentTimeMillis()));
        List parameters = new ArrayList();
        String charset = "UTF-8";
        if (topMain == null) {
            Map<String, String> fileArguments = new HashMap<>();
            fileArguments.put("encoding", charset);
            fileArguments.put("name", "ERROR");
            fileArguments.put("value", "Some error with input messages!");
            parameters.add(fileArguments);
        } else {

            Map<String, String> fileArguments = new HashMap<>();
            fileArguments.put("encoding", charset);
            Map<String, String> topMap = topMain.generateUserOutputs();
            fileArguments.put("name", "topLevel");
            fileArguments.put("value", new String(topMap.get("topLevel").getBytes(), charset));

            Set<String> set = topMap.keySet();
            for (String totplogyName : set) {

                if (!totplogyName.equals("topLevel")) {
                    topMap.get(totplogyName);
                }

            }
        }
        jo.put("parameters", parameters);
        return jo.toString();
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

    private void writeValueToFile(String value, File file) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(value);
        }
        if (!file.exists() || file.length() < value.getBytes().length) {
            throw new FileNotFoundException("File " + file.getAbsolutePath() + " doesn't exist or contents are missing ");
        }
    }

}
