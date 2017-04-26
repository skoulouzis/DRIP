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
import cz.cesnet.cloud.occi.api.http.auth.X509Authentication;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.uva.sne.drip.drip.commons.data.internal.Message;
import nl.uva.sne.drip.drip.commons.data.v1.external.CloudCredentials;
import nl.uva.sne.drip.drip.commons.data.v1.external.Key;
import nl.uva.sne.drip.drip.commons.data.v1.external.KeyPair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;
import provisioning.credential.Credential;
import provisioning.credential.EC2Credential;
import provisioning.credential.EGICredential;

/**
 *
 * @author S. Koulouzis
 */
public class MessageParsing {

    private static ObjectMapper mapper;

    public static List<File> getTopologies(JSONArray parameters, String tempInputDirPath, int level) throws JSONException, IOException {
        List<File> topologyFiles = new ArrayList<>();
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            String name = (String) param.get("name");
            if (name.equals("topology")) {
                JSONObject attributes = param.getJSONObject("attributes");
                int fileLevel = Integer.valueOf((String) attributes.get("level"));
                if (fileLevel == level) {
                    String originalFilename = (String) attributes.get("filename");
                    String fileName = originalFilename;

                    File topologyFile = new File(tempInputDirPath + File.separator + fileName);
                    topologyFile.createNewFile();
                    String val = (String) param.get("value");
                    writeValueToFile(val, topologyFile);
                    topologyFiles.add(topologyFile);
                    //We should have only one top level topoloy
                    if (level == 0) {
                        return topologyFiles;
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

    public static List<File> getSSHKeys(JSONArray parameters, String tempInputDirPath, String filename) throws JSONException, IOException {
        List<File> sshKeys = new ArrayList<>();
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            String name = (String) param.get("name");
            if (name.equals("sshkey")) {
                String sshKeyContent = (String) param.get("value");
                File sshKeyFile = new File(tempInputDirPath + File.separator + filename);
                if (sshKeyFile.exists()) {
                    sshKeyFile = new File(tempInputDirPath + File.separator + i + "_" + filename);
                }
                if (sshKeyFile.createNewFile()) {
                    MessageParsing.writeValueToFile(sshKeyContent, sshKeyFile);
                    sshKeys.add(sshKeyFile);
                }
            }
        }
        return sshKeys;
    }

    public static Map<String, Object> ymlStream2Map(InputStream in) {
        Yaml yaml = new Yaml();
        Map<String, Object> map = (Map<String, Object>) yaml.load(in);
        return map;
    }

    public static List<Credential> getCloudCredentials(JSONArray parameters, String tempInputDirPath) throws JSONException, FileNotFoundException, IOException {
        List<Credential> credentials = new ArrayList<>();
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            String name = (String) param.get("name");
            if (name.equals("cloud_credential")) {
                Credential credential = null;
                mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                String credentialString = (String) param.get("value");
                credentialString = credentialString.substring(1, credentialString.length() - 2);
                CloudCredentials cred = mapper.readValue(credentialString, CloudCredentials.class);
                if (cred.getCloudProviderName().toLowerCase().equals("ec2")) {
                    EC2Credential ec2 = new EC2Credential();
                    ec2.accessKey = cred.getAccessKeyId();
                    ec2.secretKey = cred.getSecretKey();
                    credential = ec2;
                }
                if (cred.getCloudProviderName().toLowerCase().equals("egi")) {
                    EGICredential egi = new EGICredential();
                    egi.proxyFilePath = generateProxy(cred.getAccessKeyId(), cred.getSecretKey());
                    egi.trustedCertPath = "/etc/grid-security/certificates";
                    credential = egi;
                }

                for (KeyPair pair : cred.getKeyPairs()) {
                    File dir = new File(tempInputDirPath + File.separator + pair.getId());
                    dir.mkdir();
                    Key privateKey = pair.getPrivateKey();
                    writeValueToFile(privateKey.getKey(), new File(dir.getAbsolutePath() + File.separator + privateKey.getName()));
                    Key publicKey = pair.getPublicKey();
                    writeValueToFile(publicKey.getKey(), new File(dir.getAbsolutePath() + File.separator + publicKey.getName()));
                }
                credentials.add(credential);
            }
        }

        return credentials;
    }

    private static String generateProxy(String accessKeyId, String secretKey) {
        return "/tmp/x509up_u0";
    }
}
