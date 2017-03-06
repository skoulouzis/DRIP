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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import nl.uva.sne.drip.api.dao.PlanDao;
import nl.uva.sne.drip.api.exception.BadRequestException;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.rpc.PlannerCaller;
import nl.uva.sne.drip.commons.types.Message;
import nl.uva.sne.drip.commons.types.MessageParameter;
import nl.uva.sne.drip.commons.types.Plan;
import nl.uva.sne.drip.commons.types.ToscaRepresentation;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.drip.converter.P2PConverter;
import nl.uva.sne.drip.drip.converter.SimplePlanContainer;
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

    @Autowired
    private PlanDao planDao;

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    public Plan getPlan(String toscaId) throws JSONException, UnsupportedEncodingException, IOException, TimeoutException, InterruptedException {

        try (PlannerCaller planner = new PlannerCaller(messageBrokerHost)) {
            Message plannerInvokationMessage = buildPlannerMessage(toscaId);
            Message plannerReturnedMessage = (planner.call(plannerInvokationMessage));
//            Message plannerReturnedMessage = (planner.generateFakeResponse(plannerInvokationMessage));
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            List<MessageParameter> messageParams = plannerReturnedMessage.getParameters();
            StringBuilder jsonArrayString = new StringBuilder();
            jsonArrayString.append("[");
            String prefix = "";
            for (MessageParameter mp : messageParams) {
                String value = mp.getValue();
                jsonArrayString.append(prefix);
                prefix = ",";
                String jsonValue = value.replaceAll("\\\"", "\"");
                jsonArrayString.append(jsonValue);
            }
            jsonArrayString.append("]");

            SimplePlanContainer simplePlan = P2PConverter.convert(jsonArrayString.toString(), "vm_user", "Ubuntu 16.04", "swarm");
            Plan topLevel = new Plan();
            topLevel.setLevel(0);
            topLevel.setToscaID(toscaId);
            topLevel.setName("planner_output_all.yml");
            topLevel.setKvMap(Converter.ymlString2Map(simplePlan.topLevelContents));
            Map<String, String> map = simplePlan.lowerLevelContents;
            Set<String> loweLevelPlansIDs = new HashSet<>();
            for (String lowLevelNames : map.keySet()) {
                Plan lowLevelPlan = new Plan();
                lowLevelPlan.setLevel(1);
                lowLevelPlan.setToscaID(toscaId);
                lowLevelPlan.setName(lowLevelNames);
                lowLevelPlan.setKvMap(Converter.ymlString2Map(map.get(lowLevelNames)));
                planDao.save(lowLevelPlan);
                loweLevelPlansIDs.add(lowLevelPlan.getId());
            }

            topLevel.setLoweLevelPlansIDs(loweLevelPlansIDs);
            planDao.save(topLevel);
            return topLevel;
        }
    }

    private Message buildPlannerMessage(String toscaId) throws JSONException, UnsupportedEncodingException {
        ToscaRepresentation t2 = toscaService.getDao().findOne(toscaId);
        if (t2 == null) {
            throw new BadRequestException();
        }
        Map<String, Object> map = t2.getKvMap();
        String json = Converter.map2JsonString(map);
        json = json.replaceAll("\\uff0E", "\\.");
        byte[] bytes = json.getBytes();

        Message invokationMessage = new Message();
        List parameters = new ArrayList();
        MessageParameter jsonArgument = new MessageParameter();
        String charset = "UTF-8";
        jsonArgument.setValue(new String(bytes, charset));
        jsonArgument.setEncoding(charset);
        jsonArgument.setName("input");
        parameters.add(jsonArgument);

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

    public String getToscaID(String id) {
        return planDao.findOne(id).getToscaID();
    }

    public PlanDao getDao() {
        return this.planDao;
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
