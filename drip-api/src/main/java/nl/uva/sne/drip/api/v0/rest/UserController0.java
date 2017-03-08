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
package nl.uva.sne.drip.api.v0.rest;

import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.exception.PasswordNullException;
import nl.uva.sne.drip.api.exception.UserExistsException;
import nl.uva.sne.drip.api.exception.UserNullException;
import nl.uva.sne.drip.commons.v1.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.commons.v0.types.Register;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for handling user accounts
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/manager/v0.0/switch/account/")
@Component
public class UserController0 {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String register(@RequestBody Register register) {
        if (register.user == null) {
            throw new UserNullException();
        }
        if (register.pwd == null) {
            throw new PasswordNullException();
        }
        UserDetails registeredUser = service.loadUserByUsername(register.user);
        if (registeredUser != null) {
            throw new UserExistsException("Username " + register.user + " is used");
        }
        User user = new User();
        user.setUsername(register.user);
        user.setPassword(new BCryptPasswordEncoder().encode(register.pwd));
        service.getDao().save(user);
        return "Success: " + user.getId();
    }
}
