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
import nl.uva.sne.drip.api.dao.PlanDao;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.rpc.PlannerCaller;
import nl.uva.sne.drip.commons.v1.types.Message;
import nl.uva.sne.drip.commons.v1.types.MessageParameter;
import nl.uva.sne.drip.commons.v1.types.Plan;
import nl.uva.sne.drip.commons.v1.types.ToscaRepresentation;
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

    @Autowired
    private PlanDao planDao;

    public Plan getPlan(String toscaId) throws JSONException, IOException, TimeoutException, InterruptedException {
        ToscaRepresentation tosca = toscaService.getDao().findOne(toscaId);
        Message plannerInvokationMessage = buildSimplePlannerMessage(tosca);

        Plan topLevel;
        try (PlannerCaller planner = new PlannerCaller(messageBrokerHost)) {
            Message plannerReturnedMessage = (planner.call(plannerInvokationMessage));
            List<MessageParameter> planFiles = plannerReturnedMessage.getParameters();
            topLevel = new Plan();
            Set<String> ids = topLevel.getLoweLevelPlanIDs();
            if (ids == null) {
                ids = new HashSet<>();
            }
            Plan lowerLevelPlan = null;
            for (MessageParameter p : planFiles) {
                //Should have levels in attributes
                Map<String, String> attributess = p.getAttributes();
                if (attributess != null) {
                    attributess.get("level");
                }
                String originalFileName = p.getName();
                String name = System.currentTimeMillis() + "_" + originalFileName;
                if (originalFileName.equals("planner_output_all.yml")) {
                    topLevel.setName(name);
                    topLevel.setLevel(0);
                    topLevel.setKvMap(Converter.ymlString2Map(p.getValue()));
                } else {
                    lowerLevelPlan = new Plan();
                    lowerLevelPlan.setName(name);
                    lowerLevelPlan.setKvMap(Converter.ymlString2Map(p.getValue()));
                    lowerLevelPlan.setLevel(1);
                    planDao.save(lowerLevelPlan);
                    ids.add(lowerLevelPlan.getId());
                }
            }
            topLevel.setLoweLevelPlansIDs(ids);
        }
        topLevel.setToscaID(toscaId);
        planDao.save(topLevel);
        return topLevel;
    }

    private Message buildSimplePlannerMessage(ToscaRepresentation t2) throws JSONException, UnsupportedEncodingException, IOException {
        if (t2 == null) {
            throw new NotFoundException();
        }
        Map<String, Object> map = t2.getKvMap();
        String ymlStr = Converter.map2YmlString(map);
        ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
        byte[] bytes = ymlStr.getBytes();

        Message invokationMessage = new Message();
        List parameters = new ArrayList();
        MessageParameter fileArgument = new MessageParameter();

        String charset = "UTF-8";
        fileArgument.setValue(new String(bytes, charset));
        fileArgument.setEncoding(charset);
        fileArgument.setName("input");
        parameters.add(fileArgument);

        fileArgument = new MessageParameter();
        bytes = Files.readAllBytes(Paths.get(System.getProperty("user.home") + File.separator + "Downloads/DRIP/example_a.yml"));
        fileArgument.setValue(new String(bytes, charset));
        fileArgument.setEncoding(charset);
        fileArgument.setName("example");
        parameters.add(fileArgument);

        invokationMessage.setParameters(parameters);
        invokationMessage.setCreationDate((System.currentTimeMillis()));
        return invokationMessage;
    }

    public String get(String id, String fromat) throws JSONException {
        Plan plan = planDao.findOne(id);
        if (plan == null) {
            throw new NotFoundException();
        }

        Map<String, Object> map = plan.getKvMap();
        Set<String> ids = plan.getLoweLevelPlanIDs();
        for (String lowID : ids) {
            Map<String, Object> lowLevelMap = planDao.findOne(lowID).getKvMap();
            map.putAll(lowLevelMap);
        }

        if (fromat != null && fromat.equals("yml")) {
            String ymlStr = Converter.map2YmlString(map);
            ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
            return ymlStr;
        }
        if (fromat != null && fromat.equals("json")) {
            String jsonStr = Converter.map2JsonString(map);
            jsonStr = jsonStr.replaceAll("\\uff0E", "\\.");
            return jsonStr;
        }
        String ymlStr = Converter.map2YmlString(map);
        ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
        return ymlStr;
    }

    public PlanDao getDao() {
        return planDao;
    }

    public String getToscaID(String id) {
        return planDao.findOne(id).getToscaID();
    }

    public List<Plan> findAll() {
        List<Plan> all = planDao.findAll();
        List<Plan> topLevel = new ArrayList<>();
        for (Plan p : all) {
            if (p.getLevel() == 0) {
                topLevel.add(p);
            }
        }
        return topLevel;
    }
}
