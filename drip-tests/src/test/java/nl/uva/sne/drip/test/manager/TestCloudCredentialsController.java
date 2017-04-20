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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.data.v1.external.CloudCredentials;
import nl.uva.sne.drip.data.v1.external.Key;
import nl.uva.sne.drip.data.v1.external.KeyPair;
import nl.uva.sne.drip.data.v1.external.KeyType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

/**
 *
 * @author S. Koulouzis.
 */
public class TestCloudCredentialsController extends DRIPTest {

    @Test
    public void testPOST_GETCloudCredentials() {
        for (Properties p : DRIPTest.propertiesList) {
            String[] paths = p.getProperty(DRIPTest.CLOUD_PRIVATE_KEY_PATHS_PROPERTY_NAME).split(",");
            for (String cloudPrivateKeyPath : paths) {
                try {
                    KeyPair keyPair = new KeyPair();
                    Key privateKey = new Key();
                    privateKey.setName(FileUtils.readFileToString(new File(cloudPrivateKeyPath), "UTF-8"));
                    privateKey.setKey(FilenameUtils.getBaseName(cloudPrivateKeyPath));
                    privateKey.setType(KeyType.PRIVATE);
                    Map<String, String> map = new HashMap<>();
                    map.put(cloudPrivateKeyPath, cloudPrivateKeyPath);
                    privateKey.setAttributes(map);
                    keyPair.setPrivateKey(privateKey);

                } catch (IOException ex) {
                    Logger.getLogger(TestCloudCredentialsController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            CloudCredentials cc = new CloudCredentials();
            cc.setAccessKeyId(p.getProperty(ACCESS_KEY_ID_PROPPERTY_NAME));
            cc.setCloudProviderName(p.getProperty(CLOUD_PROPVIDER_PROPPERTY_NAME));
            cc.setSecretKey(p.getProperty(DRIPTest.SECRET_KEY_PROPERTY_NAME));
            List<String> keyPairIDs = new ArrayList<>();
            cc.setKeyPairIDs(keyPairIDs);
        }
    }

}
