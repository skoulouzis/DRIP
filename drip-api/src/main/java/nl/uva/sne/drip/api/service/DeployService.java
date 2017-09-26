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
import nl.uva.sne.drip.drip.commons.data.v1.external.ConfigurationRepresentation;
import nl.uva.sne.drip.drip.commons.data.v1.external.KeyPair;
import nl.uva.sne.drip.drip.commons.data.v1.external.ScaleRequest;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.AnsibleOutput;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.AnsibleResult;
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

    private static final String[] CLOUD_SITE_NAMES = new String[]{"domain", "VMResourceID"};
    private static final String[] PUBLIC_ADRESS_NAMES = new String[]{"public_address", "publicAddress"};

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

    public DeployResponse deploySoftware(DeployRequest deployInfo) throws Exception {
        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
            Message deployerInvokationMessage = buildDeployerMessage(
                    deployInfo.getProvisionID(),
                    deployInfo.getManagerType().toLowerCase(),
                    deployInfo.getConfigurationID(),
                    null,
                    null);

//            Message response = MessageGenerator.generateArtificialMessage(System.getProperty("user.home")
//                    + File.separator + "workspace" + File.separator + "DRIP"
//                    + File.separator + "docs" + File.separator + "json_samples"
//                    + File.separator + "deployer_ansible_response_benchmark.json");
            Message response = (deployer.call(deployerInvokationMessage));
            List<MessageParameter> params = response.getParameters();
            DeployResponse deploy = handleResponse(params, deployInfo);
            deploy.setProvisionID(deployInfo.getProvisionID());
            deploy.setConfigurationID(deployInfo.getConfigurationID());
            deploy.setManagerType(deployInfo.getManagerType().toLowerCase());
            save(deploy);
            return deploy;

        } catch (IOException | TimeoutException | JSONException | InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyException ex) {
            Logger.getLogger(DeployService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Message buildDeployerMessage(String provisionID, String managerType, String configurationID, String serviceName, Integer numOfCont) throws JSONException {
        ProvisionResponse pro = provisionService.findOne(provisionID);
        if (pro == null) {
            throw new NotFoundException();
        }
        List<String> loginKeysIDs = pro.getDeployerKeyPairIDs();

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
            MessageParameter composerParameter = createComposerParameter(configurationID);
            parameters.add(composerParameter);
        }

        if (managerType.toLowerCase().equals("scale") && configurationID != null) {
            MessageParameter scaleParameter = createScaleParameter(configurationID, serviceName, numOfCont);
            parameters.add(scaleParameter);
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

    private MessageParameter createComposerParameter(String configurationID) throws JSONException {
        MessageParameter configurationParameter = createConfigurationParameter(configurationID, "composer");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", configurationID);
        configurationParameter.setAttributes(attributes);
        return configurationParameter;
    }

    private MessageParameter createConfigurationParameter(String configurationID, String confType) throws JSONException {
        String configuration = configurationService.get(configurationID, "yml");
        MessageParameter configurationParameter = new MessageParameter();
        configurationParameter.setName(confType);
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

        Message message = buildDeployerMessage(deployment.getProvisionID(), "scale", confID, scaleReq.getScaleTargetName(), scaleReq.getNumOfInstances());

        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
            Message response = (deployer.call(message));
            List<MessageParameter> params = response.getParameters();
            handleResponse(params, null);
        }
        deployment.setScale(scaleReq);
        save(deployment);
        return deployment;
    }

    private DeployResponse handleResponse(List<MessageParameter> params, DeployRequest deployInfo) throws KeyException, IOException, Exception {
        DeployResponse deployResponse = new DeployResponse();
        deployResponse.setTimestamp(System.currentTimeMillis());

        for (MessageParameter p : params) {
            String name = p.getName();

            if (name.equals("credential")) {
                String value = p.getValue();
                Key k = new Key();
                k.setKey(value);
                k.setType(Key.KeyType.PRIVATE);
                KeyPair pair = new KeyPair();
                pair.setTimestamp(System.currentTimeMillis());
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
        AnsibleResult res = ansOut.getAnsibleResult();
        if (res != null) {
            List<String> cmdList = res.getCmd();
            if (cmdList != null) {

                switch (cmdList.get(0)) {
                    case "sysbench":
                        String[] out = res.getStdout().split("\n");
                        String version = getSysbeanchVersion(out[0]);
                        int numOfThreads = getNumberOfThreads(out[3]);
                        Double executionTime = getExecutionTime(out[14]);
                        int totalNumberOfEvents = getTotalNumberOfEvents(out[15]);

                        double minExecutionTimePerRequest = getMinExecutionTimePerRequest(out[18]);
                        double avgExecutionTimePerRequest = getAvgExecutionTimePerRequest(out[19]);
                        double maxExecutionTimePerRequest = getMaxExecutionTimePerRequest(out[20]);
                        double approx95Percentile = getApprox95Percentile(out[21]);

                        double avgEventsPerThread = getAvgEventsPerThread(out[24]);
                        double stddevEventsPerThread = getStddevEventsPerThread(out[24]);

                        double avgExecTimePerThread = getAvgExecTimePerThread(out[25]);
                        double stddevExecTimePerThread = getStddevExecTimePerThread(out[25]);

                        SysbenchCPUBenchmark b = new SysbenchCPUBenchmark();
                        b.setSysbenchVersion(version);

                        b.setNumberOfThreads(numOfThreads);
                        b.setExecutionTime(executionTime * 1000);

                        b.setTotalNumberOfEvents(totalNumberOfEvents);

                        b.setAvgEventsPerThread(avgEventsPerThread);
                        b.setStddevEventsPerThread(stddevEventsPerThread);

                        b.setAvgExecTimePerThread(avgExecTimePerThread * 1000);
                        b.setStddevExecTimePerThread(stddevExecTimePerThread);
                        b.setApprox95Percentile(approx95Percentile);

                        b.setMinExecutionTimePerRequest(minExecutionTimePerRequest);
                        b.setAvgExecutionTimePerRequest(avgExecutionTimePerRequest);
                        b.setMaxExecutionTimePerRequest(maxExecutionTimePerRequest);

                        b.setAnsibleOutputID(ansOut.getId());

                        b.setCloudDeploymentDomain(ansOut.getCloudDeploymentDomain());
                        b.setDelta(ansOut.getAnsibleResult().getDelta());
                        b.setStart(ansOut.getAnsibleResult().getStart());
                        b.setEnd(ansOut.getAnsibleResult().getEnd());
                        b.setHost(ansOut.getHost());
                        b.setVmType(ansOut.getVmType());
                        b = (SysbenchCPUBenchmark) benchmarkResultService.save(b);
                        return b;

                    default:
                        return null;

                }
            }
        }
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

}
