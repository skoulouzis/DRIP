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

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a description of provisioned resources
 *
 * @author S. Koulouzis
 */
public class ProvisionResponse extends ProvisionRequest {

    private List<DeployParameter> deployParameters;
    private ArrayList<String> cloudKeyPairIDs;

    /**
     * The deploy parameters.
     *
     * @return the deployParameters
     */
    public List<DeployParameter> getDeployParameters() {
        return deployParameters;
    }

    /**
     * @param deployParameters the deployParameters to set
     */
    public void setDeployParameters(List<DeployParameter> deployParameters) {
        this.deployParameters = deployParameters;
    }

    public void setCloudKeyPairIDs(ArrayList<String> cloudKeyPairIDs) {
        this.cloudKeyPairIDs = cloudKeyPairIDs;
    }

    /**
     * @return the cloudKeyPairIDs
     */
    public ArrayList<String> getCloudKeyPairIDs() {
        return cloudKeyPairIDs;
    }

}
