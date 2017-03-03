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

import nl.uva.sne.drip.drip.converter.planner.out.Parameter;
import nl.uva.sne.drip.drip.converter.planner.out.PlannerOutput;
import nl.uva.sne.drip.drip.converter.planner.out.Value;
import nl.uva.sne.drip.drip.converter.provisioner.in.Eth;
import nl.uva.sne.drip.drip.converter.provisioner.in.SubTopology;
import nl.uva.sne.drip.drip.converter.provisioner.in.SubTopologyInfo;
import nl.uva.sne.drip.drip.converter.provisioner.in.Subnet;
import nl.uva.sne.drip.drip.converter.provisioner.in.TopTopology;
import nl.uva.sne.drip.drip.converter.provisioner.in.VM;

public class P2PConverter {

    public static SimplePlanContainer transfer(String plannerOutputJson, String userName, String OStype, String clusterType) throws JsonParseException, JsonMappingException, IOException {

        Parameter plannerOutput = getInfoFromPlanner(plannerOutputJson);

        TopTopology topTopology = new TopTopology();
        SubTopology subTopology = new SubTopology();
        SubTopologyInfo sti = new SubTopologyInfo();

        sti.cloudProvider = "EC2";
        sti.topology = UUID.randomUUID().toString();

        subTopology.publicKeyPath = null;
        subTopology.userName = userName;

        Subnet s = new Subnet();
        s.name = "s1";
        s.subnet = "192.168.10.0";
        s.netmask = "255.255.255.0";

        subTopology.subnets = new ArrayList<Subnet>();
        subTopology.subnets.add(s);

        subTopology.components = new ArrayList<VM>();

        boolean firstVM = true;
        for (int vi = 0; vi < plannerOutput.value.size(); vi++) {
            Value curValue = plannerOutput.value.get(vi);
            VM curVM = new VM();
            curVM.name = curValue.name;
            curVM.type = "Switch.nodes.Compute";
            curVM.OStype = OStype;
            curVM.domain = "ec2.us-east-1.amazonaws.com";
            curVM.clusterType = clusterType;
            curVM.dockers = curValue.docker;
            curVM.public_address = curValue.name;

            if (curValue.size.trim().toLowerCase().equals("small")) {
                curVM.nodeType = "t2.small";
            } else if (curValue.size.trim().toLowerCase().equals("medium")) {
                curVM.nodeType = "t2.medium";
            } else if (curValue.size.trim().toLowerCase().equals("large")) {
                curVM.nodeType = "t2.large";
            } else {
                throw new IllegalArgumentException("Invalid value for field 'size' in input JSON String");
            }

            Eth eth = new Eth();
            eth.name = "p1";
            eth.subnet_name = "s1";
            int hostNum = 10 + vi;
            String priAddress = "192.168.10." + hostNum;
            eth.address = priAddress;
            curVM.ethernet_port = new ArrayList<Eth>();
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

        topTopology.topologies = new ArrayList<SubTopologyInfo>();
        topTopology.topologies.add(sti);

        SimplePlanContainer spc = generateInfo(topTopology);

        return spc;
    }

    private static Parameter getInfoFromPlanner(String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PlannerOutput po = mapper.readValue(json, PlannerOutput.class);
        System.out.println("");
        return po.parameters.get(0);
    }

    private static SimplePlanContainer generateInfo(TopTopology topTopology) throws JsonProcessingException {
        SimplePlanContainer spc = new SimplePlanContainer();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        String yamlString = mapper.writeValueAsString(topTopology);
        spc.topLevelContents = yamlString.substring(4);

        Map<String, String> output = new HashMap<String, String>();
        for (int i = 0; i < topTopology.topologies.size(); i++) {
            String key = topTopology.topologies.get(i).topology;
            String value = mapper.writeValueAsString(topTopology.topologies.get(i).subTopology);
            output.put(key, value.substring(4));
        }

        spc.lowerLevelContents = output;

        return spc;
    }

}
