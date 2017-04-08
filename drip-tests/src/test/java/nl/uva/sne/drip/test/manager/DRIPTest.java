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
package nl.uva.sne.drip.test.manager;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alogo
 */
public class DRIPTest {

    public DRIPTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    private Client create(String host) {
//        SSLContext sc;
//        ClientBuilder builder = ClientBuilder.newBuilder();
//
//        try {
//            builder.hostnameVerifier(new javax.net.ssl.HostnameVerifier() {
//                public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
//                    if (hostname.equals(host)) {
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            builder = builder.sslContext(sc);
//            if (basicAutorization != null) {
//                builder.register(basicAutorization);
//            }
//        } catch (NullPointerException npe) {
//            LOGGER.warn("Null SSL context, skipping client SSL configuration", npe);
//        }
//        return builder.build();
return null;
    }

}
