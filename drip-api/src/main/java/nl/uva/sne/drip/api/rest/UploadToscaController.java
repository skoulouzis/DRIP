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
import nl.uva.sne.drip.api.rpc.Planner;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.uva.sne.drip.commons.types.IRequest;
import nl.uva.sne.drip.commons.types.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
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

                Request r = new Request();
                List args = new ArrayList();
                FileArgument fileArg = new FileArgument();
                fileArg.setURL(targetToscaFile.toURI().toString());
                args.add(targetToscaFile);
                r.setArguments(args);
                r.setCreationDate(new Date(System.currentTimeMillis()));
                r.setStatus(IRequest.Status.SUCCESS);

                String returned = planner.plan(r);

                planner.close();
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (IOException | IllegalStateException | TimeoutException | InterruptedException ex) {
                Logger.getLogger(UploadToscaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Upload failed. 'file' was empty.";
    }

    @RequestMapping(value = "/args", method = RequestMethod.GET)
    public Request args() {
        try {
            Request r = new Request();
            List args = new ArrayList();
            args.add(1);
            args.add("str");
            FileArgument targetToscaFile = new FileArgument();
            byte[] bytes = Files.readAllBytes(Paths.get("/home/alogo/Downloads/planner_output_all.yml"));
            targetToscaFile.setContents(new String(bytes, "UTF-8"));
            targetToscaFile.setEncoding("UTF-8");

            args.add(targetToscaFile);
            r.setArguments(args);
            r.setCreationDate(new Date(System.currentTimeMillis()));
            r.setStatus(IRequest.Status.SUCCESS);

            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(r);
            System.err.println(jsonInString);

            return r;
        } catch (IOException ex) {
            Logger.getLogger(UploadToscaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
