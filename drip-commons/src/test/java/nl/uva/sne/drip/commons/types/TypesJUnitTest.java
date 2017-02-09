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
package nl.uva.sne.drip.commons.types;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import nl.uva.sne.drip.commons.utils.FileHash;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author alogo
 */
public class TypesJUnitTest {

    private static File tempFile1;
    private static String tempFile1Hash;
    private static Message message1;
    private static String fileParamContentsName;
    private static File tempFile2;
    private static String tempFile2Hash;
    private static List<File> files;

    @BeforeClass
    public static void setUpClass() throws IOException, NoSuchAlgorithmException {
        files = new ArrayList<>();
        initTmpRandomFiles();
        initMessages();
    }

    @AfterClass
    public static void tearDownClass() {
        if (tempFile1 != null) {
            tempFile1.delete();
        }
    }

    private static void initTmpRandomFiles() throws IOException, NoSuchAlgorithmException {
        tempFile1 = File.createTempFile("temp-", Long.toString(System.nanoTime()));
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(tempFile1), "UTF-8")), false)) {
            Random random = new Random();
            String sep = "";
            for (int i = 0; i < 100; i++) {
                int number = random.nextInt(1000) + 1;
                writer.print(sep);
                writer.print(number / 1e3);
                sep = " ";
                writer.println();
            }
            writer.println();
        }
        tempFile1Hash = FileHash.getSHA256(tempFile1.getAbsolutePath());
        files.add(tempFile1);

        tempFile2 = File.createTempFile("temp-", Long.toString(System.nanoTime()));
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(tempFile2), "UTF-8")), false)) {
            Random random = new Random();
            String sep = "";
            for (int i = 0; i < 100; i++) {
                int number = random.nextInt(1000) + 1;
                writer.print(sep);
                writer.print(number / 1e3);
                sep = " ";
                writer.println();
            }
            writer.println();
        }
        tempFile2Hash = FileHash.getSHA256(tempFile2.getAbsolutePath());
        files.add(tempFile2);
    }

    private static void initMessages() throws IOException {
        message1 = new Message();
        message1.setCreationDate((System.currentTimeMillis()));
        List<Parameter> parameters = new ArrayList();
        Parameter numParam = new Parameter();
        String numParamName = "input";
        numParam.setName(numParamName);
        numParam.setValue("33000");
        parameters.add(numParam);

        Parameter fileParamContents = new Parameter();
        fileParamContentsName = "someInputFile";
        fileParamContents.setName(fileParamContentsName);
        byte[] bytes = Files.readAllBytes(Paths.get(tempFile1.getAbsolutePath()));
        String charset = "UTF-8";
        fileParamContents.setValue(new String(bytes, charset));
        fileParamContents.setEncoding(charset);
        parameters.add(fileParamContents);

        Parameter fileParamRef = new Parameter();
        fileParamRef.setName("theNameOfTheParamater");
        fileParamRef.setURL("http://www.gutenberg.org/cache/epub/3160/pg3160.txt");
        parameters.add(fileParamRef);
        message1.setParameters(parameters);

    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMarshallUnmarshalJackson() throws IOException, NoSuchAlgorithmException {
        File fileParam = null;
        try {

            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(message1);

//            System.err.println(jsonInString);
            assertTrue("JSON should contain " + IMessage.CREATION_DATE, jsonInString.contains(IMessage.CREATION_DATE));
            assertTrue("JSON should contain parameters", jsonInString.contains("parameters"));

//            System.err.println(jsonInString);
            Message request = mapper.readValue(jsonInString, Message.class);
            List<Parameter> params = request.getParameters();

            assertEquals(message1.getParameters().size(), params.size());
            fileParam = new File(fileParamContentsName);
            for (Parameter param : params) {
                if (param.getName().equals(fileParamContentsName)) {
                    String value = param.getValue();
                    try (PrintWriter out = new PrintWriter(fileParam)) {
                        out.print(value);
                    }
                    break;
                }
            }

            assertNotNull(fileParam);
            assertEquals(tempFile1.length(), fileParam.length());
            String fileParamHash = FileHash.getSHA256(fileParam.getAbsolutePath());
            assertEquals(tempFile1Hash, fileParamHash);
        } finally {
            if (fileParam != null) {
                fileParam.delete();
            }
        }
    }

    @Test
    public void testMarshallUnmarshalSimpleJson() throws IOException, NoSuchAlgorithmException, JSONException {
        File fileParam = null;
        try {

            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(message1);
            JSONObject jo = new JSONObject(jsonInString);
            JSONArray parameters = jo.getJSONArray("parameters");
            fileParam = new File(fileParamContentsName);
            for (int i = 0; i < parameters.length(); i++) {
                JSONObject param = (JSONObject) parameters.get(i);
                String name = (String) param.get(Parameter.NAME);
                if (name.equals(fileParamContentsName)) {
                    String value = (String) param.get(Parameter.VALUE);
                    try (PrintWriter out = new PrintWriter(fileParam)) {
                        out.print(value);
                    }
                    break;
                }
            }

            assertNotNull(fileParam);
            assertEquals(tempFile1.length(), fileParam.length());
            String fileParamHash = FileHash.getSHA256(fileParam.getAbsolutePath());
            assertEquals(tempFile1Hash, fileParamHash);

        } finally {
            if (fileParam != null) {
                fileParam.delete();
            }
        }
    }

    @Test
    public void testJacksonMarshal() throws UnsupportedEncodingException, IOException {
        Message responseMessage = new Message();
        List parameters = new ArrayList();
        String charset = "UTF-8";
        for (File f : files) {
            Parameter fileParam = new Parameter();
            byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
            fileParam.setValue(new String(bytes, charset));
            fileParam.setEncoding(charset);
            fileParam.setName(f.getName());
            parameters.add(fileParam);
        }
        responseMessage.setParameters(parameters);
        responseMessage.setCreationDate((System.currentTimeMillis()));

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(responseMessage);
    }

    @Test
    public void testsimpleJsonMarshal() throws JSONException, IOException {
        JSONObject jo = new JSONObject();
        jo.put("creationDate", (System.currentTimeMillis()));
        List parameters = new ArrayList();
        String charset = "UTF-8";
        for (File f : files) {
            Map<String, String> fileArguments = new HashMap<>();
            fileArguments.put("encoding", charset);
            fileArguments.put("name", f.getName());
            byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
            fileArguments.put("value", new String(bytes, charset));
            parameters.add(fileArguments);
        }
        jo.put("parameters", parameters);

        ObjectMapper mapper = new ObjectMapper();
        Message request = mapper.readValue(jo.toString(), Message.class);
        Date cDate = new Date(request.getCreationDate());
    }
}
