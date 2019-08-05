package com.cloud.player.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * @author jian
 */
public final class FileUtil {
    public static String file2String(File file) {
        StringBuffer sb = new StringBuffer();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            LineNumberReader reader = new LineNumberReader(bufReader);

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (Exception ignore) {
            }
            reader.close();
        } catch (Exception ignore) {
        }
        return sb.toString();
    }
}
