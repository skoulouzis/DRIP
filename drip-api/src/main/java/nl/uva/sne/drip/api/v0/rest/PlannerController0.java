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
package nl.uva.sne.drip.api.v0.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.drip.commons.data.v0.external.Result;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.PlannerService;
import nl.uva.sne.drip.api.service.ToscaService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.drip.commons.data.v0.external.Attribute;
import nl.uva.sne.drip.drip.commons.data.v0.external.Plan;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for planing the type of resources to be
 * provisopned based on a TOSCA description.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v0.0/switch/plan")
@Component
public class PlannerController0 {

    @Autowired
    private PlannerService plannerService;

    @Autowired
    private ToscaService toscaService;

    @RequestMapping(value = "/planning", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Result plan(@RequestBody Plan plan0) {
        try {
            String yaml = plan0.file;
            yaml = yaml.replaceAll("\\\\n", "\n");
            String id = toscaService.saveYamlString(yaml, null);
            nl.uva.sne.drip.drip.commons.data.v1.external.PlanResponse plan1 = plannerService.getPlan(id);
            Result r = new Result();
            r.info = ("INFO");
            r.status = ("Success");
            List<Attribute> files = new ArrayList<>();
            Attribute e = Converter.plan1toFile(plan1);
            files.add(e);
            for (String lowiID : plan1.getLoweLevelPlanIDs()) {
                nl.uva.sne.drip.drip.commons.data.v1.external.PlanResponse lowPlan1 = plannerService.findOne(lowiID);
                e = Converter.plan1toFile(lowPlan1);
                files.add(e);
                //Don't save them cause they will be re-uploaded in the provision step
                plannerService.delete(lowPlan1.getId());
            }
            r.file = files;
            plannerService.delete(plan1.getId());
            return r;
        } catch (IOException | JSONException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(PlannerController0.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
