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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import nl.uva.sne.drip.drip.commons.data.v1.external.CloudCredentials;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import nl.uva.sne.drip.drip.commons.data.internal.Message;
import nl.uva.sne.drip.drip.commons.data.internal.MessageParameter;
import nl.uva.sne.drip.drip.commons.data.v0.external.Attribute;
import nl.uva.sne.drip.drip.commons.data.v1.external.PlanResponse;

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
        Object object = yaml.load(yamlString);
        if (object instanceof List) {
            Map<String, Object> map = new HashMap<>();
            map.put("---", object);
            return map;
        }
        return (Map<String, Object>) object;
    }

    public static Map<String, Object> ymlStream2Map(InputStream in) {
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

    public static List<Object> jsonString2List(String jsonString) throws JSONException {
        JSONArray jSONArray = new JSONArray(jsonString);
        return jsonArray2List(jSONArray);
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
            } else if (value.equals("[]")) {
                value = new ArrayList();
            } else if (value.getClass().getName().equals("org.json.JSONObject$Null")) {
                value = new String();
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
            if (value instanceof String) {
                System.err.println(value);
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
        EC2_NAME_MAP.put(CloudCredentials.ACCESS_KEY_NAME, "AWSAccessKeyId");
        EC2_NAME_MAP.put(CloudCredentials.SECRET_KEY_NAME, "AWSSecretKey");
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

    public static Attribute plan1toFile(PlanResponse plan1) throws JSONException {
        Attribute e = new Attribute();
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

    public static PlanResponse File2Plan1(Attribute p0) {
        PlanResponse p1 = new PlanResponse();
        p1.setLevel(Integer.valueOf(p0.level));
        p1.setName(p0.name);
        String yaml = p0.content.replaceAll("\\\\n", "\n");
        p1.setKvMap(ymlString2Map(yaml));

        return p1;
    }

    public static Map<String, Object> cleanStringContents(String ymlContents, boolean removeNewLine) {
        if (removeNewLine) {
            //Remove '\' and 'n' if they are together and replace them with '\n'
            char[] array = ymlContents.toCharArray();
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
            ymlContents = sb.toString();
        }

        ymlContents = ymlContents.replaceAll("(?m)^[ \t]*\r?\n", "");
        for (int i = 0; i < Constants.BAD_CHARS.length; i++) {
            int hex = Constants.BAD_CHARS[i];
            ymlContents = ymlContents.replaceAll(String.valueOf((char) hex), "");
        }

        ymlContents = ymlContents.replaceAll("\\.", "\uff0E");
        Map<String, Object> map = null;
        map = Converter.ymlString2Map(ymlContents);
        return map;
    }

    public static String jsonObject2String(String msg) {
        msg = msg.replaceAll("\"", Matcher.quoteReplacement("\\\""));
        msg = "\"" + msg + "\"";
        return msg;
    }

}
