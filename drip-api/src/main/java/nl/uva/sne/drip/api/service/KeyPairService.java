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
import java.util.List;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.commons.v1.types.KeyPair;
import nl.uva.sne.drip.commons.v1.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import nl.uva.sne.drip.api.dao.KeyPairDao;
import nl.uva.sne.drip.commons.v1.types.KeyPair;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class KeyPairService {

    @Autowired
    KeyPairDao dao;

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<KeyPair> findAll() {
        return dao.findAll();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public KeyPair findOne(String id) {
        KeyPair key = dao.findOne(id);
        if (key == null) {
            throw new NotFoundException();
        }
        return key;
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public KeyPair delete(KeyPair k) {
        k = dao.findOne(k.getId());
        dao.delete(k);
        return k;
    }

    public KeyPair save(KeyPair upk) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        upk.setOwner(owner);
        return dao.save(upk);
    }

    @PostAuthorize("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        dao.deleteAll();
    }
}
