package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * CloudsStormInfrasCode
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-31T12:42:56.808Z")

public class CloudsStormInfrasCode {

    @JsonProperty("Mode")
    private String mode = null;

    @JsonProperty("InfrasCodes")
    @Valid
    private List<InfrasCode> infrasCodes = null;

    public CloudsStormInfrasCode mode(String mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Get mode
     *
     * @return mode
  *
     */
    @ApiModelProperty(value = "")

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public CloudsStormInfrasCode infrasCodes(List<InfrasCode> infrasCodes) {
        this.infrasCodes = infrasCodes;
        return this;
    }

    public CloudsStormInfrasCode addInfrasCodesItem(InfrasCode infrasCodesItem) {
        if (this.infrasCodes == null) {
            this.infrasCodes = new ArrayList<InfrasCode>();
        }
        this.infrasCodes.add(infrasCodesItem);
        return this;
    }

    /**
     * Get infrasCodes
     *
     * @return infrasCodes
  *
     */
    @ApiModelProperty(value = "")

    @Valid

    public List<InfrasCode> getInfrasCodes() {
        return infrasCodes;
    }

    public void setInfrasCodes(List<InfrasCode> infrasCodes) {
        this.infrasCodes = infrasCodes;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CloudsStormInfrasCode cloudsStormInfrasCode = (CloudsStormInfrasCode) o;
        return Objects.equals(this.mode, cloudsStormInfrasCode.mode)
                && Objects.equals(this.infrasCodes, cloudsStormInfrasCode.infrasCodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, infrasCodes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CloudsStormInfrasCode {\n");

        sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
        sb.append("    infrasCodes: ").append(toIndentedString(infrasCodes)).append("\n");
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
