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
import nl.uva.sne.drip.commons.v1.types.LoginKey;
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
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.service.UserKeyService;
import nl.uva.sne.drip.api.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for handling user public keys. These keys can
 * be used by the provisoner to allow the user to login to the VMs from the
 * machine the keys correspond to.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/user_key")
@Component
public class UserPublicKeysController {

    @Autowired
    private UserKeyService service;

//    curl -v -X POST -F "file=@.ssh/id_dsa.pub" localhost:8080/drip-api/user_key/upload
    /**
     * Uploads a public key (id_dsa.pub,id_rsa.pub)
     *
     * @param file the public key file
     * @return the ID of the stored public key
     */
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

                service.save(upk);

                return upk.getId();
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(UserPublicKeysController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

//    curl -H "Content-Type: application/json" -X POST -d  '{"key":"ssh-rsa AAAAB3NzaDWBqs75i849MytgwgQcRYMcsXIki0yeYTKABH6JqoiyFBHtYlyh/EV1t6cujb9LyNP4J5EN4fPbtwKYvxecd0LojSPxl4wjQlfrHyg6iKUYB7hVzGqACMvgYZHrtHPfrdEmOGPplPVPpoaX2j+u0BZ0yYhrWMKjzyYZKa68yy5N18+Gq+1p83HfUDwIU9wWaUYdgEvDujqF6b8p3z6LDx9Ob+RanSMZSt+b8eZRcd+F2Oy/gieJEJ8kc152VIOv8UY1xB3hVEwVnSRGgrAsa+9PChfF6efXUGWiKf8KBlWgBOYsSTsOY4ks9zkXMnbcTdC+o7xspOkyIcWjv us@u\n","name":"id_rsa.pub"}' localhost:8080/drip-api/user_key/
    /**
     * Posts the LoginKey and stores it. The LoginKey is a container for public
     * key contents. The public key contents are represented in the 'key' field.
     * All new lines in the 'key' field have to be replaced with the '\n'
     * character.
     *
     * @param key. The LoginKey
     * @return the ID of the LoginKey
     */
    @RequestMapping(method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 400, condition = "Key can't be empty")
    })
    public @ResponseBody
    String postKey(@RequestBody LoginKey key) {
        LoginKey.Type type = key.getType();
//        if (type != null && type.equals(LoginKey.Type.PRIVATE)) {
//            throw new BadRequestException("Key can't be private");
//        }
        if (key.getKey() == null) {
            throw new BadRequestException("Key can't be empty");
        }
        String originalName = key.getName();
        if (originalName == null) {
            originalName = "id.pub";
        }
        String name = System.currentTimeMillis() + "_" + originalName;
        key.setName(name);
        key.setType(LoginKey.Type.PUBLIC);

        service.save(key);
        return key.getId();
    }

    /**
     * Gets the LoginKey.
     *
     * @param id . The ID of the LoginKey to return
     * @return The LoginKey
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    LoginKey get(@PathVariable("id") String id) {
        LoginKey key = service.get(id, LoginKey.Type.PUBLIC);
        if (key == null) {
            throw new NotFoundException();
        }
        return key;
    }

//    localhost:8080/drip-api/user_key/ids
    /**
     * Gets the IDs of all the stored LoginKey
     *
     * @return a list of all the IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        List<LoginKey> all = service.getAll(LoginKey.Type.PUBLIC);
        List<String> ids = new ArrayList<>();
        for (LoginKey tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

    /**
     * Deletes a LoginKey
     *
     * @param id. The ID of the LoginKey to deleted.
     * @return The ID of the deleted LoginKey
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        service.delete(id, LoginKey.Type.PUBLIC);
        return "Deleted: " + id;
    }
    
     @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String deleteAll() {
        service.deleteAll();
        return "Done";
    }

}
