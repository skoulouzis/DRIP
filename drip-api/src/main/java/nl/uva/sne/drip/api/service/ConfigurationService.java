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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.drip.commons.data.v1.external.ConfigurationRepresentation;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.drip.commons.data.v1.external.User;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import nl.uva.sne.drip.api.dao.ConfigurationDao;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class ConfigurationService {

    @Autowired
    private ConfigurationDao dao;

    public String get(String id, String fromat) throws JSONException, NotFoundException {
        ConfigurationRepresentation configuration = findOne(id);
        if (configuration == null) {
            throw new NotFoundException();
        }

        Map<String, Object> map = configuration.getKeyValue();

        if (fromat != null && fromat.toLowerCase().equals("yml")) {
            String ymlStr = Converter.map2YmlString(map);
            ymlStr = cleanup(ymlStr);

            return ymlStr;
        }
        if (fromat != null && fromat.toLowerCase().equals("json")) {
            String jsonStr = Converter.map2JsonString(map);
            return jsonStr;
        }
        String ymlStr = Converter.map2YmlString(map);
        ymlStr = ymlStr.replaceAll("\\uff0E", ".");
        ymlStr = cleanup(ymlStr);
        return ymlStr;
    }

    public String saveFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        String str = new String(bytes, "UTF-8");
        return saveStringContents(str);
    }

    public String saveYamlString(String yamlString, String name) throws IOException {
        yamlString = yamlString.replaceAll("\\.", "\uff0E");
        Map<String, Object> map = Converter.ymlString2Map(yamlString);
        ConfigurationRepresentation t = new ConfigurationRepresentation();
        t.setKvMap(map);
        save(t);
        return t.getId();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public ConfigurationRepresentation delete(String id) {
        ConfigurationRepresentation tr = dao.findOne(id);
        if (tr == null) {
            throw new NotFoundException();
        }
        dao.delete(tr);
        return tr;
    }

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<ConfigurationRepresentation> findAll() {
        return dao.findAll();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public ConfigurationRepresentation findOne(String id) {
        return dao.findOne(id);
    }

    private ConfigurationRepresentation save(ConfigurationRepresentation ownedObject) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        ownedObject.setOwner(owner);

        return dao.save(ownedObject);
    }

    @PostAuthorize("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        dao.deleteAll();
    }

    public String saveStringContents(String yamlContents) throws IOException {
        Map<String, Object> map = Converter.cleanStringContents(yamlContents, false);
        ConfigurationRepresentation t = new ConfigurationRepresentation();
        t.setKvMap(map);
        save(t);
        return t.getId();
    }

    private String cleanup(String ymlStr) {
        ymlStr = ymlStr.replaceAll("\\uff0E", ".");
        ymlStr = ymlStr.replaceAll("\'---':", "---");
        Pattern p = Pattern.compile("version:.*");
        Matcher match = p.matcher(ymlStr);
        while (match.find()) {
            String line = match.group();
            if (!line.contains("\"") || line.contains("'")) {
                String number = line.split(": ")[1];
                ymlStr = ymlStr.replaceAll(number, "\'" + number + "\'");
            }

        }

        p = Pattern.compile("cpus:.*,");
        match = p.matcher(ymlStr);
        while (match.find()) {
            String line = match.group();
            if (!line.contains("\"") || line.contains("'")) {
                String cpusNum = line.split(":")[1];
                cpusNum = cpusNum.replaceAll(",", "").trim();
                ymlStr = ymlStr.replaceAll(cpusNum, '\'' + cpusNum + '\'');
            }
        }
        p = Pattern.compile("memory:.*");
        match = p.matcher(ymlStr);
        while (match.find()) {
            String line = match.group();
            if (!line.contains("\"") || line.contains("'")) {
                String memory = line.split(":")[1];
                memory = memory.replaceAll("}", "").trim();
                ymlStr = ymlStr.replaceAll(memory, '\'' + memory + '\'');
            }
        }

        return ymlStr.replaceAll("'''", "'");
    }

}
