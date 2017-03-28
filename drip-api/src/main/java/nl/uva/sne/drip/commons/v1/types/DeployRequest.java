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

/**
 *
 * @author S. Koulouzis
 */
public class DeployRequest extends OwnedObject {

    private String provisionID;
    private String clusterType;
    private String configurationID;

    /**
     * @return the provisionID
     */
    public String getProvisionID() {
        return provisionID;
    }

    /**
     * @param provisionID the provisionID to set
     */
    public void setProvisionID(String provisionID) {
        this.provisionID = provisionID;
    }

    /**
     * @return the clusterType
     */
    public String getClusterType() {
        return clusterType;
    }

    /**
     * @param clusterType the clusterType to set
     */
    public void setClusterType(String clusterType) {
        this.clusterType = clusterType;
    }

    /**
     * @return the configurationID
     */
    public String getConfigurationID() {
        return configurationID;
    }

    /**
     * @param configurationID the configurationID to set
     */
    public void setConfigurationID(String configurationID) {
        this.configurationID = configurationID;
    }

}
