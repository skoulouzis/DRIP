/*
 * Copyright 2019 S. Koulouzis
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.model.NodeTemplate;
import nl.uva.sne.drip.model.tosca.ToscaTemplate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import nl.uva.sne.drip.sure.tosca.client.ApiException;

/**
 *
 * @author S.Koulouzis
 */
public class ToscaHelperTest {

    private static ObjectMapper objectMapper;
    private static final String testUpdatedApplicationExampleToscaFilePath = ".." + File.separator + "TOSCA" + File.separator + "application_example_2_topologies.yaml";
    private static ToscaHelper instance;
    private static ToscaTemplate toscaTemplate;
    private static Boolean serviceUp;

    public ToscaHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws UnsupportedEncodingException, JsonProcessingException, IOException, ApiException {

        Properties prop = new Properties();
        String resourceName = "src/test/resources/application.properties";
        prop.load(new FileInputStream(resourceName));
        byte[] bytes = Files.readAllBytes(Paths.get(testUpdatedApplicationExampleToscaFilePath));
        String ymlStr = new String(bytes, "UTF-8");
        objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        toscaTemplate = objectMapper.readValue(ymlStr, ToscaTemplate.class);
        String serviceBasePath = prop.getProperty("sure-tosca.base.path");
        serviceUp = isServiceUp(serviceBasePath);
        if (serviceUp) {
            instance = new ToscaHelper(serviceBasePath);
            instance.uploadToscaTemplate(toscaTemplate);
        }

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

    /**
     * Test of getId method, of class ToscaHelper.
     */
    @Test
    public void testGetId() {
        if (serviceUp) {
            System.out.println("getId");
            Integer result = instance.getId();
            assertNotNull(result);
        }
    }

    /**
     * Test of getProvisionInterfaceDefinitions method, of class ToscaHelper.
     *
     * @throws nl.uva.sne.drip.sure.tosca.client.ApiException
     */
    @Test
    public void testGetProvisionInterfaceDefinitions() throws ApiException {
        if (serviceUp) {
            System.out.println("getProvisionInterfaceDefinitions");
            List<String> toscaInterfaceTypes = new ArrayList<>();
            String expected = "tosca.interfaces.ARTICONF.CloudsStorm";
            toscaInterfaceTypes.add(expected);
            List<Map<String, Object>> result = instance.getProvisionInterfaceDefinitions(toscaInterfaceTypes);
            assertNotNull(result);
            String key = result.get(0).keySet().iterator().next();
            assertEquals(expected, key);
        }
    }

    /**
     * Test of getVMTopologyTemplates method, of class ToscaHelper.
     *
     * @throws nl.uva.sne.drip.sure.tosca.client.ApiException
     */
    @Test
    public void testGetVMTopologyTemplates() throws ApiException {
        if (serviceUp) {
            System.out.println("getVMTopologyTemplates");
            List<NodeTemplate> result = instance.getVMTopologyTemplates();
            assertNotNull(result);
            for (NodeTemplate nodeTemplate : result) {
                assertEquals(nodeTemplate.getType(), "tosca.nodes.ARTICONF.VM.topology");
            }
        }
    }

    public static Boolean isServiceUp(String serviceBasePath) {
        try {
            URL serviceUrl = new URL(serviceBasePath);
            HttpURLConnection connection = (HttpURLConnection) serviceUrl.openConnection();
            //Set request to header to reduce load as Subirkumarsao said.
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}
