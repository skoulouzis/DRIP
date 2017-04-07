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
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.commons.utils.Constants;
import nl.uva.sne.drip.data.v1.external.PlaybookRepresentation;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.data.v1.external.User;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import nl.uva.sne.drip.api.dao.PlaybookDao;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class PlaybookService {

    @Autowired
    private PlaybookDao dao;

    public String get(String id, String fromat) throws JSONException {
        PlaybookRepresentation playbook = findOne(id);
        if (playbook == null) {
            throw new NotFoundException();
        }

        Map<String, Object> map = playbook.getKeyValue();

        if (fromat != null && fromat.toLowerCase().equals("yml")) {
            String ymlStr = Converter.map2YmlString(map);
            ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
            ymlStr = ymlStr.replaceAll("\'---':", "---");
            return ymlStr;
        }
        if (fromat != null && fromat.toLowerCase().equals("json")) {
            String jsonStr = Converter.map2JsonString(map);
            jsonStr = jsonStr.replaceAll("\\uff0E", "\\.");
            return jsonStr;
        }
        String ymlStr = Converter.map2YmlString(map);
        ymlStr = ymlStr.replaceAll("\\uff0E", "\\.");
        ymlStr = ymlStr.replaceAll("\'---':", "---");

        return ymlStr;
    }

    public String saveFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String name = System.currentTimeMillis() + "_" + originalFileName;
        byte[] bytes = file.getBytes();
        String str = new String(bytes, "UTF-8");
        return saveStringContents(str, name);
    }

    public String saveYamlString(String yamlString, String name) throws IOException {
        if (name == null) {
            name = System.currentTimeMillis() + "_" + "playbook.yaml";
        }
        yamlString = yamlString.replaceAll("\\.", "\uff0E");
        Map<String, Object> map = Converter.ymlString2Map(yamlString);
        PlaybookRepresentation t = new PlaybookRepresentation();
        t.setName(name);
        t.setKvMap(map);
        save(t);
        return t.getId();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public PlaybookRepresentation delete(String id) {
        PlaybookRepresentation tr = dao.findOne(id);
        dao.delete(tr);
        return tr;
    }

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<PlaybookRepresentation> findAll() {
        return dao.findAll();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public PlaybookRepresentation findOne(String id) {
        return dao.findOne(id);
    }

    private PlaybookRepresentation save(PlaybookRepresentation t) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        t.setOwner(owner);
        return dao.save(t);
    }

    @PostAuthorize("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        dao.deleteAll();
    }

    public String saveStringContents(String playbookContents, String name) throws IOException {

        //Remove '\' and 'n' if they are together and replace them with '\n'
        char[] array = playbookContents.toCharArray();
        StringBuilder sb = new StringBuilder();
        int prevChar = -1;
        for (int i = 0; i < array.length; i++) {
            int currentChar = (int) array[i];
            if (prevChar > 0 && prevChar == 92 && currentChar == 110) {
                sb.delete(sb.length() - 1, sb.length());
                sb.append('\n');

            } else {
                sb.append((char) currentChar);
            }
            prevChar = (int) array[i];
        }
        playbookContents = sb.toString();
        playbookContents = playbookContents.replaceAll("(?m)^[ \t]*\r?\n", "");
        for (int i = 0; i < Constants.BAD_CHARS.length; i++) {
            int hex = Constants.BAD_CHARS[i];
            playbookContents = playbookContents.replaceAll(String.valueOf((char) hex), "");
        }

        playbookContents = playbookContents.replaceAll("\\.", "\uff0E");
        Map<String, Object> map = Converter.ymlString2Map(playbookContents);
        PlaybookRepresentation t = new PlaybookRepresentation();
        t.setName(name);
        t.setKvMap(map);
        save(t);
        return t.getId();
    }

}
