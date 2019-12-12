package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * VMMetaInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-11T15:13:55.016Z")

public class VMMetaInfo {

    /**
     * @return the vmType
     */
    public String getVmType() {
        return vmType;
    }

    /**
     * @param vmType the vmType to set
     */
    public void setVmType(String vmType) {
        this.vmType = vmType;
    }
    @JsonProperty("OS")
    private String OS = null;

    @JsonProperty("VMType")
    private String vmType = null;

    @JsonProperty("CPU")
    private String CPU = null;

    @JsonProperty("MEM")
    private String MEM = null;

    @JsonProperty("Price")
    private String price = null;

    @JsonProperty("DefaultSSHAccount")
    private String defaultSSHAccount = null;

    @JsonProperty("availability")
    private String availability = null;

    @JsonProperty("extraInfo")
    @Valid
    private Map<String, Object> extraInfo = null;

    public VMMetaInfo OS(String OS) {
        this.OS = OS;
        return this;
    }

    /**
     * Get OS
     *
     * @return OS
  *
     */
    @ApiModelProperty(value = "")

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public VMMetaInfo CPU(String CPU) {
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

    public VMMetaInfo MEM(String MEM) {
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

    public VMMetaInfo price(String price) {
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

    public VMMetaInfo defaultSSHAccount(String defaultSSHAccount) {
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

    public VMMetaInfo availability(String availability) {
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

    public VMMetaInfo extraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
        return this;
    }

    public VMMetaInfo putExtraInfoItem(String key, Object extraInfoItem) {
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VMMetaInfo vmMetaInfo = (VMMetaInfo) o;
        return Objects.equals(this.OS, vmMetaInfo.OS)
                && Objects.equals(this.CPU, vmMetaInfo.CPU)
                && Objects.equals(this.MEM, vmMetaInfo.MEM)
                && Objects.equals(this.price, vmMetaInfo.price)
                && Objects.equals(this.defaultSSHAccount, vmMetaInfo.defaultSSHAccount)
                && Objects.equals(this.availability, vmMetaInfo.availability)
                && Objects.equals(this.extraInfo, vmMetaInfo.extraInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(OS, CPU, MEM, price, defaultSSHAccount, availability, extraInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VMMetaInfo {\n");

        sb.append("    OS: ").append(toIndentedString(OS)).append("\n");
        sb.append("    CPU: ").append(toIndentedString(CPU)).append("\n");
        sb.append("    MEM: ").append(toIndentedString(MEM)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    defaultSSHAccount: ").append(toIndentedString(defaultSSHAccount)).append("\n");
        sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
        sb.append("    extraInfo: ").append(toIndentedString(extraInfo)).append("\n");
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
