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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import nl.uva.sne.drip.api.dao.UserScriptDao;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.commons.v1.types.Script;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * This controller is responsible for handling user scripts. These user can be
 * used by the provisoner to run on the created VMs.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/user_script")
@Component
public class UserScriptController {

    @Autowired
    private UserScriptDao dao;

//    curl -v -X POST -F "file=@script.sh" localhost:8080/drip-api/rest/user_script/upload
    /**
     * Uploads a script
     *
     * @param file. The file of the script
     * @return the ID of the stopred script
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
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

                dao.save(us);

                return us.getId();
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(UserScriptController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Gets a script
     *
     * @param id. The ID of the script to return
     * @return the script
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Script get(@PathVariable("id") String id) {
        return dao.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") String id) {
        Script script = dao.findOne(id);
        if (script == null) {
            throw new NotFoundException();
        }
        dao.delete(id);
        return "Deleted: " + id;
    }

    /**
     * Gets the IDs of all the stored scripts
     *
     * @return a list of all the IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    public @ResponseBody
    List<String> getIds() {
        List<Script> all = dao.findAll();
        List<String> ids = new ArrayList<>();
        for (Script us : all) {
            ids.add(us.getId());
        }
        return ids;
    }
}
