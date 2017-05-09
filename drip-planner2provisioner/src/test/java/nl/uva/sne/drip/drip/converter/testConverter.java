package nl.uva.sne.drip.drip.converter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class testConverter {

    public static void main(String[] args) {
        File jsonFile = new File("input.json");
        String json = "";
        try {
            json = FileUtils.readFileToString(jsonFile, "UTF-8");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            SimplePlanContainer spc = P2PConverter.transfer(json, "zh9314", "Ubuntu 16.04", "Virginia", "kubernetes", "EC2");
            System.out.println("--topLevel:\n" + spc.topLevelContents);
            System.out.println("--lowLevel:");
            for (Map.Entry<String, String> entry : spc.lowerLevelContents.entrySet()) {
                System.out.println(entry.getKey() + ":\n" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
