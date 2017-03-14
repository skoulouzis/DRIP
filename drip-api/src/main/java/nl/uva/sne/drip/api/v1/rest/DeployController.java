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
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.DeployClusterService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.v1.types.ClusterCredentials;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * Deploys a cluster on a provisioned resources.
     *
     * @param provisionID
     * @param clusterType
     * @return the id of the cluster credentials
     */
    @RequestMapping(value = "/deploy/{id}/", method = RequestMethod.GET, params = {"cluster"})
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String deploy(@PathVariable("id") String provisionID, @RequestParam(value = "cluster") String clusterType) {
        checkClusterType(clusterType);
        ClusterCredentials clusterCred = deployService.deployCluster(provisionID, clusterType);
        return clusterCred.getId();
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
    ClusterCredentials get(@PathVariable("id") String id) {
        ClusterCredentials clusterC = deployService.findOne(id);
        if (clusterC == null) {
            throw new NotFoundException();
        }
        return clusterC;
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
        List<ClusterCredentials> all = deployService.findAll();
        List<String> ids = new ArrayList<>(all.size());
        for (ClusterCredentials pi : all) {
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
        ClusterCredentials cred = deployService.findOne(id);
        if (cred != null) {
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

  

    private void checkClusterType(String clusterType) {
        switch (clusterType.toLowerCase()) {
            case "kubernetes":
                break;
            case "swarm":
                break;
            default:
                throw new BadRequestException("Cluster type not supported. May use kubernetes or swarm");
        }

    }

}
