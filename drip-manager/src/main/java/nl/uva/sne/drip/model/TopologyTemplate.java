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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-09T16:44:17.416Z")

public class TopologyTemplate   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("inputs")
  @Valid
  private List<Map<String, String>> inputs = null;

  @JsonProperty("policies")
  @Valid
  private List<Map<String, String>> policies = null;

  @JsonProperty("outputs")
  @Valid
  private List<Map<String, String>> outputs = null;

  @JsonProperty("node_templates")
  private NodeTemplate nodeTemplates = null;

  @JsonProperty("relationship_templates")
  @Valid
  private Map<String, String> relationshipTemplates = null;

  @JsonProperty("groups")
  @Valid
  private Map<String, String> groups = null;

  @JsonProperty("substitution_mappings")
  @Valid
  private Map<String, String> substitutionMappings = null;

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

  public TopologyTemplate inputs(List<Map<String, String>> inputs) {
    this.inputs = inputs;
    return this;
  }

  public TopologyTemplate addInputsItem(Map<String, String> inputsItem) {
    if (this.inputs == null) {
      this.inputs = new ArrayList<Map<String, String>>();
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

  public List<Map<String, String>> getInputs() {
    return inputs;
  }

  public void setInputs(List<Map<String, String>> inputs) {
    this.inputs = inputs;
  }

  public TopologyTemplate policies(List<Map<String, String>> policies) {
    this.policies = policies;
    return this;
  }

  public TopologyTemplate addPoliciesItem(Map<String, String> policiesItem) {
    if (this.policies == null) {
      this.policies = new ArrayList<Map<String, String>>();
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

  public List<Map<String, String>> getPolicies() {
    return policies;
  }

  public void setPolicies(List<Map<String, String>> policies) {
    this.policies = policies;
  }

  public TopologyTemplate outputs(List<Map<String, String>> outputs) {
    this.outputs = outputs;
    return this;
  }

  public TopologyTemplate addOutputsItem(Map<String, String> outputsItem) {
    if (this.outputs == null) {
      this.outputs = new ArrayList<Map<String, String>>();
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

  public List<Map<String, String>> getOutputs() {
    return outputs;
  }

  public void setOutputs(List<Map<String, String>> outputs) {
    this.outputs = outputs;
  }

  public TopologyTemplate nodeTemplates(NodeTemplate nodeTemplates) {
    this.nodeTemplates = nodeTemplates;
    return this;
  }

  /**
   * Get nodeTemplates
   * @return nodeTemplates
  **/
  @ApiModelProperty(value = "")

  @Valid

  public NodeTemplate getNodeTemplates() {
    return nodeTemplates;
  }

  public void setNodeTemplates(NodeTemplate nodeTemplates) {
    this.nodeTemplates = nodeTemplates;
  }

  public TopologyTemplate relationshipTemplates(Map<String, String> relationshipTemplates) {
    this.relationshipTemplates = relationshipTemplates;
    return this;
  }

  public TopologyTemplate putRelationshipTemplatesItem(String key, String relationshipTemplatesItem) {
    if (this.relationshipTemplates == null) {
      this.relationshipTemplates = new HashMap<String, String>();
    }
    this.relationshipTemplates.put(key, relationshipTemplatesItem);
    return this;
  }

  /**
   * Get relationshipTemplates
   * @return relationshipTemplates
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getRelationshipTemplates() {
    return relationshipTemplates;
  }

  public void setRelationshipTemplates(Map<String, String> relationshipTemplates) {
    this.relationshipTemplates = relationshipTemplates;
  }

  public TopologyTemplate groups(Map<String, String> groups) {
    this.groups = groups;
    return this;
  }

  public TopologyTemplate putGroupsItem(String key, String groupsItem) {
    if (this.groups == null) {
      this.groups = new HashMap<String, String>();
    }
    this.groups.put(key, groupsItem);
    return this;
  }

  /**
   * Get groups
   * @return groups
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getGroups() {
    return groups;
  }

  public void setGroups(Map<String, String> groups) {
    this.groups = groups;
  }

  public TopologyTemplate substitutionMappings(Map<String, String> substitutionMappings) {
    this.substitutionMappings = substitutionMappings;
    return this;
  }

  public TopologyTemplate putSubstitutionMappingsItem(String key, String substitutionMappingsItem) {
    if (this.substitutionMappings == null) {
      this.substitutionMappings = new HashMap<String, String>();
    }
    this.substitutionMappings.put(key, substitutionMappingsItem);
    return this;
  }

  /**
   * Get substitutionMappings
   * @return substitutionMappings
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getSubstitutionMappings() {
    return substitutionMappings;
  }

  public void setSubstitutionMappings(Map<String, String> substitutionMappings) {
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

