/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.provisioner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.commons.utils.ToscaHelper;
import nl.uva.sne.drip.model.cloud.storm.CloudsStormVM;
import nl.uva.sne.drip.model.NodeTemplateMap;
import nl.uva.sne.drip.model.cloud.storm.CloudCred;
import nl.uva.sne.drip.model.cloud.storm.CloudCredentialDB;
import nl.uva.sne.drip.model.cloud.storm.CloudDB;
import nl.uva.sne.drip.model.cloud.storm.CloudsStormInfrasCode;
import nl.uva.sne.drip.model.cloud.storm.CloudsStormSubTopology;
import nl.uva.sne.drip.model.cloud.storm.CloudsStormTopTopology;
import nl.uva.sne.drip.model.cloud.storm.CloudsStormVMs;
import nl.uva.sne.drip.model.cloud.storm.CredentialInfo;
import nl.uva.sne.drip.model.cloud.storm.InfrasCode;
import nl.uva.sne.drip.model.cloud.storm.OpCode;
import nl.uva.sne.drip.model.cloud.storm.VMMetaInfo;
import nl.uva.sne.drip.model.tosca.Credential;
import nl.uva.sne.drip.model.tosca.ToscaTemplate;
import nl.uva.sne.drip.sure.tosca.client.ApiException;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author S. Koulouzis
 */
class CloudStormService {

    private List<Map.Entry> vmTopologies;
//    private String tempInputDirPath;
    private final ToscaTemplate toscaTemplate;
    private final ToscaHelper helper;
    private final CloudStormDAO cloudStormDAO;
    private final ObjectMapper objectMapper;

    CloudStormService(Properties properties, ToscaTemplate toscaTemplate) throws IOException, JsonProcessingException, ApiException {
        this.toscaTemplate = toscaTemplate;
        String cloudStormDBPath = properties.getProperty("cloud.storm.db.path");
        cloudStormDAO = new CloudStormDAO(cloudStormDBPath);
        String sureToscaBasePath = properties.getProperty("sure-tosca.base.path");
        this.helper = new ToscaHelper(sureToscaBasePath);
        this.helper.uploadToscaTemplate(toscaTemplate);
        this.objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public ToscaTemplate execute() throws FileNotFoundException, JSchException, IOException, ApiException, Exception {

        String tempInputDirPath = System.getProperty("java.io.tmpdir") + File.separator + "Input-" + Long.toString(System.nanoTime()) + File.separator;
        File tempInputDir = new File(tempInputDirPath);
        if (!(tempInputDir.mkdirs())) {
            throw new FileNotFoundException("Could not create input directory: " + tempInputDir.getAbsolutePath());
        }
        String topologyTempInputDirPath = tempInputDirPath + File.separator + "topology";
        File topologyTempInputDir = new File(topologyTempInputDirPath);
        if (!(topologyTempInputDir.mkdirs())) {
            throw new FileNotFoundException("Could not create input directory: " + topologyTempInputDir.getAbsolutePath());
        }
        Map<String, Object> subTopologiesAndVMs = writeCloudStormTopologyFiles(topologyTempInputDirPath);

        String credentialsTempInputDirPath = tempInputDirPath + File.separator + "credentials";
        File credentialsTempInputDir = new File(credentialsTempInputDirPath);
        if (!(credentialsTempInputDir.mkdirs())) {
            throw new FileNotFoundException("Could not create input directory: " + topologyTempInputDir.getAbsolutePath());
        }
        writeCloudStormCredentialsFiles(credentialsTempInputDirPath);
        String infrasCodeTempInputDirPath = tempInputDirPath + File.separator + "infrasCodes";
        File infrasCodeTempInputDir = new File(infrasCodeTempInputDirPath);
        if (!(infrasCodeTempInputDir.mkdirs())) {
            throw new FileNotFoundException("Could not create input directory: " + topologyTempInputDir.getAbsolutePath());
        }
        List<CloudsStormSubTopology> cloudStormSubtopologies = (List<CloudsStormSubTopology>) subTopologiesAndVMs.get("cloud_storm_subtopologies");
        writeCloudStormInfrasCodeFiles(infrasCodeTempInputDirPath, cloudStormSubtopologies);
        return toscaTemplate;
    }

    private Map<String, Object> writeCloudStormTopologyFiles(String tempInputDirPath) throws JSchException, IOException, ApiException, Exception {
        CloudsStormTopTopology topTopology = new CloudsStormTopTopology();
        String publicKeyPath = buildSSHKeyPair(tempInputDirPath);
        topTopology.setPublicKeyPath(publicKeyPath);
        topTopology.setUserName(getUserName());

        Map<String, Object> subTopologiesAndVMs = getCloudsStormSubTopologiesAndVMs(tempInputDirPath);
        List<CloudsStormSubTopology> cloudsStormSubTopology = (List<CloudsStormSubTopology>) subTopologiesAndVMs.get("cloud_storm_subtopologies");
        topTopology.setTopologies(cloudsStormSubTopology);

        objectMapper.writeValue(new File(tempInputDirPath + File.separator + "top.yml"), topTopology);

        return subTopologiesAndVMs;
    }

    private String buildSSHKeyPair(String tempInputDirPath) throws JSchException, IOException {
        String userPublicKeyName = "id_rsa.pub";
        String publicKeyPath = "name@" + userPublicKeyName;
        JSch jsch = new JSch();
        KeyPair kpair = KeyPair.genKeyPair(jsch, KeyPair.RSA);
        String userPrivateName = FilenameUtils.removeExtension(userPublicKeyName);
        kpair.writePrivateKey(tempInputDirPath + File.separator + userPrivateName);
        kpair.writePublicKey(tempInputDirPath + File.separator + userPublicKeyName, "auto generated user accees keys");
        kpair.dispose();
        return publicKeyPath;
    }

    private String getUserName() {
        return "vm_user";
    }

    private Map<String, Object> getCloudsStormSubTopologiesAndVMs(String tempInputDirPath) throws ApiException, IOException, Exception {
        List<NodeTemplateMap> vmTopologyTemplatesMap = helper.getVMTopologyTemplates();
        List<CloudsStormSubTopology> cloudsStormSubTopologies = new ArrayList<>();
        Map<String, Object> cloudsStormMap = new HashMap<>();
        List<CloudsStormVMs> cloudsStormVMsList = new ArrayList<>();
        int i = 0;
        for (NodeTemplateMap nodeTemplateMap : vmTopologyTemplatesMap) {
            CloudsStormSubTopology cloudsStormSubTopology = new CloudsStormSubTopology();
            String domain = helper.getTopologyDomain(nodeTemplateMap);
            String provider = helper.getTopologyProvider(nodeTemplateMap);
            cloudsStormSubTopology.setDomain(domain);
            cloudsStormSubTopology.setCloudProvider(provider);
            cloudsStormSubTopology.setTopology("vm_topology" + i);
            cloudsStormSubTopology.setStatus(CloudsStormSubTopology.StatusEnum.FRESH);
            CloudsStormVMs cloudsStormVMs = new CloudsStormVMs();

            List<CloudsStormVM> vms = new ArrayList<>();
            List<NodeTemplateMap> vmTemplatesMap = helper.getTemplateVMsForVMTopology(nodeTemplateMap);
            int j = 0;
            for (NodeTemplateMap vmMap : vmTemplatesMap) {
                CloudsStormVM cloudsStormVM = new CloudsStormVM();
                CloudDB.CloudProviderEnum cloudProviderEnum = CloudDB.CloudProviderEnum.valueOf(provider);
                String vmType = getVMType(vmMap, cloudProviderEnum);
                cloudsStormVM.setNodeType(vmType);
                cloudsStormVM.setName("vm" + j);
                String os = helper.getVMNOS(vmMap);
                cloudsStormVM.setOsType(os);
                vms.add(cloudsStormVM);
                j++;
            }
            cloudsStormVMs.setVms(vms);
            objectMapper.writeValue(new File(tempInputDirPath + File.separator + "vm_topology" + i + ".yml"), cloudsStormVMs);
            cloudsStormVMsList.add(cloudsStormVMs);
            cloudsStormSubTopologies.add(cloudsStormSubTopology);
            i++;
        }
        cloudsStormMap.put("cloud_storm_vm", cloudsStormVMsList);
        cloudsStormMap.put("cloud_storm_subtopologies", cloudsStormSubTopologies);
        return cloudsStormMap;
    }

    private String getVMType(NodeTemplateMap vmMap, CloudDB.CloudProviderEnum provider) throws IOException, Exception {
        Double numOfCores = helper.getVMNumOfCores(vmMap);
        Double memSize = helper.getVMNMemSize(vmMap);
        String os = helper.getVMNOS(vmMap);
        List<VMMetaInfo> vmInfos = cloudStormDAO.findVmMetaInfoByProvider(provider);
        for (VMMetaInfo vmInfo : vmInfos) {
            Logger.getLogger(CloudStormService.class.getName()).log(Level.INFO, "vmInfo: {0}", vmInfo);
            Logger.getLogger(CloudStormService.class.getName()).log(Level.INFO, "numOfCores:{0} memSize: {1} os: {2}", new Object[]{numOfCores, memSize, os});
            if (Objects.equals(numOfCores, Double.valueOf(vmInfo.getCPU())) && Objects.equals(memSize, Double.valueOf(vmInfo.getMEM())) && os.toLowerCase().equals(vmInfo.getOS().toLowerCase())) {
                return vmInfo.getVmType();
            }
        }
        return null;
    }

    private void writeCloudStormCredentialsFiles(String credentialsTempInputDirPath) throws ApiException, Exception {
        List<NodeTemplateMap> vmTopologiesMaps = helper.getVMTopologyTemplates();
        List<CloudCred> cloudStormCredentialList = new ArrayList<>();
        int i = 0;
        for (NodeTemplateMap vmTopologyMap : vmTopologiesMaps) {
            Credential toscaCredentials = helper.getCredentialsFromVMTopology(vmTopologyMap);

            CloudCred cloudStormCredential = new CloudCred();
            cloudStormCredential.setCloudProvider(toscaCredentials.getCloudProviderName());
            String credInfoFile = toscaCredentials.getCloudProviderName() + i + ".yml";
            cloudStormCredential.setCredInfoFile(credInfoFile);
            cloudStormCredentialList.add(cloudStormCredential);

            CredentialInfo cloudStormCredentialInfo = getCloudStormCredentialInfo(toscaCredentials, credentialsTempInputDirPath);
            objectMapper.writeValue(new File(credentialsTempInputDirPath + File.separator + toscaCredentials.getCloudProviderName() + i + ".yml"), cloudStormCredentialInfo);
            i++;
        }
        CloudCredentialDB cloudStormCredentials = new CloudCredentialDB();
        cloudStormCredentials.setCloudCreds(cloudStormCredentialList);
        objectMapper.writeValue(new File(credentialsTempInputDirPath + File.separator + "CloudStormCredentials.yml"), cloudStormCredentials);
    }

    private CredentialInfo getCloudStormCredentialInfo(Credential toscaCredentials, String tmpPath) throws FileNotFoundException {
        CredentialInfo cloudStormCredentialInfo = new CredentialInfo();
        switch (toscaCredentials.getCloudProviderName().toLowerCase()) {
            case "exogeni":

                String base64Keystore = toscaCredentials.getKeys().get("keystore");
                byte[] decoded = Base64.getDecoder().decode(base64Keystore);
                try (PrintWriter out = new PrintWriter(tmpPath + File.separator + "user.jks")) {
                    out.println(new String(decoded));
                }
                cloudStormCredentialInfo.setUserKeyName("user.jks");
                cloudStormCredentialInfo.setKeyAlias(toscaCredentials.getUser());
                cloudStormCredentialInfo.setKeyPassword(toscaCredentials.getToken());
                return cloudStormCredentialInfo;
            case "ec2":
//                cloudStormCredentialInfo.setAccessKey(toscaCredentials.get);
                return cloudStormCredentialInfo;

        }
        return null;
    }

    private void writeCloudStormInfrasCodeFiles(String infrasCodeTempInputDirPath, List<CloudsStormSubTopology> cloudStormSubtopologies) throws ApiException, IOException {
        List<NodeTemplateMap> vmTopologiesMaps = helper.getVMTopologyTemplates();
        int i = 0;
        List<InfrasCode> infrasCodes = new ArrayList<>();
        for (NodeTemplateMap vmTopologyMap : vmTopologiesMaps) {
            Map<String, Object> provisionInterface = helper.getProvisionerInterfaceFromVMTopology(vmTopologyMap);
            String operation = provisionInterface.keySet().iterator().next();
            Map<String, Object> inputs = (Map<String, Object>) provisionInterface.get(operation);
            inputs.put("object_type", cloudStormSubtopologies.get(i).getTopology());
            OpCode opCode = new OpCode();
            opCode.setLog(Boolean.FALSE);
            opCode.setObjectType(OpCode.ObjectTypeEnum.SUBTOPOLOGY);
            opCode.setObjects(cloudStormSubtopologies.get(i).getTopology());
            InfrasCode infrasCode = new InfrasCode();
            infrasCode.setCodeType(InfrasCode.CodeTypeEnum.SEQ);
            infrasCode.setOpCode(opCode);
        }
        CloudsStormInfrasCode cloudsStormInfrasCode = new CloudsStormInfrasCode();
        cloudsStormInfrasCode.setMode(CloudsStormInfrasCode.ModeEnum.LOCAL);
        cloudsStormInfrasCode.setInfrasCodes(infrasCodes);

        objectMapper.writeValue(new File(infrasCodeTempInputDirPath + File.separator + "infrasCode.yml"), cloudsStormInfrasCode);
    }

}
