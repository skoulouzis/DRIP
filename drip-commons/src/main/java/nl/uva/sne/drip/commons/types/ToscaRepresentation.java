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
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author S. Koulouzis
 */
@Document
public class ToscaRepresentation {

    @Id
    private String id;

    private Set<String> lowerLevelIDs;

    private String name;

    private Map<String, Object> kvMap;

    private Integer level;

    private Type type;

    public static enum Type {
        PLAN,
        SIDE
    }

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    public final String getId() {
        return id;
    }

    public final void setId(final String id) {
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return the lowerLevelIDs
     */
    public Set<String> getLowerLevelIDs() {
        return lowerLevelIDs;
    }

    /**
     * @param lowerLevelIDs the lowerLevelIDs to set
     */
    public void setLowerLevelIDs(Set<String> lowerLevelIDs) {
        this.lowerLevelIDs = lowerLevelIDs;
    }

}
