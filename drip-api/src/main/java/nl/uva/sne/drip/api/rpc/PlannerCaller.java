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

import java.io.IOException;

import java.util.concurrent.TimeoutException;

/**
 *
 * @author S. Koulouzis.
 */
public class PlannerCaller extends DRIPCaller {

    private static final String REQUEST_QUEUE_NAME = "planner_queue";

    public PlannerCaller(String messageBrokerHost) throws IOException, TimeoutException {
        super(messageBrokerHost, REQUEST_QUEUE_NAME);
    }

}
