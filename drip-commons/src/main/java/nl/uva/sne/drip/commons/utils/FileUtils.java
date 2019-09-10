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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author S. Koulouzis
 */
public class FileUtils {

    /**
     * Code from: http://www.mkyong.com/java/java-sha-hashing-example/
     *
     * @param filePath
     * @return
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String getSHA256(String filePath) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(filePath);

        byte[] dataBytes = new byte[1024];

        int nread;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void untar(File dest, File tarFile) throws IOException {
        Process p = Runtime.getRuntime().exec(" tar -xzvf " + tarFile.getAbsolutePath() + " -C " + dest.getAbsolutePath());
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String s = null;
        StringBuilder error = new StringBuilder();
        while ((s = stdError.readLine()) != null) {
            error.append(s);
        }
        if (s != null) {
            throw new IOException(error.toString());
        }
//        dest.mkdir();
//        TarArchiveInputStream tarIn;
//
//        tarIn = new TarArchiveInputStream(
//                new GzipCompressorInputStream(
//                        new BufferedInputStream(
//                                new FileInputStream(
//                                        tarFile
//                                )
//                        )
//                )
//        );
//
//        org.apache.commons.compress.archivers.tar.TarArchiveEntry tarEntry = tarIn.getNextTarEntry();
//
//        while (tarEntry != null) {
//            File destPath = new File(dest, tarEntry.getName());
//            if (tarEntry.isDirectory()) {
//                destPath.mkdirs();
//            } else {
//                destPath.createNewFile();
//                byte[] btoRead = new byte[1024];
//                try (BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(destPath))) {
//                    int len;
//
//                    while ((len = tarIn.read(btoRead)) != -1) {
//                        bout.write(btoRead, 0, len);
//                    }
//                }
////                Set<PosixFilePermission> perms = new HashSet<>();
////                perms.add(PosixFilePermission.OWNER_READ);
////                perms.add(PosixFilePermission.OWNER_WRITE);
////                perms.add(PosixFilePermission.OWNER_EXECUTE);
////
////                perms.add(PosixFilePermission.GROUP_READ);
////                perms.add(PosixFilePermission.GROUP_WRITE);
////                perms.add(PosixFilePermission.GROUP_EXECUTE);
////
////                perms.add(PosixFilePermission.OTHERS_READ);
////                perms.add(PosixFilePermission.OTHERS_EXECUTE);
////                perms.add(PosixFilePermission.OTHERS_EXECUTE);
////                Files.setPosixFilePermissions(Paths.get(destPath.getAbsolutePath()), perms);
//            }
//            tarEntry = tarIn.getNextTarEntry();
//        }
//        tarIn.close();
    }
}
