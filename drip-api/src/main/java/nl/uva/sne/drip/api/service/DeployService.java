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
import nl.uva.sne.drip.drip.commons.data.v1.external.ConfigurationRepresentation;
import nl.uva.sne.drip.drip.commons.data.v1.external.KeyPair;
import nl.uva.sne.drip.drip.commons.data.v1.external.KeyValueHolder;
import nl.uva.sne.drip.drip.commons.data.v1.external.PlanResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.ScaleRequest;
import nl.uva.sne.drip.drip.commons.data.v1.external.ToscaRepresentation;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.AnsibleOutput;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.BenchmarkResult;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.SysbenchCPUBenchmark;
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
            deploy.setConfigurationID(deployInfo.getConfigurationID());
            deploy.setManagerType(deployInfo.getManagerType().toLowerCase());
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
        deployResp.setManagerType("swarm_info");
        Message deployerInvokationMessage = buildDeployerMessages(
                deployResp,
                null,
                null).get(0);
        Map<String, Object> info;
        deployerInvokationMessage.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
            logger.info("Calling deployer");
            Message response = (deployer.call(deployerInvokationMessage));
            logger.info("Got response from deployer");
            List<MessageParameter> params = response.getParameters();
            info = buildSwarmInfo(params);
        }
        return info;
    }

    private List<Message> buildDeployerMessages(
            DeployRequest deployInfo,
            String serviceName,
            Integer numOfContainers) throws JSONException {
        String provisionID = deployInfo.getProvisionID();
        String managerType = deployInfo.getManagerType();
        String configurationID = deployInfo.getConfigurationID();

        ProvisionResponse pro = provisionService.findOne(provisionID);
        if (pro == null) {
            throw new NotFoundException();
        }
        List<String> loginKeysIDs = pro.getDeployerKeyPairIDs();

        List<Message> messages = new ArrayList<>();
//        if (loginKeysIDs == null || loginKeysIDs.isEmpty()) {
//            List<String> cloudConfIDs = pro.getCloudCredentialsIDs();
//            CloudCredentials cCred = cloudCredentialsService.findOne(cloudConfIDs.get(0));
//            loginKeysIDs = cCred.getkeyPairIDs();
//        }
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

        if (managerType.toLowerCase().equals("swarm") && configurationID != null) {
            Map<String, String> dockerLogin = getDockerLogin(pro);

            MessageParameter composerParameter = createComposerParameter(configurationID, dockerLogin);
            parameters.add(composerParameter);
        }

        if (managerType.toLowerCase().equals("scale") && configurationID != null) {
            MessageParameter scaleParameter = createScaleParameter(configurationID, serviceName, numOfContainers);
            parameters.add(scaleParameter);
        }
        if (managerType.toLowerCase().equals("swarm_info") && configurationID != null) {
            MessageParameter swarmInfo = createSwarmInforparameter(configurationID, serviceName);
            parameters.add(swarmInfo);
        }
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

    private MessageParameter createCredentialPartameter(DeployParameter dp, List<KeyPair> loginKeys) {
//        String cName = dp.getCloudCertificateName();
        MessageParameter messageParameter = new MessageParameter();
        messageParameter.setName("credential");
        messageParameter.setEncoding("UTF-8");
        String key = null;
        for (KeyPair lk : loginKeys) {
//            String lkName = lk.getPrivateKey().getAttributes().get("domain_name");
//            if (lkName.equals(cName)) {
            key = lk.getPrivateKey().getKey();
//                break;
//            }
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

    private MessageParameter createConfigurationParameter(String configurationID, String confType) throws JSONException {
        String configuration = configurationService.get(configurationID, "yml");
        MessageParameter configurationParameter = new MessageParameter();
        if (confType.equals("ansible")) {
            configurationParameter.setName("playbook");
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
        //Deployer needs configurationID -> name_of_deployment
        String deployId = scaleReq.getScaleTargetID();
        DeployResponse deployment = this.findOne(deployId);
        String confID = deployment.getConfigurationID();
        ConfigurationRepresentation configuration = configurationService.findOne(confID);
        Map<String, Object> map = configuration.getKeyValue();
        Map<String, Object> services = (Map<String, Object>) map.get("services");
        if (!services.containsKey(scaleReq.getScaleTargetName())) {
            throw new BadRequestException("Service name does not exist in this deployment");
        }

        deployment.setManagerType("scale");
        Message message = buildDeployerMessages(deployment,
                scaleReq.getScaleTargetName(),
                scaleReq.getNumOfInstances()).get(0);

        message.setOwner(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
            logger.info("Calling deployer");
            Message response = (deployer.call(message));
            logger.info("Got response from deployer");
            List<MessageParameter> params = response.getParameters();
            handleResponse(params, null);
        }
        deployment.setScale(scaleReq);
        deployment.setManagerType("swarm");
        save(deployment);
        return deployment;
    }

    private DeployResponse handleResponse(List<MessageParameter> params, DeployRequest deployInfo) throws KeyException, IOException, Exception {
        DeployResponse deployResponse = new DeployResponse();

        for (MessageParameter p : params) {
            String name = p.getName();

            if (name.equals("credential")) {
                String value = p.getValue();
                Key k = new Key();
                k.setKey(value);
                k.setType(Key.KeyType.PRIVATE);
                KeyPair pair = new KeyPair();
                pair.setPrivateKey(k);
                deployResponse.setKey(pair);
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
                deployResponse.setAnsibleOutputList(outputListIds);
            }
        }
        return deployResponse;
    }

    private BenchmarkResult parseSaveBenchmarkResult(AnsibleOutput ansOut) {
//        KeyValueHolder res = ansOut.getAnsibleResult();
//        if (res != null) {
//            List<String> cmdList = res.getCmd();
//            if (cmdList != null) {
//
//                switch (cmdList.get(0)) {
//                    case "sysbench":
//                        String[] out = res.getStdout().split("\n");
//                        String version = getSysbeanchVersion(out[0]);
//                        int numOfThreads = getNumberOfThreads(out[3]);
//                        Double executionTime = getExecutionTime(out[14]);
//                        int totalNumberOfEvents = getTotalNumberOfEvents(out[15]);
//
//                        double minExecutionTimePerRequest = getMinExecutionTimePerRequest(out[18]);
//                        double avgExecutionTimePerRequest = getAvgExecutionTimePerRequest(out[19]);
//                        double maxExecutionTimePerRequest = getMaxExecutionTimePerRequest(out[20]);
//                        double approx95Percentile = getApprox95Percentile(out[21]);
//
//                        double avgEventsPerThread = getAvgEventsPerThread(out[24]);
//                        double stddevEventsPerThread = getStddevEventsPerThread(out[24]);
//
//                        double avgExecTimePerThread = getAvgExecTimePerThread(out[25]);
//                        double stddevExecTimePerThread = getStddevExecTimePerThread(out[25]);
//
//                        SysbenchCPUBenchmark b = new SysbenchCPUBenchmark();
//                        b.setSysbenchVersion(version);
//
//                        b.setNumberOfThreads(numOfThreads);
//                        b.setExecutionTime(executionTime * 1000);
//
//                        b.setTotalNumberOfEvents(totalNumberOfEvents);
//
//                        b.setAvgEventsPerThread(avgEventsPerThread);
//                        b.setStddevEventsPerThread(stddevEventsPerThread);
//
//                        b.setAvgExecTimePerThread(avgExecTimePerThread * 1000);
//                        b.setStddevExecTimePerThread(stddevExecTimePerThread);
//                        b.setApprox95Percentile(approx95Percentile);
//
//                        b.setMinExecutionTimePerRequest(minExecutionTimePerRequest);
//                        b.setAvgExecutionTimePerRequest(avgExecutionTimePerRequest);
//                        b.setMaxExecutionTimePerRequest(maxExecutionTimePerRequest);
//
//                        b.setAnsibleOutputID(ansOut.getId());
//
//                        b.setCloudDeploymentDomain(ansOut.getCloudDeploymentDomain());
//                        b.setHost(ansOut.getHost());
//                        b.setVmType(ansOut.getVmType());
//                        b = (SysbenchCPUBenchmark) benchmarkResultService.save(b);
//                        return b;
//
//                    default:
//                        return null;
//
//                }
//            }
//        }
        return null;
    }

    private String getSysbeanchVersion(String string) {
        return string.replaceAll("sysbench", "").replaceAll(":  multi-threaded system evaluation benchmark", "");
    }

    private int getNumberOfThreads(String string) {
        return Integer.valueOf(string.replaceAll("Number of threads: ", ""));
    }

    private Double getExecutionTime(String string) {
        return Double.valueOf(string.replaceAll("total time:", "").replaceAll("s", "").trim());
    }

    private int getTotalNumberOfEvents(String string) {
        return Integer.valueOf(string.replaceAll("total number of events:", "").replaceAll("s", "").trim());
    }

    private Double getAvgEventsPerThread(String string) {
        return Double.valueOf(string.replaceAll("events \\(avg/stddev\\):", "").trim().split("/")[0]);
    }

    private Double getStddevEventsPerThread(String string) {
        return Double.valueOf(string.replaceAll("events \\(avg/stddev\\):", "").trim().split("/")[1]);
    }

    private Double getAvgExecTimePerThread(String string) {
        return Double.valueOf(string.replaceAll("execution time \\(avg/stddev\\):", "").trim().split("/")[0]);
    }

    private Double getStddevExecTimePerThread(String string) {
        return Double.valueOf(string.replaceAll("execution time \\(avg/stddev\\):", "").trim().split("/")[1]);
    }

    private double getMinExecutionTimePerRequest(String string) {
        return Double.valueOf(string.replaceAll("min:", "").replaceAll("ms", "").trim());
    }

    private double getAvgExecutionTimePerRequest(String string) {
        return Double.valueOf(string.replaceAll("avg:", "").replaceAll("ms", "").trim());
    }

    private double getMaxExecutionTimePerRequest(String string) {
        return Double.valueOf(string.replaceAll("max:", "").replaceAll("ms", "").trim());
    }

    private double getApprox95Percentile(String string) {
        return Double.valueOf(string.replaceAll("approx.  95 percentile:", "").replaceAll("ms", "").trim());
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
        if (resp.getManagerType().equals("swarm")) {
            Map<String, Object> swarmInfo = getSwarmInfo(resp);

            List< Map<String, Object>> stackInfo = (List) swarmInfo.get("stack_info");
            List<String> serviceNames = new ArrayList<>();
            for (Map<String, Object> map : stackInfo) {
                if (map.containsKey("name")) {
                    serviceNames.add(((String) map.get("name")));
                }
            }
            return serviceNames;
        }
        return null;
    }

    public DeployResponse getContainersStatus(String id, String serviceName) throws JSONException, IOException, TimeoutException, InterruptedException {
        DeployResponse resp = findOne(id);
        Map<String, Object> result = new HashMap<>();
        if (resp.getManagerType().equals("swarm")) {
            Map<String, Object> swarmInfo = getSwarmInfo(resp);

            List< Map<String, Object>> servicesInfo = (List) swarmInfo.get("services_info");
            List<String> taskIDs = new ArrayList<>();
            for (Map<String, Object> map : servicesInfo) {
                if (map.containsKey("name") && ((String) map.get("name")).startsWith(serviceName)) {
                    taskIDs.add(((String) map.get("ID")));
                }
            }
            List< Map<String, Object>> inspecInfo = (List) swarmInfo.get("inspect_info");
            List< Map<String, Object>> inspecInfoResult = new ArrayList<>();
            for (String taskID : taskIDs) {
                for (Map<String, Object> map : inspecInfo) {
                    if (map.containsKey("ID") && ((String) map.get("ID")).startsWith(taskID)) {
                        inspecInfoResult.add((Map<String, Object>) map.get("Status"));
                    }
                }
            }
            result.put("inspect_info", inspecInfoResult);
            resp.setManagerInfo(result);
            resp.setKey(null);
            resp.setScale(null);
        }
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

}
