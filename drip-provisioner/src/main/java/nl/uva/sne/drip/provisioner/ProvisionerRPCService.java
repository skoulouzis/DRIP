/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.provisioner;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.uva.sne.drip.commons.utils.TOSCAUtils;
import nl.uva.sne.drip.model.ToscaTemplate;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author S. Koulouzis
 */
class ProvisionerRPCService {

    private List<Map.Entry> vmTopologies;
    private String tempInputDirPath;

    ToscaTemplate execute(ToscaTemplate toscaTemplate) throws FileNotFoundException, JSchException, IOException {
        tempInputDirPath = System.getProperty("java.io.tmpdir") + File.separator + "Input-" + Long.toString(System.nanoTime()) + File.separator;
        File tempInputDir = new File(tempInputDirPath);
        if (!(tempInputDir.mkdirs())) {
            throw new FileNotFoundException("Could not create input directory: " + tempInputDir.getAbsolutePath());
        }

        buildCloudStormTopTopology(toscaTemplate);
        

        return toscaTemplate;
    }

    private Map<String, Object> buildCloudStormTopTopology(ToscaTemplate toscaTemplate) throws JSchException, IOException {
        Map<String, Object> topTopology = new HashMap<>();
        topTopology.put("userName", getVMsUSername(toscaTemplate));
        String keysPath = buildSSHKeyPair();
        topTopology.put("publicKeyPath", keysPath);
        List<Map<String, Object>> subnets = new ArrayList<>();
        int counter = 0;
        for (Map.Entry vmTopology : getVmTopologies(toscaTemplate)) {
            String subnetValue = getSubnets(vmTopology);
            if (subnetValue != null) {
                Map<String, Object> subnetMap = new HashMap<>();
                subnetMap.put("name", "subnet" + counter++);
                subnetMap.put("subnet", subnetValue.split("/")[0]);
                subnetMap.put("netmask", subnetValue.split("/")[1]);
                Object members = null;
                subnetMap.put("members", members);
                subnets.add(subnetMap);
            }
        }
        if (subnets != null) {
            topTopology.put("subnets", subnets);
        }
        List<Map<String, Object>> subTopologies = buildCloudStormSubTopologies(toscaTemplate);
        topTopology.put("topologies", subTopologies);
        return topTopology;
    }

    private String getVMsUSername(ToscaTemplate toscaTemplate) {
        List<Map.Entry> topologyVMs = getTopologyVMs(getVmTopologies(toscaTemplate).get(0), toscaTemplate);
        return (String) TOSCAUtils.getNodeProperty(topologyVMs.get(0), "user_name");
    }

    private List<Map.Entry> getTopologyVMs(Map.Entry topology, ToscaTemplate toscaTemplate) {

        List<Map.Entry> topologyVMs = new ArrayList<>();
        Map<String, Object> topologyMap = new HashMap<>();
        topologyMap.put((String) topology.getKey(), topology.getValue());
        List<Map.Entry> relatedNodes = TOSCAUtils.getRelatedNodes(topologyMap, toscaTemplate);
        for (Map.Entry node : relatedNodes) {
            if (TOSCAUtils.nodeIsOfType(node, "tosca.nodes.ARTICONF.VM.Compute")) {
                topologyVMs.add(node);
            }
        }
        return topologyVMs;
    }


    private String buildSSHKeyPair() throws JSchException, IOException {
        String userPublicKeyName = "id_rsa.pub";
        String publicKeyPath = "name@" + userPublicKeyName;
        JSch jsch = new JSch();
        KeyPair kpair = KeyPair.genKeyPair(jsch, KeyPair.RSA);
        String userPrivateName = FilenameUtils.removeExtension(userPublicKeyName);
        kpair.writePrivateKey(tempInputDirPath + File.separator + userPrivateName);
        kpair.writePublicKey(tempInputDirPath + File.separator + userPublicKeyName, "auto generated user accees keys");
        kpair.dispose();
        return publicKeyPath;
    }

    private String getSubnets(Map.Entry node) {
        return (String) TOSCAUtils.getNodeProperty(node, "subnet");
    }

    /**
     * @return the vmTopologies
     */
    public List<Map.Entry> getVmTopologies(ToscaTemplate toscaTemplate) {
        if (vmTopologies == null) {
            vmTopologies = TOSCAUtils.getNodesByType(toscaTemplate, "tosca.nodes.ARTICONF.VM.topology");
        }
        return vmTopologies;
    }

    private List<Map<String, Object>> buildCloudStormSubTopologies(ToscaTemplate toscaTemplate) {
        List<Map<String, Object>> cloudStormSubTopologies = new ArrayList<>();
        for(Map.Entry topology: getVmTopologies(toscaTemplate)){
            Map<String, Object> cloudStormSubTopology = new HashMap<>();
            cloudStormSubTopology.put("topology", topology.getKey());
            cloudStormSubTopology.put("cloudProvider", TOSCAUtils.getNodeProperty(topology, "provider"));
            cloudStormSubTopology.put("domain", TOSCAUtils.getNodeProperty(topology, "domain"));
            cloudStormSubTopology.put("status", TOSCAUtils.getNodeProperty(topology, "domain"));
            
        }
        return cloudStormSubTopologies;
    }

}
