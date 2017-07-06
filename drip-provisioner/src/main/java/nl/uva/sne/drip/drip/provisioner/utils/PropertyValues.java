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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.drip.provisioner.RPCServer;

/**
 *
 * @author S. Koulouzis
 */
public class PropertyValues {

    public static String RPC_QUEUE_NAME = "provisioner_queue_v1";
    public static String HOST = "127.0.0.1";
    public static String TRUSTED_CERTIFICATE_FOLDER;
    public static URL CA_BUNDLE_URL;
//    public static String MY_PROXY_ENDPOINT;
    public static String DOMAIN_INFO_PATH = "etc";

    public static void setPropertyValues(Properties prop) throws MalformedURLException {
        PropertyValues.HOST = prop.getProperty("rabbitmq.host", "127.0.0.1");
        PropertyValues.RPC_QUEUE_NAME = prop.getProperty("rpc.queue.name",
                "provisioner_queue_v1");
        PropertyValues.TRUSTED_CERTIFICATE_FOLDER = prop.getProperty("trusted.certificates.folder", System.getProperty("user.home") + File.separator + ".globus" + File.separator + "certificates");
        CA_BUNDLE_URL = new URL(prop.getProperty("ca.bundle.url",
                "https://dist.eugridpma.info/distribution/igtf/current/accredited/igtf-preinstalled-bundle-classic.tar.gz"));

//        MY_PROXY_ENDPOINT = prop.getProperty("my.proxy.endpoint");

        DOMAIN_INFO_PATH = prop.getProperty("domain.info.path",
                "etc");

        Logger.getLogger(RPCServer.class.getName()).log(Level.INFO,
                MessageFormat.format("rabbitmq.host: {0}", PropertyValues.HOST));
        Logger.getLogger(RPCServer.class.getName()).log(Level.INFO,
                MessageFormat.format("rpc.queue.name: {0}", PropertyValues.RPC_QUEUE_NAME));
    }
}
