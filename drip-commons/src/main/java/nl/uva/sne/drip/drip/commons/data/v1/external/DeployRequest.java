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
package nl.uva.sne.drip.drip.commons.data.v1.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.webcohesion.enunciate.metadata.DocumentationExample;

/**
 * This class holds the necessary POJO IDs to request the deployment of a 
 * software
 *
 * @author S. Koulouzis
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeployRequest extends OwnedObject {

    private String provisionID;
//    private String managerType;
//    private String configurationID;

    /**
     * The ID of the provision resources description <code>ProvisionResponse</code>
     * @return the provisionID
     */
    @DocumentationExample("58e3946e0fb4f562d84ba1ad")
    public String getProvisionID() {
        return provisionID;
    }

    /**
     * @param provisionID the provisionID to set
     */
    public void setProvisionID(String provisionID) {
        this.provisionID = provisionID;
    }
}
