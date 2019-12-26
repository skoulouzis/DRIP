package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * CloudDB
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-26T13:19:20.152Z")

public class CloudDB {

    @JsonProperty("cloudProvider")
    private String cloudProvider = null;

    @JsonProperty("dbInfoFile")
    private String dbInfoFile = null;

    public CloudDB cloudProvider(String cloudProvider) {
        this.cloudProvider = cloudProvider;
        return this;
    }

    /**
     * Get cloudProvider
     *
     * @return cloudProvider
  *
     */
    @ApiModelProperty(value = "")

    public String getCloudProvider() {
        return cloudProvider;
    }

    public void setCloudProvider(String cloudProvider) {
        this.cloudProvider = cloudProvider;
    }

    public CloudDB dbInfoFile(String dbInfoFile) {
        this.dbInfoFile = dbInfoFile;
        return this;
    }

    /**
     * Get dbInfoFile
     *
     * @return dbInfoFile
  *
     */
    @ApiModelProperty(value = "")

    public String getDbInfoFile() {
        return dbInfoFile;
    }

    public void setDbInfoFile(String dbInfoFile) {
        this.dbInfoFile = dbInfoFile;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CloudDB cloudDB = (CloudDB) o;
        return Objects.equals(this.cloudProvider, cloudDB.cloudProvider)
                && Objects.equals(this.dbInfoFile, cloudDB.dbInfoFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cloudProvider, dbInfoFile);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CloudDB {\n");

        sb.append("    cloudProvider: ").append(toIndentedString(cloudProvider)).append("\n");
        sb.append("    dbInfoFile: ").append(toIndentedString(dbInfoFile)).append("\n");
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
