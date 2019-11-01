/*
 * Copyright 2019 S. Koulouzis
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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.IOException;
import nl.uva.sne.drip.commons.sure_tosca.client.ApiException;
import nl.uva.sne.drip.commons.sure_tosca.client.DefaultApi;
import nl.uva.sne.drip.model.ToscaTemplate;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author S. Koulouzis
 */
public class ToscaHelper {

    private final DefaultApi api;

    private final ObjectMapper objectMapper;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    private final Integer id;

    public ToscaHelper(ToscaTemplate toscaTemplate) throws JsonProcessingException, IOException, ApiException {
        api = new DefaultApi();
        id = uploadToscaTemplate(toscaTemplate);
        this.objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private Integer uploadToscaTemplate(ToscaTemplate toscaTemplate) throws JsonProcessingException, IOException, ApiException {
        String ymlStr = objectMapper.writeValueAsString(toscaTemplate);
        File toscaTemplateFile = File.createTempFile("temp-toscaTemplate", ".yml");
        FileUtils.writeByteArrayToFile(toscaTemplateFile, ymlStr.getBytes());
        String resp = api.uploadToscaTemplate(toscaTemplateFile);
        return null;
    }

}
