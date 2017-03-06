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
package nl.uva.sne.drip.api.rpc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.util.concurrent.TimeoutException;
import nl.uva.sne.drip.commons.types.Message;

/**
 *
 * @author S. Koulouzis.
 */
public class PlannerCaller extends DRIPCaller {

    private static final String REQUEST_QUEUE_NAME = "planner_queue";

    public PlannerCaller(String messageBrokerHost) throws IOException, TimeoutException {
        super(messageBrokerHost, REQUEST_QUEUE_NAME);
    }

    public String generateFakeResponse(Message plannerInvokationMessage) throws IOException {
        return "{\n"
                + "  \"creationDate\": 1487002029722,\n"
                + "  \"parameters\": [\n"
                + "    {\n"
                + "      \"url\": null,\n"
                + "      \"encoding\": \"UTF-8\",\n"
                + "      \"value\": \"{\\\"name\\\":\\\"2d13d708e3a9441ab8336ce874e08dd1\\\",\\\"size\\\":\\\"Small\\\",\\\"docker\\\":\\\"mogswitch/InputDistributor\\\"}\",\n"
                + "      \"name\": \"component\",\n"
                + "      \"attributes\": null\n"
                + "    },\n"
                + "    {\n"
                + "      \"url\": null,\n"
                + "      \"encoding\": \"UTF-8\",\n"
                + "      \"value\": \"{\\\"name\\\":\\\"8fcc1788d9ee462c826572c79fdb2a6a\\\",\\\"size\\\":\\\"Small\\\",\\\"docker\\\":\\\"mogswitch/InputDistributor\\\"}\",\n"
                + "      \"name\": \"component\",\n"
                + "      \"attributes\": null\n"
                + "    },\n"
                + "    {\n"
                + "      \"url\": null,\n"
                + "      \"encoding\": \"UTF-8\",\n"
                + "      \"value\": \"{\\\"name\\\":\\\"5e0add703c8a43938a39301f572e46c0\\\",\\\"size\\\":\\\"Small\\\",\\\"docker\\\":\\\"mogswitch/InputDistributor\\\"}\",\n"
                + "      \"name\": \"component\",\n"
                + "      \"attributes\": null\n"
                + "    }\n"
                + "  ]\n"
                + "}";
    }

}
