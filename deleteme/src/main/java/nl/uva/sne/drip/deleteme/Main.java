/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.deleteme;

import java.io.IOException;

/**
 *
 * @author alogo
 */
public class Main {

    public static void main(String[] args) throws IOException {
        char[] chars = {(char) 92, (char) 110};

        String c1 = String.valueOf(chars);

        String tt = "v:" + c1 + c1 + c1 + "description:" + c1 + " example file for infrastructu";

        char[] array = tt.toCharArray();
        StringBuilder sb = new StringBuilder();
        int prevChar = -1;
        for (int i = 0; i < array.length; i++) {
            int currentChar = (int) array[i];
            if (prevChar > 0 && prevChar == 92 && currentChar == 110) {
                sb.delete(sb.length() - 1, sb.length());
                sb.append('\n');

            } else {
                sb.append((char) currentChar);
            }
            prevChar = (int) array[i];
        }

        System.err.println(tt);
        System.err.println(sb.toString());
    }
}
