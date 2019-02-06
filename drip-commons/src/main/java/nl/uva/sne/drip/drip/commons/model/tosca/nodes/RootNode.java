package nl.uva.sne.drip.drip.commons.model.tosca.nodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.uva.sne.drip.drip.commons.model.tosca.Artifact;
import nl.uva.sne.drip.drip.commons.model.tosca.Attribute;
import nl.uva.sne.drip.drip.commons.model.tosca.Interface;
import nl.uva.sne.drip.drip.commons.model.tosca.Requirement;
import nl.uva.sne.drip.drip.commons.model.tosca.capabilities.Capabilities;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Properties;
import org.neo4j.ogm.annotation.Property;

/**
 *
 * @author S. Koulouzis
 */
@NodeEntity
public abstract class RootNode {

    @Id
    @GeneratedValue
    private Long tosca_id;

    @Property(name = "tosca_name")
    private String tosca_name;

    @Property(name = "state")
    private NodeState state;

    @Properties
    private Map<String, Capabilities> capabilities;

    @Properties
    private Map<String, Attribute> attributes;
    @Properties
    private List<Requirement> requirements;

    @Properties
    private Map<String, Interface> interfaces;
    @Properties
    private Map<String, Artifact> artifacts;

    /**
     * @return the tosca_name
     */
    public String getTosca_name() {
        return tosca_name;
    }

    /**
     * @param tosca_name the tosca_name to set
     */
    public void setTosca_name(String tosca_name) {
        this.tosca_name = tosca_name;
    }

    /**
     * @return the state
     */
    public NodeState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(NodeState state) {
        this.state = state;
    }

    /**
     * @return the capabilities
     */
    public Map<String, Capabilities> getCapabilities() {
        return capabilities;
    }

    /**
     * @param capabilities the capabilities to set
     */
    public void setCapabilities(Map<String, Capabilities> capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * @return the attributes
     */
    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Map<String, Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the requirements
     */
    public List<Requirement> getRequirements() {
        return requirements;
    }

    /**
     * @param requirements the requirements to set
     */
    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    /**
     * @return the interfaces
     */
    public Map<String, Interface> getInterfaces() {
        return interfaces;
    }

    /**
     * @param interfaces the interfaces to set
     */
    public void setInterfaces(Map<String, Interface> interfaces) {
        this.interfaces = interfaces;
    }

    /**
     * @return the artifacts
     */
    public Map<String, Artifact> getArtifacts() {
        return artifacts;
    }

    /**
     * @param artifacts the artifacts to set
     */
    public void setArtifacts(Map<String, Artifact> artifacts) {
        this.artifacts = artifacts;
    }

}
