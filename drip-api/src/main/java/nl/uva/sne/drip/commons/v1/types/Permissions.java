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
package nl.uva.sne.drip.commons.v1.types;

import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author S. Koulouzis
 */
@Document
public class Permissions {

    private Set<String> read;
    private Set<String> write;

    /**
     * @return the read
     */
    public Set<String> getRead() {
        return read;
    }

    /**
     * @param read the read to set
     */
    public void setRead(Set<String> read) {
        this.read = read;
    }

    /**
     * @return the write
     */
    public Set<String> getWrite() {
        return write;
    }

    /**
     * @param write the write to set
     */
    public void setWrite(Set<String> write) {
        this.write = write;
    }

 
}
