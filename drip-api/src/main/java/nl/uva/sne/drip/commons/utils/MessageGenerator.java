/*
 * Copyright 2017 S. Koulouzis, Wang Junchao, Huan Zhou, Yang Hu 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.uva.sne.drip.commons.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import nl.uva.sne.drip.commons.v1.types.Message;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;

/**
 *
 * @author S. Koulouzis
 */
public class MessageGenerator {

    public static Message generateArtificialMessage(String path) throws IOException, TimeoutException, InterruptedException, JSONException {
//        String strResponse = "{\"creationDate\":1488368936945,\"parameters\":["
//                + "{\"name\":\"f293ff03-4b82-49e2-871a-899aadf821ce\","
//                + "\"encoding\":\"UTF-8\",\"value\":"
//                + "\"publicKeyPath: /tmp/Input-4007028381500/user.pem\\nuserName: "
//                + "zh9314\\nsubnets:\\n- {name: s1, subnet: 192.168.10.0, "
//                + "netmask: 255.255.255.0}\\ncomponents:\\n- "
//                + "name: faab6756-61b6-4800-bffa-ae9d859a9d6c\\n  "
//                + "type: Switch.nodes.Compute\\n  nodetype: t2.medium\\n  "
//                + "OStype: Ubuntu 16.04\\n  domain: ec2.us-east-1.amazonaws.com\\n  "
//                + "script: /tmp/Input-4007028381500/guiscipt.sh\\n  "
//                + "installation: null\\n  role: master\\n  "
//                + "dockers: mogswitch/InputDistributor\\n  "
//                + "public_address: 54.144.0.91\\n  instanceId: i-0e78cbf853328b820\\n  "
//                + "ethernet_port:\\n  - {name: p1, subnet_name: s1, "
//                + "address: 192.168.10.10}\\n- name: 1c75eedf-8497-46fe-aeb8-dab6a62154cb\\n  "
//                + "type: Switch.nodes.Compute\\n  nodetype: t2.medium\\n  OStype: Ubuntu 16.04\\n  domain: ec2.us-east-1.amazonaws.com\\n  script: /tmp/Input-4007028381500/guiscipt.sh\\n  installation: null\\n  role: slave\\n  dockers: mogswitch/ProxyTranscoder\\n  public_address: 34.207.254.160\\n  instanceId: i-0a99ea18fcc77ed7a\\n  ethernet_port:\\n  - {name: p1, subnet_name: s1, address: 192.168.10.11}\\n\"},{\"name\":\"kubernetes\",\"encoding\":\"UTF-8\",\"value\":\"54.144.0.91 ubuntu /tmp/Input-4007028381500/Virginia.pem master\\n34.207.254.160 ubuntu /tmp/Input-4007028381500/Virginia.pem slave\\n\"}]}";
//        String strResponse = "{\"creationDate\":1488805337447,\"parameters\":[{\"name\":\"2e5dafb6-5a1c-4a66-9dca-5841f99ea735\",\"encoding\":\"UTF-8\",\"value\":\"publicKeyPath: /tmp/Input-11594765342486/user.pem\\nuserName: zh9314\\nsubnets:\\n- {name: s1, subnet: 192.168.10.0, netmask: 255.255.255.0}\\ncomponents:\\n- name: 8fcc1788d9ee462c826572c79fdb2a6a\\n  type: Switch.nodes.Compute\\n  nodeType: t2.medium\\n  OStype: Ubuntu 16.04\\n  script: /tmp/Input-11594765342486/guiscipt.sh\\n  domain: ec2.us-east-1.amazonaws.com\\n  installation: null\\n  clusterType: swarm\\n  role: master\\n  dockers: mogswitch/ProxyTranscoder:1.0\\n  public_address: 34.207.73.18\\n  instanceId: i-0e82b5624a0df99b1\\n  ethernet_port:\\n  - {name: p1, subnet_name: s1, address: 192.168.10.10}\\n- name: 8fcc1788d9ee462c826572c79fdb2a6a\\n  type: Switch.nodes.Compute\\n  nodeType: t2.medium\\n  OStype: Ubuntu 16.04\\n  script: /tmp/Input-11594765342486/guiscipt.sh\\n  domain: ec2.us-east-1.amazonaws.com\\n  installation: null\\n  clusterType: swarm\\n  role: slave\\n  dockers: mogswitch/ProxyTranscoder:1.0\\n  public_address: 34.207.73.18\\n  instanceId: i-0e82b5624a0df99b1\\n  ethernet_port:\\n  - {name: p1, subnet_name: s1, address: 192.168.10.11}\\n\"},{\"name\":\"kubernetes\",\"encoding\":\"UTF-8\",\"value\":\"34.207.73.18 ubuntu /tmp/Input-11594765342486/Virginia.pem master\\n34.207.73.18 ubuntu /tmp/Input-11594765342486/Virginia.pem slave\\n\"}]}";
        String strResponse = FileUtils.readFileToString(new File(path));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        return mapper.readValue(strResponse, Message.class);
    }
}
