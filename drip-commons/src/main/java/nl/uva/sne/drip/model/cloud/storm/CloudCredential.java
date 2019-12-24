package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * CloudCredential
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-24T17:07:10.081Z")

public class CloudCredential {

    @JsonProperty("cloudProvider")
    private String cloudProvider = null;

    @JsonProperty("credInfoFile")
    private String credInfoFile = null;

    public CloudCredential cloudProvider(String cloudProvider) {
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

    public CloudCredential credInfoFile(String credInfoFile) {
        this.credInfoFile = credInfoFile;
        return this;
    }

    /**
     * Get credInfoFile
     *
     * @return credInfoFile
  *
     */
    @ApiModelProperty(value = "")

    public String getCredInfoFile() {
        return credInfoFile;
    }

    public void setCredInfoFile(String credInfoFile) {
        this.credInfoFile = credInfoFile;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CloudCredential cloudCredential = (CloudCredential) o;
        return Objects.equals(this.cloudProvider, cloudCredential.cloudProvider)
                && Objects.equals(this.credInfoFile, cloudCredential.credInfoFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cloudProvider, credInfoFile);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CloudCredential {\n");

        sb.append("    cloudProvider: ").append(toIndentedString(cloudProvider)).append("\n");
        sb.append("    credInfoFile: ").append(toIndentedString(credInfoFile)).append("\n");
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
