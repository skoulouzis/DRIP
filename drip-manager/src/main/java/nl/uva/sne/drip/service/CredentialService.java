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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.uva.sne.drip.dao.CredentialDAO;
import nl.uva.sne.drip.model.Credentials;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class CredentialService {

    @Autowired
    private CredentialDAO dao;

    public String save(Credentials document) {
        dao.save(document);
        return document.getId();
    }

    public Credentials findByID(String id) throws JsonProcessingException {
        Credentials credentials = dao.findById(id).get();
        return credentials;
    }

    public void deleteByID(String id) {
        dao.deleteById(id);
    }

    public List<String> getAllIds() {
        List<String> allIds = new ArrayList<>();
        List<Credentials> all = dao.findAll();
        for (Credentials tt : all) {
            allIds.add(tt.getId());
        }
        return allIds;
    }

    void deleteAll() {
        dao.deleteAll();
    }

}
