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
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.commons.v1.types.CloudCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.exception.NullKeyException;
import nl.uva.sne.drip.api.exception.NullKeyIDException;
import nl.uva.sne.drip.api.service.CloudCredentialsService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.v0.types.Configure;
import nl.uva.sne.drip.commons.v1.types.LoginKey;
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
@RequestMapping("/user/v0.0/switch/account/configure/")
@Component
public class CloudConfigurationController0 {

    @Autowired
    private CloudCredentialsService cloudCredentialsDao;

    @RequestMapping(value = "/ec2", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String postEC2Conf(@RequestBody Configure configure) {
        if (configure.key == null) {
            throw new NullKeyException();
        }
        if (configure.keyid == null) {
            throw new NullKeyIDException();
        }
        CloudCredentials cloudCredentials = new CloudCredentials();
        cloudCredentials.setKeyIdAlias(configure.keyid);
        cloudCredentials.setKey(configure.key);

        List<nl.uva.sne.drip.commons.v1.types.LoginKey> loginKeys = new ArrayList<>();

        for (nl.uva.sne.drip.commons.v0.types.LoginKey0 key0 : configure.loginKey) {
            nl.uva.sne.drip.commons.v1.types.LoginKey key1 = new nl.uva.sne.drip.commons.v1.types.LoginKey();
            key1.setKey(key0.content);
            Map<String, String> attributes = new HashMap<>();
            attributes.put("domain_name", key0.domain_name);
            key1.setAttributes(attributes);
            loginKeys.add(key1);
        }
        cloudCredentials.setLogineKeys(loginKeys);
        cloudCredentials.setCloudProviderName("ec2");

        cloudCredentials = cloudCredentialsDao.save(cloudCredentials);
        return "Success: " + cloudCredentials.getId();
    }

    @RequestMapping(value = "/geni", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String postGeniConf(@RequestBody Configure configure) {
        if (configure.geniKey == null) {
            throw new NullKeyException();
        }
        if (configure.geniKeyAlias == null) {
            throw new NullKeyIDException();
        }
        CloudCredentials cloudCredentials = new CloudCredentials();
        cloudCredentials.setKeyIdAlias(configure.geniKeyAlias);
        cloudCredentials.setKey(configure.geniKey);
        cloudCredentials.setKeyPass(configure.geniKeyPass);

        List<nl.uva.sne.drip.commons.v1.types.LoginKey> loginKeys = new ArrayList<>();

        for (nl.uva.sne.drip.commons.v0.types.LoginKey0 key0 : configure.loginPubKey) {
            nl.uva.sne.drip.commons.v1.types.LoginKey key1 = new nl.uva.sne.drip.commons.v1.types.LoginKey();
            key1.setKey(key0.content);
            key1.setType(LoginKey.Type.PUBLIC);
            loginKeys.add(key1);
        }
        for (nl.uva.sne.drip.commons.v0.types.LoginKey0 key0 : configure.loginPriKey) {
            nl.uva.sne.drip.commons.v1.types.LoginKey key1 = new nl.uva.sne.drip.commons.v1.types.LoginKey();
            key1.setKey(key0.content);
            key1.setType(LoginKey.Type.PRIVATE);
            loginKeys.add(key1);
        }
        cloudCredentials.setLogineKeys(loginKeys);
        cloudCredentials.setCloudProviderName("geni");
        cloudCredentialsDao.save(cloudCredentials);
        return "Success: " + cloudCredentials.getId();
    }

}
