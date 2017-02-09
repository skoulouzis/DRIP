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

/**
 *
 * @author S. Koulouzis
 */
public interface IParameter {

    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String VALUE = "value";
    public static final String ENCODING = "encoding";

    public void setName(String name);

    public String getName();

    public String getValue();

    public void setValue(String value);

    public String getURL();

    public void setURL(String url);

    public String getEncoding();

    public void setEncoding(String encoding);
    
}
