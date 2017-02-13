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

import nl.uva.sne.drip.api.rpc.PlannerCaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.commons.types.CloudCredentials;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import nl.uva.sne.drip.commons.types.LoginKey;
import nl.uva.sne.drip.api.dao.CloudCredentialsDao;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/rest/configuration")
@Component
public class CloudConfigurationController {

    @Autowired
    private CloudCredentialsDao dao;
//
//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public @ResponseBody
//    String loginKeyUpload(@RequestParam("file") MultipartFile file) throws JSONException {
//        if (!file.isEmpty()) {
//            try {
//
//                String originalFileName = file.getOriginalFilename();
//                String name = System.currentTimeMillis() + "_" + originalFileName;
//                byte[] bytes = file.getBytes();
//                String key = new String(bytes, "UTF-8");
//                CloudCredentials kr = new CloudCredentials();
//
//                return null;
//            } catch (IOException | IllegalStateException ex) {
//                Logger.getLogger(CloudConfigurationController.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                if (planner != null) {
//                    try {
//                        planner.close();
//                    } catch (IOException | TimeoutException ex) {
//                        Logger.getLogger(CloudConfigurationController.class.getName()).log(Level.WARNING, null, ex);
//                    }
//                }
//            }
//        }
//        return null;
//    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String postConf(CloudCredentials cc) throws JSONException {
        dao.save(cc);
        return cc.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CloudCredentials get(@PathVariable("id") String id) throws JSONException, IOException {
        return dao.findOne(id);
    }

    @RequestMapping(value = "/ids")
    public @ResponseBody
    List<String> getIds() {
        List<CloudCredentials> all = dao.findAll();
        List<String> ids = new ArrayList<>();
        for (CloudCredentials tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

}
