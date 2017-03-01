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
package nl.uva.sne.drip.api.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.dao.CloudCredentialsDao;
import nl.uva.sne.drip.commons.types.Message;
import nl.uva.sne.drip.commons.types.Parameter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
import nl.uva.sne.drip.api.rpc.DeployerCaller;
import nl.uva.sne.drip.api.service.ProvisionService;
import nl.uva.sne.drip.api.service.UserKeyService;
import nl.uva.sne.drip.api.service.UserScriptService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.types.CloudCredentials;
import nl.uva.sne.drip.commons.types.DeployParameter;
import nl.uva.sne.drip.commons.types.LoginKey;
import nl.uva.sne.drip.commons.types.ProvisionInfo;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/deployer")
@Component
public class DeployController {

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    @Autowired
    private CloudCredentialsDao cloudCredentialsDao;

    @Autowired
    private ProvisionService provisionService;

    @RequestMapping(value = "/deploy/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String deploy(@PathVariable("id") String provisionID) {
        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
            Message deployerInvokationMessage = buildDeployerMessage(provisionID);

            Message response = deployer.call(deployerInvokationMessage);
//            Message response = generateFakeResponse();
            List<Parameter> params = response.getParameters();

            for (Parameter p : params) {
                String name = p.getName();
            }
            return null;
        } catch (IOException | TimeoutException | JSONException | InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Message buildDeployerMessage(String provisionID) {
        ProvisionInfo pro = provisionService.getDao().findOne(provisionID);
        String cloudConfID = pro.getCloudConfID();
        CloudCredentials cCred = cloudCredentialsDao.findOne(cloudConfID);
        List<LoginKey> loginKeys = cCred.getLoginKeys();
        List<DeployParameter> deployParams = pro.getDeployParameters();
        List<Parameter> parameters = new ArrayList<>();
        for (DeployParameter dp : deployParams) {
            String cName = dp.getCloudCertificateName();
            Parameter messageParameter = new Parameter();
            messageParameter.setName("credential");
            messageParameter.setEncoding("UTF-8");
            String key = null;
            for (LoginKey lk : loginKeys) {
                String lkName = lk.getName();
                if (lkName == null) {
                    lkName = lk.getAttributes().get("domain_name");
                }
                if (lkName.equals(cName)) {
                    key = lk.getKey();
                    break;
                }
            }
            messageParameter.setValue(key);
            Map<String, String> attributes = new HashMap<>();
            attributes.put("IP", dp.getIP());
            attributes.put("role", dp.getRole());
            attributes.put("user", dp.getUser());
            messageParameter.setAttributes(attributes);
            parameters.add(messageParameter);
        }
        Message deployInvokationMessage = new Message();

        deployInvokationMessage.setParameters(parameters);
        deployInvokationMessage.setCreationDate(System.currentTimeMillis());
        return deployInvokationMessage;
    }

}
