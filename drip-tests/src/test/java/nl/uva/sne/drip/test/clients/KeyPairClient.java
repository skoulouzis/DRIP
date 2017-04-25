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
package nl.uva.sne.drip.test.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URL;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import nl.uva.sne.drip.data.v1.external.KeyPair;

/**
 *
 * @author S. Koulouzis
 */
public class KeyPairClient extends RestClient {

    private static final String PATH = "/user/v1.0/keys";

    public KeyPairClient(URL host) {
        super(host);
    }

    public Response postKeyPair(KeyPair pair) throws JsonProcessingException {
        String jsonInString = getMapper().writeValueAsString(pair);

        Entity<String> entity = Entity.json(jsonInString);
//        Entity<KeyPair> entity = Entity.entity(pair, "application/json");

        return getWebTarget().
                path(PATH).
                request().
                header("Authorization", "Basic dXNlcjoxMjM=").post(entity);
    }

    public String getPostKeyPairResponse(Response response) {
        return response.readEntity(String.class);
    }

    public Response get(String keyID) {
        return getWebTarget().
                path(PATH).
                path(keyID).
                request().
                header("Authorization", "Basic dXNlcjoxMjM=").get();

    }

    public KeyPair getGetKeyPairResponse(Response response) throws IOException {
        String json = response.readEntity(String.class);
        return getMapper().readValue(json, KeyPair.class);
    }

    public Response delete(String keyID) {
        return getWebTarget().
                path(PATH).
                path(keyID).
                request().
                header("Authorization", "Basic dXNlcjoxMjM=").delete();
    }

}
