/*
 * Copyright 2019 S. Koulouzis
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
package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author S. Koulouzis
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2019-12-09T16:51:48.830052+01:00[Europe/Amsterdam]")
public class CloudsStormVM {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_NODE_TYPE = "nodeType";
  @SerializedName(SERIALIZED_NAME_NODE_TYPE)
  private String nodeType;

  public static final String SERIALIZED_NAME_OS_TYPE = "OSType";
  @SerializedName(SERIALIZED_NAME_OS_TYPE)
  private String osType;

  public static final String SERIALIZED_NAME_SCRIPT = "script";
  @SerializedName(SERIALIZED_NAME_SCRIPT)
  private String script;

  public static final String SERIALIZED_NAME_PUBLIC_ADDRESS = "publicAddress";
  @SerializedName(SERIALIZED_NAME_PUBLIC_ADDRESS)
  private String publicAddress;


  public CloudsStormVM name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public CloudsStormVM nodeType(String nodeType) {
    
    this.nodeType = nodeType;
    return this;
  }

   /**
   * Get nodeType
   * @return nodeType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getNodeType() {
    return nodeType;
  }


  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }


  public CloudsStormVM osType(String osType) {
    
    this.osType = osType;
    return this;
  }

   /**
   * Get osType
   * @return osType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getOsType() {
    return osType;
  }


  public void setOsType(String osType) {
    this.osType = osType;
  }


  public CloudsStormVM script(String script) {
    
    this.script = script;
    return this;
  }

   /**
   * Get script
   * @return script
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getScript() {
    return script;
  }


  public void setScript(String script) {
    this.script = script;
  }


  public CloudsStormVM publicAddress(String publicAddress) {
    
    this.publicAddress = publicAddress;
    return this;
  }

   /**
   * Get publicAddress
   * @return publicAddress
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPublicAddress() {
    return publicAddress;
  }


  public void setPublicAddress(String publicAddress) {
    this.publicAddress = publicAddress;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CloudsStormVM cloudsStormVM = (CloudsStormVM) o;
    return Objects.equals(this.name, cloudsStormVM.name) &&
        Objects.equals(this.nodeType, cloudsStormVM.nodeType) &&
        Objects.equals(this.osType, cloudsStormVM.osType) &&
        Objects.equals(this.script, cloudsStormVM.script) &&
        Objects.equals(this.publicAddress, cloudsStormVM.publicAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, nodeType, osType, script, publicAddress);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CloudsStormVM {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nodeType: ").append(toIndentedString(nodeType)).append("\n");
    sb.append("    osType: ").append(toIndentedString(osType)).append("\n");
    sb.append("    script: ").append(toIndentedString(script)).append("\n");
    sb.append("    publicAddress: ").append(toIndentedString(publicAddress)).append("\n");
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

