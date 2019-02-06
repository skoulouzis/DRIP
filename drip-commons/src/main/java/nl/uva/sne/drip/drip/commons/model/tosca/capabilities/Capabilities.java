/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.drip.commons.model.tosca.capabilities;

import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.namespace.QName;
import nl.uva.sne.drip.drip.commons.model.tosca.Attribute;
import org.neo4j.ogm.annotation.Properties;
import org.neo4j.ogm.annotation.Property;

/**
 * http://docs.oasis-open.org/tosca/TOSCA-Simple-Profile-YAML/v1.0/os/TOSCA-Simple-Profile-YAML-v1.0-os.html#DEFN_TYPE_CAPABILITIES_ROOT
 *
 * @author alogo
 */
public abstract class Capabilities {

    @Property(name = "type")
    private String type;

    @Property(name = "description")
    private String description;

    @Property(name = "valid_source_types")
    private List<String> validSourceTypes;

    @Property(name = "occurrences")
    private List<String> occurrences;

    @Properties
    private Map<String, nl.uva.sne.drip.drip.commons.model.tosca.Property> properties;
    @Properties
    private Map<String, Attribute> attributes;

}
