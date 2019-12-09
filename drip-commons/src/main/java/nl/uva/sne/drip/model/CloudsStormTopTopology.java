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
package nl.uva.sne.drip.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author S. Koulouzis
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2019-12-09T16:51:48.830052+01:00[Europe/Amsterdam]")
public class CloudsStormTopTopology {
  public static final String SERIALIZED_NAME_USER_NAME = "userName";
  @SerializedName(SERIALIZED_NAME_USER_NAME)
  private String userName;

  public static final String SERIALIZED_NAME_PUBLIC_KEY_PATH = "publicKeyPath";
  @SerializedName(SERIALIZED_NAME_PUBLIC_KEY_PATH)
  private String publicKeyPath;

  public static final String SERIALIZED_NAME_TOPOLOGIES = "topologies";
  @SerializedName(SERIALIZED_NAME_TOPOLOGIES)
  private List<CloudsStormSubTopology> topologies = null;


  public CloudsStormTopTopology userName(String userName) {
    
    this.userName = userName;
    return this;
  }

   /**
   * Get userName
   * @return userName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getUserName() {
    return userName;
  }


  public void setUserName(String userName) {
    this.userName = userName;
  }


  public CloudsStormTopTopology publicKeyPath(String publicKeyPath) {
    
    this.publicKeyPath = publicKeyPath;
    return this;
  }

   /**
   * Get publicKeyPath
   * @return publicKeyPath
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPublicKeyPath() {
    return publicKeyPath;
  }


  public void setPublicKeyPath(String publicKeyPath) {
    this.publicKeyPath = publicKeyPath;
  }


  public CloudsStormTopTopology topologies(List<CloudsStormSubTopology> topologies) {
    
    this.topologies = topologies;
    return this;
  }

  public CloudsStormTopTopology addTopologiesItem(CloudsStormSubTopology topologiesItem) {
    if (this.topologies == null) {
      this.topologies = new ArrayList<CloudsStormSubTopology>();
    }
    this.topologies.add(topologiesItem);
    return this;
  }

   /**
   * Get topologies
   * @return topologies
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<CloudsStormSubTopology> getTopologies() {
    return topologies;
  }


  public void setTopologies(List<CloudsStormSubTopology> topologies) {
    this.topologies = topologies;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CloudsStormTopTopology cloudsStormTopTopology = (CloudsStormTopTopology) o;
    return Objects.equals(this.userName, cloudsStormTopTopology.userName) &&
        Objects.equals(this.publicKeyPath, cloudsStormTopTopology.publicKeyPath) &&
        Objects.equals(this.topologies, cloudsStormTopTopology.topologies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, publicKeyPath, topologies);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CloudsStormTopTopology {\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    publicKeyPath: ").append(toIndentedString(publicKeyPath)).append("\n");
    sb.append("    topologies: ").append(toIndentedString(topologies)).append("\n");
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