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
import java.io.File;
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
import nl.uva.sne.drip.api.dao.ProvisionInfoDao;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.CloudCredentialsNotFoundException;
import nl.uva.sne.drip.api.exception.ExceptionHandler;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.exception.PlanNotFoundException;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
import nl.uva.sne.drip.api.rpc.ProvisionerCaller;
import nl.uva.sne.drip.api.v1.rest.ProvisionController;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.commons.v1.types.CloudCredentials;
import nl.uva.sne.drip.commons.v1.types.DeployParameter;
import nl.uva.sne.drip.commons.v1.types.LoginKey;
import nl.uva.sne.drip.commons.v1.types.Message;
import nl.uva.sne.drip.commons.v1.types.MessageParameter;
import nl.uva.sne.drip.commons.v1.types.Plan;
import nl.uva.sne.drip.commons.v1.types.ProvisionInfo;
import nl.uva.sne.drip.commons.v1.types.Script;
import nl.uva.sne.drip.commons.v1.types.User;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class ProvisionService {

    @Autowired
    private ProvisionInfoDao dao;

    @Autowired
    private CloudCredentialsService cloudCredentialsService;

    @Autowired
    private SimplePlannerService planService;

    @Autowired
    private UserScriptService userScriptService;

    @Autowired
    private UserKeyService userKeysService;

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    public ProvisionInfo save(ProvisionInfo provisionInfo) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        provisionInfo.setOwner(owner);
        return dao.save(provisionInfo);
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public ProvisionInfo findOne(String id) {
        ProvisionInfo provisionInfo = dao.findOne(id);
        if (provisionInfo == null) {
            throw new NotFoundException();
        }
        return provisionInfo;
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public ProvisionInfo delete(String id) {
        ProvisionInfo provisionInfo = dao.findOne(id);
        dao.delete(provisionInfo);
        return provisionInfo;
    }

//    @PreAuthorize(" (hasRole('ROLE_ADMIN')) or (hasRole('ROLE_USER'))")
    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
//    @PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'admin')")
    public List<ProvisionInfo> findAll() {
        return dao.findAll();
    }

    public ProvisionInfo provisionResources(ProvisionInfo req) throws IOException, TimeoutException, JSONException, InterruptedException {
        try (DRIPCaller provisioner = new ProvisionerCaller(messageBrokerHost);) {
            Message provisionerInvokationMessage = buildProvisionerMessage(req);

            Message response = (provisioner.call(provisionerInvokationMessage));
//            Message response = generateFakeResponse(System.getProperty("user.home")
//                    + File.separator + "workspace" + File.separator + "DRIP"
//                    + File.separator + "doc" + File.separator + "json_samples"
//                    + File.separator + "ec2_provisioner_provisoned2.json");
            List<MessageParameter> params = response.getParameters();

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
                    req.setKvMap(kvMap);
                    req.setPlanID(req.getPlanID());
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
                    req.setDeployParameters(deployParameters);
                }
            }
            req = save(req);
            return req;
        }
    }

    private Message buildProvisionerMessage(ProvisionInfo pReq) throws JSONException, IOException {
        Message invokationMessage = new Message();
        List<MessageParameter> parameters = new ArrayList();
        CloudCredentials cred = cloudCredentialsService.findOne(pReq.getCloudCredentialsID());
        if (cred == null) {
            throw new CloudCredentialsNotFoundException();
        }
        MessageParameter conf = buildCloudConfParam(cred);
        parameters.add(conf);

        List<MessageParameter> certs = buildCertificatesParam(cred);
        parameters.addAll(certs);

        List<MessageParameter> topologies = buildTopologyParams(pReq.getPlanID());
        parameters.addAll(topologies);

        String scriptID = pReq.getscriptID();
        if (scriptID != null) {
            List<MessageParameter> userScripts = buildScriptParams(scriptID);
            parameters.addAll(userScripts);
        }

        String userKeyID = pReq.getUserKeyID();
        if (userKeyID != null) {
            List<MessageParameter> userKeys = buildKeysParams(userKeyID);
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
        List<LoginKey> loginKeys = cred.getLoginKeys();
        if (loginKeys == null || loginKeys.isEmpty()) {
            throw new BadRequestException("Log in keys can't be empty");
        }
        List<MessageParameter> parameters = new ArrayList<>();
        for (LoginKey lk : loginKeys) {
            String domainName = lk.getAttributes().get("domain_name");
            if (domainName == null) {
                domainName = lk.getAttributes().get("domain_name ");
            }
            MessageParameter cert = new MessageParameter();
            cert.setName("certificate");
            cert.setValue(lk.getKey());
            Map<String, String> attributes = new HashMap<>();
            attributes.put("filename", domainName);
            cert.setAttributes(attributes);
            parameters.add(cert);
        }
        return parameters;
    }

    private List<MessageParameter> buildTopologyParams(String planID) throws JSONException {
        Plan plan = planService.getDao().findOne(planID);
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
            Plan lowPlan = planService.getDao().findOne(lowID);
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
        LoginKey key = userKeysService.get(userKeyID, LoginKey.Type.PUBLIC);
        if (key == null) {
            throw new BadRequestException("User key: " + userKeyID + " was not found");
        }
        List<MessageParameter> parameters = new ArrayList();
        MessageParameter keyParameter = new MessageParameter();
        keyParameter.setName("sshkey");
        keyParameter.setValue(key.getKey());
        keyParameter.setEncoding("UTF-8");
        parameters.add(keyParameter);
        return parameters;

    }

    private Message generateFakeResponse(String path) throws IOException, TimeoutException, InterruptedException, JSONException {
//        String strResponse = "{\"creationDate\":1488368936945,\"parameters\":["
//                + "{\"name\":\"f293ff03-4b82-49e2-871a-899aadf821ce\","
//                + "\"encoding\":\"UTF-8\",\"value\":"
//                + "\"publicKeyPath: /tmp/Input-4007028381500/user.pem\\nuserName: "
//                + "zh9314\\nsubnets:\\n- {name: s1, subnet: 192.168.10.0, "
//                + "netmask: 255.255.255.0}\\ncomponents:\\n- "
//                + "name: faab6756-61b6-4800-bffa-ae9d859a9d6c\\n  "
//                + "type: Switch.nodes.Compute\\n  nodetype: t2.medium\\n  "
//                + "OStype: Ubuntu 16.04\\n  domain: ec2.us-east-1.amazonaws.com\\n  "
//                + "script: /tmp/Input-4007028381500/guiscipt.sh\\n  "
//                + "installation: null\\n  role: master\\n  "
//                + "dockers: mogswitch/InputDistributor\\n  "
//                + "public_address: 54.144.0.91\\n  instanceId: i-0e78cbf853328b820\\n  "
//                + "ethernet_port:\\n  - {name: p1, subnet_name: s1, "
//                + "address: 192.168.10.10}\\n- name: 1c75eedf-8497-46fe-aeb8-dab6a62154cb\\n  "
//                + "type: Switch.nodes.Compute\\n  nodetype: t2.medium\\n  OStype: Ubuntu 16.04\\n  domain: ec2.us-east-1.amazonaws.com\\n  script: /tmp/Input-4007028381500/guiscipt.sh\\n  installation: null\\n  role: slave\\n  dockers: mogswitch/ProxyTranscoder\\n  public_address: 34.207.254.160\\n  instanceId: i-0a99ea18fcc77ed7a\\n  ethernet_port:\\n  - {name: p1, subnet_name: s1, address: 192.168.10.11}\\n\"},{\"name\":\"kubernetes\",\"encoding\":\"UTF-8\",\"value\":\"54.144.0.91 ubuntu /tmp/Input-4007028381500/Virginia.pem master\\n34.207.254.160 ubuntu /tmp/Input-4007028381500/Virginia.pem slave\\n\"}]}";
//        String strResponse = "{\"creationDate\":1488805337447,\"parameters\":[{\"name\":\"2e5dafb6-5a1c-4a66-9dca-5841f99ea735\",\"encoding\":\"UTF-8\",\"value\":\"publicKeyPath: /tmp/Input-11594765342486/user.pem\\nuserName: zh9314\\nsubnets:\\n- {name: s1, subnet: 192.168.10.0, netmask: 255.255.255.0}\\ncomponents:\\n- name: 8fcc1788d9ee462c826572c79fdb2a6a\\n  type: Switch.nodes.Compute\\n  nodeType: t2.medium\\n  OStype: Ubuntu 16.04\\n  script: /tmp/Input-11594765342486/guiscipt.sh\\n  domain: ec2.us-east-1.amazonaws.com\\n  installation: null\\n  clusterType: swarm\\n  role: master\\n  dockers: mogswitch/ProxyTranscoder:1.0\\n  public_address: 34.207.73.18\\n  instanceId: i-0e82b5624a0df99b1\\n  ethernet_port:\\n  - {name: p1, subnet_name: s1, address: 192.168.10.10}\\n- name: 8fcc1788d9ee462c826572c79fdb2a6a\\n  type: Switch.nodes.Compute\\n  nodeType: t2.medium\\n  OStype: Ubuntu 16.04\\n  script: /tmp/Input-11594765342486/guiscipt.sh\\n  domain: ec2.us-east-1.amazonaws.com\\n  installation: null\\n  clusterType: swarm\\n  role: slave\\n  dockers: mogswitch/ProxyTranscoder:1.0\\n  public_address: 34.207.73.18\\n  instanceId: i-0e82b5624a0df99b1\\n  ethernet_port:\\n  - {name: p1, subnet_name: s1, address: 192.168.10.11}\\n\"},{\"name\":\"kubernetes\",\"encoding\":\"UTF-8\",\"value\":\"34.207.73.18 ubuntu /tmp/Input-11594765342486/Virginia.pem master\\n34.207.73.18 ubuntu /tmp/Input-11594765342486/Virginia.pem slave\\n\"}]}";
        String strResponse = FileUtils.readFileToString(new File(path));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        return mapper.readValue(strResponse, Message.class);
    }

    @PostFilter("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        dao.deleteAll();
    }
}
