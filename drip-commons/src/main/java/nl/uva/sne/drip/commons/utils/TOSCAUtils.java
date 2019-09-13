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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author S. Koulouzis
 */
public class TOSCAUtils {

    public static List<Map<String, Object>> getVMsFromTopology(Map<String, Object> toscaPlan) {
        List<String> vmNames = getVMsNodeNamesFromTopology(toscaPlan);
        Map<String, Object> topologyTemplate = (Map<String, Object>) toscaPlan.get("topology_template");
        List<Map<String, Object>> vmList = new ArrayList<>();
        for (String vmName : vmNames) {
            Map<String, Object> vm = (Map<String, Object>) topologyTemplate.get(vmName);
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

    public static Map<String, Object> buildTOSCAOutput(String nodeName, String value) {
        Map<String, Object> output = new HashMap();
        Map<String, Object> outputValue = new HashMap();
        List<String> att = new ArrayList<>();
        att.add(nodeName);
        att.add(value);
        outputValue.put("get_attribute", att);
        output.put("value", outputValue);
        return output;
    }
}
