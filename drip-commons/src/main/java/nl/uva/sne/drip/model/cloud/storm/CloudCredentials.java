package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * CloudCredentials
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-26T12:29:05.903Z")

public class CloudCredentials {

    @JsonProperty("cloudCreds")
    @Valid
    private List<CloudCred> cloudCreds = null;

    public CloudCredentials cloudCreds(List<CloudCred> cloudCreds) {
        this.cloudCreds = cloudCreds;
        return this;
    }

    public CloudCredentials addCloudCredentialItem(CloudCred cloudCredsItem) {
        if (this.cloudCreds == null) {
            this.cloudCreds = new ArrayList<>();
        }
        this.cloudCreds.add(cloudCredsItem);
        return this;
    }

    /**
     * Get cloudCreds
     *
     * @return cloudCreds
     *
     */
    @ApiModelProperty(value = "")

    @Valid

    public List<CloudCred> getCloudCredential() {
        return cloudCreds;
    }

    public void setCloudCreds(List<CloudCred> cloudCreds) {
        this.cloudCreds = cloudCreds;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CloudCredentials cloudCreds = (CloudCredentials) o;
        return Objects.equals(this.cloudCreds, cloudCreds.cloudCreds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cloudCreds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CloudCreds {\n");

        sb.append("    cloudCreds: ").append(toIndentedString(cloudCreds)).append("\n");
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
