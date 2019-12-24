/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.commons.utils.ToscaHelper;
import nl.uva.sne.drip.model.Message;
import nl.uva.sne.drip.model.NodeTemplate;
import nl.uva.sne.drip.model.NodeTemplateMap;
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

    public String execute(String id) throws JsonProcessingException, ApiException, Exception {

        try {
            caller.init();
            String ymlToscaTemplate = toscaTemplateService.findByID(id);
            ToscaTemplate toscaTemplate = toscaTemplateService.getYaml2ToscaTemplate(ymlToscaTemplate);
            if (requestQeueName.equals("provisioner")) {
                toscaTemplate = addCredentials(toscaTemplate);
            }
            Logger.getLogger(DRIPService.class.getName()).log(Level.INFO, "toscaTemplate:\n" + toscaTemplate);
            Message message = new Message();
            message.setOwner("user");
            message.setCreationDate(System.currentTimeMillis());
            message.setToscaTemplate(toscaTemplate);

            caller.setRequestQeueName(requestQeueName);
            Message plannerResponse = caller.call(message);
            ToscaTemplate plannedToscaTemplate = plannerResponse.getToscaTemplate();

            return toscaTemplateService.save(plannedToscaTemplate);
        } catch (IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(DRIPService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                caller.close();
            } catch (IOException | TimeoutException ex) {
                Logger.getLogger(DRIPService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
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

    private Credential getBestCredential(NodeTemplate vmTopology, List<Credential> credentials) {
        return credentials.get(0);
    }

    private ToscaTemplate addCredentials(ToscaTemplate toscaTemplate) throws IOException, JsonProcessingException, ApiException, Exception {
        helper.uploadToscaTemplate(toscaTemplate);
        List<NodeTemplateMap> vmTopologies = helper.getVMTopologyTemplates();
        List<Credential> credentials = null;
        for (NodeTemplateMap vmTopologyMap : vmTopologies) {
            String provider = helper.getTopologyProvider(vmTopologyMap);
            credentials = credentialService.findByProvider(provider.toLowerCase());
            if (credentials != null && credentials.size() > 0) {
                Credential credential = getBestCredential(vmTopologyMap.getNodeTemplate(), credentials);
                vmTopologyMap = helper.setCredentialsInVMTopology(vmTopologyMap, credential);
                toscaTemplate = helper.setVMTopologyInToscaTemplate(toscaTemplate, vmTopologyMap);
            }
        }
        return toscaTemplate;
    }

}
