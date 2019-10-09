package nl.uva.sne.drip.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NodeTemplate
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-09T16:44:17.416Z")

public class NodeTemplate   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("requirements")
  @Valid
  private List<Map<String, String>> requirements = null;

  @JsonProperty("artifacts")
  @Valid
  private Map<String, String> artifacts = null;

  @JsonProperty("properties")
  @Valid
  private Map<String, String> properties = null;

  @JsonProperty("interfaces")
  @Valid
  private Map<String, String> interfaces = null;

  @JsonProperty("capabilities")
  @Valid
  private Map<String, String> capabilities = null;

  @JsonProperty("workflows")
  @Valid
  private Map<String, String> workflows = null;

  public NodeTemplate name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public NodeTemplate type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public NodeTemplate requirements(List<Map<String, String>> requirements) {
    this.requirements = requirements;
    return this;
  }

  public NodeTemplate addRequirementsItem(Map<String, String> requirementsItem) {
    if (this.requirements == null) {
      this.requirements = new ArrayList<Map<String, String>>();
    }
    this.requirements.add(requirementsItem);
    return this;
  }

  /**
   * Get requirements
   * @return requirements
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Map<String, String>> getRequirements() {
    return requirements;
  }

  public void setRequirements(List<Map<String, String>> requirements) {
    this.requirements = requirements;
  }

  public NodeTemplate artifacts(Map<String, String> artifacts) {
    this.artifacts = artifacts;
    return this;
  }

  public NodeTemplate putArtifactsItem(String key, String artifactsItem) {
    if (this.artifacts == null) {
      this.artifacts = new HashMap<String, String>();
    }
    this.artifacts.put(key, artifactsItem);
    return this;
  }

  /**
   * Get artifacts
   * @return artifacts
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getArtifacts() {
    return artifacts;
  }

  public void setArtifacts(Map<String, String> artifacts) {
    this.artifacts = artifacts;
  }

  public NodeTemplate properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public NodeTemplate putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<String, String>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Get properties
   * @return properties
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public NodeTemplate interfaces(Map<String, String> interfaces) {
    this.interfaces = interfaces;
    return this;
  }

  public NodeTemplate putInterfacesItem(String key, String interfacesItem) {
    if (this.interfaces == null) {
      this.interfaces = new HashMap<String, String>();
    }
    this.interfaces.put(key, interfacesItem);
    return this;
  }

  /**
   * Get interfaces
   * @return interfaces
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getInterfaces() {
    return interfaces;
  }

  public void setInterfaces(Map<String, String> interfaces) {
    this.interfaces = interfaces;
  }

  public NodeTemplate capabilities(Map<String, String> capabilities) {
    this.capabilities = capabilities;
    return this;
  }

  public NodeTemplate putCapabilitiesItem(String key, String capabilitiesItem) {
    if (this.capabilities == null) {
      this.capabilities = new HashMap<String, String>();
    }
    this.capabilities.put(key, capabilitiesItem);
    return this;
  }

  /**
   * Get capabilities
   * @return capabilities
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getCapabilities() {
    return capabilities;
  }

  public void setCapabilities(Map<String, String> capabilities) {
    this.capabilities = capabilities;
  }

  public NodeTemplate workflows(Map<String, String> workflows) {
    this.workflows = workflows;
    return this;
  }

  public NodeTemplate putWorkflowsItem(String key, String workflowsItem) {
    if (this.workflows == null) {
      this.workflows = new HashMap<String, String>();
    }
    this.workflows.put(key, workflowsItem);
    return this;
  }

  /**
   * Get workflows
   * @return workflows
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getWorkflows() {
    return workflows;
  }

  public void setWorkflows(Map<String, String> workflows) {
    this.workflows = workflows;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NodeTemplate nodeTemplate = (NodeTemplate) o;
    return Objects.equals(this.name, nodeTemplate.name) &&
        Objects.equals(this.type, nodeTemplate.type) &&
        Objects.equals(this.requirements, nodeTemplate.requirements) &&
        Objects.equals(this.artifacts, nodeTemplate.artifacts) &&
        Objects.equals(this.properties, nodeTemplate.properties) &&
        Objects.equals(this.interfaces, nodeTemplate.interfaces) &&
        Objects.equals(this.capabilities, nodeTemplate.capabilities) &&
        Objects.equals(this.workflows, nodeTemplate.workflows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, requirements, artifacts, properties, interfaces, capabilities, workflows);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NodeTemplate {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    requirements: ").append(toIndentedString(requirements)).append("\n");
    sb.append("    artifacts: ").append(toIndentedString(artifacts)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    interfaces: ").append(toIndentedString(interfaces)).append("\n");
    sb.append("    capabilities: ").append(toIndentedString(capabilities)).append("\n");
    sb.append("    workflows: ").append(toIndentedString(workflows)).append("\n");
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

