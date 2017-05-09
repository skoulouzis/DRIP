package nl.uva.sne.drip.drip.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.List;
import nl.uva.sne.drip.drip.converter.plannerOut.*;
import nl.uva.sne.drip.drip.converter.provisionerIn.*;
import nl.uva.sne.drip.drip.converter.provisionerIn.EC2.*;
import nl.uva.sne.drip.drip.converter.provisionerIn.provisionerIn.EGI.*;

public class P2PConverter {

    public static SimplePlanContainer transfer(String plannerOutputJson, 
            String userName, String OStype, String domainName, String clusterType, String cloudProvider) throws JsonParseException, JsonMappingException, IOException {

        Parameter plannerOutput = getInfoFromPlanner(plannerOutputJson);

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
                System.out.println("The " + cloudProvider + " is not supported yet!");
                return null;
        }
        SubTopologyInfo sti = new SubTopologyInfo();

        sti.cloudProvider = cloudProvider;
        sti.topology = UUID.randomUUID().toString();
        sti.domain = domainName;
        sti.status = "fresh";
        sti.tag = "fixed";

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
                System.out.println("The " + cloudProvider + " is not supported yet!");
                return null;
        }

        boolean firstVM = true;
        for (int vi = 0; vi < plannerOutput.value.size(); vi++) {
            Value curValue = plannerOutput.value.get(vi);
            VM curVM;
            switch (cloudProvider.trim().toLowerCase()) {
                case "ec2":
                    curVM = new EC2VM();
                    break;
                case "egi":
                    curVM = new EGIVM();
                    break;
                default:
                    System.out.println("The " + cloudProvider + " is not supported yet!");
                    return null;
            }
            curVM.name = curValue.getName();
            curVM.type = "Switch.nodes.Compute";
            curVM.OStype = OStype;
            curVM.clusterType = clusterType;
            curVM.dockers = curValue.getDocker();

            if (cloudProvider.trim().toLowerCase().equals("ec2")) {
                switch (curValue.getSize().trim().toLowerCase()) {
                    case "small":
                        curVM.nodeType = "t2.small";
                        break;
                    case "medium":
                        curVM.nodeType = "t2.medium";
                        break;
                    case "large":
                        curVM.nodeType = "t2.large";
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid value for field 'size' in input JSON String");
                }

                Eth eth = new Eth();
                eth.name = "p1";
                eth.subnetName = "s1";
                int hostNum = 10 + vi;
                String priAddress = "192.168.10." + hostNum;
                eth.address = priAddress;
                curVM.ethernetPort = new ArrayList<Eth>();
                curVM.ethernetPort.add(eth);
            }

            if (cloudProvider.trim().toLowerCase().equals("egi")) {
                switch (curValue.getSize().trim().toLowerCase()) {
                    case "small":
                        curVM.nodeType = "small";
                        break;
                    case "medium":
                        curVM.nodeType = "medium";
                        break;
                    case "large":
                        curVM.nodeType = "large";
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid value for field 'size' in input JSON String");
                }
            }

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
                    System.out.println("The " + cloudProvider + " is not supported yet!");
                    return null;
            }
        }

        sti.subTopology = subTopology;

        topTopology.topologies = new ArrayList<>();
        topTopology.topologies.add(sti);

        SimplePlanContainer spc = generateInfo(topTopology);

        return spc;
    }

    private static Parameter getInfoFromPlanner(String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<Value> outputList = mapper.readValue(json, new TypeReference<List<Value>>() {
        });
//        PlannerOutput po = mapper.readValue(json, PlannerOutput.class);
//        System.out.println("");
        Parameter param = new Parameter();
        param.value = outputList;
        return param;
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

}
