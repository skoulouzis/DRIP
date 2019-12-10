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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import nl.uva.sne.drip.model.NodeTemplate;
import nl.uva.sne.drip.model.ToscaTemplate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import nl.uva.sne.drip.sure_tosca.client.ApiException;

/**
 *
 * @author S.Koulouzis
 */
public class ToscaHelperTest {

    private static ObjectMapper objectMapper;
    private static final String testUpdatedApplicationExampleToscaFilePath = ".." + File.separator + "TOSCA" + File.separator + "application_example_2_topologies.yaml";
    private static ToscaHelper instance;
    private static ToscaTemplate toscaTemplate;

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
        instance = new ToscaHelper(toscaTemplate, prop.getProperty("sure-tosca.base.path"));

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
        System.out.println("getId");
        Integer result = instance.getId();
        assertNotNull(result);
    }

    /**
     * Test of getProvisionInterfaceDefinitions method, of class ToscaHelper.
     */
    @Test
    public void testGetProvisionInterfaceDefinitions() throws Exception {
        System.out.println("getProvisionInterfaceDefinitions");
        List<String> toscaInterfaceTypes = new ArrayList<>();
        String expected = "tosca.interfaces.ARTICONF.CloudsStorm";
        toscaInterfaceTypes.add(expected);
        List<Map<String, Object>> result = instance.getProvisionInterfaceDefinitions(toscaInterfaceTypes);
        assertNotNull(result);
        String key = result.get(0).keySet().iterator().next();
        assertEquals(expected, key);
    }

    /**
     * Test of getVMTopologyTemplates method, of class ToscaHelper.
     */
    @Test
    public void testGetVMTopologyTemplates() throws Exception {
        System.out.println("getVMTopologyTemplates");
        List<NodeTemplate> result = instance.getVMTopologyTemplates();
        assertNotNull(result);
        for (NodeTemplate nodeTemplate : result) {
            assertEquals(nodeTemplate.getType(), "tosca.nodes.ARTICONF.VM.topology");
        }
    }

}
