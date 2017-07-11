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
package nl.uva.sne.drip.commons.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.security.cert.CertificateEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static nl.uva.sne.drip.commons.utils.FileUtils.untar;
//import org.globus.common.CoGProperties;
//import org.globus.myproxy.GetParams;
//import org.globus.myproxy.MyProxyException;
//import org.globus.util.Util;
//import org.gridforum.jgss.ExtendedGSSCredential;
//import org.gridforum.jgss.ExtendedGSSManager;
//import org.ietf.jgss.GSSCredential;
//import org.ietf.jgss.GSSException;
//import org.ietf.jgss.GSSManager;

/**
 *
 * @author S. Koulouzis
 */
public class AAUtils {

    public enum SOURCE {
        MY_PROXY,
        CERTIFICATE, PROXY_FILE
    }

    public static String generateProxy(String accessKeyId, String secretKey, SOURCE source, String myProxyEndpoint, List voname) throws IOException, CertificateEncodingException {
        File proxy_file = null;
        switch (source) {
            case MY_PROXY:
//                GetParams getRequest = new GetParams();
//                getRequest.setUserName(accessKeyId);
//                getRequest.setCredentialName(null);
//                getRequest.setLifetime(43200);
//                getRequest.setWantTrustroots(false);
//                getRequest.setPassphrase(secretKey);
//                getRequest.setVoname(voname);
//                GSSManager manager = ExtendedGSSManager.getInstance();
//                GSSCredential credential = manager.createCredential(GSSCredential.INITIATE_ONLY);
//                org.globus.myproxy.MyProxy myProxy = new org.globus.myproxy.MyProxy(myProxyEndpoint, 7512);
//                GSSCredential newCred = myProxy.get(credential, getRequest);
//                CoGProperties properties = CoGProperties.getDefault();
//                String outputFile = properties.getProxyFile();
//                proxy_file = new File(outputFile);
//                String path = proxy_file.getPath();
//                byte[] data
//                        = ((ExtendedGSSCredential) newCred).export(ExtendedGSSCredential.IMPEXP_OPAQUE);
//                Util.setOwnerAccessOnly(path);
//                Files.write(Paths.get(path), data);
////                try (FileOutputStream out = new FileOutputStream(path);) {
////                    Util.setOwnerAccessOnly(path);
////                    byte[] data
////                            = ((ExtendedGSSCredential) newCred).export(ExtendedGSSCredential.IMPEXP_OPAQUE);
////                    out.write(data);
////                }
                break;
            case PROXY_FILE:
                secretKey += "\n";
                proxy_file = new File("/tmp/x509up_u1000");
                Set<PosixFilePermission> perm = new HashSet<>();
                perm.add(PosixFilePermission.OWNER_WRITE);
                Files.setPosixFilePermissions(proxy_file.toPath(), perm);
                
                Files.write(proxy_file.toPath(), secretKey.getBytes());
                break;
            case CERTIFICATE:
                break;
            default:
                break;
        }
        Set<PosixFilePermission> perm = new HashSet<>();
        perm.add(PosixFilePermission.OWNER_READ);
        Files.setPosixFilePermissions(proxy_file.toPath(), perm);
        return proxy_file.getAbsolutePath();
    }

    public static void downloadCACertificates(URL url, String folder) throws MalformedURLException, IOException {
        String[] parts = url.getFile().split("/");
        String fileName = parts[parts.length - 1];
        File bundle = new File(folder + File.separator + fileName);
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
            untar(new File(folder), bundle);
        }
    }
}
