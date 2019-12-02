/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.uva.sne.drip.commons.sure_tosca.client.ApiException;
import nl.uva.sne.drip.commons.utils.ToscaHelper;
import nl.uva.sne.drip.dao.ProvisionerDAO;
import nl.uva.sne.drip.model.Provisioner;
import nl.uva.sne.drip.model.ToscaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class ProvisionerService {

    @Value("${message.broker.queue.provisioner}")
    private String queueName;

    @Value("${tosca.types.interface}")
    private String toscaTypesInterface;

    @Value("${sure-tosca.base.path}")
    private String sureToscaBasePath;

    @Autowired
    private ToscaTemplateService toscaTemplateService;

    @Autowired
    private ProvisionerDAO provisionerDao;

    ToscaHelper toscaHelper;
    private Integer toscaHelperID;

    public String provision(String id) throws IOException, JsonProcessingException, ApiException {

        String ymlToscaTemplate = toscaTemplateService.findByID(id);
        ToscaTemplate toscaTemplate = toscaTemplateService.getYaml2ToscaTemplate(ymlToscaTemplate);
        toscaHelper = new ToscaHelper(toscaTemplate, sureToscaBasePath);

        toscaTemplate = addProvisionInterface(toscaTemplate);

        return toscaTemplateService.save(toscaTemplate);
    }
    
    protected ToscaTemplate addProvisionInterface(ToscaTemplate toscaTemplate) throws ApiException {
        Provisioner provisioner = selectBestProvisioner();
        List<Map<String, Object>> definitions = toscaHelper.getProvisionInterfaceDefinitions(provisioner.getToscaInterfaceTypes());
        Map<String, Object> def = selectBestInterfaceDefinitions(definitions);
        Map<String, Object> provisionInterface = createProvisionInterface(def);

//        List<Map<String, NodeTemplate>> vmTopologies = getVmTopologies(toscaTemplate);
//        for (Map<String, NodeTemplate> vmTopologyMap : vmTopologies) {
//            String topologyName = vmTopologyMap.keySet().iterator().next();
//            NodeTemplate vmTopology = vmTopologyMap.get(topologyName);
//            Map<String, Object> interfaces = vmTopology.getInterfaces();
//            Map<String, Object> cloudStormInterface = getCloudStormProvisionInterface(topologyName);
//            interfaces.put("cloudStorm", cloudStormInterface);
//        }
        return toscaTemplate;
    }

    private Provisioner selectBestProvisioner() {
        if (provisionerDao.count() <= 0) {
            Provisioner provisioner = new Provisioner();
            provisioner.setDescription("CloudsStorm is a framework for managing an application-defined infrastructure among public IaaS (Infrastructure-as-a-Service) Clouds. It enables the application to customize its underlying infrastructure at software development phase and dynamically control it at operation phase.");
            provisioner.setName("CloudsStorm");
            provisioner.setVersion("1.0.0");
            List<String> toscaInterfaceTypes = new ArrayList<>();
            toscaInterfaceTypes.add("tosca.interfaces.ARTICONF.CloudsStorm");
            provisioner.setToscaInterfaceTypes(toscaInterfaceTypes);
            provisionerDao.save(provisioner);
        }
        List<Provisioner> all = provisionerDao.findAll();
        return all.get(0);
    }

    private Map<String, Object> selectBestInterfaceDefinitions(List<Map<String, Object>> definitions) {
        return definitions.get(0);
    }

    private Map<String, Object> createProvisionInterface(Map<String, Object> def) {
        Map<String, Object> inputs = (Map<String, Object>) def.get("inputs");
        return null;
    }

}
