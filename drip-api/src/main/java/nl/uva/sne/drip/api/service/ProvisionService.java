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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.CloudCredentialsNotFoundException;
import nl.uva.sne.drip.api.exception.ExceptionHandler;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.exception.PlanNotFoundException;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
import nl.uva.sne.drip.api.rpc.ProvisionerCaller0;
import nl.uva.sne.drip.api.v1.rest.ProvisionController;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.drip.commons.data.v1.external.CloudCredentials;
import nl.uva.sne.drip.drip.commons.data.v1.external.DeployParameter;
import nl.uva.sne.drip.drip.commons.data.internal.Message;
import nl.uva.sne.drip.drip.commons.data.internal.MessageParameter;
import nl.uva.sne.drip.drip.commons.data.v1.external.PlanResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.ProvisionRequest;
import nl.uva.sne.drip.drip.commons.data.v1.external.ProvisionResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.Script;
import nl.uva.sne.drip.drip.commons.data.v1.external.User;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import nl.uva.sne.drip.api.dao.ProvisionResponseDao;
import nl.uva.sne.drip.api.rpc.ProvisionerCaller1;
import nl.uva.sne.drip.commons.utils.DRIPLogHandler;
import nl.uva.sne.drip.drip.commons.data.v1.external.Key;
import nl.uva.sne.drip.drip.commons.data.v1.external.KeyPair;
import nl.uva.sne.drip.drip.commons.data.v1.external.ScaleRequest;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class ProvisionService {

    @Autowired
    private ProvisionResponseDao provisionDao;

    @Autowired
    private CloudCredentialsService cloudCredentialsService;

    @Autowired
    private SimplePlannerService simplePlanService;

    @Autowired
    private ScriptService userScriptService;

    @Autowired
    private KeyPairService keyPairService;

    @Value("${message.broker.host}")
    private String messageBrokerHost;
    private final Logger logger;

    @Autowired
    public ProvisionService(@Value("${message.broker.host}") String messageBrokerHost) throws IOException, TimeoutException {
        logger = Logger.getLogger(ProvisionService.class.getName());
        logger.addHandler(new DRIPLogHandler(messageBrokerHost));
    }

    public ProvisionResponse save(ProvisionResponse ownedObject) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        ownedObject.setOwner(owner);

        return provisionDao.save(ownedObject);
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public ProvisionResponse findOne(String id) {
        ProvisionResponse provisionInfo = provisionDao.findOne(id);
        if (provisionInfo == null) {
            throw new NotFoundException();
        }
        return provisionInfo;
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public ProvisionResponse delete(String id) {
        ProvisionResponse provisionInfo = provisionDao.findOne(id);
        if (provisionInfo != null) {
            provisionDao.delete(provisionInfo);
            return provisionInfo;
        } else {
            throw new NotFoundException();
        }
    }

//    @PreAuthorize(" (hasRole('ROLE_ADMIN')) or (hasRole('ROLE_USER'))")
    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
//    @PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'admin')")
    public List<ProvisionResponse> findAll() {
        return provisionDao.findAll();
    }

    public ProvisionResponse provisionResources(ProvisionRequest provisionRequest, int provisionerVersion) throws IOException, TimeoutException, JSONException, InterruptedException, Exception {

        switch (provisionerVersion) {
            case 0:
                return callProvisioner0(provisionRequest);
            case 1:
                return callProvisioner1(provisionRequest);
        }
        return null;

    }

    private Message buildProvisioner0Message(ProvisionRequest pReq) throws JSONException, IOException {
        Message invokationMessage = new Message();
        List<MessageParameter> parameters = new ArrayList();
        CloudCredentials cred = cloudCredentialsService.findOne(pReq.getCloudCredentialsIDs().get(0));
        if (cred == null) {
            throw new CloudCredentialsNotFoundException();
        }
        MessageParameter conf = buildCloudCredentialParam(cred, 0).get(0);
        parameters.add(conf);

        List<MessageParameter> certs = buildCertificatesParam(cred);
        parameters.addAll(certs);

        List<MessageParameter> topologies = buildTopologyParams(pReq.getPlanID());
        parameters.addAll(topologies);

        List<String> userKeyIDs = pReq.getUserKeyPairIDs();
        if (userKeyIDs != null) {
            List<MessageParameter> userKeys = buildUserKeysParams(userKeyIDs.get(0), 0);
            parameters.addAll(userKeys);
        }

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }

    private List<MessageParameter> buildCloudCredentialParam(CloudCredentials cred, int version) throws JsonProcessingException, JSONException, IOException {
        List<MessageParameter> cloudCredentialParams = new ArrayList<>();
        if (version == 0) {
            MessageParameter cloudCredentialParam = new MessageParameter();
            String provider = cred.getCloudProviderName();
            if (provider == null) {
                throw new BadRequestException("Provider name can't be null. Check the cloud credentials: " + cred.getId());
            }
            switch (cred.getCloudProviderName().toLowerCase()) {
                case "ec2":
                    cloudCredentialParam = buildEC2Conf(cred);
                    break;
            }
            cloudCredentialParams.add(cloudCredentialParam);
            return cloudCredentialParams;
        }
        if (version == 1) {
            MessageParameter cloudCred = new MessageParameter();
            cloudCred.setName("cloud_credential");
            cloudCred.setEncoding("UTF-8");
            List<KeyPair> keyPairs = new ArrayList<>();
//            List<String> keyPairIds = cred.getkeyPairIDs();
//            if (keyPairIds != null) {
//                for (String id : cred.getkeyPairIDs()) {
//                    KeyPair pair = keyDao.findOne(id);
//                    keyPairs.add(pair);
//                }
//                cred.setKeyPairs(keyPairs);
//            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            String jsonInString = mapper.writeValueAsString(cred);
            cloudCred.setValue("\"" + jsonInString + "\"");

            cloudCredentialParams.add(cloudCred);

            return cloudCredentialParams;
        }
        return null;

    }

    private List<MessageParameter> buildCertificatesParam(CloudCredentials cred) {
//        List<String> loginKeysIDs = cred.getkeyPairIDs();
        List<KeyPair> loginKeys = new ArrayList<>();
//        for (String keyID : loginKeysIDs) {
//            KeyPair key = keyDao.findOne(keyID);
//            loginKeys.add(key);
//        }
        if (loginKeys.isEmpty()) {
            throw new BadRequestException("Log in keys can't be empty");
        }
        List<MessageParameter> parameters = new ArrayList<>();
        for (KeyPair lk : loginKeys) {
            String domainName = lk.getPrivateKey().getAttributes().get("domain_name");
            if (domainName == null) {
                domainName = lk.getPrivateKey().getAttributes().get("domain_name ");
            }
            MessageParameter cert = new MessageParameter();
            cert.setName("certificate");
            cert.setValue(lk.getPrivateKey().getKey());
            Map<String, String> attributes = new HashMap<>();
            attributes.put("filename", domainName);
            cert.setAttributes(attributes);
            parameters.add(cert);
        }
        return parameters;
    }

    private List<MessageParameter> buildTopologyParams(String planID) throws JSONException, FileNotFoundException {
        PlanResponse plan = simplePlanService.getDao().findOne(planID);

        if (plan == null) {
            throw new PlanNotFoundException();
        }
        List<MessageParameter> parameters = new ArrayList();
        MessageParameter topology = new MessageParameter();
        topology.setName("topology");
        String val = Converter.map2YmlString(plan.getKeyValue());
        val = val.replaceAll("\\uff0E", ".");
        topology.setValue(val);
        Map<String, String> attributes = new HashMap<>();
        attributes.put("level", String.valueOf(plan.getLevel()));
        attributes.put("filename", FilenameUtils.removeExtension(plan.getName()));
        topology.setAttributes(attributes);
        parameters.add(topology);

        Set<String> ids = plan.getLoweLevelPlanIDs();
        if (ids != null) {
            for (String lowID : ids) {
                PlanResponse lowPlan = simplePlanService.getDao().findOne(lowID);
                topology = new MessageParameter();
                topology.setName("topology");
                String value = Converter.map2YmlString(lowPlan.getKeyValue());
                value = value.replaceAll("\\uff0E", ".");
                topology.setValue(value);
                attributes = new HashMap<>();
                attributes.put("level", String.valueOf(lowPlan.getLevel()));
                attributes.put("filename", FilenameUtils.removeExtension(lowPlan.getName()));
                topology.setAttributes(attributes);
                parameters.add(topology);
            }
        }
        return parameters;
    }

    private List<MessageParameter> buildProvisionedTopologyParams(ProvisionResponse provisionInfo) throws JSONException {
        List<MessageParameter> parameters = new ArrayList();

        Map<String, Object> map = provisionInfo.getKeyValue();
        for (String topoName : map.keySet()) {
            Map<String, Object> topo = (Map<String, Object>) map.get(topoName);
            MessageParameter topology = new MessageParameter();
            topology.setName("topology");
            topology.setValue(Converter.map2YmlString(topo));
            HashMap<String, String> attributes = new HashMap<>();
            if (topoName.equals("topology_main")) {
                attributes.put("level", String.valueOf(0));
            } else {
                attributes.put("level", String.valueOf(1));
            }
            attributes.put("filename", topoName);
            topology.setAttributes(attributes);
            parameters.add(topology);
        }
        return parameters;
    }

    private List<MessageParameter> buildClusterKeyParams(ProvisionResponse provisionInfo) {
        List<MessageParameter> parameters = new ArrayList();
        List<String> ids = provisionInfo.getDeployerKeyPairIDs();
        for (String id : ids) {
            KeyPair pair = keyPairService.findOne(id);

            MessageParameter param = new MessageParameter();
            param.setName("private_deployer_key");
            param.setValue(pair.getPrivateKey().getKey());
            HashMap<String, String> attributes = new HashMap<>();
            if (pair.getPrivateKey() != null && pair.getPrivateKey().getAttributes() != null) {
                attributes.putAll(pair.getPrivateKey().getAttributes());
            }
            attributes.put("name", pair.getPrivateKey().getName());
            param.setAttributes(attributes);
            parameters.add(param);

            param = new MessageParameter();
            param.setName("public_deployer_key");
            param.setValue(pair.getPublicKey().getKey());
            attributes = new HashMap<>();
            if (pair.getPublicKey() != null && pair.getPublicKey().getAttributes() != null) {
                attributes.putAll(pair.getPublicKey().getAttributes());
            }
            param.setAttributes(attributes);
            attributes.put("name", pair.getPublicKey().getName());
            parameters.add(param);
        }

        return parameters;
    }

    private List<MessageParameter> buildUserKeyParams(ProvisionResponse provisionInfo) {
        List<MessageParameter> parameters = new ArrayList();
        List<String> ids = provisionInfo.getUserKeyPairIDs();
        for (String id : ids) {
            KeyPair pair = keyPairService.findOne(id);

            MessageParameter param = new MessageParameter();
            param.setName("private_user_key");
            param.setValue(pair.getPrivateKey().getKey());
            HashMap<String, String> attributes = new HashMap<>();
            if (pair.getPrivateKey().getAttributes() != null) {
                attributes.putAll(pair.getPrivateKey().getAttributes());
            }
            attributes.put("name", pair.getPrivateKey().getName());
            param.setAttributes(attributes);
            parameters.add(param);

            param = new MessageParameter();
            param.setName("public_user_key");
            param.setValue(pair.getPublicKey().getKey());
            if (pair.getPublicKey().getAttributes() != null) {
                attributes.putAll(pair.getPublicKey().getAttributes());
            }
            attributes.put("name", pair.getPublicKey().getName());
            param.setAttributes(attributes);
            parameters.add(param);
        }
        return parameters;
    }

    private List<MessageParameter> buildCloudKeyParams(ProvisionResponse provisionInfo) {
        List<MessageParameter> parameters = new ArrayList();
        List<String> ids = provisionInfo.getCloudKeyPairIDs();
        if (ids != null) {
            for (String id : ids) {
                KeyPair pair = keyPairService.findOne(id);

                MessageParameter param = new MessageParameter();
                param.setName("private_cloud_key");
                param.setValue(pair.getPrivateKey().getKey());
                HashMap<String, String> attributes = new HashMap<>();
                attributes.putAll(pair.getPrivateKey().getAttributes());
                attributes.put("name", pair.getPrivateKey().getName());
                attributes.put("key_pair_id", pair.getKeyPairId());
                param.setAttributes(attributes);
                parameters.add(param);

                param = new MessageParameter();
                param.setName("public_cloud_key");
                param.setValue(pair.getPublicKey().getKey());
                attributes = new HashMap<>();
                attributes.putAll(pair.getPrivateKey().getAttributes());
                attributes.put("name", pair.getPublicKey().getName());
                attributes.put("key_pair_id", pair.getKeyPairId());
                param.setAttributes(attributes);
                parameters.add(param);
            }
        }

        return parameters;

    }

    private MessageParameter buildEC2Conf(CloudCredentials cred) throws JsonProcessingException, JSONException, IOException {
        Properties prop = Converter.getEC2Properties(cred);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        prop.store(baos, null);
        byte[] bytes = baos.toByteArray();
        MessageParameter conf = new MessageParameter();
        conf.setName("ec2.conf");
        String charset = "UTF-8";
        conf.setValue(new String(bytes, charset));
        return conf;

    }

    private List<MessageParameter> buildScriptParams(String userScriptID) {
        Script script = userScriptService.findOne(userScriptID);
        if (script == null) {
            throw new BadRequestException("User script: " + userScriptID + " was not found");
        }
        List<MessageParameter> parameters = new ArrayList();
        MessageParameter scriptParameter = new MessageParameter();
        scriptParameter.setName("guiscript");
        scriptParameter.setValue(script.getContents());
        scriptParameter.setEncoding("UTF-8");
        parameters.add(scriptParameter);
        return parameters;
    }

    private List<MessageParameter> buildUserKeysParams(String userKeyID, int version) {
        KeyPair key = keyPairService.findOne(userKeyID);
        if (key == null) {
            throw new BadRequestException("User key: " + userKeyID + " was not found");
        }
        List<MessageParameter> parameters = new ArrayList();
        MessageParameter keyParameter = new MessageParameter();
        if (version == 0) {
            keyParameter.setName("sshkey");
        }
        if (version == 1) {
            keyParameter.setName("user_ssh_key");
        }
        keyParameter.setValue(key.getPublicKey().getKey());
        keyParameter.setEncoding("UTF-8");
        parameters.add(keyParameter);
        return parameters;
    }

    @PostFilter("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        provisionDao.deleteAll();
    }

    private ProvisionResponse callProvisioner0(ProvisionRequest provisionRequest) throws IOException, TimeoutException, JSONException, InterruptedException, Exception {
        try (DRIPCaller provisioner = new ProvisionerCaller0(messageBrokerHost);) {
            Message provisionerInvokationMessage = buildProvisioner0Message(provisionRequest);
            provisionerInvokationMessage.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            logger.info("Calling provisioner");
            Message response = (provisioner.call(provisionerInvokationMessage));
            logger.info("Got provisioner response");
            return parseCreateResourcesResponse(response.getParameters(), provisionRequest, null, true, true);
        }
    }

    private ProvisionResponse callProvisioner1(ProvisionRequest provisionRequest) throws IOException, TimeoutException, JSONException, InterruptedException, Exception {
        try (DRIPCaller provisioner = new ProvisionerCaller1(messageBrokerHost);) {
            Message provisionerInvokationMessage = buildProvisioner1Message(provisionRequest);
            provisionerInvokationMessage.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            logger.info("Calling provisioner");
            Message response = (provisioner.call(provisionerInvokationMessage));
            logger.info("Got provisioner response");
            return parseCreateResourcesResponse(response.getParameters(), provisionRequest, null, true, true);
        }

    }

    public void deleteProvisionedResources(ProvisionResponse provisionInfo) throws IOException, TimeoutException, InterruptedException, JSONException {
        try (DRIPCaller provisioner = new ProvisionerCaller1(messageBrokerHost);) {
            Message deleteInvokationMessage = buildTopoplogyModificationMessage(provisionInfo, "kill_topology", null);
            deleteInvokationMessage.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            logger.info("Calling provisioner");
            Message response = (provisioner.call(deleteInvokationMessage));
            logger.info("Got provisioner response");
            parseDeleteResourcesResponse(response.getParameters(), provisionInfo);
        }
    }

    public ProvisionResponse scale(ScaleRequest scaleRequest) throws IOException, TimeoutException, JSONException, InterruptedException, Exception {
        String provisionID = scaleRequest.getScaleTargetID();
        String scaleName = scaleRequest.getScaleTargetName();
        if (scaleName == null || scaleName.length() < 1) {
            throw new BadRequestException("Must specify wich topology to scale. \'scaleName\' was empty ");
        }
        ProvisionResponse provisionInfo = findOne(provisionID);
        boolean scaleNameExists = false;
        int currentNumOfInstances = 1;
        Map<String, Object> plan = provisionInfo.getKeyValue();
        String cloudProvider = null;
        String domain = null;
        for (String key : plan.keySet()) {
            Map<String, Object> subMap = (Map<String, Object>) plan.get(key);
            if (subMap.containsKey("topologies")) {
                List< Map<String, Object>> topologies = (List< Map<String, Object>>) subMap.get("topologies");
                for (Map<String, Object> topology : topologies) {
                    if (topology.get("tag").equals("scaling") && topology.get("topology").equals(scaleName) && !scaleNameExists) {
                        cloudProvider = (String) topology.get("cloudProvider");
                        domain = (String) topology.get("domain");
                        scaleNameExists = true;
                    } else if (topology.get("tag").equals("scaled")
                            && topology.get("status").equals("running")
                            && topology.get("copyOf").equals(scaleName)) {
                        currentNumOfInstances++;
                    }
                }
            }
        }

        if (!scaleNameExists) {
            throw new BadRequestException("Name of topology dosen't exist");
        }
        int numOfInstances = Math.abs(currentNumOfInstances - scaleRequest.getNumOfInstances());

        if (currentNumOfInstances > scaleRequest.getNumOfInstances() && currentNumOfInstances != 0) {
            try (DRIPCaller provisioner = new ProvisionerCaller1(messageBrokerHost);) {
                Map<String, String> extra = new HashMap<>();
                extra.put("scale_topology_name", scaleName);
                extra.put("number_of_instances", String.valueOf(numOfInstances));
                extra.put("cloud_provider", cloudProvider);
                extra.put("domain", domain);

                Message scaleMessage = buildTopoplogyModificationMessage(provisionInfo, "scale_topology_down", extra);
                scaleMessage.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
                logger.info("Calling provisioner");
                Message response = provisioner.call(scaleMessage);
                logger.info("Got response from provisioner");
                parseCreateResourcesResponse(response.getParameters(), null, provisionInfo, false, false);
            }
        } else if (currentNumOfInstances < scaleRequest.getNumOfInstances() || currentNumOfInstances == 0) {
            try (DRIPCaller provisioner = new ProvisionerCaller1(messageBrokerHost);) {
                Map<String, String> extra = new HashMap<>();
                extra.put("scale_topology_name", scaleName);
                extra.put("number_of_instances", String.valueOf(numOfInstances));
                extra.put("cloud_provider", cloudProvider);
                extra.put("domain", domain);

                Message scaleMessage = buildTopoplogyModificationMessage(provisionInfo, "scale_topology_up", extra);
                scaleMessage.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
                logger.info("Calling provisioner");
                Message response = provisioner.call(scaleMessage);
                logger.info("Got response from provisioner");
                parseCreateResourcesResponse(response.getParameters(), null, provisionInfo, false, false);
            }
        }
        return provisionInfo;
    }

    private Message buildProvisioner1Message(ProvisionRequest provisionRequest) throws JSONException, FileNotFoundException, IOException {
        Message invokationMessage = new Message();
        List<MessageParameter> parameters = new ArrayList();

        MessageParameter action = new MessageParameter();
        action.setName("action");
        action.setValue("start_topology");
        parameters.add(action);

        List<MessageParameter> topologies = buildTopologyParams(provisionRequest.getPlanID());
        parameters.addAll(topologies);

        List<String> userKeyIDs = provisionRequest.getUserKeyPairIDs();
        if (userKeyIDs != null) {
            for (String keyID : userKeyIDs) {
                List<MessageParameter> userKeys = buildUserKeysParams(keyID, 1);
                parameters.addAll(userKeys);
            }
        }

        List<String> deployerKeys = provisionRequest.getDeployerKeyPairIDs();
        if (userKeyIDs != null) {
            for (String keyID : deployerKeys) {
                List<MessageParameter> deplyMessageKey = buildDeployKeysParams(keyID);
                parameters.addAll(deplyMessageKey);
            }
        }
        for (String id : provisionRequest.getCloudCredentialsIDs()) {
            CloudCredentials cred = cloudCredentialsService.findOne(id);
            if (cred == null) {
                throw new CloudCredentialsNotFoundException();
            }
            List<MessageParameter> cloudCredentialParams = buildCloudCredentialParam(cred, 1);
            parameters.addAll(cloudCredentialParams);
        }

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;

    }

    private Message buildTopoplogyModificationMessage(ProvisionResponse provisionInfo, String actionName, Map<String, String> extraAttributes) throws JSONException, IOException {
        Message invokationMessage = new Message();
        List<MessageParameter> parameters = new ArrayList();

        MessageParameter action = new MessageParameter();
        action.setName("action");
        action.setValue(actionName);
        parameters.add(action);

        List<MessageParameter> topologies = buildProvisionedTopologyParams(provisionInfo);
        parameters.addAll(topologies);

        List<MessageParameter> clusterKeys = buildClusterKeyParams(provisionInfo);
        parameters.addAll(clusterKeys);

        List<MessageParameter> userKeys = buildUserKeyParams(provisionInfo);
        parameters.addAll(userKeys);

        List<MessageParameter> cloudKeys = buildCloudKeyParams(provisionInfo);
        parameters.addAll(cloudKeys);

        for (String id : provisionInfo.getCloudCredentialsIDs()) {
            CloudCredentials cred = cloudCredentialsService.findOne(id);
            if (cred == null) {
                throw new CloudCredentialsNotFoundException();
            }
            List<MessageParameter> cloudCredentialParams = buildCloudCredentialParam(cred, 1);
            parameters.addAll(cloudCredentialParams);
        }
        if (extraAttributes != null && extraAttributes.containsKey("scale_topology_name")) {
            MessageParameter scale = new MessageParameter();
            scale.setName("scale_topology_name");
            scale.setValue(extraAttributes.get("scale_topology_name"));
            Map<String, String> attributes = new HashMap<>();
            attributes.put("number_of_instances", extraAttributes.get("number_of_instances"));
            attributes.put("cloud_provider", extraAttributes.get("cloud_provider"));
            attributes.put("domain", extraAttributes.get("domain"));
            scale.setAttributes(attributes);
            parameters.add(scale);
        }
        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate(System.currentTimeMillis());
        return invokationMessage;
    }

    private List<MessageParameter> buildDeployKeysParams(String keyID) {
        KeyPair key = keyPairService.findOne(keyID);
        if (key == null) {
            throw new BadRequestException("User key: " + keyID + " was not found");
        }
        List<MessageParameter> parameters = new ArrayList();
        MessageParameter keyParameter = new MessageParameter();

        keyParameter.setName("deployer_ssh_key");

        keyParameter.setValue(key.getPublicKey().getKey());
        keyParameter.setEncoding("UTF-8");
        parameters.add(keyParameter);
        return parameters;
    }

    private ProvisionResponse parseCreateResourcesResponse(List<MessageParameter> parameters,
            ProvisionRequest provisionRequest, ProvisionResponse provisionResponse, boolean saveUserKeys, boolean saveDeployerKeyI) throws Exception {
        if (provisionResponse == null) {
            provisionResponse = new ProvisionResponse();
        }

        List<DeployParameter> deployParameters = new ArrayList<>();
        Map<String, Object> kvMap = null;
        KeyPair userKey = new KeyPair();
        KeyPair deployerKey = new KeyPair();

        Map<String, Key> privateCloudKeys = new HashMap<>();
        Map<String, Key> publicCloudKeys = new HashMap<>();

        for (MessageParameter p : parameters) {
            String name = p.getName();
            if (name.toLowerCase().contains("exception")) {
                RuntimeException ex = ExceptionHandler.generateException(name, p.getValue());
                Logger.getLogger(ProvisionController.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            }
            switch (name) {
                case "deploy_parameters":
                    String value = p.getValue();
                    String[] lines = value.split("\n");
                    if (value.length() < 2) {
                        throw new Exception("Provision failed");
//                        this.deleteProvisionedResources(provisionResponse);
                    }
                    for (String line : lines) {
                        DeployParameter deployParam = new DeployParameter();
                        String[] parts = line.split(" ");
                        String deployIP = parts[0];
                        String deployUser = parts[1];
//                        String deployCertPath = parts[2];
//                        String cloudCertificateName = FilenameUtils.removeExtension(FilenameUtils.getBaseName(deployCertPath));
                        String deployRole = parts[2];

                        deployParam.setIP(deployIP);
                        deployParam.setRole(deployRole);
                        deployParam.setUser(deployUser);
//                        deployParam.setCloudCertificateName(cloudCertificateName);
                        deployParameters.add(deployParam);
                    }
                    break;
                case "public_user_key":
                    Key key = new Key();
                    key.setKey(p.getValue());
                    key.setName(p.getAttributes().get("name"));
                    key.setType(Key.KeyType.PUBLIC);
                    userKey.setPublicKey(key);
                    break;
                case "private_user_key":
                    key = new Key();
                    key.setKey(p.getValue());
                    key.setName(p.getAttributes().get("name"));
                    key.setType(Key.KeyType.PRIVATE);
                    userKey.setPrivateKey(key);
                    break;
                case "private_deployer_key":
                    key = new Key();
                    key.setKey(p.getValue());
                    key.setName(p.getAttributes().get("name"));
                    key.setType(Key.KeyType.PRIVATE);
                    deployerKey.setPrivateKey(key);
                    break;
                case "public_deployer_key":
                    key = new Key();
                    key.setKey(p.getValue());
                    key.setName(p.getAttributes().get("name"));
                    key.setType(Key.KeyType.PUBLIC);
                    deployerKey.setPublicKey(key);
                    break;
                case "public_cloud_key":
                    key = new Key();
                    key.setKey(p.getValue());
                    key.setName(p.getAttributes().get("name"));
                    key.setType(Key.KeyType.PUBLIC);
                    key.setAttributes(p.getAttributes());
                    publicCloudKeys.put(p.getAttributes().get("key_pair_id"), key);
                    break;

                case "private_cloud_key":
                    key = new Key();
                    key.setKey(p.getValue());
                    key.setName(p.getAttributes().get("name"));
                    key.setType(Key.KeyType.PRIVATE);
                    key.setAttributes(p.getAttributes());
                    privateCloudKeys.put(p.getAttributes().get("key_pair_id"), key);
                    break;

                default:
                    value = p.getValue();
                    if (kvMap == null) {
                        kvMap = new HashMap();
                    }
                    kvMap.put(name, Converter.ymlString2Map(value));
                    break;
            }
        }
        List<String> userKeyIds = null;
        if (provisionRequest != null) {
            userKeyIds = provisionRequest.getUserKeyPairIDs();
        } else {
            userKeyIds = provisionResponse.getUserKeyPairIDs();
        }

        if (saveUserKeys) {
            if (userKeyIds != null && !userKeyIds.isEmpty()) {
            } else {
                userKeyIds = new ArrayList<>();
                if (userKey.getPublicKey() != null) {
                    userKey = keyPairService.save(userKey);
                    userKeyIds.add(userKey.getId());
                }
            }
        }
        ArrayList<String> deployerKeyIds = null;
        if (saveDeployerKeyI) {
            deployerKeyIds = new ArrayList<>();
            if (deployerKey.getPublicKey() != null) {
                deployerKey = keyPairService.save(deployerKey);
                deployerKeyIds.add(deployerKey.getId());
            }
        }

        ArrayList<String> cloudKeyPairIDs = new ArrayList<>();
        List<KeyPair> allPirs = keyPairService.findAll();
        for (String id : publicCloudKeys.keySet()) {
            boolean save = true;
            String key_pair_id = privateCloudKeys.get(id).getAttributes().get("key_pair_id");
            for (KeyPair p : allPirs) {
                Key pk = p.getPrivateKey();
                if (pk != null && pk.getAttributes() != null && pk.getAttributes().containsKey("key_pair_id")) {
                    if (key_pair_id.equals(pk.getAttributes().get("key_pair_id"))) {
                        save = false;
                        break;
                    }
                }
            }
            if (save) {
                KeyPair cloudPair = new KeyPair();
                cloudPair.setPrivateKey(privateCloudKeys.get(id));
                cloudPair.setPublicKey(publicCloudKeys.get(id));
                cloudPair.setKeyPairId(id);
                cloudPair = keyPairService.save(cloudPair);
                cloudKeyPairIDs.add(cloudPair.getId());
            }

        }
        ArrayList<String> existingCloudKeyPairIDs = provisionResponse.getCloudKeyPairIDs();
        if (existingCloudKeyPairIDs != null) {
            existingCloudKeyPairIDs.addAll(cloudKeyPairIDs);
        } else {
            existingCloudKeyPairIDs = cloudKeyPairIDs;
        }
        provisionResponse.setCloudKeyPairIDs(existingCloudKeyPairIDs);

        provisionResponse.setDeployParameters(deployParameters);
        provisionResponse.setKvMap(kvMap);
        if (provisionRequest != null) {
            provisionResponse.setCloudCredentialsIDs(provisionRequest.getCloudCredentialsIDs());
            provisionResponse.setPlanID(provisionRequest.getPlanID());
        }

        if (userKeyIds != null) {
            provisionResponse.setUserKeyPairIDs(userKeyIds);
        }
        if (deployerKeyIds != null) {
            provisionResponse.setDeployerKeyPairIDs(deployerKeyIds);
        }

        provisionResponse = save(provisionResponse);
        return provisionResponse;
    }

    private void parseDeleteResourcesResponse(List<MessageParameter> parameters, ProvisionResponse provisionInfo) {
    }
}
