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

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.uva.sne.drip.model.NodeTemplate;
import nl.uva.sne.drip.model.TopologyTemplate;
import nl.uva.sne.drip.model.ToscaTemplate;

/**
 *
 * @author S. Koulouzis
 */
public class TOSCAUtils {

    public static List<Map<String, NodeTemplate>> getNodes(ToscaTemplate toscaTemplate, String filterType, String filterValue) {
        boolean byType = false;
        boolean byName = false;
        switch (filterType) {
            case "type":
                byType = true;
                break;
            case "name":
                byName = true;
                break;
        }
        TopologyTemplate topologyTemplate = toscaTemplate.getTopologyTemplate();
        Map<String, NodeTemplate> nodeTemplates = topologyTemplate.getNodeTemplates();
        List<Map<String, NodeTemplate>> nodeList = new ArrayList<>();
        Set<String> keys = nodeTemplates.keySet();
        for (String key : keys) {
            NodeTemplate node = nodeTemplates.get(key);
            if (byName && key.equals(filterValue)) {
                Map<String, NodeTemplate> ntMap = new HashMap<>();
                ntMap.put(key, node);
                nodeList.add(ntMap);
            }
            if (byType) {
                if (node.getType().equals(filterValue)) {
                    Map<String, NodeTemplate> ntMap = new HashMap<>();
                    ntMap.put(key, node);
                    nodeList.add(ntMap);
                }
            }
        }
        return nodeList;

    }

    public static List<Map<String, NodeTemplate>> getNodesByType(ToscaTemplate toscaTemplate, String nodeTypeName) {
        return getNodes(toscaTemplate, "type", nodeTypeName);
    }

    public static Map<String, Object> buildTOSCAOutput(Map<String, Object> outputs, String nodeName, String value, String key, boolean encodeValueToBas64) {
        List<Map<String, String>> values;
        if (outputs.containsKey(key)) {
            values = (List<Map<String, String>>) outputs.get(key);
        } else {
            values = new ArrayList<>();
        }
        if (encodeValueToBas64) {
            value = Base64.getEncoder().encodeToString(value.getBytes());
        }
        Map<String, String> map = new HashMap<>();
        map.put(nodeName, value);
        values.add(map);
        outputs.put(key, values);
        return outputs;
    }

    public static Map<String, String> getOutputsForNode(ToscaTemplate toscaTemplate, String nodeName) {
        TopologyTemplate topologyTemplate = toscaTemplate.getTopologyTemplate();

        List<Map<String, Object>> outputs = topologyTemplate.getOutputs();
        List<Map<String, Object>> matchedOutputs = new ArrayList<>();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public static List<String> getOutputPair(Map<String, Object> outputs, String key) {
        List<String> outputPair = (List<String>) ((Map<String, Object>) ((Map<String, Object>) outputs.get(key)).get("value")).get("get_attribute");
        return outputPair;
    }

    private static List<Map<String, Object>> getImageEnvirmoent(Map<String, Object> properties) {
        Map<String, Object> envMap = (Map<String, Object>) properties.get("environment");

        List<Map<String, Object>> imageEnv = new ArrayList<>();
        Set<String> keys = envMap.keySet();

        for (String key : keys) {
            Map<String, Object> kubernetesEnvMap = new HashMap();
            kubernetesEnvMap.put("name", key);
            kubernetesEnvMap.put("value", envMap.get(key));
            imageEnv.add(kubernetesEnvMap);
        }
        return imageEnv;
    }

    private static String getImageFile(Map<String, Object> dockerValues) {
        Map<String, Object> image = (Map<String, Object>) ((Map<String, Object>) dockerValues.get("artifacts")).get("image");
        if (image == null) {
            image = (Map<String, Object>) ((Map<String, Object>) dockerValues.get("artifacts")).get("my_image");
        }
        String imageFile = (String) image.get("file");
        return imageFile;
    }

    public static List<Map<String, NodeTemplate>> getRelatedNodes(NodeTemplate node, ToscaTemplate toscaTemplate) {

        if (node.getRequirements() != null) {
            List<String> nodeNames = new ArrayList<>();
            List<Map<String, Object>> requirements = node.getRequirements();
            for (Map<String, Object> requirement : requirements) {
                String reqName = requirement.keySet().iterator().next();
                Map<String, Object> requirementMap = (Map<String, Object>) requirement.get(reqName);
                String relatedNode = (String) requirementMap.get("node");
                nodeNames.add(relatedNode);
            }
            List<Map<String, NodeTemplate>> retaltedNodes = new ArrayList<>();
            for (String name : nodeNames) {
                List<Map<String, NodeTemplate>> relatedNode = getNodesByName(toscaTemplate, name);
                for (Map<String, NodeTemplate> rNode : relatedNode) {
                    retaltedNodes.add(rNode);
                }
            }
            return retaltedNodes;
        }
        return null;

    }

    private static List<Map<String, NodeTemplate>> getNodesByName(ToscaTemplate toscaTemplate, String name) {
        return getNodes(toscaTemplate, "name", name);
    }

    public static boolean nodeIsOfType(NodeTemplate node, String type) {
        if (node.getType().equals(type)) {
            return true;
        }
        return false;
    }

    public static Object getNodeProperty(NodeTemplate node, String propertyName) {
        if (node.getProperties() != null) {
            return node.getProperties().get(propertyName);
        }
        return null;
    }

}
