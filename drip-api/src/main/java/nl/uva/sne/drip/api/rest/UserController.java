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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.exception.PasswordNullException;
import nl.uva.sne.drip.api.exception.UserExistsException;
import nl.uva.sne.drip.api.exception.UserNotFoundException;
import nl.uva.sne.drip.api.exception.UserNullException;
import nl.uva.sne.drip.commons.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/manager/user/")
@Component
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Register new user. A normal user cannot create accounts, only the user
     * with the 'ADMIN' role can do that.
     *
     * @param user
     * @return Response on success: The ID of the newly register user. Response
     * on fail: If the user name already exists, or the user name is 'null' or
     * the password is 'null' there will be a 'BadRequestException'
     *
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String register(@RequestBody User user) {
        if (user.getUsername() == null) {
            throw new UserNullException();
        }
        if (user.getPassword() == null) {
            throw new PasswordNullException();
        }
        UserDetails registeredUser = service.loadUserByUsername(user.getUsername());
        if (registeredUser != null) {
            throw new UserExistsException("Username " + user.getUsername() + " is used");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        service.getDao().save(user);
        return user.getId();
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String modify(@RequestBody User user) {
        UserDetails registeredUser = service.loadUserByUsername(user.getUsername());
        if (registeredUser == null) {
            throw new UserNotFoundException("User " + user.getUsername() + " not found");
        }
        return this.register(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    User get(@PathVariable("id") String id) {
        try {
            User user = service.getDao().findOne(id);
            if (user == null) {
                throw new UserNotFoundException("User " + id + " not found");
            }
            return user;
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String remove(@PathVariable("id") String id) {
        try {
            User user = service.getDao().findOne(id);
            if (user == null) {
                throw new UserNotFoundException("User " + id + " not found");
            }
            service.getDao().delete(user);
            return "Deleted used :" + id;
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        try {
            List<User> all = service.getDao().findAll();
            List<String> ids = new ArrayList<>();
            for (User tr : all) {
                ids.add(tr.getId());
            }
            return ids;
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    List<User> getAll() {
        try {
            return service.getDao().findAll();
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
