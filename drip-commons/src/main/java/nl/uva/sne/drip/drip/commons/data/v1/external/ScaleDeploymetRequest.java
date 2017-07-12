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
public class ScaleDeploymetRequest extends OwnedObject {

    private String deployID;

    private String serviceName;

    private int numOfInstances;

    /**
     * The deployment ID. At the moment only swarm deployments are supported.
     *
     * @return the deployID
     */
    @DocumentationExample("58e3946e0fb4f562d84ba1ad")
    public String getDeployID() {
        return deployID;
    }

    /**
     *
     * @param deployID the deployID to set
     */
    public void setDeployID(String deployID) {
        this.deployID = deployID;
    }

    /**
     * The name of the service to scale.
     *
     * @return the serviceName
     */
    @DocumentationExample("telegreen_db")
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * The number of services to start. This number is absolute not cumulative.
     * If we have 2 service numbers running and we request for 3 we'll have 3 if
     * we request 1 we'll have 1
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
