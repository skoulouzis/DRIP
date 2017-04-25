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
package nl.uva.sne.drip.drip.provisioner.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author S. Koulouzis
 */
public class MessageParsing {

    public static File getTopology(JSONArray parameters, String tempInputDirPath, int level) throws JSONException, IOException {
        for (int i = 0; i < parameters.length(); i++) {
            JSONObject param = (JSONObject) parameters.get(i);
            String name = (String) param.get("name");
            if (name.equals("topology")) {
                JSONObject attributes = param.getJSONObject("attributes");
                int fileLevel = Integer.valueOf((String) attributes.get("level"));
                if (fileLevel == level) {
                    String originalFilename = (String) attributes.get("filename");
                    String fileName = "";
//                    String[] parts = originalFilename.split("_");
//                    String prefix = "";
//                    //Clear date part form file name
//                    if (isNumeric(parts[0])) {
//                        for (int j = 1; j < parts.length; j++) {
//                            fileName += prefix + parts[j];
//                            prefix = "_";
//                        }
//                    } else {
                    fileName = originalFilename;
//                    }

                    File topologyFile = new File(tempInputDirPath + File.separator + fileName);
                    topologyFile.createNewFile();
                    String val = (String) param.get("value");
                    writeValueToFile(val, topologyFile);
                    return topologyFile;
                }
            }
        }
        return null;
    }

    public static void writeValueToFile(String value, File file) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(value);
        }
        if (!file.exists() || file.length() < value.getBytes().length) {
            throw new FileNotFoundException("File " + file.getAbsolutePath() + " doesn't exist or contents are missing ");
        }
    }

}
