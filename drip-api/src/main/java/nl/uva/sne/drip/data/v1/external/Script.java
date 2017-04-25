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
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents a simple script that can run on a provisioned VM.
 *
 * @author S. Koulouzis
 */
@Document
public class Script extends OwnedObject {

    private String contents;

    private String name;

    /**
     * The name of the script
     *
     * @return the name
     */
    @DocumentationExample("config.sh")
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
     * The contents of the script
     *
     * @return the contents
     */
    @DocumentationExample("    #!/bin/bash\n"
            + "echo \"Reading system-wide config....\" >&2\\n"
            + ". /etc/cool.cfg\n"
            + "if [ -r ~/.coolrc ]; then\n"
            + "  echo \"Reading user config....\" >&2\\n"
            + "  . ~/.coolrc\\n"
            + "fi")
    public String getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}
