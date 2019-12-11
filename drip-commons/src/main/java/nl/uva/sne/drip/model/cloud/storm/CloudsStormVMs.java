package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CloudsStormVMs
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-11T19:44:34.555Z")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CloudsStormVMs   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("CloudsStormVM")
  @Valid
  private List<CloudsStormVM> cloudsStormVM = null;

  public CloudsStormVMs name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CloudsStormVMs cloudsStormVM(List<CloudsStormVM> cloudsStormVM) {
    this.cloudsStormVM = cloudsStormVM;
    return this;
  }

  public CloudsStormVMs addCloudsStormVMItem(CloudsStormVM cloudsStormVMItem) {
    if (this.cloudsStormVM == null) {
      this.cloudsStormVM = new ArrayList<CloudsStormVM>();
    }
    this.cloudsStormVM.add(cloudsStormVMItem);
    return this;
  }

  /**
   * Get cloudsStormVM
   * @return cloudsStormVM
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CloudsStormVM> getCloudsStormVM() {
    return cloudsStormVM;
  }

  public void setCloudsStormVM(List<CloudsStormVM> cloudsStormVM) {
    this.cloudsStormVM = cloudsStormVM;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CloudsStormVMs cloudsStormVMs = (CloudsStormVMs) o;
    return Objects.equals(this.name, cloudsStormVMs.name) &&
        Objects.equals(this.cloudsStormVM, cloudsStormVMs.cloudsStormVM);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, cloudsStormVM);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CloudsStormVMs {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    cloudsStormVM: ").append(toIndentedString(cloudsStormVM)).append("\n");
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

