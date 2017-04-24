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
package nl.uva.sne.drip.commons.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import nl.uva.sne.drip.data.internal.Message;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;

/**
 *
 * @author S. Koulouzis
 */
public class MessageGenerator {

    public static Message generateArtificialMessage(String path) throws IOException, TimeoutException, InterruptedException, JSONException {
        String strResponse = FileUtils.readFileToString(new File(path));

        String clean = strResponse;
        if (strResponse.contains("'null'")) {
            clean = strResponse.replaceAll("'null'", "null").replaceAll("\'", "\"").replaceAll(" ", "");
        }
        if (clean.contains("\"value\":{\"")) {
            return Converter.string2Message(clean);
        }
        if (clean.contains("\"null\"")) {
            clean = clean.replaceAll("\"null\"", "null");
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        return mapper.readValue(clean, Message.class);
    }
}
