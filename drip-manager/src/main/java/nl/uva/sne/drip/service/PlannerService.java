/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.model.Message;
import nl.uva.sne.drip.model.MessageParameter;
import nl.uva.sne.drip.model.ToscaTemplate;
import nl.uva.sne.drip.rpc.DRIPCaller;
import nl.uva.sne.drip.rpc.PlannerCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class PlannerService {

    @Autowired
    private ToscaTemplateService toscaTemplateService;

    @Autowired
    PlannerCaller plannerCaller;

    public String plan(String id) {

        try {
            String ymlToscaTemplate = toscaTemplateService.findByID(id);
            ToscaTemplate toscaTemplate = toscaTemplateService.getYaml2ToscaTemplate(ymlToscaTemplate);

            Message message = new Message();
            message.setCreationDate(System.currentTimeMillis());
            message.setToscaTemplate(toscaTemplate);

            Message plannerResponse = plannerCaller.call(message);
            ToscaTemplate plannedToscaTemplate = plannerResponse.getToscaTemplate();
            return toscaTemplateService.save(plannedToscaTemplate);
        } catch (IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                plannerCaller.close();
            } catch (IOException | TimeoutException ex) {
                Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
