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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.data.v1.external.Key;
import nl.uva.sne.drip.data.v1.external.KeyPair;
import nl.uva.sne.drip.data.v1.external.KeyType;
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
 * @author alogo
 */
public class TestKeysController extends DRIPTest {

    public TestKeysController() {
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
    @Test
    public void test_POST_GETCoudKeys() {
        String[] paths = DRIPTest.CLOUD_PRIVATE_KEY_PATHS_PROPERTY_NAME.split(",");
        for (String cloudPrivateKeyPath : paths) {
            try {
                KeyPair keyPair = new KeyPair();
                Key privateKey = createPrivateCloudKey(cloudPrivateKeyPath);
                keyPair.setPrivateKey(privateKey);
                post(keyPair, 200);
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(TestCloudCredentialsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void post(KeyPair keyPair, int expected) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Key createPrivateCloudKey(String cloudPrivateKeyPath) throws IOException {
        Key privateKey = new Key();
        privateKey.setName(FileUtils.readFileToString(new File(cloudPrivateKeyPath), "UTF-8"));
        privateKey.setKey(FilenameUtils.getBaseName(cloudPrivateKeyPath));
        privateKey.setType(KeyType.PRIVATE);
        Map<String, String> map = new HashMap<>();
        map.put("attribute", "value");
        privateKey.setAttributes(map);
        return privateKey;

    }
}
