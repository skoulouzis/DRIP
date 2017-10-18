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
package nl.uva.sne.drip.api.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.uva.sne.drip.api.dao.AnsibleOutputDao;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.drip.commons.data.v1.external.User;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.AnsibleOutput;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.AnsibleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class AnsibleOutputService {

    @Autowired
    private AnsibleOutputDao ansibleOutputDao;

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public AnsibleOutput findOne(String id) {
        AnsibleOutput ansibleOut = ansibleOutputDao.findOne(id);
        if (ansibleOut == null) {
            throw new NotFoundException();
        }
        return ansibleOut;
    }

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<AnsibleOutput> findAll() {
        return ansibleOutputDao.findAll();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public AnsibleOutput delete(String id) {
        AnsibleOutput ansibleOut = ansibleOutputDao.findOne(id);
        if (ansibleOut == null) {
            throw new NotFoundException();
        }
        ansibleOutputDao.delete(ansibleOut);
        return ansibleOut;
    }

    public AnsibleOutput save(AnsibleOutput ownedObject) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        ownedObject.setOwner(owner);
        ownedObject.setTimestamp(System.currentTimeMillis());
        return ansibleOutputDao.save(ownedObject);
    }

    @PostAuthorize("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        ansibleOutputDao.deleteAll();
    }

    public List<String> findAllCommands() {
        List<AnsibleOutput> all = findAll();
        Set<String> hashset = new HashSet<>();
        for (AnsibleOutput ans : all) {
            AnsibleResult res = ans.getAnsibleResult();
            if (res != null) {
                List<String> commandList = res.getCmd();
                if (commandList != null) {
                    hashset.add(commandList.get(0));
                }
            }
        }
        return new ArrayList<>(hashset);
    }

    public List<AnsibleOutput> findByCommand(String command) {
        List<AnsibleOutput> all = findAll();
        List<AnsibleOutput> filtered = new ArrayList<>();
        for (AnsibleOutput ans : all) {
            AnsibleResult res = ans.getAnsibleResult();
            if (res != null) {
                List<String> commandList = res.getCmd();
                if (commandList != null && commandList.get(0).equals(command)) {
                    filtered.add(ans);
                }
            }
        }

        return filtered;
    }

}
