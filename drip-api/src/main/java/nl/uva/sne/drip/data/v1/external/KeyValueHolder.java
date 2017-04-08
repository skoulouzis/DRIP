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
package nl.uva.sne.drip.data.v1.external;

import com.webcohesion.enunciate.metadata.DocumentationExample;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This is a generic class that hold key-value pairs. It's main usage is to hold 
 * abstract types such as TOSCA.
 * 
 * @author S. Koulouzis
 */
@Document
public class KeyValueHolder extends OwnedObject{

    private Map<String, Object> keyValue;

    /**
     *  The key-value map 
     * @return the keyValue
     */
    @DocumentationExample("\"artifact_types\":{\"tosca.artifacts.Deployment.Image.Container.Docker\":"
            + "{\"derived_from\": \"tosca.artifacts.Deployment.Image\"}}")
    public Map<String, Object> getKeyValue() {
        return keyValue;
    }

    /**
     * @param keyValue the keyValue to set
     */
    public void setKvMap(Map<String, Object> keyValue) {
        this.keyValue = keyValue;
    }

}
