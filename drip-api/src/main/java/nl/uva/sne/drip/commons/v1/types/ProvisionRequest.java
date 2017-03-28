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

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author S. Koulouzis
 */
@Document
public class ProvisionRequest extends KeyValueHolder {

    private String cloudCredentialsID;

    private String planID;

    private String publicKeyID;

    /**
     * @return the cloudCredentialsID
     */
    public String getCloudCredentialsID() {
        return cloudCredentialsID;
    }

    /**
     * @param cloudCredentialsID the cloudCredentialsID to set
     */
    public void setCloudCredentialsID(String cloudCredentialsID) {
        this.cloudCredentialsID = cloudCredentialsID;
    }

    /**
     * @return the planID
     */
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
     * @return the publicKeyID
     */
    public String getPublicKeyID() {
        return publicKeyID;
    }

    /**
     * @param userKeyID the publicKeyID to set
     */
    public void setPublicKeyID(String userKeyID) {
        this.publicKeyID = userKeyID;
    }
}
