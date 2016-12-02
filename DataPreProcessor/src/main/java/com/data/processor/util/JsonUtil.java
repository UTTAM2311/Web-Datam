package com.data.processor.util;

import static org.apache.commons.lang3.StringUtils.abbreviate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.management.ObjectName;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final SimpleDateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static final int ERROR_MSG_MAX_WIDTH = 30;

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setDateFormat(TIMESTAMP_FORMATTER);
    }

    /**
     * Utility classes should not have a public constructor.
     */
    private JsonUtil() {}

    private static JsonNode convertToJson(String json) throws IOException {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return mapper.readTree(json);
    }

    /**
     * Method to create a JsonNode from a Json String. This method logs the IOException and does not
     * throw it
     *
     * @param json (Valid Json String).
     * @return responses - JsonNode
     */
    public static JsonNode getTree(String json) {
        try {
            return convertToJson(json);
        } catch (IOException e) {
            LOGGER.error("Error while getting json tree {}", abbreviate(json, ERROR_MSG_MAX_WIDTH), e);
        }
        return null;
    }

    /**
     * Method to create a JsonNode from a Json String. This method throws IOException if
     * throwOnError is true else logs the error
     *
     * @param json (Valid Json String).
     * @return responses - JsonNode
     */
    public static JsonNode getTree(String json, boolean throwOnError) throws IOException {
        JsonNode responses = null;
        try {
            responses = convertToJson(json);
        } catch (IOException e) {
            if (throwOnError) {
                throw e;
            } else {
                LOGGER.error("Error while reading json String: {}", abbreviate(json, ERROR_MSG_MAX_WIDTH), e);
            }
        }
        return responses;
    }



    /**
     * Method to convert given Map to JsonNode.
     *
     * @param map
     * @return json
     */
    public static JsonNode convertToJson(Map<ObjectName, Object> map) {
        String body = null;
        try {
            body = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error due to :", e);
        }
        return getTree(body);
    }

    /**
     * Method to create a JsonNode from a byte array
     *
     * @param json (byte array)
     * @return responses - JsonNode
     */
    public static JsonNode getTree(byte[] json) {
        JsonNode responses = null;
        try {
            responses = mapper.readTree(json);
        } catch (IOException e) {
            LOGGER.error("Error while reading json String : {}",
                    abbreviate(new String(json, StandardCharsets.UTF_8), ERROR_MSG_MAX_WIDTH), e);
        }
        return responses;
    }

    public static ObjectMapper mapper() {
        return mapper;
    }

    public static JsonNode getJson(Map<String, ?> json) {
        return mapper.valueToTree(json);
    }

    public static String getJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("unable to convert object to json string {}", object);
        }
        return null;
    }

    public static Map<String, Object> getMap(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return new HashMap<>();
        }
        JsonNode tree;
        try {
            tree = convertToJson(jsonString);
            if (tree != null && tree.isObject()) {
                return JsonUtil.mapper().convertValue(tree, HashMap.class);
            }
        } catch (IOException e) {
            LOGGER.debug("Error converting json string to  JsonNode ", e);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("content", jsonString);
        return map;
    }

    public static String getField(String name, JsonNode jsonNode) {
        return getField(name, jsonNode, null);
    }

    public static String getField(String name, JsonNode jsonNode, String defaultValue) {
        if (jsonNode.has(name) && jsonNode.hasNonNull(name)) {
            return jsonNode.get(name).asText();
        }
        return defaultValue;
    }

    public static JsonNode toJsonNode(Object o) {
        return mapper().valueToTree(o);
    }

    public static <T> T toObject(JsonNode j, Class<T> clazz) {
        try {
            return mapper().treeToValue(j, clazz);
        } catch (JsonProcessingException e) {
            LOGGER.error("unable to convert jsonNode {} to class {} : {}", j, clazz, e.getMessage());
        }
        return null;
    }

    public static String convertPojoToString(Object clazz) throws JsonProcessingException {
        String jsonString = mapper.writeValueAsString(clazz);
        return jsonString;
    }
}
