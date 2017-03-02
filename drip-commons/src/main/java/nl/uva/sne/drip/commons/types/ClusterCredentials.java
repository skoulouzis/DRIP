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
package nl.uva.sne.drip.commons.types;

import java.util.Map;
import org.springframework.data.annotation.Id;

/**
 *
 * @author S. Koulouzis
 */
public class ClusterCredentials {

    @Id
    private String id;

    private Map<String, Object> kvMap;

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

    /**
     * @return the kvMap
     */
    public Map<String, Object> getKvMap() {
        return kvMap;
    }

    /**
     * @param kvMap the kvMap to set
     */
    public void setKvMap(Map<String, Object> kvMap) {
        this.kvMap = kvMap;
    }

}
