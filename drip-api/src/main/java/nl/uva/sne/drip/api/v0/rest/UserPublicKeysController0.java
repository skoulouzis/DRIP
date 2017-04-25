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
package nl.uva.sne.drip.api.v0.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.exception.KeyException;
import nl.uva.sne.drip.api.service.ProvisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.KeyPairService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.data.v0.external.ConfUserKey;
import nl.uva.sne.drip.data.v1.external.Key;
import nl.uva.sne.drip.data.v1.external.KeyPair;
import nl.uva.sne.drip.data.v1.external.ProvisionRequest;
import nl.uva.sne.drip.data.v1.external.ProvisionResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for handling user public keys. These keys can
 * be used by the provisoner to allow the user to login to the VMs from the
 * machine the keys correspond to.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v0.0/switch/provision")
@Component
public class UserPublicKeysController0 {

    @Autowired
    private KeyPairService service;

    @Autowired
    private ProvisionService provisionService;

    @RequestMapping(value = "/confuserkey", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String uploadUserPublicKeys(@RequestBody ConfUserKey confUserKey) {
        try {
            KeyPair pair = new KeyPair();
            Key upk = new Key();
            upk.setKey(confUserKey.file.get(0).content);
            upk.setName(confUserKey.file.get(0).name);
            upk.setType(Key.KeyType.PUBLIC);
            pair.setPublicKey(upk);
            pair = service.save(pair);

            ProvisionResponse provPlan = provisionService.findOne(confUserKey.action);
            List<String> keyPairIDs = new ArrayList<>();
            keyPairIDs.add(pair.getId());
            provPlan.setKeyPairIDs(keyPairIDs);
            provisionService.save(provPlan);

            return "Success: " + pair.getId();
        } catch (KeyException ex) {
            Logger.getLogger(UserPublicKeysController0.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
