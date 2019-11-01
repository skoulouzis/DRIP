package nl.uva.sne.drip.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import org.springframework.data.annotation.Id;

/**
 * Provisioner
 */
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Provisioner {

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    @Id
    @JsonIgnore
    private String id;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("tosca_interface_types")
    @Valid
    private List<String> tosca_interface_types = null;

    public Provisioner version(String version) {
        this.version = version;
        return this;
    }

    /**
     * Get version
     *
     * @return version
     *
     */
    @ApiModelProperty(value = "")

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Provisioner templateName(String templateName) {
        this.name = templateName;
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

    /**
     * Get description
     *
     * @return description
     *
     */
    @ApiModelProperty(value = "")

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Provisioner toscaInterfaceTypes(List<String> imports) {
        this.tosca_interface_types = imports;
        return this;
    }

    public Provisioner addToscaInterfaceTypes(String importsItem) {
        if (this.tosca_interface_types == null) {
            this.tosca_interface_types = new ArrayList();
        }
        this.tosca_interface_types.add(importsItem);
        return this;
    }

    /**
     * Get tosca_interface_types
     *
     * @return tosca_interface_types
     *
     */
    @ApiModelProperty(value = "")

    @Valid

    public List<String> getToscaInterfaceTypes() {
        return tosca_interface_types;
    }

    public void setToscaInterfaceTypes(List<String> tosca_interface_types) {
        this.tosca_interface_types = tosca_interface_types;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Provisioner toscaTemplate = (Provisioner) o;
        return Objects.equals(this.version, toscaTemplate.version)
                && Objects.equals(this.name, toscaTemplate.name)
                && Objects.equals(this.description, toscaTemplate.description)
                && Objects.equals(this.tosca_interface_types, toscaTemplate.tosca_interface_types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, name, description, tosca_interface_types);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ToscaTemplate {\n");

        sb.append("    toscaDefinitionsVersion: ").append(toIndentedString(version)).append("\n");
        sb.append("    templateName: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    imports: ").append(toIndentedString(tosca_interface_types)).append("\n");
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
