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
package nl.uva.sne.drip.api.v1.rest;

import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import nl.uva.sne.drip.drip.commons.data.v1.external.ToscaRepresentation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
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
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.service.ConfigurationService;
import nl.uva.sne.drip.api.service.ToscaService;
import nl.uva.sne.drip.api.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for storing TOSCA descriptions that can be
 * used by the planner.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/tosca")
@Component
@StatusCodes({
    @ResponseCode(code = 401, condition = "Bad credentials")
})
public class ToscaController {

    @Autowired
    private ToscaService toscaService;

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String post(@RequestBody String toscaContents) {
        try {
            return toscaService.saveStringContents(toscaContents, String.valueOf(System.currentTimeMillis()));
        } catch (IOException ex) {
            Logger.getLogger(ToscaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Uploads and stores a TOSCA description file
     *
     * @param file. The TOSCA description file
     * @return the ID of the TOSCA description
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String toscaUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("Must uplaod a file");
        }
        try {
            return toscaService.saveFile(file);
        } catch (IOException | IllegalStateException ex) {
            Logger.getLogger(ToscaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets the TOSCA description.
     *
     * @param id the ID TOSCA description.
     * @param format. the format to display the TOSCA description.
     * @return the TOSCA description.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = {"format"})
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String get(@PathVariable("id") String id, @RequestParam(value = "format") String format) {
        try {
            return toscaService.get(id, format);
        } catch (JSONException ex) {
            Logger.getLogger(ToscaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Deletes the TOSCA description.
     *
     * @param id. The ID of TOSCA description to delete.
     * @return The ID of the deleted TOSCA description.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        toscaService.delete(id);
        return "Deleted : " + id;
    }

    /**
     * Gets the IDs of all the stored TOSCA descriptions.
     *
     * @return a list of all the IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        List<ToscaRepresentation> all = toscaService.findAll();
        List<String> ids = new ArrayList<>();
        for (ToscaRepresentation tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

    /**
     * Transforms the TOSCA description to docker compose
     *
     * @param id the ID TOSCA description.
     * @param type. Transform it to a specific type e.g. "docker_compose"
     * @return the docker-compose ID.
     */
    @RequestMapping(value = "/transform/{id}", method = RequestMethod.GET, params = {"type"})
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String transform(@PathVariable("id") String id, @RequestParam(value = "type") String type) {
        try {
            String ymlContents = toscaService.transform(id, type);
            return configurationService.saveStringContents(ymlContents);
        } catch (JSONException | IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(ToscaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets the IDs of all the stored TOSCA descriptions.
     *
     * @return a list of all the IDs
     */
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String deleteAll() {
        toscaService.deleteAll();
        return "Done";
    }

}
