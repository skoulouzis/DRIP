package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * CloudsStormVMs
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-30T16:13:55.433Z")

public class CloudsStormVMs {
    
        @JsonProperty("SEngineClass")
        private String sEngineClass;
    
            

    @JsonProperty("VMs")
    @Valid
    private List<CloudsStormVM> vms = null;

    public CloudsStormVMs vms(List<CloudsStormVM> vms) {
        this.vms = vms;
        return this;
    }

    public CloudsStormVMs addVmsItem(CloudsStormVM vmsItem) {
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
    @ApiModelProperty(value = "")

    @Valid

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
        CloudsStormVMs cloudsStormVMs = (CloudsStormVMs) o;
        return Objects.equals(this.vms, cloudsStormVMs.vms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vms);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CloudsStormVMs {\n");

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
