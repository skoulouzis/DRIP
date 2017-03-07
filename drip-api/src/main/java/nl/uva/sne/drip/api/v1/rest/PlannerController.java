/*
 * Copyright 2017 S. Koulouzis, Wang Junchao, Huan Zhou, Yang Hu 
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
package nl.uva.sne.drip.api.v1.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.exception.NotFoundException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.PlannerService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.v1.types.Plan;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller is responsible for planing the type of resources to be provisopned 
 * based on a TOSCA description.
 * 
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/planner")
@Component
public class PlannerController {

//    @Autowired
//    private SimplePlannerService simplePlannerService;
    @Autowired
    private PlannerService plannerService;

    
    /**
     * Plans resources (number, size of VMs etc). 
     * @param toscaId. The id of the TOSCA description 
     * @return the id of the created plan
     */
    @RequestMapping(value = "/plan/{tosca_id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String plan(@PathVariable("tosca_id") String toscaId) {

        try {
//            Plan plan = simplePlannerService.getPlan(toscaId);
            Plan plan = plannerService.getPlan(toscaId);
            if (plan == null) {
                throw new NotFoundException("Could not make plan");
            }
            return plan.getId();
        } catch (JSONException | IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets a plan  
     * @param id. The id iof the plan
     * @param format. The format (yml,json)
     * @return the plan 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = {"format"})
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String get(@PathVariable("id") String id, @RequestParam(value = "format") String format) {
        try {
            return plannerService.get(id, format);
        } catch (JSONException ex) {
            Logger.getLogger(ToscaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets the ID of the TOSCA description that was used to generate the plan. 
     * The plan is represented by its ID
     * @param id. The plan id 
     * @return the TOSCA description ID
     */
    @RequestMapping(value = "/tosca/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String getToscaID(@PathVariable("id") String id) {
        return plannerService.getToscaID(id);
    }

    /**
     * Deletes the plan. 
     * @param id . The ID of the plan
     * @return The ID of the deleted plan
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        plannerService.getDao().delete(id);
        return "Deleted : " + id;
    }

    
    /**
     * Gets the IDs of all the stored plans 
     * @return a list of IDs 
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        List<Plan> all = plannerService.findAll();
        List<String> ids = new ArrayList<>();
        for (Plan tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

}
