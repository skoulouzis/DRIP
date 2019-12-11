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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.uva.sne.drip.model.NodeTemplate;
import nl.uva.sne.drip.model.ToscaTemplate;
import nl.uva.sne.drip.sure_tosca.DefaultApi;
import org.apache.commons.io.FileUtils;
import nl.uva.sne.drip.sure_tosca.client.ApiException;
import nl.uva.sne.drip.sure_tosca.client.Configuration;

/**
 *
 * @author S. Koulouzis
 */
public class ToscaHelper {

    private final DefaultApi api;

    private final ObjectMapper objectMapper;
    public static final String VM_CAPABILITY = "tosca.capabilities.ARTICONF.VM";

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    private final Integer id;

    public ToscaHelper(ToscaTemplate toscaTemplate, String sureToscaBasePath) throws JsonProcessingException, IOException, ApiException {
        Configuration.getDefaultApiClient().setBasePath(sureToscaBasePath);
        api = new DefaultApi(Configuration.getDefaultApiClient());
        this.objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        id = uploadToscaTemplate(toscaTemplate);

    }

    private Integer uploadToscaTemplate(ToscaTemplate toscaTemplate) throws JsonProcessingException, IOException, ApiException {
        String ymlStr = objectMapper.writeValueAsString(toscaTemplate);
        File toscaTemplateFile = File.createTempFile("temp-toscaTemplate", ".yml");
        FileUtils.writeByteArrayToFile(toscaTemplateFile, ymlStr.getBytes());
        String resp = api.uploadToscaTemplate(toscaTemplateFile);
        return Integer.valueOf(resp);
    }

    public List<Map<String, Object>> getProvisionInterfaceDefinitions(List<String> toscaInterfaceTypes) throws ApiException {
        List<Map<String, Object>> interfaceDefinitions = new ArrayList<>();
        for (String type : toscaInterfaceTypes) {
            String derivedFrom = null;
            List<Map<String, Object>> interfaces = api.getTypes(String.valueOf(id), "interface_types", null, type, null, null, null, null, null, derivedFrom);
            interfaceDefinitions.addAll(interfaces);
        }

        return interfaceDefinitions;
    }

    public List<NodeTemplate> getVMTopologyTemplates() throws ApiException {
        List<NodeTemplate> vmTopologyTemplates = api.getNodeTemplates(String.valueOf(id), "tosca.nodes.ARTICONF.VM.topology", null, null, null, null, null, null, null);
        return vmTopologyTemplates;
    }

    public List<NodeTemplate> getTopologyTemplateVMs(NodeTemplate nodeTemplate) throws ApiException {
        List<Map<String, Object>> requirements = nodeTemplate.getRequirements();
        List<NodeTemplate> vms = new ArrayList<>();
        for (Map<String, Object> req : requirements) {
            String nodeName = req.keySet().iterator().next();
            Map<String, Object> node = (Map<String, Object>) req.get(nodeName);
            if (node.get("capability").equals(VM_CAPABILITY)) {
                String vmName = (String) node.get("node");
                List<NodeTemplate> vmNodeTemplates = api.getNodeTemplates(String.valueOf(id), null, vmName, null, null, null, null, null, null);
                vms.addAll(vmNodeTemplates);
            }
        }
        return vms;

    }

    public String getVMType(NodeTemplate vm, String provider) {
        
    }

}
