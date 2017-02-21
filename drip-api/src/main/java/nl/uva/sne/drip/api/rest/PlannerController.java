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

import nl.uva.sne.drip.commons.types.ToscaRepresentation;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.rpc.PlannerCaller;
import nl.uva.sne.drip.commons.types.Message;
import nl.uva.sne.drip.commons.types.Parameter;
import nl.uva.sne.drip.commons.utils.Converter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
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

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    @Autowired
    private ToscaService toscaService;

    @RequestMapping(value = "/plan/{tosca_id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String plan(@PathVariable("tosca_id") String toscaId) {
        DRIPCaller planner;
        List<DRIPCaller> dripComponetens = new ArrayList<>();
        try {

            Message plannerInvokationMessage = buildPlannerMessage(toscaId);

            planner = new PlannerCaller(messageBrokerHost);
            dripComponetens.add(planner);
            Message plannerReturnedMessage = planner.call(plannerInvokationMessage);
            List<Parameter> toscaFiles = plannerReturnedMessage.getParameters();
            ToscaRepresentation topLevel = new ToscaRepresentation();
            ToscaRepresentation tr = null;
            for (Parameter p : toscaFiles) {
                //Should have levels in attributes
                Map<String, String> attributess = p.getAttributes();
                String originalFileName = p.getName();
                String name = System.currentTimeMillis() + "_" + originalFileName;
                if (originalFileName.equals("planner_output_all.yml")) {
                    topLevel.setName(name);
                    topLevel.setLevel(0);
                    topLevel.setKvMap(Converter.ymlString2Map(p.getValue()));
                } else {
                    tr = new ToscaRepresentation();
                    tr.setName(name);
                    tr.setKvMap(Converter.ymlString2Map(p.getValue()));
                    tr.setLevel(1);
                }
                tr.setType(ToscaRepresentation.Type.PLAN);
                toscaService.getDao().save(tr);
                Set<String> ids = topLevel.getLowerLevelIDs();
                if (ids == null) {
                    ids = new HashSet<>();
                }
                ids.add(tr.getId());
                topLevel.setLowerLevelIDs(ids);
            }
            topLevel.setType(ToscaRepresentation.Type.PLAN);
            toscaService.getDao().save(topLevel);

            return topLevel.getId();
        } catch (JSONException | IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            for (DRIPCaller drip : dripComponetens) {
                if (drip != null) {
                    try {
                        drip.close();
                    } catch (IOException | TimeoutException ex) {
                        Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
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

    private Message buildPlannerMessage(String toscaId) throws JSONException, UnsupportedEncodingException, IOException {
        ToscaRepresentation t2 = toscaService.getDao().findOne(toscaId);
        Map<String, Object> map = t2.getKvMap();
        String ymlStr = Converter.map2YmlString(map);
        ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
        byte[] bytes = ymlStr.getBytes();

        Message invokationMessage = new Message();
        List parameters = new ArrayList();
        Parameter fileArgument = new Parameter();

        String charset = "UTF-8";
        fileArgument.setValue(new String(bytes, charset));
        fileArgument.setEncoding(charset);
        fileArgument.setName("input");
        parameters.add(fileArgument);

        fileArgument = new Parameter();
        bytes = Files.readAllBytes(Paths.get(System.getProperty("user.home") + File.separator + "Downloads/DRIP/example_a.yml"));
        fileArgument.setValue(new String(bytes, charset));
        fileArgument.setEncoding(charset);
        fileArgument.setName("example");
        parameters.add(fileArgument);

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }
}
