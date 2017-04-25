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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webcohesion.enunciate.metadata.DocumentationExample;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class is a holder for the the object IDs that are required by the
 * provisioner to request for cloud resources.
 *
 * @author S. Koulouzis
 */
@Document
public class ProvisionRequest extends KeyValueHolder {

    private List<String> cloudCredentialsIDs;

    private String planID;

    private List<String> keyPairIDs;

    /**
     * The cloud credentials ids required to provision the cloud resources.
     *
     * @return the cloudCredentialsIDs
     */
    @DocumentationExample("58e3946e0fb4f562d84ba1ad")
    public List<String> getCloudCredentialsIDs() {
        return cloudCredentialsIDs;
    }

    /**
     * @param cloudCredentialsIDs the cloudCredentialsIDs to set
     */
    public void setCloudCredentialsIDs(List<String> cloudCredentialsIDs) {
        this.cloudCredentialsIDs = cloudCredentialsIDs;
    }

    /**
     * The ID of the plan <code>PlanResponse</code> to provision for.
     *
     * @return the planID
     */
    @DocumentationExample("ASedsfd46b4fDFd83ba1q")
    public String getPlanID() {
        return planID;
    }

    /**
     * @param planID the planID to set
     */
    public void setPlanID(String planID) {
        this.planID = planID;
    }

    /**
     * The key pair id for the keys to use to log in the provisioned VMs.
     * @return the keyPairIDs
     */
     @DocumentationExample("ASedsfd46b4fFd344a1A")
    public List<String> getKeyPairIDs() {
        return keyPairIDs;
    }

    /**
     * @param userKeyID the keyPairIDs to set
     */
    public void setKeyPairIDs(List<String> userKeyID) {
        this.keyPairIDs = userKeyID;
    }
    
}
