/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.deleteme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

/**
 *
 * @author alogo
 */
public class Main {

    public static void main(String[] args) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("/home/alogo/workspace/DRIP/docs/images/DRIP_arch.png"));
        byte[] encoded = Base64.getEncoder().encode(bytes);
        String cont = new String(encoded, "US-ASCII");
        System.out.println(cont);

        byte[] decoded = Base64.getDecoder().decode(cont);
        OpenOption[] options = new OpenOption[1];
        options[0] = StandardOpenOption.CREATE_NEW;

        Files.write(Paths.get("/home/alogo/Downloads/DRIP_arch.png"), decoded, options);

    }
}
