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
import java.util.List;
import java.util.Map;
import nl.uva.sne.drip.api.dao.ToscaDao;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.commons.v1.types.ToscaRepresentation;
import nl.uva.sne.drip.commons.utils.Converter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class ToscaService {

    @Autowired
    private ToscaDao dao;

    public String get(String id, String fromat) throws JSONException {
        ToscaRepresentation tosca = dao.findOne(id);
        if (tosca == null) {
            throw new NotFoundException();
        }

        Map<String, Object> map = tosca.getKvMap();

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

    public String save(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String name = System.currentTimeMillis() + "_" + originalFileName;
        byte[] bytes = file.getBytes();
        String str = new String(bytes, "UTF-8");
        str = str.replaceAll("\\.", "\uff0E");

        Map<String, Object> map = Converter.ymlString2Map(str);
        ToscaRepresentation t = new ToscaRepresentation();
        t.setName(name);
        t.setKvMap(map);
        dao.save(t);
        return t.getId();
    }

    public void delete(String id) {
        dao.delete(id);
    }

    public List<ToscaRepresentation> findAll() {
        return dao.findAll();
    }

    public ToscaDao getDao() {
        return dao;
    }

    public ToscaRepresentation get(String planID) {
        ToscaRepresentation tosca = dao.findOne(planID);
        if (tosca == null) {
            throw new NotFoundException();
        }
        return tosca;
    }
}
