/*
 * Copyright 2017 S. Koulouzis, Wang Junchao, Huan Zhou, Yang Hu 
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.DeployService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.drip.commons.data.v0.external.Deploy;
import nl.uva.sne.drip.drip.commons.data.v0.external.Attribute;
import nl.uva.sne.drip.drip.commons.data.v0.external.Result;
import nl.uva.sne.drip.drip.commons.data.v1.external.DeployRequest;
import nl.uva.sne.drip.drip.commons.data.v1.external.DeployResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.Key;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for deploying a cluster on provisioned 
 * resources.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v0.0/switch/deploy")
@Component
@PreAuthorize("isAuthenticated()")
@Deprecated
public class DeployController0 {

    @Autowired
    private DeployService deployService;

    @RequestMapping(value = "/kubernetes", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Result deployKubernetes(@RequestBody Deploy deploy) {
        try {
            DeployRequest deployReq = new DeployRequest();
            deployReq.setManagerType("kubernetes");
            deployReq.setProvisionID(deploy.action);
            return deploy(deployReq);
        } catch (Exception ex) {
            Logger.getLogger(DeployController0.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(value = "/swarm", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Result deploySwarm(@RequestBody Deploy deploy) {
        try {
            DeployRequest deployReq = new DeployRequest();
            deployReq.setManagerType("swarm");
            deployReq.setProvisionID(deploy.action);
            return deploy(deployReq);
        } catch (Exception ex) {
            Logger.getLogger(DeployController0.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Result deploy(DeployRequest deployReq) throws Exception {

        DeployResponse key = deployService.deploySoftware(deployReq);

        Result res = new Result();
        res.info = "INFO";
        res.status = "Success";
        List<Attribute> files = new ArrayList<>();
        Attribute attribute = new Attribute();
        attribute.content = key.getKeyPair().getPrivateKey().getKey();
        files.add(attribute);
        res.file = files;
        return res;
    }
}
