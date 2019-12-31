package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Options;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * OpCode
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-31T12:42:56.808Z")

public class OpCode {

    @JsonProperty("Operation")
    private String operation = null;

    @JsonProperty("Objects")
    private String objects = null;

    @JsonProperty("Command")
    private String command = null;

    @JsonProperty("Log")
    private Boolean log = null;

    @JsonProperty("Options")
    private Options options = null;

    public OpCode operation(String operation) {
        this.operation = operation;
        return this;
    }

    /**
     * Get operation
     *
     * @return operation
  *
     */
    @ApiModelProperty(value = "")

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public OpCode objects(String objects) {
        this.objects = objects;
        return this;
    }

    /**
     * Get objects
     *
     * @return objects
  *
     */
    @ApiModelProperty(value = "")

    public String getObjects() {
        return objects;
    }

    public void setObjects(String objects) {
        this.objects = objects;
    }

    public OpCode command(String command) {
        this.command = command;
        return this;
    }

    /**
     * Get command
     *
     * @return command
  *
     */
    @ApiModelProperty(value = "")

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public OpCode log(Boolean log) {
        this.log = log;
        return this;
    }

    /**
     * Get log
     *
     * @return log
  *
     */
    @ApiModelProperty(value = "")

    public Boolean isLog() {
        return log;
    }

    public void setLog(Boolean log) {
        this.log = log;
    }

    public OpCode options(Options options) {
        this.options = options;
        return this;
    }

    /**
     * Get options
     *
     * @return options
  *
     */
    @ApiModelProperty(value = "")

    @Valid

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpCode opCode = (OpCode) o;
        return Objects.equals(this.operation, opCode.operation)
                && Objects.equals(this.objects, opCode.objects)
                && Objects.equals(this.command, opCode.command)
                && Objects.equals(this.log, opCode.log)
                && Objects.equals(this.options, opCode.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, objects, command, log, options);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OpCode {\n");

        sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
        sb.append("    objects: ").append(toIndentedString(objects)).append("\n");
        sb.append("    command: ").append(toIndentedString(command)).append("\n");
        sb.append("    log: ").append(toIndentedString(log)).append("\n");
        sb.append("    options: ").append(toIndentedString(options)).append("\n");
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
