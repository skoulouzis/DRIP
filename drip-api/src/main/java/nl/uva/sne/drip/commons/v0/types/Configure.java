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

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author S. Koulouzis
 */
@XmlRootElement
public class Configure {

    public String user;
    public String pwd;
    public String keyid;
    public String key;

    @XmlElement(name = "loginKey")
    public List<LoginKey0> loginKey;
    public String geniKey;
    public String geniKeyAlias;

    @XmlElement(name = "loginPubKey")
    public List<LoginKey0> loginPubKey;

    public String geniKeyPass;
    @XmlElement(name = "loginPriKey")
    public List<LoginKey0> loginPriKey;

}
