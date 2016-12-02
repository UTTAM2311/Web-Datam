package com.tt.web.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.core.util.Verify;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * Utility class
     */
    private FileUtil() {}


    /**
     * Write contents to a File with the give name at the given file directory path.
     * 
     * @param fileBaseDir directory in which the files to be written
     * @param fileName name of the file to be written
     * @param content file contents to write
     */
    public static void writeToFile(String fileBaseDir, String fileName, String content) {
        Verify.hasText(fileBaseDir);
        Verify.hasText(fileName);
        Verify.hasText(content);

        File targetFile = createNestedFile(fileBaseDir, fileName);
        if (targetFile != null) {
            try (FileWriter fileWriter = new FileWriter(targetFile)) {
                log.debug("Writing content to file: {}", fileName);
                fileWriter.write(content);
                fileWriter.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        } else {
            log.error("Unable to create file: {}, in dir: {}", fileName, fileBaseDir);
        }
    }


    /**
     * Write contents to a File with the give name at the given file directory path.
     * 
     * @param fileBaseDir directory in which the files to be written
     * @param fileName name of the file to be written
     * @param content file contents to write
     * @param bol whether to append in the exsisting file or not
     */
    public static void writeToFile(String fileBaseDir, String fileName, String content, Boolean bol) {
        Verify.hasText(fileBaseDir);
        Verify.hasText(fileName);
        Verify.hasText(content);

        File targetFile = createNestedFile(fileBaseDir, fileName);
        if (targetFile != null) {
            try (FileWriter fileWriter = new FileWriter(targetFile, bol)) {
                log.debug("Writing content to file: {}", fileName);
                fileWriter.write(content);
                fileWriter.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        } else {
            log.error("Unable to create file: {}, in dir: {}", fileName, fileBaseDir);
        }
    }


    /**
     * Creates the directory named by this abstract pathname Ex: path = /home/user/A/B - - - >
     * creates sub-folder inside the folder, if the folder A does not exist.
     * 
     * @param path path of the folder
     */
    public static void createDir(String path) {
        Verify.hasText(path);

        File preDir = new File(path);
        if (!preDir.exists()) {
            preDir.mkdirs();
        }
    }

    /**
     * Creates a file and returns it.
     * 
     * @param fileName
     * @return
     */
    public static File createNestedFile(String rootFolder, String fileName) {
        Verify.hasText(rootFolder);
        Verify.hasText(fileName);

        File root = new File(rootFolder);
        File targetFile = new File(root, fileName);

        File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return targetFile;
    }

}
