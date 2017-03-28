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
 * This class represents the cloud credentials. They are used by the provisoner
 * to request for resources.
 *
 * @author S. Koulouzis
 */
@Document
public class CloudCredentials extends OwnedObject {

    @Id
    private String id;

    private String secretKey;

    private String accessKeyId;

    private List<String> keyIDs;

    private String cloudProviderName;

    /**
     * It is the secret key / password for accessing a cloud provider.
     *
     * @return the secret key
     */
    @DocumentationExample("7A7vo19ffdfa4SAsA6gsF5Fgbfb5rtwY6hBxtYt12")
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public final String getId() {
        return id;
    }

    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * The name of the cloud provider
     *
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

    /**
     * @return the accessKeyId
     */
    public String getAccessKeyId() {
        return accessKeyId;
    }

    /**
     * @param accessKeyId the accessKeyId to set
     */
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    /**
     * @return the keyIDs
     */
    public List<String> getKeyIDs() {
        return keyIDs;
    }

    /**
     * @param keyIDs the keyIDs to set
     */
    public void setKeyIDs(List<String> keyIDs) {
        this.keyIDs = keyIDs;
    }
}
