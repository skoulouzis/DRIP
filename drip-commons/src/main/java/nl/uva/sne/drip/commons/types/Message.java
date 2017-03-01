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
package nl.uva.sne.drip.commons.types;

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
//@Entity
public class Message implements IMessage, Serializable {

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
//    @Temporal(TemporalType.DATE)
    private Long creationDate;

    private List<Parameter> parameters;

    @Override
    public Long getCreationDate() {
        return this.creationDate;
    }

    @Override
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<Parameter> getParameters() {
        return this.parameters;
    }

    @Override
    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

}
