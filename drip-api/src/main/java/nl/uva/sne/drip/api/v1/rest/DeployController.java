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

import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.InternalServerErrorException;
import nl.uva.sne.drip.api.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.DeployService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.drip.commons.data.v1.external.DeployRequest;
import nl.uva.sne.drip.drip.commons.data.v1.external.DeployResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.ScaleRequest;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller is responsible for deploying a cluster on provisioned 
 * resources.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/deployer")
@Controller
@StatusCodes({
    @ResponseCode(code = 401, condition = "Bad credentials")
})
public class DeployController {

    @Autowired
    private DeployService deployService;

    /**
     * Deploys software (inc. swarm ,kubernetes) to provisioned VMs.
     *
     * @param deployRequest
     * @return
     */
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 400, condition = "Empty provision ID"),
        @ResponseCode(code = 500, condition = "Deploymet failed"),
        @ResponseCode(code = 200, condition = "Successful deploymet")
    })
    public @ResponseBody
    String deploy(@RequestBody DeployRequest deployRequest) {
        try {
            if (deployRequest.getProvisionID() == null) {
                throw new BadRequestException("Must provide provision ID");
            }
            DeployResponse deploy = deployService.deploySoftware(deployRequest);
            return deploy.getId();
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Scales deployment  
     * @param scaleRequest
     * @return 
     */
    @RequestMapping(value = "/scale", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String scaleDeployment(@RequestBody ScaleRequest scaleRequest) {
        try {
            return deployService.scale(scaleRequest).getId();

        } catch (TimeoutException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    /**
     * Returns a deployment description
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 404, condition = "Object not found"),
        @ResponseCode(code = 200, condition = "Object found")
    })
    public @ResponseBody
    DeployResponse get(@PathVariable("id") String id) {
        DeployResponse resp = null;
        try {
            resp = deployService.findOne(id);

//            if (resp.getManagerType().equals("swarm")) {
//                Map<String, Object> swarmInfo = deployService.getSwarmInfo(resp);
//                resp.setManagerInfo(swarmInfo);
//            }

        } catch (JSONException | IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (resp == null) {
            throw new NotFoundException();
        }
        return resp;
    }

    /**
     * For a given service name returns the container status on all nodes
     *
     * @param id
     * @param serviceName
     * @return
     */
    @RequestMapping(value = "/{id}/container_status", method = RequestMethod.GET, params = {"service_name"})
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 404, condition = "Object not found"),
        @ResponseCode(code = 200, condition = "Object found")
    })
    public @ResponseBody
    DeployResponse getContainerStatus(@PathVariable("id") String id,
            @RequestParam(value = "service_name", required = true) String serviceName) {
        DeployResponse resp = null;
        try {

            resp = deployService.getContainersStatus(id, serviceName);

        } catch (JSONException | IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (resp == null) {
            throw new NotFoundException();
        }
        return resp;
    }

    
    /**
     * Returns the service names running on the cluster
     * @param id
     * @return 
     */
    @RequestMapping(value = "/{id}/service_names", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 404, condition = "Object not found"),
        @ResponseCode(code = 200, condition = "Object found")
    })
    public @ResponseBody
    List<String> getContainerStatus(@PathVariable("id") String id) {
        List<String> names = null;
        try {
            names = deployService.getServiceNames(id);
        } catch (JSONException | IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (names == null) {
            throw new NotFoundException();
        }
        return names;
    }

    /**
     * Gets the IDs of all the stored deployment descriptions.
     *
     * @return a list of all the IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful query")
    })
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
     * Deletes entry
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful delete"),
        @ResponseCode(code = 404, condition = "Object not found")
    })
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        DeployResponse Key = null;
        try {
            Key = deployService.findOne(id);
        } catch (JSONException | IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Key != null) {
            deployService.delete(id);
            return "Deleted : " + id;
        }
        throw new NotFoundException();
    }

    /**
     * Deletes all entries. Use with caution !
     *
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful delete")
    })
    public @ResponseBody
    String deleteAll() {
        deployService.deleteAll();
        return "Done";
    }

}
