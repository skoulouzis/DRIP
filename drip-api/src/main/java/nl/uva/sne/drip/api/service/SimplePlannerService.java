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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import nl.uva.sne.drip.api.exception.NotFoundException;
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
public class SimplePlannerService {

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    @Autowired
    private ToscaService toscaService;

    public ToscaRepresentation getPlan(String toscaId) throws JSONException, IOException, TimeoutException, InterruptedException {
        Message plannerInvokationMessage = buildSimplePlannerMessage(toscaId);
        ToscaRepresentation topLevel;
        try (PlannerCaller planner = new PlannerCaller(messageBrokerHost)) {
            Message plannerReturnedMessage = planner.call(plannerInvokationMessage);
            List<Parameter> toscaFiles = plannerReturnedMessage.getParameters();
            topLevel = new ToscaRepresentation();
            ToscaRepresentation tr = null;
            for (Parameter p : toscaFiles) {
                //Should have levels in attributes
                Map<String, String> attributess = p.getAttributes();
                String originalFileName = p.getName();
                String name = System.currentTimeMillis() + "_" + originalFileName;
                if (originalFileName.equals("planner_output_all.yml")) {
                    topLevel.setName(name);
                    topLevel.setLevel(0);
                    topLevel.setKvMap(Converter.ymlString2Map(p.getValue()));
                } else {
                    tr = new ToscaRepresentation();
                    tr.setName(name);
                    tr.setKvMap(Converter.ymlString2Map(p.getValue()));
                    tr.setLevel(1);
                }
                tr.setType(ToscaRepresentation.Type.PLAN);
                toscaService.getDao().save(tr);
                Set<String> ids = topLevel.getLowerLevelIDs();
                if (ids == null) {
                    ids = new HashSet<>();
                }
                ids.add(tr.getId());
                topLevel.setLowerLevelIDs(ids);
            }   topLevel.setType(ToscaRepresentation.Type.PLAN);
            toscaService.getDao().save(topLevel);
        }
        return topLevel;
    }

    private Message buildSimplePlannerMessage(String toscaId) throws JSONException, UnsupportedEncodingException, IOException {
        ToscaRepresentation t2 = toscaService.getDao().findOne(toscaId);
        if (t2 == null) {
            throw new NotFoundException();
        }
        Map<String, Object> map = t2.getKvMap();
        String ymlStr = Converter.map2YmlString(map);
        ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
        byte[] bytes = ymlStr.getBytes();

        Message invokationMessage = new Message();
        List parameters = new ArrayList();
        Parameter fileArgument = new Parameter();

        String charset = "UTF-8";
        fileArgument.setValue(new String(bytes, charset));
        fileArgument.setEncoding(charset);
        fileArgument.setName("input");
        parameters.add(fileArgument);

        fileArgument = new Parameter();
        bytes = Files.readAllBytes(Paths.get(System.getProperty("user.home") + File.separator + "Downloads/DRIP/example_a.yml"));
        fileArgument.setValue(new String(bytes, charset));
        fileArgument.setEncoding(charset);
        fileArgument.setName("example");
        parameters.add(fileArgument);

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }
}
