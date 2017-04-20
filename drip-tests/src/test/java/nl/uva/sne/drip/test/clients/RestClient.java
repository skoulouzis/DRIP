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
package nl.uva.sne.drip.test.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URL;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;

/**
 *
 * @author S. Koulouzis
 */
public class RestClient {

    private final Client client;
    private final WebTarget webTarget;
    private final ObjectMapper mapper;

    public RestClient(URL host) {
        this.client = createClient();
        this.webTarget = client.target(host.toString());
        this.mapper = new ObjectMapper();
    }

    private Client createClient() {
        ClientBuilder builder = ClientBuilder.newBuilder();

        SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
        SSLContext sc = sslContextConfigurator.createSSLContext(true);

        builder = builder.sslContext(sc);

        builder.hostnameVerifier(new javax.net.ssl.HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        });

        return builder.build();
    }

    /**
     * @return the webTarget
     */
    public WebTarget getWebTarget() {
        return webTarget;
    }

    /**
     * @return the mapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}
