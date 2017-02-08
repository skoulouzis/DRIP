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
package nl.uva.sne.drip.drip.planner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author S. Koulouzis
 */
public class Planner {

    public List<File> plan(String input, String example, String outputDirPath) throws FileNotFoundException, IOException {
        // TODO Auto-generated method stub
        BufferedReader in;

        in = new BufferedReader(new FileReader(input));
        String line;
        boolean check = false;
        ArrayList<String> dockerNames = new ArrayList<>();
        while ((line = in.readLine()) != null) {
            if (line.contains("topology_template")) {
                check = true;
            }
            if (check) {
                if (line.contains("file")) {
                    String content = line.trim().replace('\"', ' ');
                    String[] cs = content.split(":");
                    String docker_name = cs[1].trim();
                    dockerNames.add(docker_name);
                }
            }
            if (line.contains("tosca_definitions_version")
                    || line.contains("description")
                    || line.contains("repositories")
                    || line.contains("artifact_types") || line.contains("data_types")
                    || line.contains("node_types")) {
                check = false;
            }
        }
        in.close();

        in = new BufferedReader(new FileReader(example));

        String block = "";
        String head = "";

        boolean block_b = false;
        while ((line = in.readLine()) != null) {
            if (line.contains("components")) {
                block_b = true;
                continue;
            }
            if (block_b) {
                block += line + "\n";
            }
            if (!block_b) {
                head += line + "\n";
            }
        }

        in.close();
        UUID fuuid = UUID.randomUUID();
        String file_guid = fuuid.toString();

        String outfPath = outputDirPath + "/" + file_guid + ".yml";
        FileWriter outputf = new FileWriter(outfPath);
        outputf.write(head);
        outputf.write("components:\n");
        for (int i = 0; i < dockerNames.size(); i++) {
            UUID uuid = UUID.randomUUID();
            String name_guid = uuid.toString();
            String privateAddress = "192.168.10." + (i + 10);
            if (i == 0) {
                outputf.write(generateVM(block, name_guid, dockerNames.get(i), privateAddress, "master"));
            } else {
                outputf.write(generateVM(block, name_guid, dockerNames.get(i), privateAddress, "slave"));
            }
        }
        outputf.close();

        String allFilePath = outputDirPath + "/" + "planner_output_all.yml";
        outputf = new FileWriter(allFilePath);
        outputf.write("topologies:\n");
        outputf.write("  - topology: " + file_guid + "\n");
        outputf.write("    cloudProvider: EC2\n");
        outputf.close();

        List<File> outputFiles = new ArrayList<>();
        outputFiles.add(new File(outfPath));
        outputFiles.add(new File(allFilePath));
        return outputFiles;
    }

    private String generateVM(String block, String nodeName, String dockerName, String privateAddress, String role) {
        block = block.replaceAll("nodeA", nodeName);
        block = block.replaceAll("DOCKER", "\"" + dockerName + "\"");
        block = block.replaceAll("192.168.10.10", privateAddress);
        block = block.replaceAll("ROLE", role);
        return block;
    }

}
