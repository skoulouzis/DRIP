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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.uva.sne.drip.drip.commons.data.internal.MessageParameter;
import nl.uva.sne.drip.drip.commons.data.v1.external.CloudCredentials;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.globus.gsi.X509Credential;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.myproxy.MyProxy;
import org.globus.myproxy.MyProxyException;
import org.ietf.jgss.GSSCredential;
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

    public static File getClusterKeysPair(JSONArray parameters, String tempInputDirPath) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            MessageParameter messageParam = mapper.readValue(param.toString(), MessageParameter.class);
        }
        return null;
    }

    enum SOURCE {
        MY_PROXY,
        CERTIFICATE
    }

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

    public static List<File> getSSHKeys(JSONArray parameters, String tempInputDirPath, String filename, String varName) throws JSONException, IOException {
        List<File> sshKeys = new ArrayList<>();
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            String name = (String) param.get("name");
            if (name.equals(varName)) {
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

    public static List<Credential> getCloudCredentials(JSONArray parameters, String tempInputDirPath) throws JSONException, FileNotFoundException, IOException, MyProxyException, CertificateEncodingException {
        List<Credential> credentials = new ArrayList<>();
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            String name = (String) param.get("name");
            if (name.equals("cloud_credential")) {
                Credential credential = null;
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                String credentialString = (String) param.get("value");
                credentialString = credentialString.substring(1, credentialString.length() - 1);
                CloudCredentials cred = mapper.readValue(credentialString, CloudCredentials.class);
                if (cred.getCloudProviderName().toLowerCase().equals("ec2")) {
                    EC2Credential ec2 = new EC2Credential();
                    ec2.accessKey = cred.getAccessKeyId();
                    ec2.secretKey = cred.getSecretKey();
                    credential = ec2;
                }
                if (cred.getCloudProviderName().toLowerCase().equals("egi")) {
                    EGICredential egi = new EGICredential();
                    if (PropertyValues.MY_PROXY_ENDPOINT != null || PropertyValues.MY_PROXY_ENDPOINT.length() > 2) {
                        egi.proxyFilePath = generateProxy(cred.getAccessKeyId(), cred.getSecretKey(), SOURCE.MY_PROXY);
                    } else {
                        egi.proxyFilePath = generateProxy(cred.getAccessKeyId(), cred.getSecretKey(), SOURCE.CERTIFICATE);
                    }
//                    else if (){
//                        
//                    }
//                   
                    downloadCACertificates();
                    egi.trustedCertPath = PropertyValues.TRUSTED_CERTIFICATE_FOLDER;
                    credential = egi;
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

    private static String generateProxy(String accessKeyId, String secretKey, SOURCE source) throws MyProxyException, IOException, CertificateEncodingException {
        if (source.equals(SOURCE.MY_PROXY)) {
            MyProxy myProxy = new MyProxy(PropertyValues.MY_PROXY_ENDPOINT, 7512);
            GSSCredential cert = myProxy.get(accessKeyId, secretKey, 2 * 3600);
            X509Credential gCred = ((GlobusGSSCredentialImpl) cert).getX509Credential();
            gCred.save(new FileOutputStream("/tmp/x509up_u0"));
        }

        return "/tmp/x509up_u0";
    }

    private static void downloadCACertificates() throws MalformedURLException, IOException {
        File bundle = new File(PropertyValues.CA_BUNDLE_URL.getFile());
//        Path path = Paths.get(bundle.getAbsolutePath());
//        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        if (!bundle.exists()) {
            URL website = new URL(PropertyValues.CA_BUNDLE_URL.toString());
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(PropertyValues.CA_BUNDLE_URL.getFile());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            untar(new File(PropertyValues.TRUSTED_CERTIFICATE_FOLDER), bundle);
        }
    }

    private static void untar(File dest, File tarFile) throws IOException {

        dest.mkdir();
        TarArchiveInputStream tarIn;

        tarIn = new TarArchiveInputStream(
                new GzipCompressorInputStream(
                        new BufferedInputStream(
                                new FileInputStream(
                                        tarFile
                                )
                        )
                )
        );

        org.apache.commons.compress.archivers.tar.TarArchiveEntry tarEntry = tarIn.getNextTarEntry();

        while (tarEntry != null) {
            File destPath = new File(dest, tarEntry.getName());
            if (tarEntry.isDirectory()) {
                destPath.mkdirs();
            } else {
                destPath.createNewFile();
                byte[] btoRead = new byte[1024];
                try (BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(destPath))) {
                    int len;

                    while ((len = tarIn.read(btoRead)) != -1) {
                        bout.write(btoRead, 0, len);
                    }
                }
            }
            tarEntry = tarIn.getNextTarEntry();
        }
        tarIn.close();

    }

}
