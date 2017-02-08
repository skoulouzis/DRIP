/*
 * Copyright 2017 S. Koulouzis.
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

import java.io.Serializable;

/**
 *
 * @author S. Koulouzis.
 */
public class FileParameter implements IFileParameter, IParameter, Serializable {

    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String VALUE = "value";
    public static final String ENCODING = "encoding";
    
    private String url;
    private String encoding;
    private String value;
    private String name;

    @Override
    public String getURL() {
        return this.url;
    }

    @Override
    public void setURL(String url) {
        this.url = url;
    }

    @Override
    public String getEncoding() {
        return this.encoding;
    }

    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

}
