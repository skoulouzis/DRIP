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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.api.dao.DeployDao;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
import nl.uva.sne.drip.api.rpc.DeployerCaller;
import nl.uva.sne.drip.api.v1.rest.DeployController;
import nl.uva.sne.drip.drip.commons.data.v1.external.DeployRequest;
import nl.uva.sne.drip.drip.commons.data.v1.external.DeployParameter;
import nl.uva.sne.drip.drip.commons.data.v1.external.DeployResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.Key;
import nl.uva.sne.drip.drip.commons.data.internal.Message;
import nl.uva.sne.drip.drip.commons.data.internal.MessageParameter;
import nl.uva.sne.drip.drip.commons.data.v1.external.ProvisionResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.User;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import nl.uva.sne.drip.api.dao.KeyPairDao;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.KeyException;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.commons.utils.DRIPLogHandler;
import nl.uva.sne.drip.commons.utils.TOSCAUtils;
import nl.uva.sne.drip.drip.commons.data.v1.external.ConfigurationRepresentation;
import nl.uva.sne.drip.drip.commons.data.v1.external.KeyPair;
import nl.uva.sne.drip.drip.commons.data.v1.external.PlanResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.ScaleRequest;
import nl.uva.sne.drip.drip.commons.data.v1.external.ToscaRepresentation;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.AnsibleOutput;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.BenchmarkResult;
import org.json.JSONArray;
import org.json.JSONObject;

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
    private AnsibleOutputService ansibleOutputService;

    @Autowired
    KeyPairDao keyDao;

    @Value("${message.broker.host}")
    private String messageBrokerHost;

//    @Autowired
//    private CloudCredentialsService cloudCredentialsService;
    @Autowired
    private ProvisionService provisionService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private BenchmarkResultService benchmarkResultService;

    @Autowired
    private ToscaService toscaService;

    @Autowired
    private PlannerService plannerService;

    private static final String[] CLOUD_SITE_NAMES = new String[]{"domain", "VMResourceID"};
    private static final String[] PUBLIC_ADRESS_NAMES = new String[]{"public_address", "publicAddress"};
    private final Logger logger;

    @Autowired
    public DeployService(@Value("${message.broker.host}") String messageBrokerHost) throws IOException, TimeoutException {
        logger = Logger.getLogger(DeployService.class.getName());
        logger.addHandler(new DRIPLogHandler(messageBrokerHost));
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public DeployResponse findOne(String id) throws JSONException, IOException, TimeoutException, InterruptedException {
        DeployResponse deployDescription = deployDao.findOne(id);
        if (deployDescription == null) {
            throw new NotFoundException();
        }
        return deployDescription;
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

    public DeployResponse save(DeployResponse ownedObject) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        ownedObject.setOwner(owner);

        return deployDao.save(ownedObject);
    }

    public DeployResponse deploySoftware(DeployRequest deployInfo) throws Exception {
        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
            Message deployerInvokationMessage = buildDeployerMessages(
                    deployInfo,
                    null,
                    null).get(0);
            ;
            deployerInvokationMessage.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

            logger.info("Calling deployer");
            Message response = (deployer.call(deployerInvokationMessage));
            logger.info("Got response from deployer");
            List<MessageParameter> params = response.getParameters();
            DeployResponse deploy = handleResponse(params, deployInfo);
            deploy.setProvisionID(deployInfo.getProvisionID());
//            deploy.setConfigurationID(deployInfo.getConfigurationID());
//            deploy.setManagerType(deployInfo.getManagerType().toLowerCase());
            logger.info("Deployment saved");
            save(deploy);
            return deploy;

        } catch (IOException | TimeoutException | JSONException | InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyException ex) {
            Logger.getLogger(DeployService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Map<String, Object> getSwarmInfo(DeployResponse deployResp) throws JSONException, IOException, TimeoutException, InterruptedException {
//        deployResp.setManagerType("swarm_info");
//        Message deployerInvokationMessage = buildDeployerMessages(
//                deployResp,
//                null,
//                null).get(0);
//        Map<String, Object> info;
//        deployerInvokationMessage.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
//        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
//            logger.info("Calling deployer");
//            Message response = (deployer.call(deployerInvokationMessage));
//            logger.info("Got response from deployer");
//            List<MessageParameter> params = response.getParameters();
//            info = buildSwarmInfo(params);
//        }
        return null;
    }

    private List<Message> buildDeployerMessages(
            DeployRequest deployInfo,
            String serviceName,
            Integer numOfContainers) throws JSONException {
        String provisionID = deployInfo.getProvisionID();

        ProvisionResponse pro = provisionService.findOne(provisionID);
        if (pro == null) {
            throw new NotFoundException();
        }

        List<Message> messages = new ArrayList<>();
        List<MessageParameter> parameters = new ArrayList<>();
        Map<String, Object> toscaProvisonMap = pro.getKeyValue();
        List<String> vmNames = TOSCAUtils.getVMsNodeNamesFromTopology(toscaProvisonMap);
        for (String name : vmNames) {
            Map<String, String> outputs = TOSCAUtils.getOutputsForNode(toscaProvisonMap, name);
            MessageParameter messageParameter = createCredentialPartameter(outputs);
            parameters.add(messageParameter);
        }

        MessageParameter managerTypeParameter = createManagerTypeParameter("kubernetes");
        parameters.add(managerTypeParameter);
        
        List<Map<String, Object>> deployments = TOSCAUtils.tosca2KubernetesDeployment(toscaProvisonMap);
//        String deploymentEncoded = new String(Base64.getDecoder().decode(Converter.map2YmlString(deployments)));
//        MessageParameter confParam = createConfigurationParameter(deploymentEncoded, "kubernetes");
//        parameters.add(confParam);

        Message deployInvokationMessage = new Message();
        deployInvokationMessage.setParameters(parameters);
        deployInvokationMessage.setCreationDate(System.currentTimeMillis());
        messages.add(deployInvokationMessage);
        return messages;
    }

    @PostAuthorize("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        deployDao.deleteAll();
    }

    private MessageParameter createCredentialPartameter(Map<String, String> outputs) {
        MessageParameter messageParameter = new MessageParameter();
        messageParameter.setName("credential");
        messageParameter.setEncoding("UTF-8");
//        This key is configured on all vms fo the 'user_name'
        messageParameter.setValue(outputs.get("private_user_key"));
        Map<String, String> attributes = new HashMap<>();
        attributes.put("IP", outputs.get("ip"));
        attributes.put("role", outputs.get("role"));
        attributes.put("user", outputs.get("user_name"));
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
        return createConfigurationParameter(configurationID, "ansible");
    }

    private MessageParameter createComposerParameter(String configurationID, Map<String, String> dockerLogin) throws JSONException {
        MessageParameter configurationParameter = createConfigurationParameter(configurationID, "composer");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", configurationID);
        if (dockerLogin != null) {
            attributes.put("docker_login_username", dockerLogin.get("username"));
            attributes.put("docker_login_password", dockerLogin.get("password"));
            attributes.put("docker_login_registry", dockerLogin.get("registry"));
        }

        configurationParameter.setAttributes(attributes);
        return configurationParameter;
    }

    private MessageParameter createConfigurationParameter(String configuration, String confType) throws JSONException {
        MessageParameter configurationParameter = new MessageParameter();
        if (confType.equals("ansible")) {
            configurationParameter.setName("playbook");
        } else if (confType.equals("kubernetes")) {
            configurationParameter.setName("deployment");
        } else {
            configurationParameter.setName(confType);
        }

        configurationParameter.setEncoding("UTF-8");
        configurationParameter.setValue(configuration);
        return configurationParameter;
    }

    private MessageParameter createScaleParameter(String configurationID, String serviceName, int numOfContainers) throws JSONException {
        MessageParameter scaleParameter = new MessageParameter();
        scaleParameter.setName("scale");
        scaleParameter.setEncoding("UTF-8");
        scaleParameter.setValue(configurationID);
        Map<String, String> attributes = new HashMap<>();
        attributes.put("service", serviceName);
        attributes.put("number_of_containers", String.valueOf(numOfContainers));
        scaleParameter.setAttributes(attributes);
        return scaleParameter;
    }

    private MessageParameter createSwarmInforparameter(String configurationID, String serviceName) {
        MessageParameter swarmInfoParameter = new MessageParameter();
        swarmInfoParameter.setName("swarm_info");
        swarmInfoParameter.setEncoding("UTF-8");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("service", serviceName);
        attributes.put("name", configurationID);
        swarmInfoParameter.setAttributes(attributes);
        return swarmInfoParameter;
    }

    public DeployResponse scale(ScaleRequest scaleReq) throws IOException, TimeoutException, InterruptedException, JSONException, Exception {
//        //Deployer needs configurationID -> name_of_deployment
//        String deployId = scaleReq.getScaleTargetID();
//        DeployResponse deployment = this.findOne(deployId);
//        String confID = deployment.getConfigurationID();
//        ConfigurationRepresentation configuration = configurationService.findOne(confID);
//        Map<String, Object> map = configuration.getKeyValue();
//        Map<String, Object> services = (Map<String, Object>) map.get("services");
//        if (!services.containsKey(scaleReq.getScaleTargetName())) {
//            throw new BadRequestException("Service name does not exist in this deployment");
//        }
//
//        deployment.setManagerType("scale");
//        Message message = buildDeployerMessages(deployment,
//                scaleReq.getScaleTargetName(),
//                scaleReq.getNumOfInstances()).get(0);
//
//        message.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
//        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
//            logger.info("Calling deployer");
//            Message response = (deployer.call(message));
//            logger.info("Got response from deployer");
//            List<MessageParameter> params = response.getParameters();
//            handleResponse(params, null);
//        }
//        deployment.setScale(scaleReq);
//        deployment.setManagerType("swarm");
//        save(deployment);
        return null;
    }

    private DeployResponse handleResponse(List<MessageParameter> params, DeployRequest deployInfo) throws KeyException, IOException, Exception {
        DeployResponse deployResponse = new DeployResponse();
        deployResponse.setProvisionID(deployInfo.getProvisionID());
        deployResponse.setKvMap(provisionService.findOne(deployInfo.getProvisionID()).getKeyValue());
        for (MessageParameter p : params) {
            String name = p.getName();

            if (name.equals("credential")) {
                String value = p.getValue();
                Key k = new Key();
                k.setKey(value);
                k.setType(Key.KeyType.PRIVATE);
                KeyPair pair = new KeyPair();
                pair.setPrivateKey(k);
//                deployResponse.setKey(pair);
                save(deployResponse);
                return deployResponse;
            }
            if (name.equals("ansible_output")) {
                String value = p.getValue();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                value = parseValue(value);

                List<AnsibleOutput> outputList = mapper.readValue(value, new TypeReference<List<AnsibleOutput>>() {
                });

                List<String> outputListIds = new ArrayList<>();
                Map<String, String> nodeTypeCache = new HashMap<>();
                Map<String, String> domainCache = new HashMap<>();
                Map<String, String> osTypeCache = new HashMap<>();
                Map<String, String> cloudProviderCache = new HashMap<>();

                for (AnsibleOutput ansOut : outputList) {
                    Map<String, Object> map = provisionService.findOne(deployInfo.getProvisionID()).getKeyValue();
                    String nodeType = nodeTypeCache.get(ansOut.getHost());
                    String domain = domainCache.get(ansOut.getHost());
                    String os = osTypeCache.get(ansOut.getHost());
                    if (nodeType == null) {
                        List<Map<String, Object>> components = null;
                        List<Map<String, Object>> topologies = null;
                        if (map.containsKey("components")) {
                            components = (List<Map<String, Object>>) map.get("components");
//                            topologies = (List<Map<String, Object>>) map.get("topologies");
                        } else {
                            for (String key : map.keySet()) {
                                Map<String, Object> subMap = (Map<String, Object>) map.get(key);
                                if (subMap.containsKey("components") && components == null) {
                                    components = (List<Map<String, Object>>) subMap.get("components");
                                }
                                if (subMap.containsKey("topologies") && topologies == null) {
                                    topologies = (List<Map<String, Object>>) subMap.get("topologies");
                                }
                                if (components != null && topologies != null) {
                                    break;
                                }
                            }
                        }

                        for (Map<String, Object> component : components) {
                            String publicAddress = null;
                            for (String addressName : PUBLIC_ADRESS_NAMES) {
                                if (component.containsKey(addressName)) {
                                    publicAddress = (String) component.get(addressName);
                                    break;
                                }
                            }
                            if (publicAddress != null && publicAddress.equals(ansOut.getHost())) {
                                nodeType = (String) component.get("nodeType");
                                for (String siteName : CLOUD_SITE_NAMES) {
                                    if (component.containsKey(siteName)) {
                                        domain = (String) component.get(siteName);
                                        break;
                                    }
                                }
                                os = (String) component.get("OStype");

                                nodeTypeCache.put(ansOut.getHost(), nodeType);
                                domainCache.put(ansOut.getHost(), domain);
                                osTypeCache.put(ansOut.getHost(), os);
                                break;
                            }
                        }
                    }
                    ansOut.setVmType(nodeType);
                    ansOut.setCloudDeploymentDomain(domain);
                    ansOut.setProvisionID(deployInfo.getProvisionID());
//                    ansOut.setCloudProvider(provider); 

                    ansOut = ansibleOutputService.save(ansOut);
                    BenchmarkResult benchmarkResult = parseSaveBenchmarkResult(ansOut);

                    outputListIds.add(ansOut.getId());
                }
//                deployResponse.setAnsibleOutputList(outputListIds);
            }
        }
        return deployResponse;
    }

    private BenchmarkResult parseSaveBenchmarkResult(AnsibleOutput ansOut) {
        return null;
    }

    private String parseValue(String value) throws JSONException {
        JSONArray ja = new JSONArray(value);
        JSONArray newJa = new JSONArray();

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            JSONObject result = ((JSONObject) jo.get("result"));
            if (result.has("msg")) {
                String msg;
                try {
                    msg = (String) result.get("msg");
                } catch (java.lang.ClassCastException ex) {
                    JSONObject message = (JSONObject) result.get("msg");
                    msg = Converter.jsonObject2String(message.toString());
                    result.put("msg", msg);
                    jo.put("result", result);
                }
            }
            if (result.has("invocation")) {
                JSONObject invocation = ((JSONObject) (result).get("invocation"));
                if (invocation.has("module_args")) {
                    JSONObject module_args = (JSONObject) invocation.get("module_args");
                    if (module_args.has("msg")) {
                        String msg;
                        try {
                            msg = (String) module_args.get("msg");
                        } catch (java.lang.ClassCastException ex) {
                            JSONObject message = (JSONObject) module_args.get("msg");
                            msg = Converter.jsonObject2String(message.toString());
                            module_args.put("msg", msg);
                            invocation.put("module_args", module_args);
                        }
                    }
                }
                if (invocation.has("msg")) {
                    String msg;
                    try {
                        msg = (String) invocation.get("msg");
                    } catch (java.lang.ClassCastException ex) {
                        JSONObject message = (JSONObject) invocation.get("msg");
                        msg = Converter.jsonObject2String(message.toString());
                        invocation.put("msg", msg);
                        result.put("invocation", invocation);
                    }
                }
            }

            newJa.put(jo);
        }
        return newJa.toString();
    }

    private Map<String, Object> buildSwarmInfo(List<MessageParameter> params) throws JSONException, IOException {
        Map<String, Object> info = new HashMap();
        for (MessageParameter param : params) {
            String jsonResp = param.getValue().replaceAll("^\"|\"$", "");
            Map<String, Object> kv = Converter.jsonString2Map(jsonResp);

            info.putAll(kv);
        }
        return info;
    }

    public List<String> getServiceNames(String id) throws JSONException, IOException, TimeoutException, InterruptedException {
        DeployResponse resp = findOne(id);
//        if (resp.getManagerType().equals("swarm")) {
//            Map<String, Object> swarmInfo = getSwarmInfo(resp);
//
//            List< Map<String, Object>> stackInfo = (List) swarmInfo.get("stack_info");
//            List<String> serviceNames = new ArrayList<>();
//            for (Map<String, Object> map : stackInfo) {
//                if (map.containsKey("name")) {
//                    serviceNames.add(((String) map.get("name")));
//                }
//            }
//            return serviceNames;
//        }
        return null;
    }

    public DeployResponse getContainersStatus(String id, String serviceName) throws JSONException, IOException, TimeoutException, InterruptedException {
        DeployResponse resp = findOne(id);
        Map<String, Object> result = new HashMap<>();
//        if (resp.getManagerType().equals("swarm")) {
//            Map<String, Object> swarmInfo = getSwarmInfo(resp);
//
//            List< Map<String, Object>> servicesInfo = (List) swarmInfo.get("services_info");
//            List<String> taskIDs = new ArrayList<>();
//            for (Map<String, Object> map : servicesInfo) {
//                if (map.containsKey("name") && ((String) map.get("name")).startsWith(serviceName)) {
//                    taskIDs.add(((String) map.get("ID")));
//                }
//            }
//            List< Map<String, Object>> inspecInfo = (List) swarmInfo.get("inspect_info");
//            List< Map<String, Object>> inspecInfoResult = new ArrayList<>();
//            for (String taskID : taskIDs) {
//                for (Map<String, Object> map : inspecInfo) {
//                    if (map.containsKey("ID") && ((String) map.get("ID")).startsWith(taskID)) {
//                        inspecInfoResult.add((Map<String, Object>) map.get("Status"));
//                    }
//                }
//            }
//            result.put("inspect_info", inspecInfoResult);
//            resp.setManagerInfo(result);
//            resp.setKey(null);
//            resp.setScale(null);
//        }
        return resp;
    }

    private Map<String, String> getDockerLogin(ProvisionResponse pro) {
        String planID = pro.getPlanID();
        PlanResponse plan = plannerService.findOne(planID);
        String toscaID = plan.getToscaID();
        if (toscaID != null) {
            ToscaRepresentation tosca = toscaService.findOne(plan.getToscaID());
            Map<String, Object> map = tosca.getKeyValue();
            map.get("repositories");
            HashMap dockerLogin = new HashMap();
            return dockerLogin;
        }
        return null;
    }

    public String get(String id, String format) throws JSONException, IOException, TimeoutException, InterruptedException {
        DeployResponse deploy = findOne(id);
        Map<String, Object> map = deploy.getKeyValue();
        if (format != null && format.equals("yml")) {
            String ymlStr = Converter.map2YmlString(map);
            ymlStr = ymlStr.replaceAll("\\uff0E", ".");
            return ymlStr;
        }
        if (format != null && format.equals("json")) {
            String jsonStr = Converter.map2JsonString(map);
            jsonStr = jsonStr.replaceAll("\\uff0E", ".");
            return jsonStr;
        }
        String ymlStr = Converter.map2YmlString(map);
        ymlStr = ymlStr.replaceAll("\\uff0E", ".");
        return ymlStr;

    }

}
