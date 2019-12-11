package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CloudsStormSubTopology
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-11T15:13:55.016Z")

public class CloudsStormSubTopology   {
  @JsonProperty("topology")
  private String topology = null;

  @JsonProperty("cloudProvider")
  private String cloudProvider = null;

  @JsonProperty("domain")
  private String domain = null;

  @JsonProperty("status")
  private String status = null;

  public CloudsStormSubTopology topology(String topology) {
    this.topology = topology;
    return this;
  }

  /**
   * Get topology
   * @return topology
  **/
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
   * @return cloudProvider
  **/
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
   * @return domain
  **/
  @ApiModelProperty(value = "")


  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public CloudsStormSubTopology status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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
    return Objects.equals(this.topology, cloudsStormSubTopology.topology) &&
        Objects.equals(this.cloudProvider, cloudsStormSubTopology.cloudProvider) &&
        Objects.equals(this.domain, cloudsStormSubTopology.domain) &&
        Objects.equals(this.status, cloudsStormSubTopology.status);
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

