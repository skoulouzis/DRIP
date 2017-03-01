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

import com.fasterxml.jackson.core.JsonParser;
import nl.uva.sne.drip.commons.types.Provision;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.dao.CloudCredentialsDao;
import nl.uva.sne.drip.commons.types.Message;
import nl.uva.sne.drip.commons.types.Parameter;
import nl.uva.sne.drip.commons.utils.Converter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
import nl.uva.sne.drip.api.rpc.ProvisionerCaller;
import nl.uva.sne.drip.api.service.ProvisionService;
import nl.uva.sne.drip.api.service.SimplePlannerService;
import nl.uva.sne.drip.api.service.UserKeyService;
import nl.uva.sne.drip.api.service.UserScriptService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.types.CloudCredentials;
import nl.uva.sne.drip.commons.types.LoginKey;
import nl.uva.sne.drip.commons.types.Plan;
import nl.uva.sne.drip.commons.types.UserScript;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/provisioner")
@Component
public class ProvisionController {

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    @Autowired
    private UserScriptService userScriptService;

    @Autowired
    private UserKeyService userKeysService;

    @Autowired
    private CloudCredentialsDao cloudCredentialsDao;

    @Autowired
    private ProvisionService provisionService;

    @Autowired
    private SimplePlannerService planService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    Provision get(@PathVariable("id") String id) {
        return provisionService.getDao().findOne(id);
    }

    @RequestMapping(value = "/provision", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String plann(@RequestBody Provision req) {
        try (DRIPCaller provisioner = new ProvisionerCaller(messageBrokerHost);) {
            Message provisionerInvokationMessage = buildProvisionerMessage(req);

//            Message response = provisioner.call(provisionerInvokationMessage);
            Message response = generateFakeResponse();
            List<Parameter> params = response.getParameters();

            for (Parameter p : params) {
                String name = p.getName();
                if (!name.equals("kubernetes")) {
                    String value = p.getValue();
                    Map<String, Object> kvMap = Converter.ymlString2Map(value);
                    req.setKvMap(kvMap);
                    req.setPlanID(req.getPlanID());
                    provisionService.getDao().save(req);
                }
            }
            return req.getId();
        } catch (IOException | TimeoutException | JSONException | InterruptedException ex) {
            Logger.getLogger(ProvisionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Message buildProvisionerMessage(Provision pReq) throws JSONException, IOException {
        Message invokationMessage = new Message();
        List<Parameter> parameters = new ArrayList();
        CloudCredentials cred = cloudCredentialsDao.findOne(pReq.getCloudConfID());
        if (cred == null) {
            throw new NotFoundException("Cloud credentials :" + pReq.getCloudConfID() + " not found");
        }
        Parameter conf = buildCloudConfParam(cred);
        parameters.add(conf);

        List<Parameter> certs = buildCertificatesParam(cred);
        parameters.addAll(certs);

        List<Parameter> topologies = buildTopologyParams(pReq.getPlanID());
        parameters.addAll(topologies);

        String scriptID = pReq.getUserScriptID();
        if (scriptID != null) {
            List<Parameter> userScripts = buildScriptParams(scriptID);
            parameters.addAll(userScripts);
        }

        String userKeyID = pReq.getUserKeyID();
        if (userKeyID != null) {
            List<Parameter> userKeys = buildKeysParams(userKeyID);
            parameters.addAll(userKeys);
        }

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }

    private Parameter buildCloudConfParam(CloudCredentials cred) throws JsonProcessingException, JSONException, IOException {
        Parameter conf = null;
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

    private List<Parameter> buildCertificatesParam(CloudCredentials cred) {
        List<LoginKey> loginKeys = cred.getLoginKeys();
        if (loginKeys == null || loginKeys.isEmpty()) {
            throw new BadRequestException("Log in keys can't be empty");
        }
        List<Parameter> parameters = new ArrayList<>();
        for (LoginKey lk : loginKeys) {
            String domainName = lk.getAttributes().get("domain_name");
            if (domainName == null) {
                domainName = lk.getAttributes().get("domain_name ");
            }
            Parameter cert = new Parameter();
            cert.setName("certificate");
            cert.setValue(lk.getKey());
            Map<String, String> attributes = new HashMap<>();
            attributes.put("filename", domainName);
            cert.setAttributes(attributes);
            parameters.add(cert);
        }
        return parameters;
    }

    private List<Parameter> buildTopologyParams(String planID) throws JSONException {
        Plan plan = planService.getDao().findOne(planID);
        if (plan == null) {
            throw new NotFoundException();
        }
        List<Parameter> parameters = new ArrayList();
        Parameter topology = new Parameter();
        topology.setName("topology");
        topology.setValue(Converter.map2YmlString(plan.getKvMap()));
        Map<String, String> attributes = new HashMap<>();
        attributes.put("level", String.valueOf(plan.getLevel()));
        attributes.put("filename", FilenameUtils.removeExtension(plan.getName()));
        topology.setAttributes(attributes);
        parameters.add(topology);

        Set<String> ids = plan.getLoweLevelPlanIDs();
        for (String lowID : ids) {
            Plan lowPlan = planService.getDao().findOne(lowID);
            topology = new Parameter();
            topology.setName("topology");
            topology.setValue(Converter.map2YmlString(lowPlan.getKvMap()));
            attributes = new HashMap<>();
            attributes.put("level", String.valueOf(lowPlan.getLevel()));
            attributes.put("filename", FilenameUtils.removeExtension(lowPlan.getName()));
            topology.setAttributes(attributes);
            parameters.add(topology);
        }
        return parameters;
    }

    private Parameter buildEC2Conf(CloudCredentials cred) throws JsonProcessingException, JSONException, IOException {

        Properties prop = Converter.getEC2Properties(cred);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        prop.store(baos, null);
        byte[] bytes = baos.toByteArray();
        Parameter conf = new Parameter();
        conf.setName("ec2.conf");
        String charset = "UTF-8";
        conf.setValue(new String(bytes, charset));
        return conf;

    }

    private List<Parameter> buildScriptParams(String userScriptID) {
        UserScript script = userScriptService.getDao().findOne(userScriptID);
        if (script == null) {
            throw new BadRequestException("User script: " + userScriptID + " was not found");
        }
        List<Parameter> parameters = new ArrayList();
        Parameter scriptParameter = new Parameter();
        scriptParameter.setName("guiscript");
        scriptParameter.setValue(script.getContents());
        scriptParameter.setEncoding("UTF-8");
        parameters.add(scriptParameter);
        return parameters;
    }

    private List<Parameter> buildKeysParams(String userKeyID) {
        LoginKey key = userKeysService.get(userKeyID, LoginKey.Type.PUBLIC);
        if (key == null) {
            throw new BadRequestException("User key: " + userKeyID + " was not found");
        }
        List<Parameter> parameters = new ArrayList();
        Parameter keyParameter = new Parameter();
        keyParameter.setName("sshkey");
        keyParameter.setValue(key.getKey());
        keyParameter.setEncoding("UTF-8");
        parameters.add(keyParameter);
        return parameters;

    }

    private Message generateFakeResponse() throws IOException, TimeoutException, InterruptedException, JSONException {
        String strResponse = "{\"creationDate\":1488368936945,\"parameters\":["
                + "{\"name\":\"f293ff03-4b82-49e2-871a-899aadf821ce\","
                + "\"encoding\":\"UTF-8\",\"value\":"
                + "\"publicKeyPath: /tmp/Input-4007028381500/user.pem\\nuserName: "
                + "zh9314\\nsubnets:\\n- {name: s1, subnet: 192.168.10.0, "
                + "netmask: 255.255.255.0}\\ncomponents:\\n- "
                + "name: faab6756-61b6-4800-bffa-ae9d859a9d6c\\n  "
                + "type: Switch.nodes.Compute\\n  nodetype: t2.medium\\n  "
                + "OStype: Ubuntu 16.04\\n  domain: ec2.us-east-1.amazonaws.com\\n  "
                + "script: /tmp/Input-4007028381500/guiscipt.sh\\n  "
                + "installation: null\\n  role: master\\n  "
                + "dockers: mogswitch/InputDistributor\\n  "
                + "public_address: 54.144.0.91\\n  instanceId: i-0e78cbf853328b820\\n  "
                + "ethernet_port:\\n  - {name: p1, subnet_name: s1, "
                + "address: 192.168.10.10}\\n- name: 1c75eedf-8497-46fe-aeb8-dab6a62154cb\\n  "
                + "type: Switch.nodes.Compute\\n  nodetype: t2.medium\\n  OStype: Ubuntu 16.04\\n  domain: ec2.us-east-1.amazonaws.com\\n  script: /tmp/Input-4007028381500/guiscipt.sh\\n  installation: null\\n  role: slave\\n  dockers: mogswitch/ProxyTranscoder\\n  public_address: 34.207.254.160\\n  instanceId: i-0a99ea18fcc77ed7a\\n  ethernet_port:\\n  - {name: p1, subnet_name: s1, address: 192.168.10.11}\\n\"},{\"name\":\"kubernetes\",\"encoding\":\"UTF-8\",\"value\":\"54.144.0.91 ubuntu /tmp/Input-4007028381500/Virginia.pem master\\n34.207.254.160 ubuntu /tmp/Input-4007028381500/Virginia.pem slave\\n\"}]}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        return mapper.readValue(strResponse, Message.class);
    }
}
