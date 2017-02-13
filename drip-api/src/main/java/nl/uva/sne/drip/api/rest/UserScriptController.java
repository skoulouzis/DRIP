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

import nl.uva.sne.drip.commons.types.UserPublicKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import nl.uva.sne.drip.api.dao.UserScriptDao;
import nl.uva.sne.drip.commons.types.UserScript;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/rest/user_script")
@Component
public class UserScriptController {

    @Autowired
    private UserScriptDao dao;

//    curl -v -X POST -F "file=@script.sh" localhost:8080/drip-api/rest/user_script/upload
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String uploadUserScript(@RequestParam("file") MultipartFile file) throws JSONException {
        if (!file.isEmpty()) {
            try {
                String originalFileName = file.getOriginalFilename();
                String name = System.currentTimeMillis() + "_" + originalFileName;
                byte[] bytes = file.getBytes();
                String conents = new String(bytes, "UTF-8");

                UserScript us = new UserScript();
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
    
//    @RequestMapping(method = RequestMethod.POST)
//    public @ResponseBody
//    String postConf(UserScript us) {
//        String name = System.currentTimeMillis() + "_" + us.getName();
//        us.setName(name);
//        dao.save(us);
//        return us.getId();
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserScript get(@PathVariable("id") String id) {
        return dao.findOne(id);
    }

    @RequestMapping(value = "/ids")
    public @ResponseBody
    List<String> getIds() {
        List<UserScript> all = dao.findAll();
        List<String> ids = new ArrayList<>();
        for (UserScript us : all) {
            ids.add(us.getId());
        }
        return ids;
    }
}
