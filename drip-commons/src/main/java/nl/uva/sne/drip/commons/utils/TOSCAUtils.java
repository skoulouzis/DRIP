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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author S. Koulouzis
 */
public class TOSCAUtils {

    public static List<Map<String, Object>> getVMsFromTopology(Map<String, Object> toscaPlan) {
        List<String> vmNames = getVMsNodeNamesFromTopology(toscaPlan);
        Map<String, Object> nodeTemplates = (Map<String, Object>) ((Map<String, Object>) toscaPlan.get("topology_template")).get("node_templates");
        List<Map<String, Object>> vmList = new ArrayList<>();
        for (String vmName : vmNames) {
            Map<String, Object> vm = (Map<String, Object>) nodeTemplates.get(vmName);
//            Map<String, Object> properties = (Map<String, Object>) vm.get("properties");
            vmList.add(vm);
        }
        return vmList;
    }

    public static List<String> getVMsNodeNamesFromTopology(Map<String, Object> toscaPlan) {
        Map<String, Object> topologyTemplate = (Map<String, Object>) toscaPlan.get("topology_template");
        Map<String, Object> nodeTemplates = (Map<String, Object>) (topologyTemplate).get("node_templates");
        Iterator it = nodeTemplates.entrySet().iterator();
        List<String> vmNames = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry node = (Map.Entry) it.next();
            Map<String, Object> nodeValue = (Map<String, Object>) node.getValue();
            String type = (String) nodeValue.get("type");
            if (type.equals("tosca.nodes.ARTICONF.VM.topology")) {
                List<Map<String, Object>> requirements = (List<Map<String, Object>>) nodeValue.get("requirements");
                for (Map<String, Object> req : requirements) {
                    Map.Entry<String, Object> requirementEntry = req.entrySet().iterator().next();
                    String nodeName = (String) ((Map<String, Object>) req.get(requirementEntry.getKey())).get("node");
                    vmNames.add(nodeName);
                }
            }
        }
        return vmNames;
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

    public static Map<String, String> getOutputsForNode(Map<String, Object> toscaProvisonMap, String nodeName) {
        Map<String, Object> topologyTemplate = (Map<String, Object>) ((Map<String, Object>) toscaProvisonMap.get("topology_template"));
        Map<String, Object> outputs = (Map<String, Object>) topologyTemplate.get("outputs");
        Map<String, String> matchedOutputs = new HashMap<>();
        Iterator it = outputs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry output = (Map.Entry) it.next();
            List<Map<String, String>> val = (List<Map<String, String>>) output.getValue();
            for (Map<String, String> map : val) {
                if (map.containsKey(nodeName)) {
                    matchedOutputs.put((String) output.getKey(), map.get(nodeName));
                }
            }
        }
        return matchedOutputs;
    }

    public static List<String> getOutputPair(Map<String, Object> outputs, String key) {
        List<String> outputPair = (List<String>) ((Map<String, Object>) ((Map<String, Object>) outputs.get(key)).get("value")).get("get_attribute");
        return outputPair;
    }

    public static List<Map.Entry> getDockerContainers(Map<String, Object> toscaPlan) {
        Map<String, Object> topologyTemplate = (Map<String, Object>) toscaPlan.get("topology_template");
        Map<String, Object> nodeTemplates = (Map<String, Object>) (topologyTemplate).get("node_templates");
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

    public static List<Map<String, Object>> tosca2KubernetesDeployment(Map<String, Object> toscaPlan) {
        List<Map.Entry> dockerContainers = getDockerContainers(toscaPlan);
//        String containerName = "mysql";
//        String appName = containerName;

//        String image1 = "mysql:5.7";
//        String imageName1 = "mysql";
//        Map<String, Object> envMap1 = new HashMap();
//        envMap1.put("MYSQL_DATABASE", "wordpress");
//        envMap1.put("MYSQL_PASSWORD", "wordpress");
//        envMap1.put("MYSQL_ROOT_PASSWORD", "somewordpress");
//        envMap1.put("MYSQL_USER", "wordpress");
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
            Map<String, Object> template = new HashMap();
            template.put("metadata", metadata);

            Map<String, Object> topSpec = new HashMap();
            topSpec.put("replicas", 1);
            topSpec.put("template", template);

            Map<String, Object> dockerValues = (Map<String, Object>) docker.getValue();

            Map<String, Object> properties = (Map<String, Object>) dockerValues.get("properties");
            Map<String, Object> envMap = (Map<String, Object>) properties.get("environment");
            Map<String, Object> imageEnv = new HashMap();
            imageEnv.put("env", envMap);

            Map<String, Object> image = (Map<String, Object>) ((Map<String, Object>) dockerValues.get("artifacts")).get("image");
            String imageFile = (String) image.get("file");
            Map<String, Object> container = new HashMap();
            container.put("image", imageFile);
            container.put("name", name);
            container.put("env", imageEnv);

            List<String> toscaPortsList = (List<String>) properties.get("ports");
            if (toscaPortsList != null) {
                Map<String, Object> ports = new HashMap();
                System.err.println(toscaPortsList);
                for (String portEntry : toscaPortsList) {
                    String[] portsArray = portEntry.split(":");
                    Map<String, Object> portMap = new HashMap();
                    portMap.put("containerPort", portsArray[0]);
                    List<Map<String, Object>> kubernetesPortsList = new ArrayList<>();
                    kubernetesPortsList.add(portMap);
                }
            }

//            ports.put("ports", portsList);
            List<Map<String, Object>> containersList = new ArrayList<>();
            containersList.add(container);
            Map<String, Object> containers = new HashMap();
            containers.put("containers", containersList);

            Map<String, Object> spec1 = new HashMap();
            spec1.put("containers", containers);
//            spec1.put("ports", ports);
            topSpec.put("spec", spec1);

            Map<String, Object> deployment = new HashMap();
            deployment.put("spec", topSpec);
            deployment.put("metadata", metadata);
            deployment.put("kind", "Deployment");
            deployment.put("apiVersion", "extensions/v1beta1");

            try {
                System.err.println(Converter.map2YmlString(deployment));
            } catch (JSONException ex) {
                Logger.getLogger(TOSCAUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

            deployments.add(deployment);
        }

        return deployments;
    }
}
