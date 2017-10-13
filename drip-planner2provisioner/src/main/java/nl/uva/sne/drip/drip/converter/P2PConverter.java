package nl.uva.sne.drip.drip.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.drip.converter.provisionerIn.*;
import nl.uva.sne.drip.drip.converter.provisionerIn.EC2.*;
import nl.uva.sne.drip.drip.converter.provisionerIn.provisionerIn.EGI.*;
import org.json.JSONException;

public class P2PConverter {

    public static SimplePlanContainer transfer(String plannerOutputJson,
            String userName, String domainName, String cloudProvider) throws JsonParseException, JsonMappingException, IOException, JSONException {

        List<Object> vmList = Converter.jsonString2List(plannerOutputJson);

        String provisionerScalingMode = "fixed";
        for (Object element : vmList) {
            Map<String, Object> map = (Map<String, Object>) element;
            if (map.containsKey("scaling_mode")) {
                String scalingMode = (String) map.get("scaling_mode");
                if (!scalingMode.equals("single")) {
                    provisionerScalingMode = "scaling";
                    break;
                }
            }
        }

        TopTopology topTopology = new TopTopology();
        SubTopology subTopology = null;
        switch (cloudProvider.trim().toLowerCase()) {
            case "ec2":
                subTopology = new EC2SubTopology();
                break;
            case "egi":
                subTopology = new EGISubTopology();
                break;
            default:
                Logger.getLogger(P2PConverter.class.getName()).log(Level.WARNING, "The {0} is not supported yet!", cloudProvider);
                return null;
        }
        SubTopologyInfo sti = new SubTopologyInfo();
        sti.cloudProvider = cloudProvider;
        sti.topology = UUID.randomUUID().toString();
        sti.domain = domainName;
        sti.status = "fresh";
        sti.tag = provisionerScalingMode;

        topTopology.publicKeyPath = null;
        topTopology.userName = userName;

        if (cloudProvider.trim().toLowerCase().equals("ec2")) {
            Subnet s = new Subnet();
            s.name = "s1";
            s.subnet = "192.168.10.0";
            s.netmask = "255.255.255.0";
            subTopology.subnets = new ArrayList<>();
            subTopology.subnets.add(s);
        }

        switch (cloudProvider.trim().toLowerCase()) {
            case "ec2":
                ((EC2SubTopology) subTopology).components = new ArrayList<>();
                break;
            case "egi":
                ((EGISubTopology) subTopology).components = new ArrayList<>();
                break;
            default:
                Logger.getLogger(P2PConverter.class.getName()).log(Level.WARNING, "The {0} is not supported yet!", cloudProvider);
                return null;
        }

        boolean firstVM = true;
        for (Object element : vmList) {
            Map<String, Object> map = (Map<String, Object>) element;
            VM curVM;
            switch (cloudProvider.trim().toLowerCase()) {
                case "ec2":
                    curVM = new EC2VM();
                    break;
                case "egi":
                    curVM = new EGIVM();
                    break;
                default:
                    Logger.getLogger(P2PConverter.class.getName()).log(Level.WARNING, "The {0} is not supported yet!", cloudProvider);
                    return null;
            }
            curVM.name = (String) map.get("name");
            curVM.type = (String) map.get("type");
            curVM.OStype = ((Map<String, String>) map.get("os")).get("distribution") + " " + ((Map<String, Double>) map.get("os")).get("os_version");
//            curVM.clusterType = clusterType;
//            curVM.dockers = curValue.getDocker();
            curVM.nodeType = getSize((Map<String, String>) map.get("host"), cloudProvider);

//                Eth eth = new Eth();
//                eth.name = "p1";
//                eth.subnetName = "s1";
//                int hostNum = 10 + vi;
//                String priAddress = "192.168.10." + hostNum;
//                eth.address = priAddress;
//                curVM.ethernetPort = new ArrayList<Eth>();
//                curVM.ethernetPort.add(eth);           
            if (firstVM) {
                curVM.role = "master";
                firstVM = false;
            } else {
                curVM.role = "slave";
            }
            switch (cloudProvider.trim().toLowerCase()) {
                case "ec2":
                    ((EC2SubTopology) subTopology).components.add((EC2VM) curVM);
                    break;
                case "egi":
                    ((EGISubTopology) subTopology).components.add((EGIVM) curVM);
                    break;
                default:
                    Logger.getLogger(P2PConverter.class.getName()).log(Level.WARNING, "The {0} is not supported yet!", cloudProvider);
                    return null;
            }
        }
        sti.subTopology = subTopology;

        topTopology.topologies = new ArrayList<>();
        topTopology.topologies.add(sti);

        SimplePlanContainer spc = generateInfo(topTopology);

        return spc;
    }

    private static SimplePlanContainer generateInfo(TopTopology topTopology) throws JsonProcessingException {
        SimplePlanContainer spc = new SimplePlanContainer();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        String yamlString = mapper.writeValueAsString(topTopology);
        spc.topLevelContents = yamlString.substring(4);

        Map<String, String> output = new HashMap<>();
        for (int i = 0; i < topTopology.topologies.size(); i++) {
            String key = topTopology.topologies.get(i).topology;
            String value = mapper.writeValueAsString(topTopology.topologies.get(i).subTopology);
            output.put(key, value.substring(4));
        }

        spc.lowerLevelContents = output;

        return spc;
    }

    private static String getSize(Map<String, String> map, String cloudProvider) {
        switch (cloudProvider.trim().toLowerCase()) {
            case "ec2":
                return "t2.medium";
            case "egi":
                return "medium";
            default:
                Logger.getLogger(P2PConverter.class.getName()).log(Level.WARNING, "The {0} is not supported yet!", cloudProvider);
                return null;
        }

    }
}
