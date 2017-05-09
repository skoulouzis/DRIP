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
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author S. Koulouzis
 */
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanRequest {

    private String toscaID;

    private String managerType;

    private String vmUserName;

    private String cloudProvider;

    private String osType;

    private String domain;

    /**
     * @return the toscaID
     */
    public String getToscaID() {
        return toscaID;
    }

    /**
     * @param toscaID the toscaID to set
     */
    public void setToscaID(String toscaID) {
        this.toscaID = toscaID;
    }

    /**
     * @return the managerType
     */
    public String getManagerType() {
        return managerType;
    }

    /**
     * @param managerType the managerType to set
     */
    public void setManagerType(String managerType) {
        this.managerType = managerType;
    }

    /**
     * @return the vmUserName
     */
    public String getVmUserName() {
        return vmUserName;
    }

    /**
     * @param vmUserName the vmUserName to set
     */
    public void setVmUserName(String vmUserName) {
        this.vmUserName = vmUserName;
    }

    /**
     * @return the cloudProvider
     */
    public String getCloudProvider() {
        return cloudProvider;
    }

    /**
     * @param cloudProvider the cloudProvider to set
     */
    public void setCloudProvider(String cloudProvider) {
        this.cloudProvider = cloudProvider;
    }

    /**
     * @return the osType
     */
    public String getOsType() {
        return osType;
    }

    /**
     * @param osType the osType to set
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

}
