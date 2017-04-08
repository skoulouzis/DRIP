/*
 * Copyright 2017 S. Koulouzis.
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

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;


/**
 *
 * @author S. Koulouzis.
 */
public class CloudCredentialsController {

    public CloudCredentialsController() {
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
    
    
   private void post(String lcmId, String metadataId, String storageId, int expected) {
//    String payload = "{\"local-storage-id\" : \"" + storageId + "\"}";
//    Entity<String> entity = Entity.entity(payload, "application/json");
//    Response resp = getWebTarget().path(TRIGGER_PATH).path(lcmId).path("metadata").path(metadataId)
//        .request().header(AUTH_USER_HEADER, "admin")
//        .header(BasicAuthenticationManager.BASIC_AUTHENTICATION_HEADER, basicAuthTokenAdmin)
//        .post(entity);
//    assertEquals(expected, resp.getStatus());
  }
      
    
}
