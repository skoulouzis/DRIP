/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.api.NotFoundException;
import nl.uva.sne.drip.commons.utils.ToscaHelper;
import nl.uva.sne.drip.model.Exceptions.MissingCredentialsException;
import nl.uva.sne.drip.model.Exceptions.MissingVMTopologyException;
import nl.uva.sne.drip.model.Exceptions.TypeExeption;
import nl.uva.sne.drip.model.Message;
import nl.uva.sne.drip.model.NodeTemplateMap;
import nl.uva.sne.drip.model.cloud.storm.CloudsStormSubTopology;
import nl.uva.sne.drip.model.tosca.Credential;
import nl.uva.sne.drip.model.tosca.ToscaTemplate;
import nl.uva.sne.drip.rpc.DRIPCaller;
import nl.uva.sne.drip.sure.tosca.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class DRIPService {

    @Autowired
    private ToscaTemplateService toscaTemplateService;

    @Autowired
    DRIPCaller caller;

    @Autowired
    CredentialService credentialService;

    private String requestQeueName;

    @Autowired
    private ToscaHelper helper;

    @Autowired
    ProvisionerService provisionerService;

    enum PROVISIONER_OPERATION {
        PROVISION, DELETE, START, STOP, H_SCALE, V_SCALE, CONFIGURE
    }

    enum DELETE_ACTIONS {
        PROVISION, DEPLOYMENT
    }

    private String execute(ToscaTemplate toscaTemplate) throws JsonProcessingException, ApiException, IOException, TimeoutException, InterruptedException {

        caller.init();
        Logger.getLogger(DRIPService.class.getName()).log(Level.INFO, "toscaTemplate:\n{0}", toscaTemplate);
        Message message = new Message();
        message.setOwner("user");
        message.setCreationDate(System.currentTimeMillis());
        message.setToscaTemplate(toscaTemplate);

        caller.setRequestQeueName(requestQeueName);
        Message plannerResponse = caller.call(message);
        ToscaTemplate updatedToscaTemplate = plannerResponse.getToscaTemplate();
        caller.close();
        return toscaTemplateService.save(updatedToscaTemplate);

    }

    /**
     * @return the requestQeueName
     */
    public String getRequestQeueName() {
        return requestQeueName;
    }

    /**
     * @param requestQeueName the requestQeueName to set
     */
    public void setRequestQeueName(String requestQeueName) {
        this.requestQeueName = requestQeueName;
    }

    private Credential getBestCredential(List<Credential> credentials) {
        return credentials.get(0);
    }

    protected ToscaTemplate addCredentials(ToscaTemplate toscaTemplate) throws MissingCredentialsException, ApiException, TypeExeption, MissingVMTopologyException {
        List<NodeTemplateMap> vmTopologies = helper.getVMTopologyTemplates();
        if (vmTopologies == null) {
            throw new MissingVMTopologyException("ToscaTemplate: " + toscaTemplate + " has no VM topology");
        }
        List<Credential> credentials = null;
        for (NodeTemplateMap vmTopologyMap : vmTopologies) {
            String provider = helper.getTopologyProvider(vmTopologyMap);
            if (needsCredentials(provider)) {
                credentials = credentialService.findByProvider(provider);
                if (credentials == null || credentials.size() <= 0) {
                    throw new MissingCredentialsException("Provider: " + provider + " needs credentials but non clould be found");
                } else {
                    Credential credential = getBestCredential(credentials);
                    vmTopologyMap = helper.setCredentialsInVMTopology(vmTopologyMap, credential);
                    toscaTemplate = helper.setNodeInToscaTemplate(toscaTemplate, vmTopologyMap);
                }
            }

        }
        Logger.getLogger(ToscaHelper.class.getName()).log(Level.FINE, "Added credetials to ToscaTemplate");
        return toscaTemplate;
    }

    public String plan(String id) throws ApiException, NotFoundException, IOException, JsonProcessingException, TimeoutException, InterruptedException {
        ToscaTemplate toscaTemplate = initExecution(id);
        return execute(toscaTemplate);
    }

    public String provision(String id) throws MissingCredentialsException, ApiException, TypeExeption, IOException, JsonProcessingException, TimeoutException, InterruptedException, NotFoundException, MissingVMTopologyException {
        ToscaTemplate toscaTemplate = initExecution(id);
        toscaTemplate = addCredentials(toscaTemplate);
        toscaTemplate = setProvisionerOperation(toscaTemplate, PROVISIONER_OPERATION.PROVISION);
        return execute(toscaTemplate);
    }

    protected ToscaTemplate setProvisionerOperation(ToscaTemplate toscaTemplate, PROVISIONER_OPERATION operation) throws IOException, JsonProcessingException, ApiException {
        List<NodeTemplateMap> vmTopologies = helper.getVMTopologyTemplates();
        for (NodeTemplateMap vmTopologyMap : vmTopologies) {
            Map<String, Object> provisionerInterface = helper.getProvisionerInterfaceFromVMTopology(vmTopologyMap);
            if (provisionerInterface == null || !provisionerInterface.containsKey(operation.toString().toLowerCase())) {
                provisionerInterface = new HashMap<>();
                Map<String, Object> inputsMap = new HashMap<>();
                inputsMap.put("code_type", " SEQ");
                inputsMap.put("object_type", " SubTopology");
                Map<String, Object> provisionMap = new HashMap<>();
                provisionMap.put("inputs", inputsMap);
                provisionerInterface.put(operation.toString().toLowerCase(), provisionMap);
                vmTopologyMap = helper.setProvisionerInterfaceInVMTopology(vmTopologyMap, provisionerInterface);
                toscaTemplate = helper.setNodeInToscaTemplate(toscaTemplate, vmTopologyMap);
            }
        }
        return toscaTemplate;
    }

    private boolean needsCredentials(String provider) {
        switch (provider) {
            case "local":
                return false;
            default:
                return true;
        }
    }

    public String deploy(String id) throws JsonProcessingException, NotFoundException, IOException, ApiException, Exception {
        ToscaTemplate toscaTemplate = initExecution(id);
        return execute(toscaTemplate);
    }

    protected ToscaTemplate initExecution(String id) throws JsonProcessingException, NotFoundException, IOException, ApiException {
        String ymlToscaTemplate = toscaTemplateService.findByID(id);
        Logger.getLogger(DRIPService.class.getName()).log(Level.FINE, "Found ToscaTemplate with id: {0}", id);
        ToscaTemplate toscaTemplate = toscaTemplateService.getYaml2ToscaTemplate(ymlToscaTemplate);
        helper.uploadToscaTemplate(toscaTemplate);
        return toscaTemplate;
    }

    void deleteActions(ToscaTemplate toscaTemplate) throws ApiException, TypeExeption, IOException {
        helper.uploadToscaTemplate(toscaTemplate);
        List<NodeTemplateMap> vmTopologies = helper.getVMTopologyTemplates();
        if (vmTopologies != null) {
            for (NodeTemplateMap vmTopology : vmTopologies) {
                CloudsStormSubTopology.StatusEnum status = helper.getVMTopologyTemplateStatus(vmTopology);
                if (!status.equals(CloudsStormSubTopology.StatusEnum.DELETED)) {
                    Logger.getLogger(ToscaHelper.class.getName()).log(Level.FINE, "Deleting VMs from " + vmTopology);
                }

            }
        }

    }

}
