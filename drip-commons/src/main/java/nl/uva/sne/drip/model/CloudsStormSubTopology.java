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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2019-12-09T16:00:08.961312+01:00[Europe/Amsterdam]")
public class CloudsStormSubTopology {

    public static final String SERIALIZED_NAME_VMS = "VMs";
    @SerializedName(SERIALIZED_NAME_VMS)
    private List<CloudsStormVM> vms = null;

    public CloudsStormSubTopology vms(List<CloudsStormVM> vms) {

        this.vms = vms;
        return this;
    }

    public CloudsStormSubTopology addVmsItem(CloudsStormVM vmsItem) {
        if (this.vms == null) {
            this.vms = new ArrayList<CloudsStormVM>();
        }
        this.vms.add(vmsItem);
        return this;
    }

    /**
     * Get vms
     *
     * @return vms
     *
     */
    @javax.annotation.Nullable
    @ApiModelProperty(value = "")

    public List<CloudsStormVM> getVms() {
        return vms;
    }

    public void setVms(List<CloudsStormVM> vms) {
        this.vms = vms;
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
        return Objects.equals(this.vms, cloudsStormSubTopology.vms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vms);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CloudsStormSubTopology {\n");
        sb.append("    vms: ").append(toIndentedString(vms)).append("\n");
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
