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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.exception.KeyException;
import nl.uva.sne.drip.drip.commons.data.v1.external.CloudCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.exception.NullKeyException;
import nl.uva.sne.drip.api.exception.NullKeyIDException;
import nl.uva.sne.drip.api.service.CloudCredentialsService;
import nl.uva.sne.drip.api.service.KeyPairService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.drip.commons.data.v0.external.Configure;
import nl.uva.sne.drip.drip.commons.data.v1.external.Key;
import nl.uva.sne.drip.drip.commons.data.v1.external.KeyPair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * This controller is responsible for handling cloud credentials used by the
 * provisoner to request for resources (VMs).
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v0.0/switch/account/configure")
@Component
public class CloudConfigurationController0 {

    @Autowired
    private CloudCredentialsService cloudCredentialsService;

    @Autowired
    private KeyPairService keyService;

    @RequestMapping(value = "/ec2", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String postEC2Conf(@RequestBody Configure configure) throws Exception {
        if (configure.key == null) {
            throw new NullKeyException();
        }
        if (configure.keyid == null) {
            throw new NullKeyIDException();
        }
        CloudCredentials cloudCredentials = new CloudCredentials();
        cloudCredentials.setTimestamp(System.currentTimeMillis());
        cloudCredentials.setAccessKeyId(configure.keyid);
        cloudCredentials.setSecretKey(configure.key);

        List<String> loginKeyIDs = new ArrayList<>();

        for (nl.uva.sne.drip.drip.commons.data.v0.external.LoginKey0 key0 : configure.loginKey) {
            try {
                nl.uva.sne.drip.drip.commons.data.v1.external.Key key1 = new nl.uva.sne.drip.drip.commons.data.v1.external.Key();
                KeyPair pair = new KeyPair();
                pair.setTimestamp(System.currentTimeMillis());
                key1.setKey(key0.content);
                Map<String, String> attributes = new HashMap<>();
                attributes.put("domain_name", key0.domain_name);
                key1.setAttributes(attributes);
                pair.setPrivateKey(key1);
                pair = keyService.save(pair);
                loginKeyIDs.add(pair.getId());
            } catch (KeyException ex) {
                Logger.getLogger(CloudConfigurationController0.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        cloudCredentials.setKeyIDs(loginKeyIDs);
        cloudCredentials.setCloudProviderName("ec2");

        cloudCredentials = cloudCredentialsService.save(cloudCredentials);
        return "Success: " + cloudCredentials.getId();
    }

    @RequestMapping(value = "/geni", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String postGeniConf(@RequestBody Configure configure) throws Exception {
        if (configure.geniKey == null) {
            throw new NullKeyException();
        }
        if (configure.geniKeyAlias == null) {
            throw new NullKeyIDException();
        }
        CloudCredentials cloudCredentials = new CloudCredentials();
        cloudCredentials.setTimestamp(System.currentTimeMillis());
//        cloudCredentials.setKeyIdAlias(configure.geniKeyAlias);
        cloudCredentials.setAccessKeyId(configure.geniKey);
        cloudCredentials.setSecretKey(configure.geniKeyPass);

        List<String> loginKeyIDs = new ArrayList<>();

        for (nl.uva.sne.drip.drip.commons.data.v0.external.LoginKey0 key0 : configure.loginPubKey) {
            try {
                nl.uva.sne.drip.drip.commons.data.v1.external.Key key1 = new nl.uva.sne.drip.drip.commons.data.v1.external.Key();
                key1.setKey(key0.content);
                key1.setType(Key.KeyType.PUBLIC);
                KeyPair pair = new KeyPair();
                pair.setTimestamp(System.currentTimeMillis());
                pair.setPublicKey(key1);
                pair = keyService.save(pair);
                loginKeyIDs.add(pair.getId());
            } catch (KeyException ex) {
                Logger.getLogger(CloudConfigurationController0.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        for (nl.uva.sne.drip.drip.commons.data.v0.external.LoginKey0 key0 : configure.loginPriKey) {
            try {
                nl.uva.sne.drip.drip.commons.data.v1.external.Key key1 = new nl.uva.sne.drip.drip.commons.data.v1.external.Key();
                key1.setKey(key0.content);
                key1.setType(Key.KeyType.PRIVATE);
                KeyPair pair = new KeyPair();
                pair.setTimestamp(System.currentTimeMillis());
                pair.setPrivateKey(key1);
                pair = keyService.save(pair);
                loginKeyIDs.add(pair.getId());
            } catch (KeyException ex) {
                Logger.getLogger(CloudConfigurationController0.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        cloudCredentials.setKeyIDs(loginKeyIDs);
        cloudCredentials.setCloudProviderName("geni");
        cloudCredentialsService.save(cloudCredentials);
        return "Success: " + cloudCredentials.getId();
    }

}
