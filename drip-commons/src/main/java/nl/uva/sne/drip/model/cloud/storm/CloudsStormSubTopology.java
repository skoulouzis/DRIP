package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * CloudsStormSubTopology
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-31T12:57:31.148Z")

public class CloudsStormSubTopology {

    /**
     * @return the scaledFrom
     */
    public Object getScaledFrom() {
        return scaledFrom;
    }

    /**
     * @param scaledFrom the scaledFrom to set
     */
    public void setScaledFrom(Object scaledFrom) {
        this.scaledFrom = scaledFrom;
    }

    /**
     * @return the sshKeyPairId
     */
    public String getSshKeyPairId() {
        return sshKeyPairId;
    }

    /**
     * @param sshKeyPairId the sshKeyPairId to set
     */
    public void setSshKeyPairId(String sshKeyPairId) {
        this.sshKeyPairId = sshKeyPairId;
    }

    /**
     * @return the subTopologyClass
     */
    public String getSubTopologyClass() {
        return subTopologyClass;
    }

    /**
     * @param subTopologyClass the subTopologyClass to set
     */
    public void setSubTopologyClass(String subTopologyClass) {
        this.subTopologyClass = subTopologyClass;
    }

    @JsonProperty("topology")
    private String topology = null;

    @JsonProperty("cloudProvider")
    private String cloudProvider = null;

    @JsonProperty("domain")
    private String domain = null;

    @JsonProperty("subTopologyClass")
    private String subTopologyClass = null;

    @JsonProperty("logsInfo")
    private Object logsInfo;

    @JsonProperty("sshKeyPairId")
    private String sshKeyPairId;
    @JsonProperty("scaledFrom")
    private Object scaledFrom;

    /**
     * Gets or Sets status
     */
    public enum StatusEnum {
        FRESH("fresh"),
        RUNNING("running"),
        DELETED("deleted"),
        FAILED("failed"),
        STOPPED("stopped");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("status")
    private StatusEnum status = null;

    public CloudsStormSubTopology topology(String topology) {
        this.topology = topology;
        return this;
    }

    /**
     * Get topology
     *
     * @return topology
     *
     */
    @ApiModelProperty(value = "")

    public String getTopology() {
        return topology;
    }

    public void setTopology(String topology) {
        this.topology = topology;
    }

    public CloudsStormSubTopology cloudProvider(String cloudProvider) {
        this.cloudProvider = cloudProvider;
        return this;
    }

    /**
     * Get cloudProvider
     *
     * @return cloudProvider
     *
     */
    @ApiModelProperty(value = "")

    public String getCloudProvider() {
        return cloudProvider;
    }

    public void setCloudProvider(String cloudProvider) {
        this.cloudProvider = cloudProvider;
    }

    public CloudsStormSubTopology domain(String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * Get domain
     *
     * @return domain
     *
     */
    @ApiModelProperty(value = "")

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public CloudsStormSubTopology status(StatusEnum status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     *
     */
    @ApiModelProperty(value = "")

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CloudsStormSubTopology cloudsStormSubTopology = (CloudsStormSubTopology) o;
        return Objects.equals(this.topology, cloudsStormSubTopology.topology)
                && Objects.equals(this.cloudProvider, cloudsStormSubTopology.cloudProvider)
                && Objects.equals(this.domain, cloudsStormSubTopology.domain)
                && Objects.equals(this.status, cloudsStormSubTopology.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topology, cloudProvider, domain, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CloudsStormSubTopology {\n");

        sb.append("    topology: ").append(toIndentedString(topology)).append("\n");
        sb.append("    cloudProvider: ").append(toIndentedString(cloudProvider)).append("\n");
        sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
