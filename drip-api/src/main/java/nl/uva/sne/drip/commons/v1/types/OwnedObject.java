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
package nl.uva.sne.drip.commons.v1.types;

import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

/**
 *
 * @author S. Koulouzis
 */
@Document
public class OwnedObject {

    @NotNull
    private String owner;

    @NotNull
    private Permissions permissions;

    /**
     * @return the permissions
     */
    public Permissions getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the ownerID to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

}
