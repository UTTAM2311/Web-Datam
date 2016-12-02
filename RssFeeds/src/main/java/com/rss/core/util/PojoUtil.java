package com.rss.core.util;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PojoUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    private PojoUtil() {}

    public static <T> T createPojo(Map<String, Object> map, Class<T> clazz) {
        T pojo = mapper.convertValue(map, clazz);
        return pojo;
    };

    public static String convertPojoToString(Object clazz) throws JsonProcessingException {
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clazz);
        return jsonString;
    }
}
