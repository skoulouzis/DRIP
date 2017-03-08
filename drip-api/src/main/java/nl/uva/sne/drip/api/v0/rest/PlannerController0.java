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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.commons.v0.types.Result;
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
import nl.uva.sne.drip.commons.v0.types.File;
import nl.uva.sne.drip.commons.v0.types.Plan;
import org.apache.commons.io.FilenameUtils;
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

    @RequestMapping(value = "/planning", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Result plan(@RequestBody Plan plan0) {

        try {
            String yaml = plan0.getFile();
            yaml = yaml.replaceAll("\\\\n", "\n");
            String id = toscaService.save(yaml, null);
            nl.uva.sne.drip.commons.v1.types.Plan plan1 = plannerService.getPlan(id);

            Result r = new Result();
            r.setInfo("INFO");
            r.setStatus(Result.status.Success);
            List<File> files = new ArrayList<>();
            File e = new File();
            e.level = String.valueOf(plan1.getLevel());
            String p1Name = FilenameUtils.getBaseName(plan1.getName());
            if (p1Name == null) {
                p1Name = "Planned_tosca_file_" + plan1.getLevel();
                plan1.setName(p1Name);
                plannerService.getDao().save(plan1);
            }

            e.name = p1Name;
            e.content = Converter.map2YmlString(plan1.getKvMap()).replaceAll("\n", "\\n");
            files.add(e);

            for (String lowiID : plan1.getLoweLevelPlanIDs()) {
                nl.uva.sne.drip.commons.v1.types.Plan lowPlan1 = plannerService.getDao().findOne(lowiID);
                e = new File();
                e.level = String.valueOf(lowPlan1.getLevel());
                p1Name = lowPlan1.getName();
                if (p1Name == null) {
                    p1Name = "Planned_tosca_file_" + lowPlan1.getLevel();
                    plan1.setName(p1Name);
                    plannerService.getDao().save(lowPlan1);
                }

                e.name = p1Name;
                e.content = Converter.map2YmlString(lowPlan1.getKvMap()).replaceAll("\n", "\\n");;
                files.add(e);
            }

            r.file = files;

            return r;
        } catch (IOException | JSONException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(PlannerController0.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Result plan() {
        Result r = new Result();
        r.setInfo("INFO");
        r.setStatus(Result.status.Success);
        List<File> files = new ArrayList<>();
        File e = new File();
        e.level = "0";
        e.name = "Planned_tosca_file_a.yaml";
        e.content = "$Planned_tosca_file_a";
        files.add(e);
        r.file = files;
        return r;

    }

}
