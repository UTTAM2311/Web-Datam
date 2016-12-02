package com.datum.ui.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datum.ui.core.Util.FileUtil;
import com.datum.ui.core.Util.SelectionUtil;
import com.datum.ui.core.constants.Constants;

public class DefaultDataSelector implements DataSlector {

    private static final Logger log = LoggerFactory.getLogger(DefaultDataSelector.class);
    private final String storageBaseFolder;
    private final String[] topics;

    public DefaultDataSelector(String storagerBaseFolder, String[] topics) {
        this.storageBaseFolder = storagerBaseFolder;
        this.topics = topics;
    }

    @Override
    public String selectTopic() {
        String topic = SelectionUtil.randomSelection(topics);
        return topic;
    }


    @Override
    public String selectData() {
        String topicPath = storageBaseFolder + selectTopic();
        String[] dataArray = FileUtil.getFiles(topicPath);
        String data = SelectionUtil.randomSelection(dataArray);
        String dataContent = null;
        try {
            dataContent = FileUtil.readFile(topicPath + Constants.SLASH + data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return dataContent;
    }

}
