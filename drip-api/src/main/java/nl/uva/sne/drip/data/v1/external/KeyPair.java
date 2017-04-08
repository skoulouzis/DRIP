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

import nl.uva.sne.drip.api.exception.KeyException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class hold the pair of public private keys. The kyes may be used for
 * logging in VMs.
 *
 * @author S. Koulouzis
 */
@Document
public class KeyPair extends OwnedObject {

    private Key privateKey;
    private Key publicKey;

    /**
     * @return the privateKey
     */
    public Key getPrivateKey() {
        return privateKey;
    }

    /**
     * @param privateKey the privateKey to set
     * @throws nl.uva.sne.drip.api.exception.KeyException
     */
    public void setPrivateKey(Key privateKey) throws KeyException {
        if (privateKey.getType() != Key.Type.PRIVATE) {
            throw new KeyException("Trying to add public key to private");
        }
        this.privateKey = privateKey;
    }

    /**
     * @return the publicKey
     */
    public Key getPublicKey() {
        return publicKey;
    }

    /**
     * @param publicKey the publicKey to set
     * @throws nl.uva.sne.drip.api.exception.KeyException
     */
    public void setPublicKey(Key publicKey) throws KeyException {
        if (privateKey.getType() != Key.Type.PUBLIC) {
            throw new KeyException("Trying to add private to public");
        }
        this.publicKey = publicKey;
    }

}
