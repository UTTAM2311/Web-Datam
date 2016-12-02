package com.datum.ui.core.Util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datum.ui.core.DefaultDataSelector;
import com.datum.ui.core.constants.Constants;
import com.fasterxml.jackson.databind.JsonNode;

public class UserUtil {

    private static final Logger log = LoggerFactory.getLogger(UserUtil.class);
    private static Map<String, String[]> userAccountDetilsMap = new HashMap<>();

    // TODO : decide how to set this value
    private static final String baseFolderPath = "DummyUser/";

    // TODO: See how to set this
    private static final String storagePath = "DataStore/";

    static {
        String[] directories = FileUtil.getFolders(baseFolderPath);
        for (String userAccount : directories) {
            String userFilePath = baseFolderPath + userAccount + Constants.SLASH + Constants.USER_JSON;
            try {
                String jsonString = FileUtil.readFile(userFilePath, StandardCharsets.UTF_8);
                JsonNode userDataNode = JsonUtil.getTree(jsonString);
                String preferences = userDataNode.get(Constants.PREFERENCES).asText();
                String[] preferencesList = preferences.split(Constants.COMMA);
                userAccountDetilsMap.put(userAccount, preferencesList);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public static String createUserAccount(String userInfoJsonString) {

        JsonNode userDataNode = JsonUtil.getTree(userInfoJsonString);
        JsonNode userNameNode = userDataNode.get(Constants.USER_NAME);
        JsonNode passwordNode = userDataNode.get(Constants.PASSWORD);
        JsonNode preferencesNode = userDataNode.get(Constants.PREFERENCES);

        if (userNameNode == null || passwordNode == null || preferencesNode == null) {
            return "provide valid details";
        }

        String userName = userNameNode.asText();
        String password = passwordNode.asText();
        String preferences = preferencesNode.asText();
        String response = Constants.EMPTY_STRING;

        try {

            if (!checkIfUserAlreadyExists(userName, password, preferences)) {
                createSaparateAccountForUser(userName, password, preferences, userInfoJsonString);
                response = "successfully create your account";
            } else {
                response = "Already acoount exists with give details";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response = "unable To create your account, try again";
        }
        return response;
    }


    public static void collectUserResponse(String userResponseJson) {
        JsonNode userResponseDataNode = JsonUtil.getTree(userResponseJson);

        String userSpecificKey = userResponseDataNode.get(Constants.USER_KEY).asText();
        String topic = userResponseDataNode.get(Constants.USER_TOPIC).asText();
        String fileName = userResponseDataNode.get(Constants.USER_FILE_NAME).asText();

        String userFolderPath = baseFolderPath + userSpecificKey + Constants.SLASH + topic + Constants.SLASH + fileName;

        try {
            FileUtil.writeContentToFile(userFolderPath, userResponseJson);
        } catch (IOException e) {
            log.error("unable to write user response to file {}", e.getMessage());
        }
    }


    public static String getDataForUser(String userKey) {
        String[] userTopics = userAccountDetilsMap.get(userKey);
        DefaultDataSelector dataSelector = new DefaultDataSelector(storagePath, userTopics);
        String filteredData = DataUtil.filterNewsData(dataSelector.selectData());
        return filteredData;
    }


    private static boolean checkIfUserAlreadyExists(String userName, String password, String preferences) {
        String key = userName + password;
        if (userAccountDetilsMap.containsKey(key)) {
            return true;
        } else {
            String[] preferencesArray = preferences.split(Constants.COMMA);
            userAccountDetilsMap.put(key, preferencesArray);
            return false;
        }
    }

    private static void createSaparateAccountForUser(String userName, String password, String preferences,
            String userDetails) throws Exception {
        String folderName = baseFolderPath + userName + password + Constants.SLASH;
        FileUtil.createFolder(folderName);
        String[] preferencesArray = preferences.split(Constants.COMMA);
        for (String pre : preferencesArray) {
            String preferenceFolder = folderName + pre;
            FileUtil.createFolder(preferenceFolder);
        }
        FileUtil.writeContentToFile(folderName + Constants.USER_JSON, userDetails);
    }

}
