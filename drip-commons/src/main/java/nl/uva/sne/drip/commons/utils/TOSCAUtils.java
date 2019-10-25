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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.uva.sne.drip.model.TopologyTemplate;
import nl.uva.sne.drip.model.ToscaTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author S. Koulouzis
 */
public class TOSCAUtils {

    public static List<Map.Entry> getNodes(ToscaTemplate toscaTemplate, String filterType, String filterValue) {
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
        Map<String, Object> nodeTemplates = topologyTemplate.getNodeTemplates();
        List<Map.Entry> nodeList = new ArrayList<>();

        Iterator it = nodeTemplates.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry node = (Map.Entry) it.next();
            Map<String, Object> nodeValue = (Map<String, Object>) node.getValue();
            if (byName && node.getKey().equals(filterValue)) {
                nodeList.add(node);
            }
            if (byType) {
                String type = (String) nodeValue.get("type");
                if (type.equals(filterValue)) {
                    nodeList.add(node);
                }
            }

        }
        return nodeList;

    }

    public static List<Map.Entry> getNodesByType(ToscaTemplate toscaTemplate, String nodeTypeName) {
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

    public static List<Map.Entry> getDockerContainers(ToscaTemplate toscaTemplate) {
        TopologyTemplate topologyTemplate = toscaTemplate.getTopologyTemplate();
        Map<String, Object> nodeTemplates = topologyTemplate.getNodeTemplates();
        Iterator it = nodeTemplates.entrySet().iterator();
        List<Map.Entry> dockerContainers = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry node = (Map.Entry) it.next();
            Map<String, Object> nodeValue = (Map<String, Object>) node.getValue();
            String type = (String) nodeValue.get("type");
            if (type.equals("tosca.nodes.ARTICONF.Container.Application.Docker")) {
                dockerContainers.add(node);
            }
        }
        return dockerContainers;
    }

    public static List<Map<String, Object>> tosca2KubernetesDeployment(ToscaTemplate toscaTemplate) {
        List<Map.Entry> dockerContainers = getDockerContainers(toscaTemplate);
        List<Map<String, Object>> deployments = new ArrayList<>();

        Iterator<Map.Entry> dicIt = dockerContainers.iterator();
        while (dicIt.hasNext()) {
            Map.Entry docker = dicIt.next();
            String name = (String) docker.getKey();
            Map<String, Object> labels = new HashMap();
            labels.put("app", name);

            Map<String, Object> metadata = new HashMap();
            metadata.put("labels", labels);
            metadata.put("name", name);

            Map<String, Object> selector = new HashMap();
            selector.put("matchLabels", labels);
            Map<String, Object> template = new HashMap();
            template.put("metadata", metadata);

            Map<String, Object> dockerValues = (Map<String, Object>) docker.getValue();

            List<Map<String, Object>> containersList = createContainerList(dockerValues, name);

            Map<String, Object> spec1 = new HashMap();
            spec1.put("containers", containersList);
            template.put("spec", spec1);

            Map<String, Object> topSpec = new HashMap();
            topSpec.put("selector", selector);
            topSpec.put("replicas", 1);
            topSpec.put("template", template);

            Map<String, Object> deployment = new HashMap();
            deployment.put("spec", topSpec);
            deployment.put("metadata", metadata);
            deployment.put("kind", "Deployment");
            deployment.put("apiVersion", "apps/v1");

            deployments.add(deployment);
        }

        return deployments;
    }

    public static List<Map<String, Object>> tosca2KubernetesService(ToscaTemplate toscaTemplate) {
        List<Map.Entry> dockerContainers = getDockerContainers(toscaTemplate);
        List<Map<String, Object>> services = new ArrayList<>();
        Iterator<Map.Entry> dicIt = dockerContainers.iterator();
        while (dicIt.hasNext()) {
            Map.Entry docker = dicIt.next();
            String name = (String) docker.getKey();
            Map<String, Object> dockerValues = (Map<String, Object>) docker.getValue();

            Map<String, Object> spec = new HashMap();
            spec.put("type", "NodePort");

            Map<String, Object> properties = (Map<String, Object>) dockerValues.get("properties");
            List<String> toscaPortsList = (List<String>) properties.get("ports");
            List< Map<String, Object>> portList = new ArrayList<>();
            if (toscaPortsList != null) {
                for (String portEntry : toscaPortsList) {
                    String[] portsArray = portEntry.split(":");
                    Map<String, Object> portMap = new HashMap();
                    portMap.put("port", Integer.valueOf(portsArray[1]));
                    portList.add(portMap);
                }
                spec.put("ports", portList);
            }

            Map<String, Object> selector = new HashMap();
            selector.put("app", name);
            spec.put("selector", selector);

            Map<String, Object> labels = new HashMap();
            labels.put("app", name);
            Map<String, Object> metadata = new HashMap();
            metadata.put("labels", labels);
            metadata.put("name", name);

            Map<String, Object> service = new HashMap();
            service.put("spec", spec);
            service.put("metadata", metadata);
            service.put("kind", "Service");
            service.put("apiVersion", "v1");
            services.add(service);
        }
        return services;
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

    private static List<Map<String, Object>> createContainerList(Map<String, Object> dockerValues, String name) {
        Map<String, Object> properties = (Map<String, Object>) dockerValues.get("properties");
        List<Map<String, Object>> imageEnv = getImageEnvirmoent(properties);

        String imageFile = getImageFile(dockerValues);

        Map<String, Object> container = new HashMap();
        container.put("image", imageFile);
        container.put("name", name);
        container.put("env", imageEnv);

        List<String> toscaPortsList = (List<String>) properties.get("ports");
        if (toscaPortsList != null) {
            List< Map<String, Object>> portList = new ArrayList<>();
            for (String portEntry : toscaPortsList) {
                String[] portsArray = portEntry.split(":");
                Map<String, Object> portMap = new HashMap();
                portMap.put("containerPort", Integer.valueOf(portsArray[0]));
                portList.add(portMap);
            }
            container.put("ports", portList);
        }
        List<Map<String, Object>> containersList = new ArrayList<>();
        containersList.add(container);
        return containersList;
    }

    public static List<Map.Entry> getRelatedNodes(Map<String, Object> node, ToscaTemplate toscaTemplate) {
        String nodeName = node.keySet().iterator().next();
        Map<String, Object> nodeMap = (Map<String, Object>) node.get(nodeName);

        if (nodeMap.containsKey("requirements")) {
            List<String> nodeNames = new ArrayList<>();
            List<Map<String, Object>> requirements = (List<Map<String, Object>>) nodeMap.get("requirements");
            for (Map<String, Object> requirement : requirements) {
                String reqName = requirement.keySet().iterator().next();
                Map<String, Object> requirementMap = (Map<String, Object>) requirement.get(reqName);
//                String requirementCapability = (String) requirementMap.get("capability");
                String relatedNode = (String) requirementMap.get("node");
                nodeNames.add(relatedNode);
            }
            List<Map.Entry> retaltedNodes = new ArrayList<>();
            for (String name : nodeNames) {
                List<Map.Entry> relatedNode = getNodesByName(toscaTemplate, name);
                for (Map.Entry rNode : relatedNode) {
                    retaltedNodes.add(rNode);
                }

            }
            return retaltedNodes;
        }
        return null;

    }

    private static List<Map.Entry> getNodesByName(ToscaTemplate toscaTemplate, String name) {
        return getNodes(toscaTemplate, "name", name);
    }

    public static boolean nodeIsOfType(Map.Entry node, String type) {
        String nodeName = (String) node.getKey();
        Map<String, Object> nodeMap = (Map<String, Object>) node.getValue();
        if (nodeMap.containsKey("type") && nodeMap.get("type").equals(type)) {
            return true;
        }
        return false;
    }

    public static Object getNodeProperty(Map.Entry node, String propertyName) {
        Map<String, Object> nodeMap = (Map<String, Object>) node.getValue();
        if (nodeMap.containsKey("properties") ) {
            Map<String, Object> properties = (Map<String, Object>) nodeMap.get("properties");
            return  properties.get(propertyName);
        }        
        return null;
    }
    
    

}
