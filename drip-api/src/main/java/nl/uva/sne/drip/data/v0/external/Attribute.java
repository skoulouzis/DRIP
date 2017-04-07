/*
 * Copyright 2017 S. Koulouzis, Wang Junchao, Huan Zhou, Yang Hu 
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
package nl.uva.sne.drip.data.v0.external;

import com.webcohesion.enunciate.metadata.DocumentationExample;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author S. Koulouzis
 */
public class Attribute {

    /**
     * Name of the file/public key. 
     */
    @DocumentationExample("planner_output_all")
    @XmlAttribute
    public String name;
    
        /**
     * There are two levels of description files for topologies designed by users. 
     * If the level attribute for the element file is 1, then this file is the top-level description, 
     * which defines how the sub-topologies are connected to each other. 
     * If the level attribute is 0, then the file is the low-level description, 
     * which describes the topology in one data center in detail. 
     * On the other hand, the name of low-level description file must be the 
     * sub-topology name appeared in the high-level description file. (For example, here should be zh_a and zh_b.)
     */
    @DocumentationExample("0")
    @XmlAttribute
    public String level;

    @XmlValue
    public String content;

}
