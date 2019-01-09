/*
 * Copyright 2019 S. Koulouzis, Huan Zhou, Yang Hu 
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
package nl.uva.sne.drip.service;

import lambdaInfrs.engine.TEngine.TEngine;
import nl.uva.sne.drip.drip.commons.model.InfrastructureDescription;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import topology.analysis.TopologyAnalysisMain;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class CloudStormService {

    @Value("${provisioner.queue.prefix}")
    private String provisionerQueuePrefix;

    public InfrastructureDescription provision(InfrastructureDescription description) {
        return null;
    }

    public InfrastructureDescription start() {
        return null;
    }

    public InfrastructureDescription stop() {
        return null;
    }

    public InfrastructureDescription delete() {
        return null;
    }

    public InfrastructureDescription recover() {
        return null;
    }

    public InfrastructureDescription detectFailure() {
        return null;
    }

    public InfrastructureDescription scale() {
        return null;
    }

}
