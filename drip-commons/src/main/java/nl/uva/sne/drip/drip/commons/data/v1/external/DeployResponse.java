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
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the response of a deploy request. It may hold a key
 * pair used for logging in and managing a docker cluster. Currently they key
 * pair is only used by kubernetes
 *
 * @author S. Koulouzis
 */
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeployResponse extends DeployRequest {

    private KeyPair key;

    private List<String> ansibleOutputListIDs;
    
    private ScaleRequest scale;

    public void setAnsibleOutputList(List<String> outputListIDs) {
        this.ansibleOutputListIDs = outputListIDs;
    }

    /**
     * @return the ansibleOutputList
     */
    public List<String> getAnsibleOutputList() {
        return ansibleOutputListIDs;
    }

    public void setKey(KeyPair key) {
        this.key = key;
    }

    /**
     * The key pair to log in and manage a docker cluster
     *
     * @return
     */
    public KeyPair getKeyPair() {
        return key;
    }

    /**
     * The scale information if any for this deployment 
     * @return the scale
     */
    public ScaleRequest getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(ScaleRequest scale) {
        this.scale = scale;
    }

}
