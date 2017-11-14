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
package nl.uva.sne.drip.api.v1.rest;

import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.DRIPLogService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.drip.commons.data.v1.external.DRIPLogRecord;

/**
 * This controller is responsible for storing TOSCA descriptions that can be
 * used by the planner.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/logs")
@Component
@StatusCodes({
    @ResponseCode(code = 401, condition = "Bad credentials")
})
public class LogController {

    @Autowired
    private DRIPLogService logService;

    @RequestMapping(method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    DRIPLogRecord get() {
        try {
            return logService.get();
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(LogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
