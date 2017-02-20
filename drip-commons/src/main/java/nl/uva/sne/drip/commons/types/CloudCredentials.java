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
package nl.uva.sne.drip.commons.types;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author S. Koulouzis
 */
@Document
public class CloudCredentials {

    @Id
    private String id;

    private String key;

    
    private String keyIdAlias;
    
    private List<LoginKey> loginKeys;
    
    
    private String cloudProviderName;

    public final String getId() {
        return id;
    }

    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the keyIdAlias
     */
    public String getKeyIdAlias() {
        return keyIdAlias;
    }

    /**
     * @param keyIdAlias the keyIdAlias to set
     */
    public void setKeyIdAlias(String keyIdAlias) {
        this.keyIdAlias = keyIdAlias;
    }

    /**
     * @return the loginKeys
     */
    public List<LoginKey> getLoginKeys() {
        return loginKeys;
    }

    /**
     * @param loginKeys the loginKeys to set
     */
    public void setLogineKeys(List<LoginKey> loginKeys) {
        this.loginKeys = loginKeys;
    }

    /**
     * @return the cloudProviderName
     */
    public String getCloudProviderName() {
        return cloudProviderName;
    }

    /**
     * @param cloudProviderName the cloudProviderName to set
     */
    public void setCloudProviderName(String cloudProviderName) {
        this.cloudProviderName = cloudProviderName;
    }
}
