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

import nl.uva.sne.drip.test.clients.KeyPairClient;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import nl.uva.sne.drip.data.v1.external.CloudCredentials;
import nl.uva.sne.drip.data.v1.external.Key;
import nl.uva.sne.drip.data.v1.external.KeyPair;
import nl.uva.sne.drip.data.v1.external.KeyType;
import static nl.uva.sne.drip.test.manager.DRIPTest.ACCESS_KEY_ID_PROPPERTY_NAME;
import static nl.uva.sne.drip.test.manager.DRIPTest.CLOUD_PROPVIDER_PROPPERTY_NAME;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author S. Koulouzis
 */
public class TestKeysController extends DRIPTest {

    @Test
    public void test_POST_GET_DELETECoudKeys() throws MalformedURLException {
        for (Properties p : DRIPTest.propertiesList) {
            String[] paths = p.getProperty(DRIPTest.CLOUD_PRIVATE_KEY_PATHS_PROPERTY_NAME).split(",");
            KeyPairClient client = new KeyPairClient(new URL(p.getProperty(DRIPTest.DRIP_HOST_PROPERTY_NAME)));
            for (String cloudPrivateKeyPath : paths) {
                try {
                    KeyPair keyPair = new KeyPair();
                    Key privateKey = createPrivateCloudKey(cloudPrivateKeyPath);
                    keyPair.setPrivateKey(privateKey);
                    Response response = client.postKeyPair(keyPair);
                    assertEquals(200, response.getStatus());
                    String keyID = client.getPostKeyPairResponse(response);
                    assertNotNull(keyID);

                    response = client.get(keyID);
                    assertEquals(200, response.getStatus());
                    KeyPair returnedPair = client.getGetKeyPairResponse(response);
                    assertNotNull(returnedPair);
                    assertEquals(keyID, returnedPair.getId());
                    assertNotNull(returnedPair.getPrivateKey());

                    response = client.delete(keyID);
                    assertEquals(200, response.getStatus());

                } catch (IOException ex) {
                    Logger.getLogger(TestCloudCredentialsController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private Key createPrivateCloudKey(String cloudPrivateKeyPath) throws IOException {
        Key privateKey = new Key();
        privateKey.setName(FilenameUtils.getBaseName(cloudPrivateKeyPath));
        privateKey.setKey(FileUtils.readFileToString(new File(cloudPrivateKeyPath), "UTF-8"));
        privateKey.setType(KeyType.PRIVATE);
        Map<String, String> map = new HashMap<>();
        map.put("domain_name", FilenameUtils.getBaseName(cloudPrivateKeyPath));
        privateKey.setAttributes(map);
        return privateKey;

    }
}
