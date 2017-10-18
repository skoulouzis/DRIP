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

import java.util.List;
import nl.uva.sne.drip.api.dao.MonitorringMessageDao;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.drip.commons.data.v1.external.DeployResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.MonitorringMessage;
import nl.uva.sne.drip.drip.commons.data.v1.external.User;
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
public class MonitorringMessageService {

    @Autowired
    private MonitorringMessageDao dao;

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public MonitorringMessage delete(String id) {
        MonitorringMessage tr = dao.findOne(id);
        if (tr == null) {
            throw new NotFoundException();
        }
        dao.delete(tr);
        return tr;
    }

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<MonitorringMessage> findAll() {
        return dao.findAll();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public MonitorringMessage findOne(String id) {
        return dao.findOne(id);
    }

    @PostAuthorize("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        dao.deleteAll();
    }

    public MonitorringMessage save(MonitorringMessage ownedObject) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        ownedObject.setOwner(owner);
        ownedObject.setTimestamp(System.currentTimeMillis());
        return dao.save(ownedObject);
    }
}
