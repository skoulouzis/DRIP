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
 * TopologyTemplate
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-10T17:15:46.465Z")

public class TopologyTemplate   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("inputs")
  @Valid
  private List<Map<String, Object>> inputs = null;

  @JsonProperty("policies")
  @Valid
  private List<Map<String, Object>> policies = null;

  @JsonProperty("outputs")
  @Valid
  private List<Map<String, Object>> outputs = null;

  @JsonProperty("node_templates")
  @Valid
  private Map<String, Object> nodeTemplates = null;

  @JsonProperty("relationship_templates")
  @Valid
  private Map<String, Object> relationshipTemplates = null;

  @JsonProperty("groups")
  @Valid
  private Map<String, Object> groups = null;

  @JsonProperty("substitution_mappings")
  @Valid
  private Map<String, Object> substitutionMappings = null;

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

  public TopologyTemplate inputs(List<Map<String, Object>> inputs) {
    this.inputs = inputs;
    return this;
  }

  public TopologyTemplate addInputsItem(Map<String, Object> inputsItem) {
    if (this.inputs == null) {
      this.inputs = new ArrayList<Map<String, Object>>();
    }
    this.inputs.add(inputsItem);
    return this;
  }

  /**
   * Get inputs
   * @return inputs
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Map<String, Object>> getInputs() {
    return inputs;
  }

  public void setInputs(List<Map<String, Object>> inputs) {
    this.inputs = inputs;
  }

  public TopologyTemplate policies(List<Map<String, Object>> policies) {
    this.policies = policies;
    return this;
  }

  public TopologyTemplate addPoliciesItem(Map<String, Object> policiesItem) {
    if (this.policies == null) {
      this.policies = new ArrayList<Map<String, Object>>();
    }
    this.policies.add(policiesItem);
    return this;
  }

  /**
   * Get policies
   * @return policies
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Map<String, Object>> getPolicies() {
    return policies;
  }

  public void setPolicies(List<Map<String, Object>> policies) {
    this.policies = policies;
  }

  public TopologyTemplate outputs(List<Map<String, Object>> outputs) {
    this.outputs = outputs;
    return this;
  }

  public TopologyTemplate addOutputsItem(Map<String, Object> outputsItem) {
    if (this.outputs == null) {
      this.outputs = new ArrayList<Map<String, Object>>();
    }
    this.outputs.add(outputsItem);
    return this;
  }

  /**
   * Get outputs
   * @return outputs
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Map<String, Object>> getOutputs() {
    return outputs;
  }

  public void setOutputs(List<Map<String, Object>> outputs) {
    this.outputs = outputs;
  }

  public TopologyTemplate nodeTemplates(Map<String, Object> nodeTemplates) {
    this.nodeTemplates = nodeTemplates;
    return this;
  }

  public TopologyTemplate putNodeTemplatesItem(String key, Object nodeTemplatesItem) {
    if (this.nodeTemplates == null) {
      this.nodeTemplates = new HashMap<String, Object>();
    }
    this.nodeTemplates.put(key, nodeTemplatesItem);
    return this;
  }

  /**
   * Get nodeTemplates
   * @return nodeTemplates
  **/
  @ApiModelProperty(value = "")


  public Map<String, Object> getNodeTemplates() {
    return nodeTemplates;
  }

  public void setNodeTemplates(Map<String, Object> nodeTemplates) {
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

  public TopologyTemplate groups(Map<String, Object> groups) {
    this.groups = groups;
    return this;
  }

  public TopologyTemplate putGroupsItem(String key, Object groupsItem) {
    if (this.groups == null) {
      this.groups = new HashMap<String, Object>();
    }
    this.groups.put(key, groupsItem);
    return this;
  }

  /**
   * Get groups
   * @return groups
  **/
  @ApiModelProperty(value = "")


  public Map<String, Object> getGroups() {
    return groups;
  }

  public void setGroups(Map<String, Object> groups) {
    this.groups = groups;
  }

  public TopologyTemplate substitutionMappings(Map<String, Object> substitutionMappings) {
    this.substitutionMappings = substitutionMappings;
    return this;
  }

  public TopologyTemplate putSubstitutionMappingsItem(String key, Object substitutionMappingsItem) {
    if (this.substitutionMappings == null) {
      this.substitutionMappings = new HashMap<String, Object>();
    }
    this.substitutionMappings.put(key, substitutionMappingsItem);
    return this;
  }

  /**
   * Get substitutionMappings
   * @return substitutionMappings
  **/
  @ApiModelProperty(value = "")


  public Map<String, Object> getSubstitutionMappings() {
    return substitutionMappings;
  }

  public void setSubstitutionMappings(Map<String, Object> substitutionMappings) {
    this.substitutionMappings = substitutionMappings;
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
        Objects.equals(this.policies, topologyTemplate.policies) &&
        Objects.equals(this.outputs, topologyTemplate.outputs) &&
        Objects.equals(this.nodeTemplates, topologyTemplate.nodeTemplates) &&
        Objects.equals(this.relationshipTemplates, topologyTemplate.relationshipTemplates) &&
        Objects.equals(this.groups, topologyTemplate.groups) &&
        Objects.equals(this.substitutionMappings, topologyTemplate.substitutionMappings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, inputs, policies, outputs, nodeTemplates, relationshipTemplates, groups, substitutionMappings);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TopologyTemplate {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    inputs: ").append(toIndentedString(inputs)).append("\n");
    sb.append("    policies: ").append(toIndentedString(policies)).append("\n");
    sb.append("    outputs: ").append(toIndentedString(outputs)).append("\n");
    sb.append("    nodeTemplates: ").append(toIndentedString(nodeTemplates)).append("\n");
    sb.append("    relationshipTemplates: ").append(toIndentedString(relationshipTemplates)).append("\n");
    sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
    sb.append("    substitutionMappings: ").append(toIndentedString(substitutionMappings)).append("\n");
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

