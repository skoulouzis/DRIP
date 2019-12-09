/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.model.Message;
import nl.uva.sne.drip.model.ToscaTemplate;
import nl.uva.sne.drip.rpc.DRIPCaller;
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

    private String requestQeueName;

    public String execute(String id) {

        try {
            caller.init();
            String ymlToscaTemplate = toscaTemplateService.findByID(id);
            ToscaTemplate toscaTemplate = toscaTemplateService.getYaml2ToscaTemplate(ymlToscaTemplate);
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

}
