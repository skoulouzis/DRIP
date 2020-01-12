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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.uva.sne.drip.model.NodeTemplate;
import nl.uva.sne.drip.model.NodeTemplateMap;
import nl.uva.sne.drip.model.tosca.Credential;
import nl.uva.sne.drip.model.tosca.ToscaTemplate;
import nl.uva.sne.drip.sure.tosca.client.DefaultApi;
import org.apache.commons.io.FileUtils;
import nl.uva.sne.drip.sure.tosca.client.ApiException;
import nl.uva.sne.drip.sure.tosca.client.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author S. Koulouzis
 */
public class ToscaHelper {

    private DefaultApi api;

    private ObjectMapper objectMapper;
    public static final String VM_CAPABILITY = "tosca.capabilities.ARTICONF.VM";
    private static final String VM_TYPE = "tosca.nodes.ARTICONF.VM.Compute";
    private static final String VM_NUM_OF_CORES = "num_cores";
    private static final String MEM_SIZE = "mem_size";
    private static final String VM_OS = "os";
    private static final String VM_TOPOLOGY = "tosca.nodes.ARTICONF.VM.topology";
    private Integer id;

    @Autowired
    public ToscaHelper(String sureToscaBasePath) {
        init(sureToscaBasePath);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    private void init(String sureToscaBasePath) {
        Configuration.getDefaultApiClient().setBasePath(sureToscaBasePath);
        Configuration.getDefaultApiClient().setConnectTimeout(1200000);
        api = new DefaultApi(Configuration.getDefaultApiClient());
        this.objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }

    public void uploadToscaTemplate(ToscaTemplate toscaTemplate) throws JsonProcessingException, IOException, ApiException {
        String ymlStr = objectMapper.writeValueAsString(toscaTemplate);
        File toscaTemplateFile = File.createTempFile("temp-toscaTemplate", ".yml");
        FileUtils.writeByteArrayToFile(toscaTemplateFile, ymlStr.getBytes());
        String resp = api.uploadToscaTemplate(toscaTemplateFile);
        id = Integer.valueOf(resp);
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

    public List<NodeTemplateMap> getVMTopologyTemplates() throws ApiException {
        List<NodeTemplateMap> vmTopologyTemplates = api.getNodeTemplates(String.valueOf(id), "tosca.nodes.ARTICONF.VM.topology", null, null, null, null, null, null, null);
        return vmTopologyTemplates;
    }

    public List<NodeTemplateMap> getTemplateVMsForVMTopology(NodeTemplateMap nodeTemplateMap) throws ApiException {
        NodeTemplate nodeTemplate = nodeTemplateMap.getNodeTemplate();
        List<Map<String, Object>> requirements = nodeTemplate.getRequirements();
        List<NodeTemplateMap> vms = new ArrayList<>();
        for (Map<String, Object> req : requirements) {
            String nodeName = req.keySet().iterator().next();
            Map<String, Object> node = (Map<String, Object>) req.get(nodeName);
            if (node.get("capability").equals(VM_CAPABILITY)) {
                String vmName = (String) node.get("node");
                List<NodeTemplateMap> vmNodeTemplates = api.getNodeTemplates(String.valueOf(id), null, vmName, null, null, null, null, null, null);
                vms.addAll(vmNodeTemplates);
            }
        }
        return vms;

    }

    public Double getVMNumOfCores(NodeTemplateMap vmMap) throws Exception {
        NodeTemplate vm = vmMap.getNodeTemplate();
        if (vm.getType().equals(VM_TYPE)) {
            return (Double) vm.getProperties().get(VM_NUM_OF_CORES);
        } else {
            throw new Exception("NodeTemplate is not of type: " + VM_TYPE + " it is of type: " + vm.getType());
        }
    }

    public Double getVMNMemSize(NodeTemplateMap vmMap) throws Exception {
        NodeTemplate vm = vmMap.getNodeTemplate();
        if (vm.getType().equals(VM_TYPE)) {
            String memScalar = (String) vm.getProperties().get(MEM_SIZE);
            String[] memScalarArray = memScalar.split(" ");
            String memSize = memScalarArray[0];
            String memUnit = memScalarArray[1];
            Double memSizeInGB = convertToGB(Integer.valueOf(memSize), memUnit);
            return memSizeInGB;
        } else {
            throw new Exception("NodeTemplate is not of type: " + VM_TYPE + " it is of type: " + vm.getType());
        }

    }

    public String getVMNOS(NodeTemplateMap vmMap) throws Exception {
        NodeTemplate vm = vmMap.getNodeTemplate();
        if (vm.getType().equals(VM_TYPE)) {
            return (String) vm.getProperties().get(VM_OS);
        } else {
            throw new Exception("NodeTemplate is not of type: " + VM_TYPE + " it is of type: " + vm.getType());
        }
    }

    private Double convertToGB(Integer value, String memUnit) {
        switch (memUnit) {
            case "GB":
                return Double.valueOf(value);
            case "MB":
                return value * 0.001;
            case "KB":
                return value * 0.000001;
            default:
                return null;
        }
    }

    public String getTopologyDomain(NodeTemplateMap nodeTemplateMap) throws Exception {
        NodeTemplate nodeTemplate = nodeTemplateMap.getNodeTemplate();
        if (nodeTemplate.getType().equals(VM_TOPOLOGY)) {
            return (String) nodeTemplate.getProperties().get("domain");
        } else {
            throw new Exception("NodeTemplateMap is not of type: " + VM_TOPOLOGY + " it is of type: " + nodeTemplate.getType());
        }
    }

    public String getTopologyProvider(NodeTemplateMap nodeTemplateMap) throws Exception {
        NodeTemplate nodeTemplate = nodeTemplateMap.getNodeTemplate();
        if (nodeTemplate.getType().equals(VM_TOPOLOGY)) {
            return (String) nodeTemplate.getProperties().get("provider");
        } else {
            throw new Exception("NodeTemplate is not of type: " + VM_TOPOLOGY + " it is of type: " + nodeTemplate.getType());
        }
    }

    public NodeTemplateMap setCredentialsInVMTopology(NodeTemplateMap vmTopologyMap, Credential credential) throws Exception {
        NodeTemplate vmTopology = vmTopologyMap.getNodeTemplate();
        if (vmTopology.getType().equals(VM_TOPOLOGY)) {
            Map<String, Object> att = vmTopology.getAttributes();
            if (att == null) {
                att = new HashMap<>();
            }
            att.put("credential", credential);
            vmTopology.setAttributes(att);
            vmTopologyMap.setNodeTemplate(vmTopology);
            return vmTopologyMap;
        } else {
            throw new Exception("NodeTemplate is not of type: " + VM_TOPOLOGY + " it is of type: " + vmTopology.getType());
        }
    }

    public Credential getCredentialsFromVMTopology(NodeTemplateMap vmTopologyMap) throws Exception {
        NodeTemplate vmTopology = vmTopologyMap.getNodeTemplate();
        if (vmTopology.getType().equals(VM_TOPOLOGY)) {
            Map<String, Object> att = vmTopology.getAttributes();
            String ymlStr = Converter.map2YmlString((Map<String, Object>) att.get("credential"));
            Credential toscaCredential = objectMapper.readValue(ymlStr, Credential.class);
            return toscaCredential;

        } else {
            throw new Exception("NodeTemplate is not of type: " + VM_TOPOLOGY + " it is of type: " + vmTopology.getType());
        }
    }

    public ToscaTemplate setVMTopologyInToscaTemplate(ToscaTemplate toscaTemplate, NodeTemplateMap vmTopologyMap) {
        Map<String, NodeTemplate> nodes = toscaTemplate.getTopologyTemplate().getNodeTemplates();
        nodes.put(vmTopologyMap.getName(), vmTopologyMap.getNodeTemplate());
        return toscaTemplate;
    }

//    public Map<String, Object> getProvisionInterface(Provisioner provisioner, String operation) throws ApiException {
//        List<String> toscaInterfaceTypes = new ArrayList<>();
//        toscaInterfaceTypes.add(provisioner.getToscaInterfaceType());
//        List<Map<String, Object>> definitions = getProvisionInterfaceDefinitions(toscaInterfaceTypes);
//        Map<String, Object> definition = getBestProvisionInterfaceDefinition(definitions);
//        Map<String, Object> provisionInterface = getProvisionInterfaceInstanceDefaultValues(definition, operation);
//        return provisionInterface;
//    }
    public Map<String, Object> getProvisionerInterfaceFromVMTopology(NodeTemplateMap vmTopologyMap) {
        return (Map<String, Object>) vmTopologyMap.getNodeTemplate().getInterfaces().get("CloudsStorm");
    }

    public NodeTemplateMap setProvisionerInterfaceInVMTopology(NodeTemplateMap vmTopologyMap, Map<String, Object> provisionerInterface) {
        System.err.println(provisionerInterface);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getVMTopologyUser() throws ApiException {
        List<NodeTemplateMap> vmTopologyTemplatesMap = getVMTopologyTemplates();
        for (NodeTemplateMap nodeTemplateMap : vmTopologyTemplatesMap) {
            List<NodeTemplateMap> vmTemplatesMap = getTemplateVMsForVMTopology(nodeTemplateMap);
            for (NodeTemplateMap vmMap : vmTemplatesMap) {
                Map<String, Object> prop = vmMap.getNodeTemplate().getProperties();
                if (prop != null && prop.containsKey("user_name")) {
                    return (String) prop.get("user_name");
                }
            }
        }
        return "vm_user";
    }
}
