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
package nl.uva.sne.drip.drip.commons.data.v1.external.ansible;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.webcohesion.enunciate.metadata.DocumentationExample;
import nl.uva.sne.drip.drip.commons.data.v1.external.KeyValueHolder;
import nl.uva.sne.drip.drip.commons.data.v1.external.OwnedObject;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the the ansible out put for a specific VM. This can be
 * used as a archive / log of ansible executions
 *
 * @author S. Koulouzis
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class AnsibleOutput extends OwnedObject {

    @Indexed
    private String host;

    @Indexed
    private String cloudDeploymentDomain;

    @Indexed
    private String vmType;

    private KeyValueHolder result;


    private String provisionID;

    @Indexed
    private String cloudProvider;

    /**
     * The host (IP) of the VM that executed the playbook
     *
     * @return the host
     */
    @JsonProperty("host")
    @DocumentationExample("147.228.242.58")
    public String getHost() {
        return host;
    }

    @JsonProperty("host")
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * The output of the playbook execution
     *
     * @return the result
     */
    @JsonProperty("result")
    public KeyValueHolder getAnsibleResult() {
        return result;
    }

    @JsonProperty("result")
    public void setAnsiibleResult(KeyValueHolder result) {
        this.result = result;
    }

    /**
     * The cloud domain where the VM that run the playbook
     *
     * @return the domain
     */
    @DocumentationExample("https://carach5.ics.muni.cz:11443")
    @JsonProperty("cloudDeploymentDomain")
    public String getCloudDeploymentDomain() {
        return cloudDeploymentDomain;
    }

    @JsonProperty("cloudDeploymentDomain")
    public void setCloudDeploymentDomain(String cloudDeploymentDomain) {
        this.cloudDeploymentDomain = cloudDeploymentDomain;
    }

    /**
     * The type of VM that run the playbook
     *
     * @return
     */
    @DocumentationExample("medium")
    @JsonProperty("vmType")
    public String getVmType() {
        return vmType;
    }

    @JsonProperty("vmType")
    public void setVmType(String vmType) {
        this.vmType = vmType;
    }

    @JsonProperty("provisionID")
    public void setProvisionID(String provisionID) {
        this.provisionID = provisionID;
    }

    /**
     * The provision used to spawn the VM for this playbook execution
     *
     * @return the provision ID
     */
    @DocumentationExample("59172db6e452f1b9b666a621")
    @JsonProperty("provisionID")
    public String getProvisionID() {
        return provisionID;
    }

    /**
     * The cloud provider
     *
     * @return the cloudProvider
     */
    @DocumentationExample("ec2")
    @JsonProperty("cloudProvider")
    public String getCloudProvider() {
        return cloudProvider;
    }

    /**
     * @param cloudProvider the cloudProvider to set
     */
    public void setCloudProvider(String cloudProvider) {
        this.cloudProvider = cloudProvider;
    }
}
