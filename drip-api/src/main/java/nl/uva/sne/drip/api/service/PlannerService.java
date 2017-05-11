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
import nl.uva.sne.drip.commons.utils.Constants;
import nl.uva.sne.drip.drip.commons.data.internal.Message;
import nl.uva.sne.drip.drip.commons.data.internal.MessageParameter;
import nl.uva.sne.drip.drip.commons.data.v1.external.PlanResponse;
import nl.uva.sne.drip.drip.commons.data.v1.external.ToscaRepresentation;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.drip.commons.data.v1.external.User;
import nl.uva.sne.drip.drip.converter.P2PConverter;
import nl.uva.sne.drip.drip.converter.SimplePlanContainer;
import nl.uva.sne.drip.drip.converter.plannerOut.Parameter;
import nl.uva.sne.drip.drip.converter.plannerOut.PlannerOutput;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class PlannerService {

    @Autowired
    private ToscaService toscaService;

    @Autowired
    private PlanDao planDao;

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    public PlanResponse getPlan(String toscaId, String clusterType, String vmUser, String cloudProvider, String OSType, String domainName) throws JSONException, UnsupportedEncodingException, IOException, TimeoutException, InterruptedException {

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

//            SimplePlanContainer simplePlan = P2PConverter.convert(jsonArrayString.toString(), "vm_user", "Ubuntu 16.04", clusterType);
            SimplePlanContainer simplePlan = P2PConverter.transfer(jsonArrayString.toString(), vmUser, OSType, domainName, clusterType, cloudProvider);

            PlanResponse topLevel = new PlanResponse();
            topLevel.setTimestamp(System.currentTimeMillis());
            topLevel.setLevel(0);
            topLevel.setToscaID(toscaId);
            topLevel.setName("planner_output_all.yml");
            topLevel.setKvMap(Converter.ymlString2Map(simplePlan.topLevelContents));
            Map<String, String> map = simplePlan.lowerLevelContents;
            Set<String> loweLevelPlansIDs = new HashSet<>();
            for (String lowLevelNames : map.keySet()) {
                PlanResponse lowLevelPlan = new PlanResponse();
                lowLevelPlan.setTimestamp(System.currentTimeMillis());
                lowLevelPlan.setLevel(1);
                lowLevelPlan.setToscaID(toscaId);
                lowLevelPlan.setName(lowLevelNames);
                lowLevelPlan.setKvMap(Converter.ymlString2Map(map.get(lowLevelNames)));
                save(lowLevelPlan);
                loweLevelPlansIDs.add(lowLevelPlan.getId());
            }

            topLevel.setLoweLevelPlansIDs(loweLevelPlansIDs);
            save(topLevel);
            return topLevel;
        }
    }

    private Message buildPlannerMessage(String toscaId) throws JSONException, UnsupportedEncodingException {
        ToscaRepresentation t2 = toscaService.findOne(toscaId);
        if (t2 == null) {
            throw new BadRequestException();
        }
        Map<String, Object> map = t2.getKeyValue();
        String json = Converter.map2JsonString(map);
        json = json.replaceAll("\\uff0E", ".");
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
        PlanResponse plan = findOne(id);
        if (plan == null) {
            throw new NotFoundException();
        }

        Map<String, Object> map = plan.getKeyValue();
        Set<String> ids = plan.getLoweLevelPlanIDs();
        if (ids != null) {
            for (String lowID : ids) {
                PlanResponse ll = findOne(lowID);
                Map<String, Object> lowLevelMap = ll.getKeyValue();
                if (lowLevelMap != null) {
                    map.put(ll.getName(), lowLevelMap);
                }
            }
        }

        if (fromat != null && fromat.equals("yml")) {
            String ymlStr = Converter.map2YmlString(map);
            ymlStr = ymlStr.replaceAll("\\uff0E", ".");
            return ymlStr;
        }
        if (fromat != null && fromat.equals("json")) {
            String jsonStr = Converter.map2JsonString(map);
            jsonStr = jsonStr.replaceAll("\\uff0E", ".");
            return jsonStr;
        }
        String ymlStr = Converter.map2YmlString(map);
        ymlStr = ymlStr.replaceAll("\\uff0E", ".");
        return ymlStr;
    }

    public String getToscaID(String id) {
        return findOne(id).getToscaID();
    }

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<PlanResponse> findAll() {
        List<PlanResponse> all = planDao.findAll();
        List<PlanResponse> topLevel = new ArrayList<>();
        for (PlanResponse p : all) {
            if (p.getLevel() == 0) {
                topLevel.add(p);
            }
        }
        return topLevel;
    }

    public PlanResponse save(PlanResponse plan) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        plan.setOwner(owner);
        return planDao.save(plan);
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public PlanResponse findOne(String id) {
        PlanResponse plan = planDao.findOne(id);
        if (plan == null) {
            throw new NotFoundException();
        }
        return plan;
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public PlanResponse delete(String id) {
        PlanResponse plan = planDao.findOne(id);
        if (plan == null) {
            throw new NotFoundException();
        }
        Set<String> lowIds = plan.getLoweLevelPlanIDs();
        if (lowIds != null) {
            for (String lId : lowIds) {
                planDao.delete(lId);
            }
        }
        planDao.delete(plan);
        return plan;
    }

    @PostAuthorize("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        planDao.deleteAll();
    }

    public String saveStringContents(String ymlContents, Integer level, String name) {
        Map<String, Object> map = Converter.cleanStringContents(ymlContents,true);
        PlanResponse pr = new PlanResponse();
        pr.setKvMap(map);
        pr.setLevel(level);
        pr.setName(name);
        save(pr);
        return pr.getId();
    }

}
