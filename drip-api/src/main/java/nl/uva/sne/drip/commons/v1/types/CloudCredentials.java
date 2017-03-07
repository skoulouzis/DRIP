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

import com.webcohesion.enunciate.metadata.DocumentationExample;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the cloud credentials for a cloud. They are used by the
 * provisoner to request for resources.
 *
 * @author S. Koulouzis
 */
@Document
public class CloudCredentials {

    @Id
    private String id;
    
    private String key;

    private String keyIdAlias;

    /**
     * A list of login keys that can be used to log in to the deployed VMs. All
     * new lines in the 'key' field have to be replaced with the '\n'
     */
    private List<LoginKey> loginKeys;

    private String cloudProviderName;


    public final String getId() {
        return id;
    }

    
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * The key for the cloud provider.
     * @return the key
     */
    @DocumentationExample("6J7uo99ifrff45126Gsy5vgb3bmrtwY6hBxtYt9y")
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
     * The key id for the cloud provider or the key alias.
     * @return the keyIdAlias
     */
    @DocumentationExample("AKIAITY3K5ZUQ6M7YBSQ")
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
     * The login keys
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
     * The name of the cloud provider
     * @return the cloudProviderName
     */
    @DocumentationExample("ec2")
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
