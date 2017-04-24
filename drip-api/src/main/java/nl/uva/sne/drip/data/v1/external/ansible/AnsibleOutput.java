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
package nl.uva.sne.drip.data.v1.external.ansible;

/**
 *
 * @author S. Koulouzis
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nl.uva.sne.drip.data.v1.external.OwnedObject;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "host",
    "result"
})
@Document
public class AnsibleOutput extends OwnedObject {

    @Indexed
    @JsonProperty("host")
    private String host;

    @Indexed
    @JsonProperty("cloudDeploymentDomain")
    private String cloudDeploymentDomain;

    @Indexed
    @JsonProperty("vmType")
    private String vmType;

    @JsonProperty("result")
    private AnsibleResult result;

    @Indexed
    @JsonProperty("provisionID")
    private String provisionID;

    @JsonProperty("host")
    public String getHost() {
        return host;
    }

    @JsonProperty("host")
    public void setHost(String host) {
        this.host = host;
    }

    @JsonProperty("result")
    public AnsibleResult getAnsiibleResult() {
        return result;
    }

    @JsonProperty("result")
    public void setAnsiibleResult(AnsibleResult result) {
        this.result = result;
    }

    @JsonProperty("cloudDeploymentDomain")
    public String getCloudDeploymentDomain() {
        return cloudDeploymentDomain;
    }

    @JsonProperty("cloudDeploymentDomain")
    public void setCloudDeploymentDomain(String cloudDeploymentDomain) {
        this.cloudDeploymentDomain = cloudDeploymentDomain;
    }

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

    @JsonProperty("provisionID")
    public String getProvisionID() {
        return provisionID;
    }
}
