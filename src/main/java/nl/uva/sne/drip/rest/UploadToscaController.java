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
package nl.uva.sne.drip.rest;

import nl.uva.sne.drip.rpc.Planner;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        if (!file.isEmpty()) {
            try {

                String originalFileName = file.getOriginalFilename();
                String name = originalFileName + System.currentTimeMillis();
                File targetToscaFile = new File(inputToscaFolderPath + File.separator + name);
                file.transferTo(targetToscaFile);

                Planner planner = new Planner(messageBrokerHost);
                String returned = planner.plan(targetToscaFile);
                System.err.println(returned);
                planner.close();
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (IOException | IllegalStateException | TimeoutException | InterruptedException ex) {
                Logger.getLogger(UploadToscaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Upload failed. 'file' was empty.";
    }
}
