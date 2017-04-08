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
package nl.uva.sne.drip.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
import nl.uva.sne.drip.api.rpc.DeployerCaller;
import nl.uva.sne.drip.api.v1.rest.DeployController;
import nl.uva.sne.drip.data.v1.external.CloudCredentials;
import nl.uva.sne.drip.data.v1.external.DeployRequest;
import nl.uva.sne.drip.data.v1.external.DeployParameter;
import nl.uva.sne.drip.data.v1.external.DeployResponse;
import nl.uva.sne.drip.data.v1.external.Key;
import nl.uva.sne.drip.data.v1.external.Message;
import nl.uva.sne.drip.data.v1.external.MessageParameter;
import nl.uva.sne.drip.data.v1.external.ProvisionResponse;
import nl.uva.sne.drip.data.v1.external.User;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import nl.uva.sne.drip.api.dao.DeployDao;
import nl.uva.sne.drip.api.dao.KeyPairDao;
import nl.uva.sne.drip.api.exception.KeyException;
import nl.uva.sne.drip.data.v1.external.KeyPair;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class DeployService {

    @Autowired
    private DeployDao deployDao;

    @Autowired
    KeyPairDao keyDao;

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    @Autowired
    private CloudCredentialsService cloudCredentialsService;

    @Autowired
    private ProvisionService provisionService;

    @Autowired
    private PlaybookService playbookService;

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public DeployResponse findOne(String id) {
        DeployResponse creds = deployDao.findOne(id);
        if (creds == null) {
            throw new NotFoundException();
        }
        return creds;
    }

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<DeployResponse> findAll() {
        return deployDao.findAll();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public DeployResponse delete(String id) {
        DeployResponse creds = deployDao.findOne(id);
        if (creds == null) {
            throw new NotFoundException();
        }
        deployDao.delete(creds);
        return creds;
    }

    public DeployResponse save(DeployResponse clusterCred) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        clusterCred.setOwner(owner);
        return deployDao.save(clusterCred);
    }

    public DeployResponse deployCluster(DeployRequest deployInfo) {
        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
            Message deployerInvokationMessage = buildDeployerMessage(
                    deployInfo.getProvisionID(),
                    deployInfo.getManagerType().toLowerCase(),
                    deployInfo.getConfigurationID());

//            Message deployerInvokationMessage = MessageGenerator.generateArtificialMessage(System.getProperty("user.home")
//                    + File.separator + "workspace" + File.separator + "DRIP"
//                    + File.separator + "docs" + File.separator + "json_samples"
//                    + File.separator + "deployer_invocation.json");
            Message response = (deployer.call(deployerInvokationMessage));
//            Message response = generateFakeResponse();
            List<MessageParameter> params = response.getParameters();
            DeployResponse deployResponse = new DeployResponse();
            for (MessageParameter p : params) {
                String name = p.getName();
                if (name.equals("credential")) {
                    String value = p.getValue();
                    Key k = new Key();
                    k.setKey(value);
                    KeyPair pair = new KeyPair();
                    pair.setPrivateKey(k);
                    deployResponse.setKey(pair);
                    save(deployResponse);
                    return deployResponse;
                }
            }

        } catch (IOException | TimeoutException | JSONException | InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyException ex) {
            Logger.getLogger(DeployService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Message buildDeployerMessage(String provisionID, String managerType, String configurationID) throws JSONException {
        ProvisionResponse pro = provisionService.findOne(provisionID);
        if (pro == null) {
            throw new NotFoundException();
        }
        List<String> cloudConfIDs = pro.getCloudCredentialsIDs();
        CloudCredentials cCred = cloudCredentialsService.findOne(cloudConfIDs.get(0));
        List<String> loginKeysIDs = cCred.getkeyPairIDs();
        List<KeyPair> loginKeys = new ArrayList<>();
        for (String keyID : loginKeysIDs) {
            KeyPair key = keyDao.findOne(keyID);
            loginKeys.add(key);
        }

        List<DeployParameter> deployParams = pro.getDeployParameters();
        List<MessageParameter> parameters = new ArrayList<>();

        for (DeployParameter dp : deployParams) {
            MessageParameter messageParameter = createCredentialPartameter(dp, loginKeys);
            parameters.add(messageParameter);
        }

        MessageParameter managerTypeParameter = createManagerTypeParameter(managerType);
        parameters.add(managerTypeParameter);

        if (managerType.toLowerCase().equals("ansible") && configurationID != null) {
            MessageParameter ansibleParameter = createAnsibleParameter(configurationID);
            parameters.add(ansibleParameter);
        }

        Message deployInvokationMessage = new Message();
        deployInvokationMessage.setParameters(parameters);
        deployInvokationMessage.setCreationDate(System.currentTimeMillis());
        return deployInvokationMessage;
    }

    @PostAuthorize("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        deployDao.deleteAll();
    }

    private MessageParameter createCredentialPartameter(DeployParameter dp, List<KeyPair> loginKeys) {
        String cName = dp.getCloudCertificateName();
        MessageParameter messageParameter = new MessageParameter();
        messageParameter.setName("credential");
        messageParameter.setEncoding("UTF-8");
        String key = null;
        for (KeyPair lk : loginKeys) {
            String lkName = lk.getPrivateKey().getAttributes().get("domain_name");
            if (lkName.equals(cName)) {
                key = lk.getPrivateKey().getKey();
                break;
            }
        }
        messageParameter.setValue(key);
        Map<String, String> attributes = new HashMap<>();
        attributes.put("IP", dp.getIP());
        attributes.put("role", dp.getRole());
        attributes.put("user", dp.getUser());
        messageParameter.setAttributes(attributes);
        return messageParameter;
    }

    private MessageParameter createManagerTypeParameter(String managerType) {
        MessageParameter managerTypeParameter = new MessageParameter();
        managerTypeParameter.setName("cluster");
        managerTypeParameter.setEncoding("UTF-8");
        managerTypeParameter.setValue(managerType);
        return managerTypeParameter;
    }

    private MessageParameter createAnsibleParameter(String configurationID) throws JSONException {
        String playbook = playbookService.get(configurationID, "yml");
        MessageParameter ansibleParameter = new MessageParameter();
        ansibleParameter.setName("playbook");
        ansibleParameter.setEncoding("UTF-8");
        ansibleParameter.setValue(playbook);
        return ansibleParameter;
    }
}
