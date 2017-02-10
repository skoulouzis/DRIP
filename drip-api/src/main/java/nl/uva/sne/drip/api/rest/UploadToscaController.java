/*
 * Copyright 2017 S. Koulouzis.
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

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.uva.sne.drip.commons.types.Parameter;
import nl.uva.sne.drip.api.rpc.PlannerCaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.commons.types.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/rest")
@Component
public class UploadToscaController {

    @Value("${input.tosca.folder.path}")
    private String inputToscaFolderPath;

    @Value("${message.broker.host}")
    private String messageBrokerHost;

//    curl -X POST -F "file=@DRIP/input.yaml" localhost:8080/drip-api/rest/upload 
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String toscaUpload(@RequestParam("file") MultipartFile file) {
        PlannerCaller planner = null;
        if (!file.isEmpty()) {
            try {
                
                String originalFileName = file.getOriginalFilename();
                String name = System.currentTimeMillis() + "_" + originalFileName;

                Message invokationMessage = new Message();

                List parameters = new ArrayList();
                Parameter fileArgument = new Parameter();
                byte[] bytes = file.getBytes();
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
                ObjectMapper mapper = new ObjectMapper();
                Message request = mapper.readValue(returned, Message.class);

                System.err.println(returned);
                System.err.println(request.getCreationDate());

                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (IOException | IllegalStateException | TimeoutException | InterruptedException ex) {
                Logger.getLogger(UploadToscaController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (planner != null) {
                    try {
                        planner.close();
                    } catch (IOException | TimeoutException ex) {
                        Logger.getLogger(UploadToscaController.class.getName()).log(Level.WARNING, null, ex);
                    }
                }
            }
        }
        return "Upload failed. 'file' was empty.";
    }
}
