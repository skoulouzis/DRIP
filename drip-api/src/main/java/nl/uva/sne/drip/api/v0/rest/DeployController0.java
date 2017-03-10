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
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.DeployClusterService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.v0.types.Deploy;
import nl.uva.sne.drip.commons.v0.types.File;
import nl.uva.sne.drip.commons.v0.types.Result;
import nl.uva.sne.drip.commons.v1.types.ClusterCredentials;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for deploying a cluster on provisoned
 * resources.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v0.0/switch/deploy/")
@Component
@PreAuthorize("isAuthenticated()")
public class DeployController0 {

    @Autowired
    private DeployClusterService deployService;

    @RequestMapping(value = "/kubernetes", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Result deployKubernetes(@RequestBody Deploy deploy) {
        return deploy(deploy, "kubernetes");
    }

    @RequestMapping(value = "/swarm", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Result deploySwarm(@RequestBody Deploy deploy) {
        return deploy(deploy, "swarm");
    }

    private Result deploy(Deploy deploy, String clusterType) {
        String provisionID = deploy.action;

        ClusterCredentials clusterCred = deployService.deployCluster(provisionID, clusterType);

        Result res = new Result();
        res.info = "INFO";
        res.status = "Success";
        List<File> files = new ArrayList<>();
        File e = new File();
        e.content = clusterCred.getKey();
        files.add(e);
        res.file = files;
        return res;
    }
}
