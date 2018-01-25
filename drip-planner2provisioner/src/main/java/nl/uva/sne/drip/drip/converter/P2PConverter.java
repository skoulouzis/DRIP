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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.drip.converter.provisionerIn.*;
import nl.uva.sne.drip.drip.converter.provisionerIn.EC2.*;
import nl.uva.sne.drip.drip.converter.provisionerIn.provisionerIn.EGI.*;
import org.json.JSONException;

public class P2PConverter {

    public static SimplePlanContainer transfer(String plannerOutputJson,
            String userName, String domainName, String cloudProvider) throws JsonParseException, JsonMappingException, IOException, JSONException {
        cloudProvider = cloudProvider.toUpperCase();
        List<Object> vmList = Converter.jsonString2List(plannerOutputJson);

        TopTopology topTopology = new TopTopology();
        topTopology.publicKeyPath = "name@id_rsa.pub";
        topTopology.userName = userName;
        topTopology.topologies = new ArrayList<>();

        boolean firstVM = true;
        SubTopologyInfo sti = new SubTopologyInfo();
        String provisionerScalingMode = "fixed";
        SubTopology subTopology = createSubTopology(cloudProvider);
        sti.cloudProvider = cloudProvider;
        sti.topology = UUID.randomUUID().toString();
        sti.domain = domainName;
        sti.status = "fresh";
        sti.statusInfo = null;
        sti.tag = provisionerScalingMode;

        Map<String, SubTopologyInfo> subTopologyInfos = new HashMap<>();
        for (Object element : vmList) {
            VM vm = createVM(element, cloudProvider, firstVM);
            firstVM = false;

            if (isScalable(element)) {
                sti = new SubTopologyInfo();
                subTopology = createSubTopology(cloudProvider);
                provisionerScalingMode = "scaling";
                sti.cloudProvider = cloudProvider;
                sti.topology = UUID.randomUUID().toString();
                sti.domain = domainName;
                sti.status = "fresh";
                sti.tag = provisionerScalingMode;
                sti.statusInfo = null;
            } else {
                for (SubTopologyInfo info : subTopologyInfos.values()) {
                    if (!info.tag.equals("scaling")) {
                        sti = info;
                        subTopology = sti.subTopology;
                        break;
                    }
                }
            }
            subTopology = addVMToSubTopology(cloudProvider, vm, subTopology);
            if (cloudProvider.trim().toLowerCase().equals("ec2")) {
                Subnet s = new Subnet();
                s.name = "s1";
                s.subnet = "192.168.10.0";
                s.netmask = "255.255.255.0";
                subTopology.subnets = new ArrayList<>();
                subTopology.subnets.add(s);
            }
            sti.subTopology = subTopology;
            subTopologyInfos.put(sti.topology, sti);

        }
        for (SubTopologyInfo info : subTopologyInfos.values()) {
            topTopology.topologies.add(info);
        }

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

    private static int analyzeRequirements(Map<String, String> map) {
        int size = 5;
        String memSizeGB = map.get("mem_size");
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(memSizeGB);
        int memSize = 0;
        while (m.find()) {
            memSize = Integer.valueOf(m.group());
        }

        if (Integer.valueOf(map.get("num_cpus")) >= 16 && memSize >= 32) {
            size = 10;
        }
        return size;

    }

    private static String getSize(Map<String, String> map, String cloudProvider) {
        int size = analyzeRequirements(map);
        switch (cloudProvider.trim().toLowerCase()) {
            case "ec2":
                if (size <= 1) {
                    return "t2.nano";
                }
                if (size > 1 && size <= 2) {
                    return "t2.micro";
                }
                if (size > 2 && size <= 3) {
                    return "t2.small";
                }
                if (size > 3 && size <= 4) {
                    return "t2.medium";
                }
                if (size > 3 && size <= 4) {
                    return "t2.medium";
                }
                if (size > 4 && size <= 5) {
                    return "t2.large";
                }
                if (size > 5 && size <= 6) {
                    return "t2.xlarge";
                }
                if (size > 6) {
                    return "t2.2xlarge";
                }
                return "t2.medium";
            case "egi":
                if (size <= 1) {
                    return "small";
                }
                if (size > 1 && size <= 5) {
                    return "medium";
                }
                if (size > 5 && size <= 10) {
                    return "mammoth";
                }
            default:
                Logger.getLogger(P2PConverter.class.getName()).log(Level.WARNING, "The {0} is not supported yet!", cloudProvider);
                return null;
        }

    }

    private static VM createVM(Object element, String cloudProvider, boolean firstVM) throws JSONException, IOException {
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
        Map<String, Object> map = null;
        if (element instanceof Map) {
            map = (Map<String, Object>) element;
        } else if (element instanceof String) {
            map = Converter.jsonString2Map((String) element);
        }
        curVM.name = (String) map.get("name");
        if (curVM.name == null) {
            curVM.name = "node_vm";
        }
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
        } else {
            curVM.role = "slave";
        }

        return curVM;
    }

    private static SubTopology createSubTopology(String cloudProvider) {
        SubTopology subTopology;
        switch (cloudProvider.trim().toLowerCase()) {
            case "ec2":
                subTopology = new EC2SubTopology();
                ((EC2SubTopology) subTopology).components = new ArrayList<>();
                break;
            case "egi":
                subTopology = new EGISubTopology();
                ((EGISubTopology) subTopology).components = new ArrayList<>();
                break;
            default:
                Logger.getLogger(P2PConverter.class.getName()).log(Level.WARNING, "The {0} is not supported yet!", cloudProvider);
                return null;
        }
        return subTopology;
    }

    private static SubTopology addVMToSubTopology(String cloudProvider, VM vm, SubTopology subTopology) {
        switch (cloudProvider.trim().toLowerCase()) {
            case "ec2":
                ((EC2SubTopology) subTopology).components.add((EC2VM) vm);
                break;
            case "egi":
                ((EGISubTopology) subTopology).components.add((EGIVM) vm);
                break;
            default:
                Logger.getLogger(P2PConverter.class.getName()).log(Level.WARNING, "The {0} is not supported yet!", cloudProvider);
//                    return null;
            }
        return subTopology;
    }

    private static boolean isScalable(Object element) throws JSONException, IOException {
        Map<String, Object> map = null;
        if (element instanceof Map) {
            map = (Map<String, Object>) element;
        } else if (element instanceof String) {
            map = Converter.jsonString2Map((String) element);
        }
        if (map != null && map.containsKey("scaling_mode")) {
            String scalingMode = (String) map.get("scaling_mode");
            if (!scalingMode.equals("single")) {
                return true;
            }
        }
        return false;
    }
}
