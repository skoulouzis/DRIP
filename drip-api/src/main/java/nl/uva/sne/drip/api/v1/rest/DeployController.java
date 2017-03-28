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
package nl.uva.sne.drip.api.v1.rest;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.DeployClusterService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.v1.types.DeployRequest;
import nl.uva.sne.drip.commons.v1.types.DeployResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * This controller is responsible for deploying a cluster on provisoned
 * resources.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/deployer")
@Controller
public class DeployController {

    @Autowired
    private DeployClusterService deployService;

    @RequestMapping(value = "/deploy/", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String deploy(DeployRequest deployRequest) {
        DeployResponse key = deployService.deployCluster(deployRequest);
        return key.getId();
    }

    /**
     * Gets the cluster credentials.
     *
     * @param id
     * @return the cluster credentials
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    DeployResponse get(@PathVariable("id") String id) {
        DeployResponse resp = deployService.findOne(id);
        if (resp == null) {
            throw new NotFoundException();
        }
        return resp;
    }

    /**
     * Gets the IDs of all the stored cluster credentials
     *
     * @return a list of all the IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        List<DeployResponse> all = deployService.findAll();
        List<String> ids = new ArrayList<>(all.size());
        for (DeployResponse pi : all) {
            ids.add(pi.getId());
        }
        return ids;
    }

    /**
     * Deletes a cluster credential
     *
     * @param id. The id of the cluster credential
     * @return the id f the deleted cluster credential
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        DeployResponse Key = deployService.findOne(id);
        if (Key != null) {
            deployService.delete(id);
            return "Deleted : " + id;
        }
        throw new NotFoundException();
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String deleteAll() {
        deployService.deleteAll();
        return "Done";
    }

}
