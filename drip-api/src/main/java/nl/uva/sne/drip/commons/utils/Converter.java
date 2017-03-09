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
package nl.uva.sne.drip.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import nl.uva.sne.drip.commons.v0.types.File;
import nl.uva.sne.drip.commons.v1.types.CloudCredentials;
import nl.uva.sne.drip.commons.v1.types.Message;
import nl.uva.sne.drip.commons.v1.types.MessageParameter;
import nl.uva.sne.drip.commons.v1.types.Plan;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author S. Koulouzis
 */
public class Converter {

    private static final Map<String, String> EC2_NAME_MAP = new HashMap();

    public static String ymlString2Json(String yamlString) {
        JSONObject jsonObject = new JSONObject(ymlString2Map(yamlString));
        return jsonObject.toString();
    }

    public static Map<String, Object> ymlString2Map(String yamlString) {
        Yaml yaml = new Yaml();
        return (Map<String, Object>) yaml.load(yamlString);
    }

    public static Map<String, Object> ymlString2Map(InputStream in) {
        Yaml yaml = new Yaml();
        Map<String, Object> map = (Map<String, Object>) yaml.load(in);
        return map;
    }

    public static String map2YmlString(Map<String, Object> map) throws JSONException {
        JSONObject jsonObject = new JSONObject(map);
        String yamlStr = json2Yml2(jsonObject.toString());
        return yamlStr;
    }

    public static String map2JsonString(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    public static Map<String, Object> jsonString2Map(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject2Map(jsonObject);
    }

    public static Map<String, Object> jsonObject2Map(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = jsonArray2List((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = jsonObject2Map((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> jsonArray2List(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = jsonArray2List((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = jsonObject2Map((JSONObject) value);
            }
            list.add(value);
        }
        return list;

    }

    public static String json2Yml2(String jsonString) throws JSONException {
        Yaml yaml = new Yaml();
        String yamlStr = yaml.dump(ymlString2Map(jsonString));
        return yamlStr;
    }

    public static Properties Object2Properties(Object obj) throws JsonProcessingException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(obj);
        return Property.toProperties(new JSONObject(jsonInString));
    }

    public static Properties getEC2Properties(CloudCredentials cred) throws JsonProcessingException, JSONException {
        Properties prop = Object2Properties(cred);
        Enumeration<String> names = (Enumeration<String>) prop.propertyNames();
        Properties ec2Props = new Properties();
        if (EC2_NAME_MAP.isEmpty()) {
            initEC2_NAME_MAP();
        }
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String ec2Name = EC2_NAME_MAP.get(name);
            if (ec2Name != null) {
                ec2Props.setProperty(ec2Name, prop.getProperty(name));
            }

        }
        return ec2Props;
    }

    private static void initEC2_NAME_MAP() {
        EC2_NAME_MAP.put("keyIdAlias", "AWSAccessKeyId");
        EC2_NAME_MAP.put("key", "AWSSecretKey");
    }

    public static Message string2Message(String clean) throws JSONException, IOException {
        Message mess = new Message();
        JSONObject jsonObj = new JSONObject(clean);
        long creationDate = jsonObj.getLong("creationDate");
        mess.setCreationDate(creationDate);
        JSONArray jsonParams = (JSONArray) jsonObj.get("parameters");
        List<MessageParameter> params = new ArrayList<>();
        for (int i = 0; i < jsonParams.length(); i++) {
            MessageParameter p = new MessageParameter();
            JSONObject jsonParam = (JSONObject) jsonParams.get(i);
            String url;
            if (!jsonObj.isNull("url")) {
                p.setURL((String) jsonObj.get("url"));
            }
            if (!jsonObj.isNull("encoding")) {
                p.setEncoding(jsonObj.getString("encoding"));

            }
            if (!jsonObj.isNull("attributes")) {
                Map<String, String> attributes = new ObjectMapper().readValue("", Map.class);
                p.setAttributes(attributes);

            }
            String val = jsonParam.getString("value");
            val = val.replaceAll("\"", "\\\"");
            p.setValue(val);
            params.add(p);
        }
        mess.setParameters(params);
        return mess;
    }

    public static File plan1toFile(Plan plan1) throws JSONException {
        File e = new File();
        e.level = String.valueOf(plan1.getLevel());
        String p1Name = FilenameUtils.getBaseName(plan1.getName());
        if (p1Name == null) {
            p1Name = "Planned_tosca_file_" + plan1.getLevel();
            plan1.setName(p1Name);
        }

        e.name = p1Name;
        String ymlString = Converter.map2YmlString(plan1.getKeyValue());
        e.content = ymlString.replaceAll("\n", "\\\\n");
        return e;
    }

    public static Plan File2Plan1(File p0) {
        Plan p1 = new Plan();
        p1.setLevel(Integer.valueOf(p0.level));
        p1.setName(p0.name);
        String yaml = p0.content.replaceAll("\\\\n", "\n");
        p1.setKvMap(ymlString2Map(yaml));

        return p1;
    }

}
