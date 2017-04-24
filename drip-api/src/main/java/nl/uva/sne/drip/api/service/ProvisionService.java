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

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.ByteArrayOutputStream;
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
import nl.uva.sne.drip.api.rpc.ProvisionerCaller;
import nl.uva.sne.drip.api.v1.rest.ProvisionController;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.data.v1.external.CloudCredentials;
import nl.uva.sne.drip.data.v1.external.DeployParameter;
import nl.uva.sne.drip.data.internal.Message;
import nl.uva.sne.drip.data.internal.MessageParameter;
import nl.uva.sne.drip.data.v1.external.PlanResponse;
import nl.uva.sne.drip.data.v1.external.ProvisionRequest;
import nl.uva.sne.drip.data.v1.external.ProvisionResponse;
import nl.uva.sne.drip.data.v1.external.Script;
import nl.uva.sne.drip.data.v1.external.User;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import nl.uva.sne.drip.api.dao.ProvisionResponseDao;
import nl.uva.sne.drip.api.dao.KeyPairDao;
import nl.uva.sne.drip.data.v1.external.KeyPair;

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
    private KeyPairDao keyDao;

    @Autowired
    private CloudCredentialsService cloudCredentialsService;

    @Autowired
    private SimplePlannerService planService;

    @Autowired
    private ScriptService userScriptService;

    @Autowired
    private KeyPairService userKeysService;

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    public ProvisionResponse save(ProvisionResponse provision) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        provision.setOwner(owner);
        return provisionDao.save(provision);
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
        provisionDao.delete(provisionInfo);
        return provisionInfo;
    }

//    @PreAuthorize(" (hasRole('ROLE_ADMIN')) or (hasRole('ROLE_USER'))")
    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
//    @PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'admin')")
    public List<ProvisionResponse> findAll() {
        return provisionDao.findAll();
    }

    public ProvisionResponse provisionResources(ProvisionRequest provisionRequest) throws IOException, TimeoutException, JSONException, InterruptedException {
        try (DRIPCaller provisioner = new ProvisionerCaller(messageBrokerHost);) {
            Message provisionerInvokationMessage = buildProvisionerMessage(provisionRequest);

            Message response = (provisioner.call(provisionerInvokationMessage));
//            Message response = MessageGenerator.generateArtificialMessage(System.getProperty("user.home")
//                    + File.separator + "workspace" + File.separator + "DRIP"
//                    + File.separator + "docs" + File.separator + "json_samples"
//                    + File.separator + "ec2_provisioner_provisoned3.json");
            List<MessageParameter> params = response.getParameters();
            ProvisionResponse provisionResponse = new ProvisionResponse();
            provisionResponse.setTimestamp(System.currentTimeMillis());
            for (MessageParameter p : params) {
                String name = p.getName();
                if (name.toLowerCase().contains("exception")) {
                    RuntimeException ex = ExceptionHandler.generateException(name, p.getValue());
                    Logger.getLogger(ProvisionController.class.getName()).log(Level.SEVERE, null, ex);
                    throw ex;
                }

                if (!name.equals("kubernetes")) {
                    String value = p.getValue();
                    Map<String, Object> kvMap = Converter.ymlString2Map(value);
                    provisionResponse.setKvMap(kvMap);
                    provisionResponse.setPlanID(provisionRequest.getPlanID());
                } else {
                    String value = p.getValue();
                    String[] lines = value.split("\n");
                    List<DeployParameter> deployParameters = new ArrayList<>();
                    for (String line : lines) {
                        DeployParameter deployParam = new DeployParameter();
                        String[] parts = line.split(" ");
                        String deployIP = parts[0];

                        String deployUser = parts[1];

                        String deployCertPath = parts[2];
                        String cloudCertificateName = FilenameUtils.removeExtension(FilenameUtils.getBaseName(deployCertPath));
                        String deployRole = parts[3];

                        deployParam.setIP(deployIP);
                        deployParam.setRole(deployRole);
                        deployParam.setUser(deployUser);
                        deployParam.setCloudCertificateName(cloudCertificateName);
                        deployParameters.add(deployParam);
                    }
                    provisionResponse.setDeployParameters(deployParameters);
                }
            }
            provisionResponse.setCloudCredentialsIDs(provisionRequest.getCloudCredentialsIDs());
            provisionResponse.setKeyPairIDs(provisionRequest.getKeyPairIDs());

            provisionResponse = save(provisionResponse);
            return provisionResponse;
        }
    }

    private Message buildProvisionerMessage(ProvisionRequest pReq) throws JSONException, IOException {
        Message invokationMessage = new Message();
        List<MessageParameter> parameters = new ArrayList();
        CloudCredentials cred = cloudCredentialsService.findOne(pReq.getCloudCredentialsIDs().get(0));
        if (cred == null) {
            throw new CloudCredentialsNotFoundException();
        }
        MessageParameter conf = buildCloudConfParam(cred);
        parameters.add(conf);

        List<MessageParameter> certs = buildCertificatesParam(cred);
        parameters.addAll(certs);

        List<MessageParameter> topologies = buildTopologyParams(pReq.getPlanID());
        parameters.addAll(topologies);

        List<String> userKeyIDs = pReq.getKeyPairIDs();
        if (userKeyIDs != null) {
            List<MessageParameter> userKeys = buildKeysParams(userKeyIDs.get(0));
            parameters.addAll(userKeys);
        }

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }

    private MessageParameter buildCloudConfParam(CloudCredentials cred) throws JsonProcessingException, JSONException, IOException {
        MessageParameter conf = null;
        String provider = cred.getCloudProviderName();
        if (provider == null) {
            throw new BadRequestException("Provider name can't be null. Check the cloud credentials: " + cred.getId());
        }
        switch (cred.getCloudProviderName().toLowerCase()) {
            case "ec2":
                conf = buildEC2Conf(cred);
                break;
        }
        return conf;
    }

    private List<MessageParameter> buildCertificatesParam(CloudCredentials cred) {
        List<String> loginKeysIDs = cred.getkeyPairIDs();
        List<KeyPair> loginKeys = new ArrayList<>();
        for (String keyID : loginKeysIDs) {
            KeyPair key = keyDao.findOne(keyID);
            loginKeys.add(key);
        }

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

    private List<MessageParameter> buildTopologyParams(String planID) throws JSONException {
        PlanResponse plan = planService.getDao().findOne(planID);
        if (plan == null) {
            throw new PlanNotFoundException();
        }
        List<MessageParameter> parameters = new ArrayList();
        MessageParameter topology = new MessageParameter();
        topology.setName("topology");
        topology.setValue(Converter.map2YmlString(plan.getKeyValue()));
        Map<String, String> attributes = new HashMap<>();
        attributes.put("level", String.valueOf(plan.getLevel()));
        attributes.put("filename", FilenameUtils.removeExtension(plan.getName()));
        topology.setAttributes(attributes);
        parameters.add(topology);

        Set<String> ids = plan.getLoweLevelPlanIDs();
        for (String lowID : ids) {
            PlanResponse lowPlan = planService.getDao().findOne(lowID);
            topology = new MessageParameter();
            topology.setName("topology");
            topology.setValue(Converter.map2YmlString(lowPlan.getKeyValue()));
            attributes = new HashMap<>();
            attributes.put("level", String.valueOf(lowPlan.getLevel()));
            attributes.put("filename", FilenameUtils.removeExtension(lowPlan.getName()));
            topology.setAttributes(attributes);
            parameters.add(topology);
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

    private List<MessageParameter> buildKeysParams(String userKeyID) {
        KeyPair key = userKeysService.findOne(userKeyID);
        if (key == null) {
            throw new BadRequestException("User key: " + userKeyID + " was not found");
        }
        List<MessageParameter> parameters = new ArrayList();
        MessageParameter keyParameter = new MessageParameter();
        keyParameter.setName("sshkey");
        keyParameter.setValue(key.getPublicKey().getKey());
        keyParameter.setEncoding("UTF-8");
        parameters.add(keyParameter);
        return parameters;

    }

    @PostFilter("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        provisionDao.deleteAll();
    }
}
