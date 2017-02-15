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
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.types.CloudCredentials;
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

            Message provisionerInvokationMessage = buildProvisionerMessage(plannerReturnedMessage, "58a1f0a963d42f004b1d63ad");
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

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public Message get() {

        try {

            File tempFile1 = new File("/home/alogo/Downloads/DRIP/input.yaml");

            Message message1 = new Message();
            message1.setCreationDate((System.currentTimeMillis()));
            List<Parameter> parameters = new ArrayList();
            Parameter numParam = new Parameter();
            String numParamName = "input";
            numParam.setName(numParamName);
            numParam.setValue("33000");
            parameters.add(numParam);

            Parameter fileParamContents = new Parameter();
            String fileParamContentsName = "someInputFile";
            fileParamContents.setName(fileParamContentsName);
            byte[] bytes = Files.readAllBytes(Paths.get(tempFile1.getAbsolutePath()));
            String charset = "UTF-8";
            fileParamContents.setValue(new String(bytes, charset));
            fileParamContents.setEncoding(charset);
            parameters.add(fileParamContents);

            Parameter fileParamRef = new Parameter();
            fileParamRef.setName("theNameOfTheParamater");
            fileParamRef.setURL("http://www.gutenberg.org/cache/epub/3160/pg3160.txt");
            Map<String, String> attributes = new HashMap<>();
            attributes.put("level", "0");
            fileParamRef.setAttributes(attributes);
            parameters.add(fileParamRef);

            message1.setParameters(parameters);
            return message1;
        } catch (IOException ex) {
            Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
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
        Parameter conf = new Parameter();
        String charset = "UTF-8";
        CloudCredentials cred = cloudCredentialsDao.findOne(cloudConfID);
        Properties prop = Converter.Object2Properties(cred);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        prop.store(baos, null);
        byte[] bytes = baos.toByteArray();
        conf.setName("ec2.conf");
        conf.setValue(new String(bytes, charset));
        parameters.add(conf);

        List<Parameter> returnedParams = plannerReturnedMessage.getParameters();
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

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }
}
