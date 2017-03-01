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
package nl.uva.sne.drip.api.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.rpc.PlannerCaller;
import nl.uva.sne.drip.commons.types.Message;
import nl.uva.sne.drip.commons.types.Parameter;
import nl.uva.sne.drip.commons.types.ToscaRepresentation;
import nl.uva.sne.drip.commons.utils.Converter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class PlannerService {

    @Autowired
    private ToscaService toscaService;

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    public ToscaRepresentation getPlan(String toscaId) throws JSONException, UnsupportedEncodingException, IOException, TimeoutException, InterruptedException {

        try (PlannerCaller planner = new PlannerCaller(messageBrokerHost)) {
            Message plannerInvokationMessage = buildPlannerMessage(toscaId);

            Message plannerReturnedMessage = planner.call(plannerInvokationMessage);
            List<Parameter> parameters = plannerReturnedMessage.getParameters();
            for (Parameter param : parameters) {

            }
            ToscaRepresentation tr = new ToscaRepresentation();
            Map<String, Object> kvMap = null;
            tr.setKvMap(kvMap);

            return null;
        }
    }

    private Message buildPlannerMessage(String toscaId) throws JSONException, UnsupportedEncodingException {
        ToscaRepresentation t2 = toscaService.getDao().findOne(toscaId);
        if (t2 == null) {
            throw new BadRequestException("The description: " + toscaId + " is a plan. Cannot be used as planner input");
        }
        Map<String, Object> map = t2.getKvMap();
        String json = Converter.map2JsonString(map);
        json = json.replaceAll("\\uff0E", "\\.");
        byte[] bytes = json.getBytes();

        Message invokationMessage = new Message();
        List parameters = new ArrayList();
        Parameter jsonArgument = new Parameter();
        String charset = "UTF-8";
        jsonArgument.setValue(new String(bytes, charset));
        jsonArgument.setEncoding(charset);
        jsonArgument.setName("input");
        parameters.add(jsonArgument);

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }

}
