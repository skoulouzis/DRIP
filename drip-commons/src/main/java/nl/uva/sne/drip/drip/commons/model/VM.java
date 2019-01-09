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
package nl.uva.sne.drip.drip.commons.model;

import com.webcohesion.enunciate.metadata.DocumentationExample;

/**
 * The lowest level is the VM level. It describes the types of VMs required,
 * mainly referring to the computing capacities, CPU, memory, etc
 *
 * @author S. Koulouzis
 */
public class VM {

    /**
     * This field can be omitted. After provisioning, you can check the actual 
     * public address of this VM from this field.
     * @return the publicAddress
     */
    @DocumentationExample("145.18.150.10")
    public String getPublicAddress() {
        return publicAddress;
    }

    /**
     * @param publicAddress the publicAddress to set
     */
    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }

    /**
     * It indicates the specific operating system required by the application.
     * The value of above two fields should be supported by the Cloud database
     * information.
     *
     * @return the osType
     */
    @DocumentationExample("Ubuntu 14.04")
    public String getOsType() {
        return osType;
    }

    /**
     * @param osType the osType to set
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * It indicates the computing capacity of the VM, such as t2.small or
     * t2.medium for Cloud EC2.
     *
     * @return the nodeType
     */
    @DocumentationExample("t2.small")
    public String getNodeType() {
        return nodeType;
    }

    /**
     * @param nodeType the nodeType to set
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * A user-defined node name of this VM. It should be unique in the entire
     * infrastructure, e.g., node1. This will also be the hostname of this VM.
     * And this VM can be accessed from other connected VMs with this hostname,
     * e.g., ping node1.
     *
     * @return the name
     */
    @DocumentationExample("node1")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private String nodeType;

    private String osType;
    
    
    private String publicAddress;

}
