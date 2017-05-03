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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import org.globus.myproxy.MyProxyException;
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
                    if (!fileName.endsWith(".yml")) {
                        fileName += ".yml";
                    }
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

    public static Map<String, Object> ymlStream2Map(InputStream in) {
        Yaml yaml = new Yaml();
        Map<String, Object> map = (Map<String, Object>) yaml.load(in);
        return map;
    }

    public static List<Credential> getCloudCredentials(JSONArray parameters, String tempInputDirPath) throws JSONException, FileNotFoundException, IOException, MyProxyException, CertificateEncodingException {
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
                        downloadCACertificates(new URL(trustedCertificatesURL));
                    } else {
                        downloadCACertificates(PropertyValues.CA_BUNDLE_URL);
                    }
                    String myProxyEndpoint = null;
                    if (att != null && att.containsKey("myProxyEndpoint")) {
                        myProxyEndpoint = (String) att.get("myProxyEndpoint");
                    }
                    if (myProxyEndpoint == null && PropertyValues.MY_PROXY_ENDPOINT != null) {
                        myProxyEndpoint = PropertyValues.MY_PROXY_ENDPOINT;
                    }
                    if (myProxyEndpoint != null) {
                        egi.proxyFilePath = generateProxy(cred.getAccessKeyId(), cred.getSecretKey(), SOURCE.MY_PROXY);
                    } else {
                        egi.proxyFilePath = generateProxy(cred.getAccessKeyId(), cred.getSecretKey(), SOURCE.CERTIFICATE);
                    }
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
            //After 10 years of grid comuting and using certificates we still can't get it to work.             
//            MyProxy myProxy = new MyProxy(PropertyValues.MY_PROXY_ENDPOINT, 7512);
//            myProxy.writeTrustRoots(PropertyValues.TRUSTED_CERTIFICATE_FOLDER);
//            
//            GSSCredential cert = myProxy.get(accessKeyId, secretKey, 2 * 3600);
//            X509Credential gCred = ((GlobusGSSCredentialImpl) cert).getX509Credential();
//            gCred.save(new FileOutputStream("/tmp/x509up_u0"));
            String cmd = "myproxy-logon "
                    + "--voms fedcloud.egi.eu "
                    + "-s " + PropertyValues.MY_PROXY_ENDPOINT
                    + " -l " + accessKeyId
                    + " --stdin_pass"
                    + " --out /tmp/x509up_u0";
//
            InputStream fileIn = new ByteArrayInputStream(secretKey.getBytes());
            Process process = Runtime.getRuntime().exec(cmd);
            OutputStream stdin = process.getOutputStream();
            InputStream stdout = process.getInputStream();
            InputStream stderr = process.getErrorStream();
            pipeStream(fileIn, stdin);
        }
        return "/tmp/x509up_u0";
    }

    public static void pipeStream(InputStream input, OutputStream output)
            throws IOException {
        byte buffer[] = new byte[1024];
        int numRead;

        do {
            numRead = input.read(buffer);
            output.write(buffer, 0, numRead);
        } while (input.available() > 0);

        output.flush();
    }

    private static void downloadCACertificates(URL url) throws MalformedURLException, IOException {
        String[] parts = url.getFile().split("/");
        String fileName = parts[parts.length - 1];
        File bundle = new File(PropertyValues.TRUSTED_CERTIFICATE_FOLDER + File.separator + fileName);
        if (!bundle.getParentFile().exists()) {
            if (!bundle.getParentFile().mkdirs()) {
                throw new IOException(bundle + " could not be created");
            }
        }

        if (!bundle.exists()) {
            URL website = new URL(url.toString());
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());

            FileOutputStream fos = new FileOutputStream(bundle);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            untar(new File(PropertyValues.TRUSTED_CERTIFICATE_FOLDER), bundle);
        }
    }

    private static void untar(File dest, File tarFile) throws IOException {
        Process p = Runtime.getRuntime().exec(" tar -xzvf " + tarFile.getAbsolutePath() + " -C " + dest.getAbsolutePath());
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String s = null;
        StringBuilder error = new StringBuilder();
        while ((s = stdError.readLine()) != null) {
            error.append(s);
        }
        if (s != null) {
            throw new IOException(error.toString());
        }
//        dest.mkdir();
//        TarArchiveInputStream tarIn;
//
//        tarIn = new TarArchiveInputStream(
//                new GzipCompressorInputStream(
//                        new BufferedInputStream(
//                                new FileInputStream(
//                                        tarFile
//                                )
//                        )
//                )
//        );
//
//        org.apache.commons.compress.archivers.tar.TarArchiveEntry tarEntry = tarIn.getNextTarEntry();
//
//        while (tarEntry != null) {
//            File destPath = new File(dest, tarEntry.getName());
//            if (tarEntry.isDirectory()) {
//                destPath.mkdirs();
//            } else {
//                destPath.createNewFile();
//                byte[] btoRead = new byte[1024];
//                try (BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(destPath))) {
//                    int len;
//
//                    while ((len = tarIn.read(btoRead)) != -1) {
//                        bout.write(btoRead, 0, len);
//                    }
//                }
////                Set<PosixFilePermission> perms = new HashSet<>();
////                perms.add(PosixFilePermission.OWNER_READ);
////                perms.add(PosixFilePermission.OWNER_WRITE);
////                perms.add(PosixFilePermission.OWNER_EXECUTE);
////
////                perms.add(PosixFilePermission.GROUP_READ);
////                perms.add(PosixFilePermission.GROUP_WRITE);
////                perms.add(PosixFilePermission.GROUP_EXECUTE);
////
////                perms.add(PosixFilePermission.OTHERS_READ);
////                perms.add(PosixFilePermission.OTHERS_EXECUTE);
////                perms.add(PosixFilePermission.OTHERS_EXECUTE);
////                Files.setPosixFilePermissions(Paths.get(destPath.getAbsolutePath()), perms);
//            }
//            tarEntry = tarIn.getNextTarEntry();
//        }
//        tarIn.close();
    }

}
