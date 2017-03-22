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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.commons.v1.types.CloudCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.exception.NullKeyException;
import nl.uva.sne.drip.api.exception.NullKeyIDException;
import nl.uva.sne.drip.api.service.CloudCredentialsService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.v1.types.LoginKey;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * This controller is responsible for handling CloudCredentials.
 * CloudCredentials are a represntation of the credentials that are used by the
 * provisoner to request for resources (VMs)
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/credentials/cloud")
@Component
public class CloudCredentialsController {

    @Autowired
    private CloudCredentialsService cloudCredentialsService;

    /**
     * Post the cloud credentials.
     *
     * @param cloudCredentials
     * @return the CloudCredentials id
     */
    @RequestMapping(method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 400, condition = "Key or KeyIdAlias can't be empty")
    })
    public @ResponseBody
    String postConf(@RequestBody CloudCredentials cloudCredentials) {
        if (cloudCredentials.getKey() == null) {
            throw new NullKeyException();
        }
        if (cloudCredentials.getKeyIdAlias() == null) {
            throw new NullKeyIDException();
        }
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cloudCredentials = cloudCredentialsService.save(cloudCredentials);
        return cloudCredentials.getId();
    }

    /**
     * Upload the login keys for a cloud provider. The cloud credentials have to
     * be created
     *
     * @param file
     * @param id
     * @return the CloudCredentials id
     */
    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String addLogineKey(@RequestParam("file") MultipartFile file, @PathVariable("id") String id) {
        try {

            CloudCredentials cloudCredentials = cloudCredentialsService.findOne(id);
            if (cloudCredentials == null) {
                throw new NotFoundException();
            }
            if (file.isEmpty()) {
                throw new BadRequestException("Must uplaod a file");
            }
            String originalFileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            List<LoginKey> logInKeys = cloudCredentials.getLoginKeys();
            if (logInKeys == null) {
                logInKeys = new ArrayList<>();
            }
            LoginKey key = new LoginKey();
            key.setKey(new String(bytes, "UTF-8"));
            if (cloudCredentials.getCloudProviderName().toLowerCase().equals("ec2")) {
                Map<String, String> attributes = new HashMap<>();
                attributes.put("domain_name", FilenameUtils.removeExtension(originalFileName));
                key.setAttributes(attributes);
            }
            logInKeys.add(key);
            cloudCredentials.setLogineKeys(logInKeys);
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            cloudCredentials = cloudCredentialsService.save(cloudCredentials);
            return cloudCredentials.getId();
        } catch (IOException ex) {
            Logger.getLogger(CloudCredentialsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get the cloud credentials
     *
     * @param id the id of the cloud credentials
     * @return the cloud credentials
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    CloudCredentials get(@PathVariable("id") String id) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CloudCredentials cc = cloudCredentialsService.findOne(id);
        if (cc == null) {
            throw new NotFoundException();
        }
        return cc;
    }

    /**
     * Delete the cloud credentials
     *
     * @param id the id of the cloud credentials
     * @return the id of the cloud credentials
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        cloudCredentialsService.delete(id);
        return "Deleted :" + id;
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String deleteAll() {
        cloudCredentialsService.deleteAll();
        return "Done";
    }

    /**
     * Gets all the IDs of the stored cloud credentials
     *
     * @return a list of stored IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        List<CloudCredentials> all = cloudCredentialsService.findAll();
        List<String> ids = new ArrayList<>();
        for (CloudCredentials tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

}
