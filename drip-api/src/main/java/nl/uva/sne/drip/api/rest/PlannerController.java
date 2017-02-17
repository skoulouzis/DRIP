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

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.ByteArrayOutputStream;
import nl.uva.sne.drip.commons.types.ToscaRepresentation;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.dao.CloudCredentialsDao;
import nl.uva.sne.drip.api.rpc.PlannerCaller;
import nl.uva.sne.drip.commons.types.Message;
import nl.uva.sne.drip.commons.types.Parameter;
import nl.uva.sne.drip.commons.utils.Converter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.dao.ToscaDao;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
import nl.uva.sne.drip.api.rpc.ProvisionerCaller;
import nl.uva.sne.drip.api.service.PlannerService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.types.CloudCredentials;
import nl.uva.sne.drip.commons.types.LoginKey;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/planner")
@Component
public class PlannerController {

    @Value("${message.broker.host}")
    private String messageBrokerHost;
    @Autowired
    private ToscaDao dao;
    @Autowired
    private CloudCredentialsDao cloudCredentialsDao;

//    @Autowired
//    PlannerService plannerService;
    @RequestMapping(value = "/plan/{tosca_id}", method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String plann(@PathVariable("tosca_id") String toscaId) {
        DRIPCaller planner = null;
        DRIPCaller provisioner = null;
        List<DRIPCaller> dripComponetens = new ArrayList<>();
        try {

            Message plannerInvokationMessage = buildPlannerMessage(toscaId);

            planner = new PlannerCaller(messageBrokerHost);
            dripComponetens.add(planner);
            Message plannerReturnedMessage = planner.call(plannerInvokationMessage);

            Message provisionerInvokationMessage = buildProvisionerMessage(plannerReturnedMessage, "58a7281c55363e65b3c9eb82");
            provisioner = new ProvisionerCaller(messageBrokerHost);
            dripComponetens.add(provisioner);
            provisioner.call(provisionerInvokationMessage);

            return "";
        } catch (JSONException | IOException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            for (DRIPCaller drip : dripComponetens) {
                if (drip != null) {
                    try {
                        drip.close();
                    } catch (IOException | TimeoutException ex) {
                        Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
        return null;
    }

    private Message buildPlannerMessage(String toscaId) throws JSONException, UnsupportedEncodingException, IOException {
        ToscaRepresentation t2 = dao.findOne(toscaId);
        Map<String, Object> map = t2.getKvMap();
        String ymlStr = Converter.map2YmlString(map);
        ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
        byte[] bytes = ymlStr.getBytes();

        Message invokationMessage = new Message();
        List parameters = new ArrayList();
        Parameter fileArgument = new Parameter();

        String charset = "UTF-8";
        fileArgument.setValue(new String(bytes, charset));
        fileArgument.setEncoding(charset);
        fileArgument.setName("input");
        parameters.add(fileArgument);

        fileArgument = new Parameter();
        bytes = Files.readAllBytes(Paths.get(System.getProperty("user.home") + File.separator + "Downloads/DRIP/example_a.yml"));
        fileArgument.setValue(new String(bytes, charset));
        fileArgument.setEncoding(charset);
        fileArgument.setName("example");
        parameters.add(fileArgument);

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }

    private Message buildProvisionerMessage(Message plannerReturnedMessage, String cloudConfID) throws IOException, JsonProcessingException, JSONException {
        Message invokationMessage = new Message();
        List<Parameter> parameters = new ArrayList();
        CloudCredentials cred = cloudCredentialsDao.findOne(cloudConfID);

        Parameter conf = buildCloudConfParam(cred);
        parameters.add(conf);

        List<Parameter> certs = buildCertificatesParam(cred);
        parameters.addAll(certs);

        List<Parameter> topologies = buildTopologyParams(plannerReturnedMessage);
        parameters.addAll(topologies);

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }

    private Parameter buildCloudConfParam(CloudCredentials cred) throws JsonProcessingException, JSONException, IOException {
        Parameter conf = null;

        switch (cred.getCloudProviderName().toLowerCase()) {
            case "ec2":
                conf = buildEC2Conf(cred);
                break;
        }
        return conf;
    }

    private List<Parameter> buildCertificatesParam(CloudCredentials cred) {
        List<LoginKey> loginKeys = cred.getLogineKys();
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

    private List<Parameter> buildTopologyParams(Message plannerReturnedMessage) {
        List<Parameter> returnedParams = plannerReturnedMessage.getParameters();
        List<Parameter> parameters = new ArrayList();
        for (Parameter param : returnedParams) {
            Parameter topology = new Parameter();
            String name = param.getName();
            if (name.equals("planner_output_all.yml")) {
                topology.setName("topology");
                topology.setValue(param.getValue());
                Map<String, String> attributes = new HashMap<>();
                attributes.put("level", "0");
                attributes.put("filename", FilenameUtils.removeExtension(name));
                topology.setAttributes(attributes);
            } else {
                topology.setName("topology");
                topology.setValue(param.getValue());
                Map<String, String> attributes = new HashMap<>();
                attributes.put("level", "1");
                attributes.put("filename", FilenameUtils.removeExtension(name));
                topology.setAttributes(attributes);
            }
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
}
