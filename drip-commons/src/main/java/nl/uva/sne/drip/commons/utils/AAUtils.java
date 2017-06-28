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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.CertificateEncodingException;
import java.util.List;
import org.globus.common.CoGProperties;
import org.globus.myproxy.GetParams;
import org.globus.myproxy.MyProxyException;
import org.globus.util.Util;
import org.gridforum.jgss.ExtendedGSSCredential;
import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;

/**
 *
 * @author S. Koulouzis
 */
public class AAUtils {

    public static String generateProxy(String accessKeyId, String secretKey, SOURCE source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public enum SOURCE {
        MY_PROXY,
        CERTIFICATE
    }

    public static String generateProxy(String accessKeyId, String secretKey, SOURCE source, String myProxyEndpoint, List voname) throws IOException, CertificateEncodingException, GSSException, MyProxyException {
        File proxy_file = null;
        if (source.equals(SOURCE.MY_PROXY)) {
            GetParams getRequest = new GetParams();
            getRequest.setUserName(accessKeyId);
            getRequest.setCredentialName(null);
            getRequest.setLifetime(43200);
            getRequest.setWantTrustroots(false);
            getRequest.setPassphrase(secretKey);
            getRequest.setVoname(voname);

            GSSManager manager = ExtendedGSSManager.getInstance();
            GSSCredential credential = manager.createCredential(GSSCredential.INITIATE_ONLY);

            org.globus.myproxy.MyProxy myProxy = new org.globus.myproxy.MyProxy(myProxyEndpoint, 7512);
            GSSCredential newCred = myProxy.get(credential, getRequest);

            CoGProperties properties = CoGProperties.getDefault();
            String outputFile = properties.getProxyFile();
            proxy_file = new File(outputFile);
            String path = proxy_file.getPath();

            try (FileOutputStream out = new FileOutputStream(path);) {

                // set read only permissions
                Util.setOwnerAccessOnly(path);
                byte[] data
                        = ((ExtendedGSSCredential) newCred).export(ExtendedGSSCredential.IMPEXP_OPAQUE);
                out.write(data);
            }
        } else if (source.equals(SOURCE.CERTIFICATE)) {

        }
        return proxy_file.getAbsolutePath();
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
}
