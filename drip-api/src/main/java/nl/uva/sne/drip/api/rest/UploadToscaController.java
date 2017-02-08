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

import nl.uva.sne.drip.commons.types.FileParameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.uva.sne.drip.api.rpc.PlannerCaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
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
import nl.uva.sne.drip.commons.types.IMessage;

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
        if (!file.isEmpty()) {
            try {

                String originalFileName = file.getOriginalFilename();
                String name = System.currentTimeMillis() + "_" + originalFileName;
//                File targetToscaFile = new File(inputToscaFolderPath + File.separator + name);
//                file.transferTo(targetToscaFile);

                Message invokationMessage = new Message();

                List parameters = new ArrayList();
                FileParameter fileArgument = new FileParameter();
                byte[] bytes = file.getBytes();//Files.readAllBytes(Paths.get(targetToscaFile.getAbsolutePath()));
                String charset = "UTF-8";
                fileArgument.setValue(new String(bytes, charset));
                fileArgument.setEncoding(charset);
                fileArgument.setName(name);
                parameters.add(fileArgument);

                fileArgument = new FileParameter();
                bytes = Files.readAllBytes(Paths.get("/home/alogo/Downloads/DRIP/example_a.yml"));
                fileArgument.setValue(new String(bytes, charset));
                fileArgument.setEncoding(charset);
                fileArgument.setName("example_a.yml");
                parameters.add(fileArgument);

                invokationMessage.setParameters(parameters);
                invokationMessage.setCreationDate(new Date(System.currentTimeMillis()));

                PlannerCaller planner = new PlannerCaller(messageBrokerHost);
                String returned = planner.plan(invokationMessage);

                planner.close();
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (IOException | IllegalStateException | TimeoutException | InterruptedException ex) {
                Logger.getLogger(UploadToscaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Upload failed. 'file' was empty.";
    }

    @RequestMapping(value = "/args", method = RequestMethod.GET)
    public Message args() {
        try {
            Message r = new Message();
            List args = new ArrayList();
            args.add(1);
            args.add("str");
            FileParameter targetToscaFile = new FileParameter();
            byte[] bytes = Files.readAllBytes(Paths.get("/home/alogo/Downloads/planner_output_all.yml"));
            targetToscaFile.setValue(new String(bytes, "UTF-8"));
            targetToscaFile.setName("planner_output_all.yml");
            targetToscaFile.setEncoding("UTF-8");

            args.add(targetToscaFile);
            r.setParameters(args);
            r.setCreationDate(new Date(System.currentTimeMillis()));

//            ObjectMapper mapper = new ObjectMapper();
//            String jsonInString = mapper.writeValueAsString(r);
//            System.err.println(jsonInString);
            return r;
        } catch (IOException ex) {
            Logger.getLogger(UploadToscaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
