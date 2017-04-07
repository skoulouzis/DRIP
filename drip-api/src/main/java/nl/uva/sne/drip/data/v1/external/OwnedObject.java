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
package nl.uva.sne.drip.data.v1.external;

import com.webcohesion.enunciate.metadata.DocumentationExample;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This is the base class for users to own resources. Many classes extend this 
 * class
 * @author S. Koulouzis
 */
@Document
public class OwnedObject {

    @NotNull
    private String owner;

    /**
     * The owner (username) for the particular object.
     * @return the owner
     */
    @DocumentationExample("user1")
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
