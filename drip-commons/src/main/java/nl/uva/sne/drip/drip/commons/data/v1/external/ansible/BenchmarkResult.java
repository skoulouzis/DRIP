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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webcohesion.enunciate.metadata.DocumentationExample;
import java.util.Date;
import nl.uva.sne.drip.drip.commons.data.v1.external.OwnedObject;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents an benchmark result for a specific VM.
 *
 * @author S. Koulouzis
 */
@Document
public class BenchmarkResult extends OwnedObject {

    @Indexed
    private String host;
    @Indexed
    private String cloudDeploymentDomain;

    @Indexed
    private String vmType;

    @Indexed
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date end;

    @Indexed
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date start;

    @Indexed
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSSSSS")
    private Date delta;

    /**
     * @return the host
     */
    @DocumentationExample("147.228.242.58")
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the cloudDeploymentDomain
     */
    @DocumentationExample("us-east-1")
    public String getCloudDeploymentDomain() {
        return cloudDeploymentDomain;
    }

    /**
     * @param cloudDeploymentDomain the cloudDeploymentDomain to set
     */
    public void setCloudDeploymentDomain(String cloudDeploymentDomain) {
        this.cloudDeploymentDomain = cloudDeploymentDomain;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the delta
     */
    public Date getDelta() {
        return delta;
    }

    /**
     * @param delta the delta to set
     */
    public void setDelta(Date delta) {
        this.delta = delta;
    }

    /**
     * @return the vmType
     */
    public String getVmType() {
        return vmType;
    }

    /**
     * @param vmType the vmType to set
     */
    public void setVmType(String vmType) {
        this.vmType = vmType;
    }

}
