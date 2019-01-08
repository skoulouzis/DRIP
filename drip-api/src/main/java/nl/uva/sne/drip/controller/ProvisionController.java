/*
 * Copyright 2019 S. Koulouzis, Huan Zhou, Yang Hu 
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
package nl.uva.sne.drip.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import nl.uva.sne.drip.service.CloudStormService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This controller is responsible for obtaining resources from cloud providers
 *
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/provisioner")
@StatusCodes({
    @ResponseCode(code = 401, condition = "Bad credentials")
})
public class ProvisionController {

    @Autowired
    private CloudStormService provisionService;

}
