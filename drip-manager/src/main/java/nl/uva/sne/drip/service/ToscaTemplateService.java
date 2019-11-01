/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.uva.sne.drip.api.ApiException;
import nl.uva.sne.drip.dao.ToscaTemplateDAO;
import nl.uva.sne.drip.model.ToscaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class ToscaTemplateService {

    private final ObjectMapper objectMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public ToscaTemplateService() {
        this.objectMapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Autowired
    private ToscaTemplateDAO dao;

    public String save(ToscaTemplate tt) {
        dao.save(tt);
        return tt.getId();
    }

    public String saveFile(MultipartFile file) throws IOException, ApiException {
        String originalFileName = file.getOriginalFilename();
        String name = System.currentTimeMillis() + "_" + originalFileName;
        byte[] bytes = file.getBytes();
        String ymlStr = new String(bytes, "UTF-8");
        ToscaTemplate tt = objectMapper.readValue(ymlStr, ToscaTemplate.class);
        save(tt);
        return tt.getId();
    }

    public String updateToscaTemplateByID(String id, MultipartFile file) throws IOException, ApiException {
        ToscaTemplate tt = dao.findById(id).get();
        if (tt == null) {
            throw new ApiException(404, "Tosca Template with id :" + id + " not found");
        }
        byte[] bytes = file.getBytes();
        String ymlStr = new String(bytes, "UTF-8");
        tt = objectMapper.readValue(ymlStr, ToscaTemplate.class);
        tt.setId(id);
        return save(tt);
    }

    public String findByID(String id) throws JsonProcessingException {
        ToscaTemplate tt = dao.findById(id).get();
        String ymlStr = objectMapper.writeValueAsString(tt);
        return ymlStr;
    }

    public void deleteByID(String id) {
        dao.deleteById(id);
    }

    public List<String> getAllIds() {
        List<String> allIds = new ArrayList<>();
        List<ToscaTemplate> all = dao.findAll();
        for (ToscaTemplate tt : all) {
            allIds.add(tt.getId());
        }
        return allIds;
    }

    void deleteAll() {
        dao.deleteAll();
    }

    public ToscaTemplate getYaml2ToscaTemplate(String ymlStr) throws IOException {
        return objectMapper.readValue(ymlStr, ToscaTemplate.class);
    }

}
