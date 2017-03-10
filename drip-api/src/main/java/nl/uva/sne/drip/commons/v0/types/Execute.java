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
package nl.uva.sne.drip.commons.v0.types;

import com.webcohesion.enunciate.metadata.DocumentationExample;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author S. Koulouzis
 */
@XmlRootElement
public class Execute {

    /**
     * Not used. It's there for backwards compatibility.
     */
    @DocumentationExample("user")
    public String user;
    /**
     * Not used. It's there for backwards compatibility.
     */
    @DocumentationExample("123")
    public String pwd;

    /**
     * A referance id used from the servcie to do the provisioning
     */
    @DocumentationExample("58c2c2f3a8d4b56889878d03")
    public String action;

}
