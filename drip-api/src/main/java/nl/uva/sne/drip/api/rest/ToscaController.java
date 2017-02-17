/*
 * Copyright 2017 S. Koulouzis.
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
import nl.uva.sne.drip.api.rpc.PlannerCaller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.commons.utils.Converter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import nl.uva.sne.drip.api.dao.ToscaDao;
import nl.uva.sne.drip.api.service.UserService;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/tosca")
@Component
public class ToscaController {

//    @Value("${message.broker.host}")
//    private String messageBrokerHost;
    @Autowired
    private ToscaDao dao;

//    curl -X POST -F "file=@DRIP/input.yaml" localhost:8080/drip-api/upload
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String toscaUpload(@RequestParam("file") MultipartFile file) {
        PlannerCaller planner = null;
        if (!file.isEmpty()) {
            try {

                String originalFileName = file.getOriginalFilename();
                String name = System.currentTimeMillis() + "_" + originalFileName;
                byte[] bytes = file.getBytes();
                String str = new String(bytes, "UTF-8");
                str = str.replaceAll("\\.", "\uff0E");

                Map<String, Object> map = Converter.ymlString2Map(str);
                ToscaRepresentation t = new ToscaRepresentation();
                t.setName(name);
                t.setKvMap(map);
                dao.save(t);

                return t.getId();
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(ToscaController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (planner != null) {
                    try {
                        planner.close();
                    } catch (IOException | TimeoutException ex) {
                        Logger.getLogger(ToscaController.class.getName()).log(Level.WARNING, null, ex);
                    }
                }
            }
        }
        return null;
    }

//    curl http://localhost:8080/drip-api/tosca/589e1160d9925f9dc127e882/?fromat=yaml
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = {"fromat"})
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String get(@PathVariable("id") String id, @RequestParam(value = "fromat") String fromat) {
        try {
            Map<String, Object> map = dao.findOne(id).getKvMap();
            if (fromat != null && fromat.equals("yml")) {
                String ymlStr = Converter.map2YmlString(map);
                ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
                return ymlStr;
            }
            if (fromat != null && fromat.equals("json")) {
                String jsonStr = Converter.map2JsonString(map);
                jsonStr = jsonStr.replaceAll("\\uff0E", "\\.");
                return jsonStr;
            }
            String ymlStr = Converter.map2YmlString(map);
            ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
            return ymlStr;
        } catch (JSONException ex) {
            Logger.getLogger(ToscaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    ToscaRepresentation getToscaRepresentation(@PathVariable("id") String id) {

        ToscaRepresentation tosca = dao.findOne(id);

        return tosca;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        dao.delete(id);
        return "Deleted tosca :" + id;
    }

//    http://localhost:8080/drip-api/tosca/ids
    @RequestMapping(value = "/ids")
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        List<ToscaRepresentation> all = dao.findAll();
        List<String> ids = new ArrayList<>();
        for (ToscaRepresentation tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

}
