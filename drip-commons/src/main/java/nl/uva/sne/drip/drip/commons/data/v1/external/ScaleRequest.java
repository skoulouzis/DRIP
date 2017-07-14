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
 *
 * This class represents a scale request for a deployment. At the moment we only
 * support swarm.
 *
 *
 * @author S. Koulouzis
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScaleRequest extends OwnedObject {

    private String scaleTargetID;

    private String scaleTargetName;

    private int numOfInstances;

    /**
     * The ID of the deployment or provisioned resources to scale. For
     * deployment scale only swarm deployments are supported.
     *
     * @return the scaleTargetID
     */
    @DocumentationExample("58e3946e0fb4f562d84ba1ad")
    public String getScaleTargetID() {
        return scaleTargetID;
    }

    /**
     *
     * @param deployID the scaleTargetID to set
     */
    public void setScaleTargetID(String deployID) {
        this.scaleTargetID = deployID;
    }

    /**
     * The name of the service or topology to scale.
     *
     * @return the scaleTargetName
     */
    @DocumentationExample("telegreen_db")
    public String getScaleTargetName() {
        return scaleTargetName;
    }

    /**
     * @param serviceName the scaleTargetName to set
     */
    public void setScaleTargetName(String serviceName) {
        this.scaleTargetName = serviceName;
    }

    /**
     * The number of instances to start. This number is absolute not cumulative.
     * If we have 2 instances running and we request for 3 we'll have 3 if we
     * request 1 we'll have 1
     *
     * @return the numOfInstances
     */
    @DocumentationExample("5")
    public int getNumOfInstances() {
        return numOfInstances;
    }

    /**
     * @param numOfInstances the numOfInstances to set
     */
    public void setNumOfInstances(int numOfInstances) {
        this.numOfInstances = numOfInstances;
    }

}
