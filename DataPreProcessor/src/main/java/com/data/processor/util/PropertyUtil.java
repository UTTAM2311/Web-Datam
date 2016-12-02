package com.data.processor.util;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities to read Properties files.
 */
public class PropertyUtil {

    private static final Logger log = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * Utility class
     */
    private PropertyUtil() {}


    /**
     * Get the property from the class-path .
     *
     * @param fileName The property name to which we get the value from .
     * @return Properties The properties loaded .
     * @throws Exception Any exception thrown , you will need to catch it .
     */
    public static Properties load(String fileName) {
        Verify.hasText(fileName);
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
        Properties props = new Properties();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            props.load(is);
        } catch (Exception e) {
            log.error("Error while loading config {}", fileName, e);
        }
        
        return props;
    }

    /**
     * Method to provide merged Properties Object
     * 
     * @param fileNames
     * @return merged Properties loaded
     */
    public static Properties load(List<String> fileNames) {
        Verify.notNull(fileNames);
        
        Properties mergedProps = new Properties();
        for (String fileName : fileNames) {
            mergedProps.putAll(load(fileName));
        }
        
        return mergedProps;
    }
}
