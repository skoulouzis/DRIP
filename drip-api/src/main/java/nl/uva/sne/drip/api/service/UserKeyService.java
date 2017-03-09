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
import nl.uva.sne.drip.api.dao.UserKeyDao;
import nl.uva.sne.drip.commons.v1.types.LoginKey;
import nl.uva.sne.drip.commons.v1.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class UserKeyService {

    @Autowired
    UserKeyDao dao;

    public List<LoginKey> getAll(LoginKey.Type type) {
        List<LoginKey> all = findAll();
        if (all != null) {
            List<LoginKey> allPublic = new ArrayList<>();
            for (LoginKey tr : all) {
                if (tr.getType() != null && tr.getType().equals(LoginKey.Type.PUBLIC)) {
                    allPublic.add(tr);
                }
            }
            return allPublic;
        }
        return null;
    }

    public LoginKey get(String id, LoginKey.Type type) {
        LoginKey key = findOne(id);
        if (key.getType().equals(type)) {
            return key;
        }
        return null;
    }

    public void delete(String id, LoginKey.Type type) {
        LoginKey k = get(id, type);
        if (k != null) {
            delete(k);
        }
    }

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<LoginKey> findAll() {
        return dao.findAll();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public LoginKey findOne(String id) {
        return dao.findOne(id);
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public LoginKey delete(LoginKey k) {
        k = dao.findOne(k.getId());
        dao.delete(k);
        return k;
    }

    public LoginKey save(LoginKey upk) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        upk.setOwner(owner);
        return dao.save(upk);
    }
}
