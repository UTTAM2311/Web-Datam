package com.rss.core.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtil {

    /**
     * Reads all the content of a file to one String
     * 
     * @param path
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * 
     * @param path
     * @param stringBuilder
     * @return
     */
    public static boolean writeContentToFile(String path, StringBuilder stringBuilder) {
        try (FileWriter fw = new FileWriter(path, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {

            out.println(stringBuilder.toString());

        } catch (IOException e) {
            // TODO : log the error...
            return false;
        }
        return true;
    }

    public static void writeContentToFile(String path, String content) throws IOException {
        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        out.println(content);
        if (out != null) {
            out.close();
        }
    }

    public static void writeContentToFile(String path, String content, Boolean bol) throws IOException {
        FileWriter fw = new FileWriter(path, bol);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        out.println(content);
        if (out != null) {
            out.close();
        }
    }

    public static List<String> getFileContentAsList(String filePath) throws IOException {
        Verify.notNull(filePath);
        File file = new File(filePath);
        List<String> filePathList = FileUtils.readLines(file);
        return filePathList;
    }

    public static void createFolder(String folder) throws Exception {
        File folderPath = new File(folder);
        if (!folderPath.exists()) {
            boolean success = folderPath.mkdirs();
            if (!success) {
                throw new Exception("Not able to cretaed the folder : " + folder);
            }
        }

    }

}
