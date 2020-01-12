package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * CloudsStormVM
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-30T16:13:55.433Z")

public class CloudsStormVM {

    /**
     * @return the extraInfo
     */
    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    /**
     * @param extraInfo the extraInfo to set
     */
    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("nodeType")
    private String nodeType = null;

    @JsonProperty("OStype")
    private String osType = null;

    @JsonProperty("script")
    private String script = null;

    @JsonProperty("publicAddress")
    private String publicAddress = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("OS")
    private String OS = null;

    @JsonProperty("CPU")
    private String CPU = null;

    @JsonProperty("MEM")
    private String MEM = null;

    @JsonProperty("VMType")
    private String VMType = null;

    @JsonProperty("Price")
    private String Price = null;

    @JsonProperty("extraInfo")
    @Valid
    private Map<String, Object> extraInfo = null;

    public CloudsStormVM name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     *
     */
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
     *
     * @return nodeType
     *
     */
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
     *
     * @return osType
     *
     */
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
     *
     * @return script
     *
     */
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
     *
     * @return publicAddress
     *
     */
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
        return Objects.equals(this.name, cloudsStormVM.name)
                && Objects.equals(this.nodeType, cloudsStormVM.nodeType)
                && Objects.equals(this.osType, cloudsStormVM.osType)
                && Objects.equals(this.script, cloudsStormVM.script)
                && Objects.equals(this.publicAddress, cloudsStormVM.publicAddress);
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

    public String getCPU() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getMEM() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getVmType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getOS() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
