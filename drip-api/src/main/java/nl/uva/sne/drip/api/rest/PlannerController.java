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

import nl.uva.sne.drip.commons.types.ToscaRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/planner")
@Component
public class PlannerController {

    @Value("${message.broker.host}")
    private String messageBrokerHost;
    @Autowired
    private ToscaDao dao;

    @RequestMapping(value = "/plan/{tosca_id}", method = RequestMethod.POST)
    public @ResponseBody
    String plann(@PathVariable("tosca_id") String toscaId) {
        PlannerCaller planner = null;
        try {
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
            bytes = Files.readAllBytes(Paths.get("/home/alogo/Downloads/DRIP/example_a.yml"));
            fileArgument.setValue(new String(bytes, charset));
            fileArgument.setEncoding(charset);
            fileArgument.setName("example");
            parameters.add(fileArgument);

            invokationMessage.setParameters(parameters);
            invokationMessage.setCreationDate((System.currentTimeMillis()));

            planner = new PlannerCaller(messageBrokerHost);
            String returned = planner.plan(invokationMessage);
            System.err.println(returned);

            ObjectMapper mapper = new ObjectMapper();
            Message request = mapper.readValue(returned, Message.class);

            return returned;
        } catch (UnsupportedEncodingException | TimeoutException | InterruptedException ex) {
            Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException | IOException ex) {
            Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (planner != null) {
                try {
                    planner.close();
                } catch (IOException | TimeoutException ex) {
                    Logger.getLogger(PlannerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
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
}
