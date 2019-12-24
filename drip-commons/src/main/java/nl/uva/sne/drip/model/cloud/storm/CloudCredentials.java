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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-24T17:07:10.081Z")

public class CloudCredentials   {
  @JsonProperty("CloudCredential")
  @Valid
  private List<CloudCredential> cloudCredential = null;

  public CloudCredentials cloudCredential(List<CloudCredential> cloudCredential) {
    this.cloudCredential = cloudCredential;
    return this;
  }

  public CloudCredentials addCloudCredentialItem(CloudCredential cloudCredentialItem) {
    if (this.cloudCredential == null) {
      this.cloudCredential = new ArrayList<CloudCredential>();
    }
    this.cloudCredential.add(cloudCredentialItem);
    return this;
  }

  /**
   * Get cloudCredential
   * @return cloudCredential
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CloudCredential> getCloudCredential() {
    return cloudCredential;
  }

  public void setCloudCredential(List<CloudCredential> cloudCredential) {
    this.cloudCredential = cloudCredential;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CloudCredentials cloudCredentials = (CloudCredentials) o;
    return Objects.equals(this.cloudCredential, cloudCredentials.cloudCredential);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cloudCredential);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CloudCredentials {\n");
    
    sb.append("    cloudCredential: ").append(toIndentedString(cloudCredential)).append("\n");
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

