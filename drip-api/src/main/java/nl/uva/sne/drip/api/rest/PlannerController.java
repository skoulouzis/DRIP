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
package nl.uva.sne.drip.api.rest;

import nl.uva.sne.drip.api.service.SimplePlannerService;
import nl.uva.sne.drip.commons.types.ToscaRepresentation;
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
import nl.uva.sne.drip.api.service.ToscaService;
import nl.uva.sne.drip.api.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/planner")
@Component
public class PlannerController {

    @Autowired
    private ToscaService toscaService;

    @Autowired
    private SimplePlannerService simplePlannerService;

    @Autowired
    private PlannerService plannerService;

    @RequestMapping(value = "/plan/{tosca_id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String plan(@PathVariable("tosca_id") String toscaId) {

        try {
//            ToscaRepresentation plan = simplePlannerService.getPlan(toscaId);
//            return plan.getId();
            ToscaRepresentation plan = plannerService.getPlan(toscaId);
            if (plan == null) {
                throw new NotFoundException("Could not make plan");
            }
            return plan.getId();
        } catch (JSONException | IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = {"format"})
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String get(@PathVariable("id") String id, @RequestParam(value = "format") String format) {
        try {
            return toscaService.get(id, format, ToscaRepresentation.Type.PLAN);
        } catch (JSONException ex) {
            Logger.getLogger(ToscaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        toscaService.delete(id, ToscaRepresentation.Type.PLAN);
        return "Deleted tosca :" + id;
    }

//    http://localhost:8080/drip-api/tosca/ids
    @RequestMapping(value = "/ids")
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        List<ToscaRepresentation> all = toscaService.findAll(ToscaRepresentation.Type.PLAN);
        List<String> ids = new ArrayList<>();
        for (ToscaRepresentation tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

}
