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
import nl.uva.sne.drip.api.dao.CloudCredentialsDao;
import nl.uva.sne.drip.commons.v1.types.CloudCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class CloudCredentialsService {

    @Autowired
    private CloudCredentialsDao dao;

//    @PreFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public CloudCredentials save(CloudCredentials cloudCredentials) {

//        String owner = user.getUsername();
//        cloudCredentials.setOwner(owner);
        System.err.println(cloudCredentials.getOwner());
        return dao.save(cloudCredentials);
    }

//    @PreAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    @PreAuthorize("hasPermission(#returnObject, 'read')")
    public CloudCredentials findOne(String id) {
        CloudCredentials creds = dao.findOne(id);
        return creds;
    }

    public void delete(String id) {
        dao.delete(id);
    }

//    @PreAuthorize(" (hasRole('ROLE_ADMIN')) or (hasRole('ROLE_USER'))")
//    @PostFilter("(filterObject.owner == authentication.name)")
    public List<CloudCredentials> findAll() {
        return dao.findAll();
    }
}
