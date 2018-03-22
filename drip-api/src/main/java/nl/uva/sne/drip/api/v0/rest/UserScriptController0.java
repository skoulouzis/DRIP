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
package nl.uva.sne.drip.api.v0.rest;

import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.service.ProvisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.ScriptService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.drip.commons.data.v0.external.ConfScript;
import nl.uva.sne.drip.drip.commons.data.v1.external.ProvisionResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.Script;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This controller is responsible for handling user scripts. These user can be
 * used by the provisioner to run on the created VMs.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v0.0/switch/provision")
@Component
@Deprecated
public class UserScriptController0 {

    @Autowired
    private ScriptService scriptService;

    @RequestMapping(value = "/confscript", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String uploadUserScript(@RequestBody ConfScript confScript) {
        Script script = new Script();
        script.setContents(confScript.script);
        script = scriptService.save(script);
        return "Success: script for GUI is uploaded: " + script.getId();
    }

}
