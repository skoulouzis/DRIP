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
import nl.uva.sne.drip.model.Provisioner;
import nl.uva.sne.drip.model.tosca.Credential;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class ProvisionerService {




    public List<Provisioner> findAll() {
        List<Provisioner> all = new ArrayList<>();
        Provisioner provisioner = new Provisioner();
        provisioner.
        all.add(provisioner);
        return all;
    }

}
