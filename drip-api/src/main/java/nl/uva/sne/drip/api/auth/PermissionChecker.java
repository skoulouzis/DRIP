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
package nl.uva.sne.drip.api.auth;

import nl.uva.sne.drip.commons.v1.types.OwnedObject;
import nl.uva.sne.drip.commons.v1.types.User;
import org.springframework.stereotype.Component;

/**
 *
 * @author S. Koulouzis
 */
@Component("PermissionChecker")
public class PermissionChecker {

    public boolean canRead(OwnedObject obj, User user) {
        return false;
    }

    public boolean isOwner(OwnedObject obj, User user) {
        String ownerid = obj.getOwner();
        return user.getId().equals(ownerid);
    }
}
