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
import nl.uva.sne.drip.drip.commons.data.v1.external.ConfigurationRepresentation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.service.ConfigurationService;
import nl.uva.sne.drip.api.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for storing PlayBook descriptions that can be
 * used by the planner.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/deployer/configuration")
@Component
@StatusCodes({
    @ResponseCode(code = 401, condition = "Bad credentials")
})
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    /**
     * Post a deployment configuration.
     *
     * @param yamlContents
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful post"),
        @ResponseCode(code = 400, condition = "contents are not valid (e.g. not a yaml format)")
    })
    public @ResponseBody
    String post(@RequestBody String yamlContents) {
        try {
            return configurationService.saveStringContents(yamlContents);
        } catch (IOException ex) {
            throw new BadRequestException("Not valid contents");
        }

    }

    /**
     * Uploads and stores a PlayBook description file
     *
     * @param file. The PlayBook description file
     * @return the ID of the PlayBook description
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful upload"),
        @ResponseCode(code = 400, condition = "Didn't upload (multipart) file or contents are not valid (e.g. not a yaml format)")
    })
    public @ResponseBody
    String toscaUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("Must uplaod a file");
        }
        try {
            return configurationService.saveFile(file);
        } catch (IOException | IllegalStateException ex) {
            throw new BadRequestException("Not valid contents");
        }
    }

    /**
     * Gets the PlayBook description.
     *
     * @param id the ID PlayBook description.
     * @param format. the format to display the PlayBook description.
     * @return the PlayBook description.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = {"format"})
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Object found"),
        @ResponseCode(code = 400, condition = "contents are not valid (e.g. not a yaml format)"),
        @ResponseCode(code = 404, condition = "Object not found")
    })
    public @ResponseBody
    String get(@PathVariable("id") String id, @RequestParam(value = "format") String format) {
        try {
            return configurationService.get(id, format);
        } catch (JSONException | NotFoundException ex) {
            throw new BadRequestException("Not valid contents");
        }
    }

    /**
     * Deletes the PlayBook description.
     *
     * @param id. The ID of PlayBook description to delete.
     * @return The ID of the deleted PlayBook description.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful delete"),
        @ResponseCode(code = 404, condition = "Object not found")
    })
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        try {
            configurationService.delete(id);
        } catch (NotFoundException ex) {
            throw ex;
        }
        return "Deleted : " + id;
    }

    /**
     * Gets the IDs of all the stored PlayBook descriptions.
     *
     * @return a list of all the IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful query")
    })
    public @ResponseBody
    List<String> getIds() {
        List<ConfigurationRepresentation> all = configurationService.findAll();
        List<String> ids = new ArrayList<>();
        for (ConfigurationRepresentation tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

    /**
     * Deletes all entries. Use with caution !
     *
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful delete")
    })
    public @ResponseBody
    String deleteAll() {
        configurationService.deleteAll();
        return "Done";
    }

}
