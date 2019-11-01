package nl.uva.sne.drip.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;

/**
 * TopologyTemplate
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-01T13:58:45.661Z")

public class TopologyTemplate   {
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
    
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("inputs")
  private String inputs = null;

  @JsonProperty("node_templates")
  @Valid
  private Map<String, NodeTemplate> nodeTemplates = null;

  @JsonProperty("relationship_templates")
  @Valid
  private Map<String, Object> relationshipTemplates = null;

  @JsonProperty("outputs")
  private String outputs = null;

  @JsonProperty("groups")
  private String groups = null;

  @JsonProperty("substitution_mappings")
  private String substitutionMappings = null;

  @JsonProperty("policies")
  @Valid
  private List<String> policies = null;

  public TopologyTemplate description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TopologyTemplate inputs(String inputs) {
    this.inputs = inputs;
    return this;
  }

  /**
   * Get inputs
   * @return inputs
  **/
  @ApiModelProperty(value = "")


  public String getInputs() {
    return inputs;
  }

  public void setInputs(String inputs) {
    this.inputs = inputs;
  }

  public TopologyTemplate nodeTemplates(Map<String, NodeTemplate> nodeTemplates) {
    this.nodeTemplates = nodeTemplates;
    return this;
  }

  public TopologyTemplate putNodeTemplatesItem(String key, NodeTemplate nodeTemplatesItem) {
    if (this.nodeTemplates == null) {
      this.nodeTemplates = new HashMap<String, NodeTemplate>();
    }
    this.nodeTemplates.put(key, nodeTemplatesItem);
    return this;
  }

  /**
   * Get nodeTemplates
   * @return nodeTemplates
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Map<String, NodeTemplate> getNodeTemplates() {
    return nodeTemplates;
  }

  public void setNodeTemplates(Map<String, NodeTemplate> nodeTemplates) {
    this.nodeTemplates = nodeTemplates;
  }

  public TopologyTemplate relationshipTemplates(Map<String, Object> relationshipTemplates) {
    this.relationshipTemplates = relationshipTemplates;
    return this;
  }

  public TopologyTemplate putRelationshipTemplatesItem(String key, Object relationshipTemplatesItem) {
    if (this.relationshipTemplates == null) {
      this.relationshipTemplates = new HashMap<String, Object>();
    }
    this.relationshipTemplates.put(key, relationshipTemplatesItem);
    return this;
  }

  /**
   * Get relationshipTemplates
   * @return relationshipTemplates
  **/
  @ApiModelProperty(value = "")


  public Map<String, Object> getRelationshipTemplates() {
    return relationshipTemplates;
  }

  public void setRelationshipTemplates(Map<String, Object> relationshipTemplates) {
    this.relationshipTemplates = relationshipTemplates;
  }

  public TopologyTemplate outputs(String outputs) {
    this.outputs = outputs;
    return this;
  }

  /**
   * Get outputs
   * @return outputs
  **/
  @ApiModelProperty(value = "")


  public String getOutputs() {
    return outputs;
  }

  public void setOutputs(String outputs) {
    this.outputs = outputs;
  }

  public TopologyTemplate groups(String groups) {
    this.groups = groups;
    return this;
  }

  /**
   * Get groups
   * @return groups
  **/
  @ApiModelProperty(value = "")


  public String getGroups() {
    return groups;
  }

  public void setGroups(String groups) {
    this.groups = groups;
  }

  public TopologyTemplate substitutionMappings(String substitutionMappings) {
    this.substitutionMappings = substitutionMappings;
    return this;
  }

  /**
   * Get substitutionMappings
   * @return substitutionMappings
  **/
  @ApiModelProperty(value = "")


  public String getSubstitutionMappings() {
    return substitutionMappings;
  }

  public void setSubstitutionMappings(String substitutionMappings) {
    this.substitutionMappings = substitutionMappings;
  }

  public TopologyTemplate policies(List<String> policies) {
    this.policies = policies;
    return this;
  }

  public TopologyTemplate addPoliciesItem(String policiesItem) {
    if (this.policies == null) {
      this.policies = new ArrayList<String>();
    }
    this.policies.add(policiesItem);
    return this;
  }

  /**
   * Get policies
   * @return policies
  **/
  @ApiModelProperty(value = "")


  public List<String> getPolicies() {
    return policies;
  }

  public void setPolicies(List<String> policies) {
    this.policies = policies;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TopologyTemplate topologyTemplate = (TopologyTemplate) o;
    return Objects.equals(this.description, topologyTemplate.description) &&
        Objects.equals(this.inputs, topologyTemplate.inputs) &&
        Objects.equals(this.nodeTemplates, topologyTemplate.nodeTemplates) &&
        Objects.equals(this.relationshipTemplates, topologyTemplate.relationshipTemplates) &&
        Objects.equals(this.outputs, topologyTemplate.outputs) &&
        Objects.equals(this.groups, topologyTemplate.groups) &&
        Objects.equals(this.substitutionMappings, topologyTemplate.substitutionMappings) &&
        Objects.equals(this.policies, topologyTemplate.policies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, inputs, nodeTemplates, relationshipTemplates, outputs, groups, substitutionMappings, policies);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TopologyTemplate {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    inputs: ").append(toIndentedString(inputs)).append("\n");
    sb.append("    nodeTemplates: ").append(toIndentedString(nodeTemplates)).append("\n");
    sb.append("    relationshipTemplates: ").append(toIndentedString(relationshipTemplates)).append("\n");
    sb.append("    outputs: ").append(toIndentedString(outputs)).append("\n");
    sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
    sb.append("    substitutionMappings: ").append(toIndentedString(substitutionMappings)).append("\n");
    sb.append("    policies: ").append(toIndentedString(policies)).append("\n");
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

