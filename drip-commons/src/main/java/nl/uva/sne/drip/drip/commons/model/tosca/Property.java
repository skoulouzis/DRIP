/*
 * Copyright 2019 S. Koulouzis, Huan Zhou, Yang Hu 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.uva.sne.drip.drip.commons.model.tosca;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.namespace.QName;

/**
 *
 * @author S. Koulouzis
 */
public abstract class Property {

    @org.neo4j.ogm.annotation.Property(name = "type")
    private String type;

    @org.neo4j.ogm.annotation.Property(name = "description")
    private String description;

    @org.neo4j.ogm.annotation.Property(name = "required")
    private Boolean required;

    @org.neo4j.ogm.annotation.Property(name = "default")
    private Object defaultValue;

    @org.neo4j.ogm.annotation.Property(name = "status")
    private PropertyStatus status;

    @org.neo4j.ogm.annotation.Property(name = "constraints")
    private List<Constraint> constraints;

    @org.neo4j.ogm.annotation.Property(name = "entry_schema")
    private EntrySchema entrySchema;

}