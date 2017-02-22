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

import nl.uva.sne.drip.commons.types.ProvisionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.ByteArrayOutputStream;
import nl.uva.sne.drip.commons.types.ToscaRepresentation;
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
import nl.uva.sne.drip.api.dao.ToscaDao;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
import nl.uva.sne.drip.api.rpc.ProvisionerCaller;
import nl.uva.sne.drip.api.service.ToscaService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.types.CloudCredentials;
import nl.uva.sne.drip.commons.types.LoginKey;
import org.apache.commons.io.FilenameUtils;
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
    private ToscaService toscaService;

    @Autowired
    private CloudCredentialsDao cloudCredentialsDao;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    ProvisionRequest get() {
        ProvisionRequest re = new ProvisionRequest();
        re.setCloudConfID("58a1f0a963d42f004b1d63ad");
        re.setPlanID("58ad99d578b6ba941aeb22a4");
        re.setUserKeyID("58a20be263d4a5898835676e");
        re.setUserScriptID("58a2112363d41754cca042b4");
        return re;
    }

    @RequestMapping(value = "/provision", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String plann(@RequestBody ProvisionRequest req) {

        try (DRIPCaller provisioner = new ProvisionerCaller(messageBrokerHost);) {
            Message provisionerInvokationMessage = buildProvisionerMessage(req);

            Message response = provisioner.call(provisionerInvokationMessage);
            return "";
        } catch (IOException | TimeoutException | JSONException | InterruptedException ex) {
            Logger.getLogger(ProvisionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Message buildProvisionerMessage(ProvisionRequest pReq) throws JSONException, IOException {
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

        List<Parameter> userScripts = buildScriptParams(pReq.getUserScriptID());
        parameters.addAll(userScripts);

        List<Parameter> userKeys = buildKeysParams(pReq.getUserKeyID());
        parameters.addAll(userScripts);

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
        ToscaRepresentation plan = toscaService.get(planID, ToscaRepresentation.Type.PLAN);
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

        Set<String> ids = plan.getLowerLevelIDs();
        for (String lowID : ids) {
            plan = toscaService.get(lowID, ToscaRepresentation.Type.PLAN);
            topology = new Parameter();
            topology.setName("topology");
            topology.setValue(Converter.map2YmlString(plan.getKvMap()));
            attributes = new HashMap<>();
            attributes.put("level", String.valueOf(plan.getLevel()));
            attributes.put("filename", FilenameUtils.removeExtension(plan.getName()));
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

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<Parameter> buildKeysParams(String userKeyID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
