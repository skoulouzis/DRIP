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

import nl.uva.sne.drip.commons.types.LoginKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import nl.uva.sne.drip.api.dao.UserKeyDao;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/user_key")
@Component
public class UserPublicKeysController {

    @Autowired
    private UserKeyDao dao;

//    curl -v -X POST -F "file=@.ssh/id_dsa.pub" localhost:8080/drip-api/user_key/upload
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String uploadUserPublicKeys(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String originalFileName = file.getOriginalFilename();
                String name = System.currentTimeMillis() + "_" + originalFileName;
                byte[] bytes = file.getBytes();
                String key = new String(bytes, "UTF-8");

                LoginKey upk = new LoginKey();
                upk.setKey(key);
                upk.setName(name);
                upk.setType(LoginKey.Type.PUBLIC);
                dao.save(upk);

                return upk.getId();
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(UserPublicKeysController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

//    curl -H "Content-Type: application/json" -X POST -d  '{"key":"ssh-rsa AAAAB3NzaDWBqs75i849MytgwgQcRYMcsXIki0yeYTKABH6JqoiyFBHtYlyh/EV1t6cujb9LyNP4J5EN4fPbtwKYvxecd0LojSPxl4wjQlfrHyg6iKUYB7hVzGqACMvgYZHrtHPfrdEmOGPplPVPpoaX2j+u0BZ0yYhrWMKjzyYZKa68yy5N18+Gq+1p83HfUDwIU9wWaUYdgEvDujqF6b8p3z6LDx9Ob+RanSMZSt+b8eZRcd+F2Oy/gieJEJ8kc152VIOv8UY1xB3hVEwVnSRGgrAsa+9PChfF6efXUGWiKf8KBlWgBOYsSTsOY4ks9zkXMnbcTdC+o7xspOkyIcWjv us@u\n","name":"id_rsa.pub"}' localhost:8080/drip-api/user_key/
//    @RequestMapping(method = RequestMethod.POST)
//    @RolesAllowed({UserService.USER, UserService.ADMIN})
//    public @ResponseBody
//    String postConf(LoginKey uk) throws JSONException {
//        String name = System.currentTimeMillis() + "_" + uk.getName();
//        uk.setName(name);
//        dao.save(uk);
//        return uk.getId();
//    }
    //curl localhost:8080/drip-api/user_key/58a20be263d4a5898835676e
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public LoginKey get(@PathVariable("id") String id) {
        LoginKey key = dao.findOne(id);
        if (key == null || !key.getType().equals(LoginKey.Type.PUBLIC)) {
            throw new NotFoundException();
        }

        return key;
    }

//    localhost:8080/drip-api/user_key/ids
    @RequestMapping(value = "/ids")
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        List<LoginKey> all = dao.findAll();
        List<String> ids = new ArrayList<>();
        for (LoginKey tr : all) {
            if (tr.getType().equals(LoginKey.Type.PUBLIC)) {
                ids.add(tr.getId());
            }
        }
        return ids;
    }

}
