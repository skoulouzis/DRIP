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
import nl.uva.sne.drip.drip.commons.data.v1.external.ProvisionRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.CloudCredentialsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.service.ProvisionService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.drip.commons.data.v1.external.ProvisionResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.ScaleRequest;
import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for obtaining resources from cloud providers
 * based the plan generated by the planner
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/provisioner")
@Component
@StatusCodes({
    @ResponseCode(code = 401, condition = "Bad credentials")
})
public class ProvisionController {

    @Autowired
    private ProvisionService provisionService;

    /**
     * Gets the supported providers
     *
     * @return the supported providers
     */
    @RequestMapping(value = "/providers", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 404, condition = "object not found"),
        @ResponseCode(code = 200, condition = "object exists")
    })
    public @ResponseBody
    String[] getSuportedProviders() {
        return new String[]{"egi", "ec2"};
    }

    /**
     * Gets the ProvisionRequest
     *
     * @param id. The id of the ProvisionRequest
     * @return the requested ProvisionRequest
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 404, condition = "object not found"),
        @ResponseCode(code = 200, condition = "object exists")
    })
    public @ResponseBody
    ProvisionResponse get(@PathVariable("id") String id) {
        ProvisionResponse pro = provisionService.findOne(id);
        if (pro == null) {
            throw new NotFoundException();
        }
        return provisionService.findOne(id);
    }

    /**
     * Deletes the ProvisionRequest
     *
     * @param id. The ID of the ProvisionRequest to be deleted
     * @return the ID of the deleted ProvisionRequest
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 404, condition = "object not found"),
        @ResponseCode(code = 200, condition = "delete successful")
    })
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        ProvisionResponse provPlan = provisionService.findOne(id);
        if (provPlan != null) {
            try {
                provisionService.deleteProvisionedResources(provPlan);
                ProvisionResponse provisionInfo = provisionService.delete(id);
                return "Deleted : " + id;
            } catch (IOException | TimeoutException | InterruptedException | JSONException ex) {
                Logger.getLogger(ProvisionController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        provisionService.deleteAll();
        return "Done";
    }

    /**
     * Gets the IDs of all the stored ProvisionRequest
     *
     * @return a list of IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful query")
    })
    public @ResponseBody
    List<String> getIds() {
        List<ProvisionResponse> all = provisionService.findAll();
        List<String> ids = new ArrayList<>(all.size());
        for (ProvisionResponse pi : all) {
            ids.add(pi.getId());
        }
        return ids;
    }

    /**
     * Provision the resources specified by a plan.
     *
     * @param req. The ProvisionRequest. This is a container the plan ID, cloud
     * credent ID, etc.
     * @return The ID of the provisioned ProvisionRequest
     */
    @RequestMapping(value = "/provision", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 400, condition = "Plan not found or credentials not found"),
        @ResponseCode(code = 200, condition = "provision success")
    })
    public @ResponseBody
    String provision(@RequestBody ProvisionRequest req) {
        if (req.getCloudCredentialsIDs() == null) {
            throw new BadRequestException();
        }
        if (req.getPlanID() == null || req.getPlanID().length() < 2) {
            throw new BadRequestException();
        }
        try {
            ProvisionResponse resp = provisionService.provisionResources(req, 1);

            return resp.getId();

        } catch (Exception ex) {
            if (ex instanceof nl.uva.sne.drip.api.exception.PlanNotFoundException
                    || ex instanceof nl.uva.sne.drip.api.exception.NotFoundException
                    || ex instanceof CloudCredentialsNotFoundException) {
                throw new BadRequestException(ex.getMessage());
            }
            Logger.getLogger(ProvisionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Updates or creates a new ProvisionResponse.
     *
     * @param resp
     * @return
     */
    @RequestMapping(value = "/post/provision", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 400, condition = "Plan not found or credentials not found or something important"),
        @ResponseCode(code = 200, condition = "provision success")
    })
    public @ResponseBody
    String postProvisionResponse(@RequestBody ProvisionResponse resp) {
//        if (resp.getCloudCredentialsIDs() == null || resp.getCloudCredentialsIDs().get(0) == null
//                || resp.getCloudCredentialsIDs().get(0).length() < 2) {
//            throw new BadRequestException();
//        }
//        if (resp.getPlanID() == null || resp.getPlanID().length() < 2) {
//            throw new BadRequestException();
//        }
//        if (resp.getDeployParameters() == null || resp.getDeployParameters().get(0) == null) {
//            throw new BadRequestException();
//        }
        resp = provisionService.save(resp);

        return resp.getId();
    }

    @RequestMapping(value = "/scale", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String scaleDeployment(@RequestBody ScaleRequest scaleRequest) {
        try {
            return provisionService.scale(scaleRequest).getId();
        } catch (IOException | TimeoutException | JSONException ex) {
            Logger.getLogger(ProvisionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProvisionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    ProvisionRequest sample() {
        ProvisionRequest req = new ProvisionRequest();
        List<String> cloudCredentialsIDs = new ArrayList<>();
        cloudCredentialsIDs.add("58f8d74f2af451b88c779d7a");
        cloudCredentialsIDs.add("438dAFDf2ead451we8rf34Af");
        req.setCloudCredentialsIDs(cloudCredentialsIDs);
        List<String> keyPairIDs = new ArrayList<>();
        keyPairIDs.add("58f8da042af45d6621813c4e");
        req.setUserKeyPairIDs(keyPairIDs);
        req.setPlanID("58da51f7f7b42e7d967752a1");
        return req;
    }

}
