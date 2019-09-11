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
package nl.uva.sne.drip.drip.provisioner.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.uva.sne.drip.commons.utils.AAUtils;
import nl.uva.sne.drip.commons.utils.AAUtils.SOURCE;
import static nl.uva.sne.drip.commons.utils.AAUtils.downloadCACertificates;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.drip.commons.data.internal.MessageParameter;
import nl.uva.sne.drip.drip.commons.data.v1.external.CloudCredentials;
import nl.uva.sne.drip.drip.converter.P2PConverter;
import nl.uva.sne.drip.drip.converter.SimplePlanContainer;
import org.apache.commons.io.FileUtils;
//import org.globus.myproxy.MyProxyException;
import org.ietf.jgss.GSSException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;
import provisioning.credential.Credential;
import provisioning.credential.EC2Credential;
import provisioning.credential.EGICredential;
import provisioning.credential.ExoGENICredential;

/**
 *
 * @author S. Koulouzis
 */
public class MessageParsing {
    private static SimplePlanContainer simplePlan;

    public static List<File> getTopologies(JSONArray parameters, String tempInputDirPath, int level) throws JSONException, IOException {
        List<File> topologyFiles = new ArrayList<>();
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            String name = (String) param.get("name");
            if (name.equals("topology")) {
                String toscaPlan = new String(Base64.getDecoder().decode((String) param.get("value")));
                Map<String, Object> toscaPlanMap = Converter.ymlString2Map(toscaPlan);
                
                
                if (level == 0) {
                    simplePlan = P2PConverter.transfer(toscaPlanMap, null, null, null);
                    String fileName = "planner_output_all.yml";
                    File topologyFile = new File(tempInputDirPath + File.separator + fileName);
                    topologyFile.createNewFile();
                    writeValueToFile(simplePlan.topLevelContents, topologyFile);
                    topologyFiles.add(topologyFile);
                    //We should have only one top level topoloy
//                    if (level == 0) {
                    return topologyFiles;
//                    }
                } else {
                    Set<String> keys = simplePlan.lowerLevelContents.keySet();
                    for (String key : keys) {
                        String fileName = key;
                        if (!fileName.endsWith(".yml")) {
                            fileName += ".yml";
                        }
                        File topologyFile = new File(tempInputDirPath + File.separator + fileName);
                        topologyFile.createNewFile();
                        String topologyContents = (String) simplePlan.lowerLevelContents.get(key);
                        writeValueToFile(topologyContents, topologyFile);
                        topologyFiles.add(topologyFile);
                    }

                }
            }
        }
        return topologyFiles;
    }

    public static void writeValueToFile(String value, File file) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(value);
        }
        if (!file.exists() || file.length() < value.getBytes().length) {
            throw new FileNotFoundException("File " + file.getAbsolutePath() + " doesn't exist or contents are missing ");
        }
    }

    public static List<File> getSSHKeys(JSONArray parameters, String tempInputDirPath, String filename, String varName) throws JSONException, IOException {
        List<File> sshKeys = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);

            MessageParameter messageParam = mapper.readValue(param.toString(), MessageParameter.class);
            String name = messageParam.getName();
            String value = messageParam.getValue();

            if (name.equals(varName)) {
                File sshKeyFile = null;
                if (messageParam.getAttributes() != null) {
                    if (messageParam.getAttributes().get("name") != null) {
                        filename = messageParam.getAttributes().get("name");
                    }
                    if (messageParam.getAttributes().get("key_pair_id") != null) {
                        File tempInputDir = new File(tempInputDirPath + File.separator + messageParam.getAttributes().get("key_pair_id"));
                        tempInputDir.mkdir();
                        sshKeyFile = new File(tempInputDir.getAbsolutePath() + File.separator + filename);
                    }
                }
                if (sshKeyFile == null) {
                    sshKeyFile = new File(tempInputDirPath + File.separator + filename);
                }
                if (sshKeyFile.exists()) {
                    sshKeyFile = new File(tempInputDirPath + File.separator + i + "_" + filename);
                }
                if (sshKeyFile.createNewFile()) {
                    MessageParsing.writeValueToFile(value, sshKeyFile);
                    sshKeys.add(sshKeyFile);
                }
            }
        }
        return sshKeys;
    }

    public static MessageParameter getScaleInfo(JSONArray parameters) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            MessageParameter messageParam = mapper.readValue(param.toString(), MessageParameter.class);
            String name = messageParam.getName();
            String value = messageParam.getValue();
            if (name.equals("scale_topology_name")) {
                return messageParam;
            }
        }

        return null;
    }

    public static Map<String, Object> ymlStream2Map(InputStream in) {
        Yaml yaml = new Yaml();
        Map<String, Object> map = (Map<String, Object>) yaml.load(in);
        return map;
    }

    public static List<Credential> getCloudCredentials(JSONArray parameters, String tempInputDirPath) throws JSONException, FileNotFoundException, IOException, CertificateEncodingException, GSSException {
        List<Credential> credentials = new ArrayList<>();
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            MessageParameter messageParam = mapper.readValue(param.toString(), MessageParameter.class);
            String name = messageParam.getName();
            String value = messageParam.getValue();

            if (name.equals("cloud_credential")) {
                Credential credential = null;

                value = value.substring(1, value.length() - 1);
                CloudCredentials cred = mapper.readValue(value, CloudCredentials.class);
                if (cred.getCloudProviderName().toLowerCase().equals("ec2")) {
                    EC2Credential ec2 = new EC2Credential();
                    ec2.accessKey = cred.getAccessKeyId();
                    ec2.secretKey = cred.getSecretKey();
                    credential = ec2;
                }
                if (cred.getCloudProviderName().toLowerCase().equals("egi")) {
                    EGICredential egi = new EGICredential();
                    Map<String, Object> att = cred.getAttributes();
                    String trustedCertificatesURL = null;
                    if (att != null && att.containsKey("trustedCertificatesURL")) {
                        trustedCertificatesURL = (String) att.get("trustedCertificatesURL");
                    }
                    if (trustedCertificatesURL != null) {
                        downloadCACertificates(new URL(trustedCertificatesURL), PropertyValues.TRUSTED_CERTIFICATE_FOLDER);
                    } else {
                        downloadCACertificates(PropertyValues.CA_BUNDLE_URL, PropertyValues.TRUSTED_CERTIFICATE_FOLDER);
                    }
                    String myProxyEndpoint = null;
                    if (att != null && att.containsKey("myProxyEndpoint")) {
                        myProxyEndpoint = (String) att.get("myProxyEndpoint");
                    }
                    if (myProxyEndpoint != null) {
                        String[] myVOs = null;
                        List voNames = null;
                        if (att != null && att.containsKey("voms")) {
                            myVOs = ((String) att.get("voms")).split(",");
                            voNames = (List) Arrays.asList(myVOs);
                        }
                        egi.proxyFilePath = AAUtils.generateProxy(cred.getAccessKeyId(), cred.getSecretKey(), SOURCE.MY_PROXY, myProxyEndpoint, voNames);
                    } else {
                        egi.proxyFilePath = AAUtils.generateProxy(cred.getAccessKeyId(), cred.getSecretKey(), SOURCE.PROXY_FILE, myProxyEndpoint, null);
                    }
                    egi.trustedCertPath = PropertyValues.TRUSTED_CERTIFICATE_FOLDER;
                    credential = egi;
                }
                if (cred.getCloudProviderName().toLowerCase().equals("exogeni")) {
                    ExoGENICredential exoGeniCredential = new ExoGENICredential();
                    exoGeniCredential.keyAlias = cred.getAccessKeyId();
                    exoGeniCredential.keyPassword = cred.getSecretKey();
                    Map<String, Object> att = cred.getAttributes();
                    if (att != null && att.containsKey("keystore")) {
                        String javaKeyStoreEncoded = (String) att.get("keystore");
                        byte[] decoded = Base64.getDecoder().decode(javaKeyStoreEncoded);
                        File keyStoreFile = new File(tempInputDirPath + File.separator + "user.jks");
                        FileUtils.writeByteArrayToFile(keyStoreFile, decoded);
                        exoGeniCredential.userKeyPath = keyStoreFile.getAbsolutePath();
                    }

                    credential = exoGeniCredential;
                }

//                for (KeyPair pair : cred.getKeyPairs()) {
//                    if (pair != null) {
//                        File dir = new File(tempInputDirPath + File.separator + pair.getId());
//                        dir.mkdir();
//                        Key privateKey = pair.getPrivateKey();
//                        if (privateKey != null) {
//                            writeValueToFile(privateKey.getKey(), new File(dir.getAbsolutePath() + File.separator + privateKey.getName()));
//                        }
//                        Key publicKey = pair.getPublicKey();
//                        if (publicKey != null) {
//                            writeValueToFile(publicKey.getKey(), new File(dir.getAbsolutePath() + File.separator + publicKey.getName()));
//                        }
//                    }
//                }
                credentials.add(credential);
            }
        }

        return credentials;
    }

}
