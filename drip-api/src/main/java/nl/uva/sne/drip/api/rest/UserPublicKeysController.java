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
import nl.uva.sne.drip.api.dao.UserKeyDao;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/rest/user_key")
@Component
public class UserPublicKeysController {

    @Autowired
    private UserKeyDao dao;

//    curl -v -X POST -F "file=@.ssh/id_dsa.pub" localhost:8080/drip-api/rest/user_key/upload
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String uploadUserPublicKeys(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String originalFileName = file.getOriginalFilename();
                String name = System.currentTimeMillis() + "_" + originalFileName;
                byte[] bytes = file.getBytes();
                String key = new String(bytes, "UTF-8");

                UserPublicKey upk = new UserPublicKey();
                upk.setKey(key);
                upk.setName(name);
                dao.save(upk);

                return upk.getId();
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(UserPublicKeysController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    
//    curl -H "Content-Type: application/json" -X POST -d  '{"key":"ssh-rsa AAAAB3NzaDWBqs75i849MytgwgQcRYMcsXIki0yeYTKABH6JqoiyFBHtYlyh/EV1t6cujb9LyNP4J5EN4fPbtwKYvxecd0LojSPxl4wjQlfrHyg6iKUYB7hVzGqACMvgYZHrtHPfrdEmOGPplPVPpoaX2j+u0BZ0yYhrWMKjzyYZKa68yy5N18+Gq+1p83HfUDwIU9wWaUYdgEvDujqF6b8p3z6LDx9Ob+RanSMZSt+b8eZRcd+F2Oy/gieJEJ8kc152VIOv8UY1xB3hVEwVnSRGgrAsa+9PChfF6efXUGWiKf8KBlWgBOYsSTsOY4ks9zkXMnbcTdC+o7xspOkyIcWjv us@u\n","name":"id_rsa.pub"}' localhost:8080/drip-api/rest/user_key/
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String postConf(UserPublicKey uk) throws JSONException {
        String name = System.currentTimeMillis() + "_" + uk.getName();
        uk.setName(name);
        dao.save(uk);
        return uk.getId();
    }

    //curl localhost:8080/drip-api/rest/user_key/58a20be263d4a5898835676e
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserPublicKey get(@PathVariable("id") String id) {
        return dao.findOne(id);
    }

//    localhost:8080/drip-api/rest/user_key/ids
    @RequestMapping(value = "/ids")
    public @ResponseBody
    List<String> getIds() {
        List<UserPublicKey> all = dao.findAll();
        List<String> ids = new ArrayList<>();
        for (UserPublicKey tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

}
