package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.OpCode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * InfrasCode
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-31T12:42:56.808Z")

public class InfrasCode {

    @JsonProperty("CodeType")
    private String codeType = null;

    @JsonProperty("OpCode")
    private OpCode opCode = null;

    @JsonProperty("Count")
    private Integer count = null;

    @JsonProperty("OpCodes")
    @Valid
    private List<OpCode> opCodes = null;

    public InfrasCode codeType(String codeType) {
        this.codeType = codeType;
        return this;
    }

    /**
     * Get codeType
     *
     * @return codeType
  *
     */
    @ApiModelProperty(value = "")

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public InfrasCode opCode(OpCode opCode) {
        this.opCode = opCode;
        return this;
    }

    /**
     * Get opCode
     *
     * @return opCode
  *
     */
    @ApiModelProperty(value = "")

    @Valid

    public OpCode getOpCode() {
        return opCode;
    }

    public void setOpCode(OpCode opCode) {
        this.opCode = opCode;
    }

    public InfrasCode count(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * Get count
     *
     * @return count
  *
     */
    @ApiModelProperty(value = "")

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public InfrasCode opCodes(List<OpCode> opCodes) {
        this.opCodes = opCodes;
        return this;
    }

    public InfrasCode addOpCodesItem(OpCode opCodesItem) {
        if (this.opCodes == null) {
            this.opCodes = new ArrayList<OpCode>();
        }
        this.opCodes.add(opCodesItem);
        return this;
    }

    /**
     * Get opCodes
     *
     * @return opCodes
  *
     */
    @ApiModelProperty(value = "")

    @Valid

    public List<OpCode> getOpCodes() {
        return opCodes;
    }

    public void setOpCodes(List<OpCode> opCodes) {
        this.opCodes = opCodes;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InfrasCode infrasCode = (InfrasCode) o;
        return Objects.equals(this.codeType, infrasCode.codeType)
                && Objects.equals(this.opCode, infrasCode.opCode)
                && Objects.equals(this.count, infrasCode.count)
                && Objects.equals(this.opCodes, infrasCode.opCodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeType, opCode, count, opCodes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InfrasCode {\n");

        sb.append("    codeType: ").append(toIndentedString(codeType)).append("\n");
        sb.append("    opCode: ").append(toIndentedString(opCode)).append("\n");
        sb.append("    count: ").append(toIndentedString(count)).append("\n");
        sb.append("    opCodes: ").append(toIndentedString(opCodes)).append("\n");
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
