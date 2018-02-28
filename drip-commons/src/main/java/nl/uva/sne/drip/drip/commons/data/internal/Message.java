/*
 * Copyright 2017 S. Koulouzis.
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
package nl.uva.sne.drip.drip.commons.data.internal;

import java.io.Serializable;
import java.util.List;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

/**
 *
 *
 *
 * @author S. Koulouzis.
 */
public class Message implements Serializable {

    private String owner;

    private Long creationDate;

    private List<MessageParameter> parameters;

    public Long getCreationDate() {
        return this.creationDate;
    }

    public void setParameters(List<MessageParameter> parameters) {
        this.parameters = parameters;
    }

    public List<MessageParameter> getParameters() {
        return this.parameters;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

}
