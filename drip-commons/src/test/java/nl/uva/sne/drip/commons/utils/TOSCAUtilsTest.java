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
package nl.uva.sne.drip.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.uva.sne.drip.model.NodeTemplate;
import nl.uva.sne.drip.model.ToscaTemplate;
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
public class TOSCAUtilsTest {

    private static ToscaTemplate toscaTemplate;

    public TOSCAUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        String toscaFilePath = "../TOSCA/application_example_output.yaml";
        String ymlStr = new String(Files.readAllBytes(Paths.get(toscaFilePath)));
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        toscaTemplate = objectMapper.readValue(ymlStr, ToscaTemplate.class);
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
     * Test of getNodesByType method, of class TOSCAUtils.
     */
    @Test
    public void testGetNodesByType() {
        System.out.println("getNodesByType");
        String nodeTypeName = "tosca.nodes.ARTICONF.VM.topology";
        List<Map<String, NodeTemplate>> result = testGetNodesByType(nodeTypeName);
        assertEquals(1, result.size());

        nodeTypeName = "tosca.nodes.ARTICONF.VM.Compute";
        result = testGetNodesByType(nodeTypeName);
        assertEquals(2, result.size());
    }

    /**
     * Test of getRelatedNodes method, of class TOSCAUtils.
     */
    @Test
    public void testGetRelatedNodes() {
        System.out.println("getRelatedNodes");
        String nodeTypeName = "tosca.nodes.ARTICONF.VM.topology";
        List<Map<String, NodeTemplate>> vmTopologies = TOSCAUtils.getNodesByType(toscaTemplate, nodeTypeName);
        NodeTemplate vmTopology = vmTopologies.get(0).get(vmTopologies.get(0).keySet().iterator().next());

        nodeTypeName = "tosca.nodes.ARTICONF.VM.Compute";

        List<Map<String, NodeTemplate>> result = TOSCAUtils.getRelatedNodes(vmTopology, toscaTemplate);
        assertEquals(2, result.size());

        for (Map<String, NodeTemplate> node : result) {
            String name = node.keySet().iterator().next();
            assertEquals(nodeTypeName, node.get(name).getType());
        }
    }

    /**
     * Test of nodeIsOfType method, of class TOSCAUtils.
     */
    @Test
    public void testNodeIsOfType() {
        System.out.println("nodeIsOfType");
        String nodeTypeName = "tosca.nodes.ARTICONF.VM.topology";
        List<Map<String, NodeTemplate>> vmTopologies = TOSCAUtils.getNodesByType(toscaTemplate, nodeTypeName);
        NodeTemplate vmTopology = vmTopologies.get(0).get(vmTopologies.get(0).keySet().iterator().next());

        boolean expResult = true;
        boolean result = TOSCAUtils.nodeIsOfType(vmTopology, nodeTypeName);
        assertEquals(expResult, result);

        expResult = false;
        nodeTypeName = "tosca.nodes.ARTICONF";
        result = TOSCAUtils.nodeIsOfType(vmTopology, nodeTypeName);
        assertEquals(expResult, result);

    }

    /**
     * Test of getNodeProperty method, of class TOSCAUtils.
     */
    @Test
    public void testGetNodeProperty() {
        System.out.println("getNodeProperty");
        testGetVMProperties();

    }

    private void testGetVMProperties() {
        String nodeTypeName = "tosca.nodes.ARTICONF.VM.Compute";
        List<Map<String, NodeTemplate>> vms = TOSCAUtils.getNodesByType(toscaTemplate, nodeTypeName);
        assertEquals(2, vms.size());
        Map<String, Object> vmProertyMap = new HashMap<>();
        vmProertyMap.put("disk_size", "50000 MB");
        vmProertyMap.put("host_name", "vm");
        vmProertyMap.put("mem_size", "6000 MB");
        vmProertyMap.put("num_cores", 2);
        vmProertyMap.put("os", "ubuntu 16");
        vmProertyMap.put("user_name", "vm_user");

        for (Map<String, NodeTemplate> vmMap : vms) {
            Set<String> keys = vmProertyMap.keySet();
            NodeTemplate vm = vmMap.get(vmMap.keySet().iterator().next());
            for (String propName : keys) {
                Object result = TOSCAUtils.getNodeProperty(vm, propName);
                assertEquals(vmProertyMap.get(propName), result);
            }
        }
    }

    private List<Map<String, NodeTemplate>> testGetNodesByType(String nodeTypeName) {

        List<Map<String, NodeTemplate>> result = TOSCAUtils.getNodesByType(toscaTemplate, nodeTypeName);
        for (Map<String, NodeTemplate> node : result) {
            String name = node.keySet().iterator().next();
            assertEquals(nodeTypeName, node.get(name).getType());
        }
        return result;
    }

}
