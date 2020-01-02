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

import com.fasterxml.jackson.annotation.JsonInclude;
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
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import nl.uva.sne.drip.model.NodeTemplate;
import nl.uva.sne.drip.model.NodeTemplateMap;
import nl.uva.sne.drip.model.Provisioner;
import nl.uva.sne.drip.model.tosca.Credential;
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
    private ToscaTemplate toscaTemplateWithCredentials;

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
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

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
     * Test of getSupportedProvisionInterfaceDefinitions method, of class
     * ToscaHelper.
     *
     * @throws nl.uva.sne.drip.sure.tosca.client.ApiException
     */
    @Test
    public void testGetProvisionInterfaceDefinitions() throws ApiException {
        if (serviceUp) {
            System.out.println("getProvisionInterfaceDefinitions");
            String expected = "tosca.interfaces.ARTICONF.CloudsStorm";
            List<String> toscaInterfaceTypes = new ArrayList<>();
            toscaInterfaceTypes.add(expected);
            List<Map<String, Object>> result = instance.getProvisionInterfaceDefinitions(toscaInterfaceTypes);
            assertNotNull(result);
            String key = result.get(0).keySet().iterator().next();
//            assertEquals(expected, key);
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
            List<NodeTemplateMap> result = instance.getVMTopologyTemplates();
            assertNotNull(result);
            for (NodeTemplateMap nodeTemplateMap : result) {
                assertEquals(nodeTemplateMap.getNodeTemplate().getType(), "tosca.nodes.ARTICONF.VM.topology");
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

    /**
     * Test of getTemplateVMsForVMTopology method, of class ToscaHelper.
     */
    @Test
    public void testGetTemplateVMsForVMTopology() throws Exception {
        if (serviceUp) {
            System.out.println("getTemplateVMsForVMTopology");
            List<NodeTemplateMap> vmTopologyTemplatesMap = instance.getVMTopologyTemplates();
            for (NodeTemplateMap nodeTemplateMap : vmTopologyTemplatesMap) {
                List<NodeTemplateMap> result = instance.getTemplateVMsForVMTopology(nodeTemplateMap);
                for (NodeTemplateMap mvmTopology : result) {
                    assertEquals("tosca.nodes.ARTICONF.VM.Compute", mvmTopology.getNodeTemplate().getType());

                }
            }
        }

    }

    /**
     * Test of setCredentialsInVMTopology method, of class ToscaHelper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSetCredentialsAndInterface() throws Exception {
        if (serviceUp) {
            toscaTemplateWithCredentials = null;

            System.out.println("setCredentialsInVMTopology");
            List<NodeTemplateMap> vmTopologies = instance.getVMTopologyTemplates();

            for (NodeTemplateMap vmTopologyMap : vmTopologies) {
                String provider = instance.getTopologyProvider(vmTopologyMap);

                Credential credential = new Credential();
                credential.setCloudProviderName(provider);
                credential.setProtocol("protocol");
                Map<String, String> keys = new HashMap<>();
                keys.put("key1", "eeeeeeavovfouirveiuvbepuyb8rwqovd8boacbdbvwy8oqry7f08r3euadinanzxcjc078yn0183xoqedw");
                credential.setKeys(keys);
                credential.setToken("ijwbfciweubfriw");
                credential.setTokenType("passwrd");
                credential.setUser("user");
                vmTopologyMap = instance.setCredentialsInVMTopology(vmTopologyMap, credential);
                toscaTemplateWithCredentials = instance.setVMTopologyInToscaTemplate(toscaTemplate, vmTopologyMap);

            }
            instance.uploadToscaTemplate(toscaTemplateWithCredentials);
            NodeTemplate topology_1 = toscaTemplateWithCredentials.getTopologyTemplate().getNodeTemplates().get("topology_1");
            Map<String, Object> attributes = topology_1.getAttributes();
            assertNotNull(attributes);
            assertNotNull(attributes.get("credential"));
            NodeTemplate topology = toscaTemplateWithCredentials.getTopologyTemplate().getNodeTemplates().get("topology");
            attributes = topology.getAttributes();
            assertNotNull(attributes);
            assertNotNull(attributes.get("credential"));

            List<NodeTemplateMap> vmTopologiesMaps = instance.getVMTopologyTemplates();

            for (NodeTemplateMap vmTopologyMap : vmTopologiesMaps) {
                Credential toscaCredentials = instance.getCredentialsFromVMTopology(vmTopologyMap);
                assertNotNull(toscaCredentials);
            }

            vmTopologies = instance.getVMTopologyTemplates();
            ToscaTemplate toscaTemplateWithInterface = null;
            Provisioner provisioner = new Provisioner();
            provisioner.setName("CloudsStorm");
            provisioner.setDescription("Interface for VM topology management with CloudsStorm. More at https://cloudsstorm.github.io/");
            provisioner.setToscaInterfaceType("tosca.interfaces.ARTICONF.CloudsStorm");
            String operation = "provision";

//            for (NodeTemplateMap vmTopologyMap : vmTopologies) {
//                Map<String, Object> provisionInterface = instance.getProvisionInterface(provisioner, operation);
//                List<String> objects = new ArrayList<>();
//                objects.add("subtopology");
//                String key = provisionInterface.keySet().iterator().next();
//                Map<String, Object> provisionOperation = (Map<String, Object>) provisionInterface.get(key);
//                Map<String, Object> operationMap = (Map<String, Object>) provisionOperation.get(operation);
//                List<Map<String, Object>> inputs = (List<Map<String, Object>>) operationMap.get("inputs");
//                for (Map<String, Object> input : inputs) {
//                    if (input.containsKey("objects")) {
//                        input.put("objects", objects);
//                        break;
//                    }
//                }
//                vmTopologyMap = instance.setProvisionerInterfaceInVMTopology(vmTopologyMap, provisionInterface);
//                toscaTemplateWithInterface = instance.setVMTopologyInToscaTemplate(toscaTemplate, vmTopologyMap);
////            }
//            instance.uploadToscaTemplate(toscaTemplateWithInterface);
//            topology_1 = toscaTemplateWithCredentials.getTopologyTemplate().getNodeTemplates().get("topology_1");
//
//            topology = toscaTemplateWithCredentials.getTopologyTemplate().getNodeTemplates().get("topology");
            instance.uploadToscaTemplate(toscaTemplate);
        }
    }

}
