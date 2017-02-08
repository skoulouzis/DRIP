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
package nl.uva.sne.drip.api.rest;

/**
 *
 * @author S. Koulouzis.
 */
interface IFileArgument {

    public void setID(String id);

    public String getID();

    public String getURL();

    public void setURL(String url);

    public String getEncoding();

    public void setEncoding(String encoding);

    public String getContents();

    public void setContents(String contents);

}
