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

import com.webcohesion.enunciate.metadata.DocumentationExample;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the cloud credentials. They are used by the provisoner
 * to request for resources.
 *
 * @author S. Koulouzis
 */
@Document
public class CloudCredentials extends OwnedObject {

    public static String ACCESS_KEY_NAME = "accessKeyId";
    public static String SECRET_KEY_NAME = "secretKey";

    private String secretKey;

    private String accessKeyId;

    private List<String> keyPairIDs;

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
     * The access key ID for a cloud provider.
     *
     * @return the accessKeyId
     */
    @DocumentationExample("AKIKIQY9K1ZUQ6M7YBSQ")
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
     * The IDs of of the key pairs for accessing the VMs of that cloud provider.
     *
     * @return the keyPairIDs
     */
    @DocumentationExample("AKIKIQY9K1ZUQ6M7YBSQ, LKJ2KIQY9K1F236M7YASD")
    public List<String> getkeyPairIDs() {
        return keyPairIDs;
    }

    /**
     * @param keyIDs the keyPairIDs to set
     */
    public void setKeyIDs(List<String> keyIDs) {
        this.keyPairIDs = keyIDs;
    }
}
