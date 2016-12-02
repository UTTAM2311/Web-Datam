package com.data.processor.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PojoUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    private PojoUtils() {}

    public static <T> T createPojo(Map<String, Object> map, Class<T> clazz) {
        T pojo = mapper.convertValue(map, clazz);
        return pojo;
    };

    public static void writePojoToFile(Object clazz, String filePath) throws IOException {
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clazz);
        FileUtil.writeContentToFile(filePath, jsonString);
    }
}
