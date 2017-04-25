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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.data.v1.external.Script;
import org.springframework.web.bind.annotation.PathVariable;
import nl.uva.sne.drip.api.service.ScriptService;
import nl.uva.sne.drip.api.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for handling user scripts. These user can be
 * used by the provisoner to run on the created VMs.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/script")
@Component
@StatusCodes({
    @ResponseCode(code = 401, condition = "Bad credentials")
})
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

//    curl -v -X POST -F "file=@script.sh" localhost:8080/drip-api/rest/user_script/upload
    /**
     * Uploads a script
     *
     * @param file. The file of the script
     * @return the ID of the stopred script
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String uploadUserScript(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String originalFileName = file.getOriginalFilename();
                String name = System.currentTimeMillis() + "_" + originalFileName;
                byte[] bytes = file.getBytes();
                String conents = new String(bytes, "UTF-8");

                Script us = new Script();
                us.setContents(conents);
                us.setName(name);

                scriptService.save(us);

                return us.getId();
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(ScriptController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String uploadUserScript(@RequestBody Script script) {
        return null;
    }

    /**
     * Gets a script
     *
     * @param id. The ID of the script to return
     * @return the script
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Script get(@PathVariable("id") String id) {
        return scriptService.findOne(id);
    }

    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Script get() {
        Script script = new Script();
        script.setContents("#! /bin/bash\n"
                + " apt-get update\n"
                + " apt-get -y install linux-image-extra-$(uname -r) linux-image-extra-virtual\n"
                + " apt-get -y install apt-transport-https ca-certificates curl software-properties-common\n"
                + " curl -fsSL https://download.docker.com/linux/ubuntu/gpg |  apt-key add -\n"
                + " apt-key fingerprint 0EBFCD88\n"
                + " add-apt-repository \"deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable\"\n"
                + " apt-get update\n"
                + " apt-get -y install docker-ce\n"
                + " service docker restart");
        script.setName("setupDocker.sh");

        return script;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        Script script = scriptService.findOne(id);
        if (script == null) {
            throw new NotFoundException();
        }
        scriptService.delete(id);
        return "Deleted: " + id;
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String deleteAll() {
        scriptService.deleteAll();
        return "Done";
    }

    /**
     * Gets the IDs of all the stored scripts
     *
     * @return a list of all the IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    public @ResponseBody
    List<String> getIds() {
        List<Script> all = scriptService.findAll();
        List<String> ids = new ArrayList<>();
        for (Script us : all) {
            ids.add(us.getId());
        }
        return ids;
    }
}
