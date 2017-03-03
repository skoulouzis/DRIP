package nl.uva.sne.drip.drip.converter;

import nl.uva.sne.drip.drip.converter.planner.out.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.List;

import nl.uva.sne.drip.drip.converter.provisioner.in.Eth;
import nl.uva.sne.drip.drip.converter.provisioner.in.SubTopology;
import nl.uva.sne.drip.drip.converter.provisioner.in.SubTopologyInfo;
import nl.uva.sne.drip.drip.converter.provisioner.in.Subnet;
import nl.uva.sne.drip.drip.converter.provisioner.in.TopTopology;
import nl.uva.sne.drip.drip.converter.provisioner.in.VM;

public class P2PConverter {

    public static SimplePlanContainer convert(String plannerOutputJson, String userName, String OStype, String clusterType) throws JsonParseException, JsonMappingException, IOException {

        List<Component> components = getInfoFromPlanner(plannerOutputJson);

        TopTopology topTopology = new TopTopology();
        SubTopology subTopology = new SubTopology();
        SubTopologyInfo sti = new SubTopologyInfo();

        sti.cloudProvider = "EC2";
        sti.topology = UUID.randomUUID().toString();

        subTopology.publicKeyPath = "~/.ssh/id_dsa.pub";
        subTopology.userName = userName;

        Subnet s = new Subnet();
        s.name = "s1";
        s.subnet = "192.168.10.0";
        s.netmask = "255.255.255.0";

        subTopology.subnets = new ArrayList<>();
        subTopology.subnets.add(s);

        subTopology.components = new ArrayList<>();

        boolean firstVM = true;
        int count = 0;
        for (Component cmp : components) {

            VM curVM = new VM();
            curVM.name = cmp.getName();
            curVM.type = "Switch.nodes.Compute";
            curVM.OStype = OStype;
            curVM.domain = "ec2.us-east-1.amazonaws.com";
            curVM.clusterType = clusterType;
            curVM.dockers = cmp.getDocker();
            curVM.public_address = cmp.getName();
            curVM.nodeType = "t2" + cmp.getSize().toLowerCase();

            Eth eth = new Eth();
            eth.name = "p1";
            eth.subnet_name = "s1";
            int hostNum = 10 + count++;
            String priAddress = "192.168.10." + hostNum;
            eth.address = priAddress;
            curVM.ethernet_port = new ArrayList<>();
            curVM.ethernet_port.add(eth);
            if (firstVM) {
                curVM.role = "master";
                firstVM = false;
            } else {
                curVM.role = "slave";
            }
            subTopology.components.add(curVM);
        }

        sti.subTopology = subTopology;

        topTopology.topologies = new ArrayList<>();
        topTopology.topologies.add(sti);

        SimplePlanContainer spc = generateInfo(topTopology);

        return spc;
    }

    private static List<Component> getInfoFromPlanner(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Component>> mapType = new TypeReference<List<Component>>() {
        };
        List<Component> components = mapper.readValue(json, mapType);
        return components;
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
