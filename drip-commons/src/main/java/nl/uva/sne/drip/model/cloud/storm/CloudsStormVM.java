package nl.uva.sne.drip.model.cloud.storm;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * CloudsStormVM
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-12T18:26:54.530Z")

public class CloudsStormVM {

    /**
     * @return the selfEthAddresses
     */
    public String getSelfEthAddresses() {
        return selfEthAddresses;
    }

    /**
     * @param selfEthAddresses the selfEthAddresses to set
     */
    public void setSelfEthAddresses(String selfEthAddresses) {
        this.selfEthAddresses = selfEthAddresses;
    }

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("nodeType")
    private String nodeType = null;

    @JsonProperty("OStype")
    @JsonAlias({"os", "OS"})
    private String ostype = null;

    @JsonProperty("script")
    private String script = null;

    @JsonProperty("publicAddress")
    private String publicAddress = null;
//
//    @JsonProperty("type")
//    private String type = null;

    @JsonProperty("extraInfo")
    @Valid
    private Map<String, Object> extraInfo = null;

    @JsonProperty("CPU")
    private String CPU = null;

    @JsonProperty("MEM")
    @JsonAlias({"Mem"})
    private String MEM = null;

    @JsonProperty("VMType")
    @JsonAlias({"type"})
    private String vmType = null;

    @JsonProperty("Price")
    private String price = null;

    @JsonProperty("DefaultSSHAccount")
    private String defaultSSHAccount = null;

    @JsonProperty("availability")
    private String availability = null;

    @JsonProperty("selfEthAddresses")
    private String selfEthAddresses = null;

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

    public CloudsStormVM ostype(String ostype) {
        this.ostype = ostype;
        return this;
    }

    /**
     * Get ostype
     *
     * @return ostype
     *
     */
    @ApiModelProperty(value = "")

    public String getOstype() {
        return ostype;
    }

    public void setOstype(String ostype) {
        this.ostype = ostype;
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

//    public CloudsStormVM type(String type) {
//        this.type = type;
//        return this;
//    }
//
//    /**
//     * Get type
//     *
//     * @return type
//     *
//     */
//    @ApiModelProperty(value = "")
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
    public CloudsStormVM extraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
        return this;
    }

    public CloudsStormVM putExtraInfoItem(String key, Object extraInfoItem) {
        if (this.extraInfo == null) {
            this.extraInfo = new HashMap<String, Object>();
        }
        this.extraInfo.put(key, extraInfoItem);
        return this;
    }

    /**
     * Get extraInfo
     *
     * @return extraInfo
     *
     */
    @ApiModelProperty(value = "")

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }

//    public CloudsStormVM OS(String OS) {
//        this.OS = OS;
//        return this;
//    }
//
//    /**
//     * Get OS
//     *
//     * @return OS
//     *
//     */
//    @ApiModelProperty(value = "")
//
//    public String getOS() {
//        return OS;
//    }
//
//    public void setOS(String OS) {
//        this.OS = OS;
//    }
    public CloudsStormVM CPU(String CPU) {
        this.CPU = CPU;
        return this;
    }

    /**
     * Get CPU
     *
     * @return CPU
     *
     */
    @ApiModelProperty(value = "")

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public CloudsStormVM MEM(String MEM) {
        this.MEM = MEM;
        return this;
    }

    /**
     * Get MEM
     *
     * @return MEM
     *
     */
    @ApiModelProperty(value = "")

    public String getMEM() {
        return MEM;
    }

    public void setMEM(String MEM) {
        this.MEM = MEM;
    }

    public CloudsStormVM vmType(String vmType) {
        this.vmType = vmType;
        return this;
    }

    /**
     * Get vmType
     *
     * @return vmType
     *
     */
    @ApiModelProperty(value = "")

    public String getVmType() {
        return vmType;
    }

    public void setVmType(String vmType) {
        this.vmType = vmType;
    }

    public CloudsStormVM price(String price) {
        this.price = price;
        return this;
    }

    /**
     * Get price
     *
     * @return price
     *
     */
    @ApiModelProperty(value = "")

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public CloudsStormVM defaultSSHAccount(String defaultSSHAccount) {
        this.defaultSSHAccount = defaultSSHAccount;
        return this;
    }

    /**
     * Get defaultSSHAccount
     *
     * @return defaultSSHAccount
     *
     */
    @ApiModelProperty(value = "")

    public String getDefaultSSHAccount() {
        return defaultSSHAccount;
    }

    public void setDefaultSSHAccount(String defaultSSHAccount) {
        this.defaultSSHAccount = defaultSSHAccount;
    }

    public CloudsStormVM availability(String availability) {
        this.availability = availability;
        return this;
    }

    /**
     * Get availability
     *
     * @return availability
     *
     */
    @ApiModelProperty(value = "")

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
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
                && Objects.equals(this.ostype, cloudsStormVM.ostype)
                && Objects.equals(this.script, cloudsStormVM.script)
                && Objects.equals(this.publicAddress, cloudsStormVM.publicAddress)
                //                && Objects.equals(this.type, cloudsStormVM.type)
                && Objects.equals(this.extraInfo, cloudsStormVM.extraInfo)
                //                && Objects.equals(this.OS, cloudsStormVM.OS)
                && Objects.equals(this.CPU, cloudsStormVM.CPU)
                && Objects.equals(this.MEM, cloudsStormVM.MEM)
                && Objects.equals(this.vmType, cloudsStormVM.vmType)
                && Objects.equals(this.price, cloudsStormVM.price)
                && Objects.equals(this.defaultSSHAccount, cloudsStormVM.defaultSSHAccount)
                && Objects.equals(this.availability, cloudsStormVM.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nodeType, ostype, script, publicAddress, extraInfo, CPU, MEM, vmType, price, defaultSSHAccount, availability);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CloudsStormVM {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    nodeType: ").append(toIndentedString(nodeType)).append("\n");
        sb.append("    ostype: ").append(toIndentedString(ostype)).append("\n");
        sb.append("    script: ").append(toIndentedString(script)).append("\n");
        sb.append("    publicAddress: ").append(toIndentedString(publicAddress)).append("\n");
//        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    extraInfo: ").append(toIndentedString(extraInfo)).append("\n");
//        sb.append("    OS: ").append(toIndentedString(OS)).append("\n");
        sb.append("    CPU: ").append(toIndentedString(CPU)).append("\n");
        sb.append("    MEM: ").append(toIndentedString(MEM)).append("\n");
        sb.append("    vmType: ").append(toIndentedString(vmType)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    defaultSSHAccount: ").append(toIndentedString(defaultSSHAccount)).append("\n");
        sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
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
