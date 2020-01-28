/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.uva.sne.drip.dao.CredentialDAO;
import nl.uva.sne.drip.model.tosca.Credential;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class CredentialService {

    @Autowired
    private CredentialDAO dao;

    public String save(Credential document) {
        dao.save(document);
        return document.getId();
    }

    public Credential findByID(String id) throws JsonProcessingException {
        Credential credentials = dao.findById(id).get();
        return credentials;
    }

    public void deleteByID(String id) {
        dao.deleteById(id);
    }

    public List<String> getAllIds() {
        List<String> allIds = new ArrayList<>();
        List<Credential> all = dao.findAll();
        for (Credential tt : all) {
            allIds.add(tt.getId());
        }
        return allIds;
    }

    void deleteAll() {
        dao.deleteAll();
    }

    List<Credential> findByProvider(String provider) {
        return dao.findBycloudProviderName(provider);
    }

}
