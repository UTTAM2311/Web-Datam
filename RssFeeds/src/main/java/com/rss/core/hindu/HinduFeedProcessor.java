package com.rss.core.hindu;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rss.core.FeedProcessor;
import com.rss.core.impl.AbstractFeedDetails;
import com.rss.core.impl.MetaDataPojo;
import com.rss.core.util.FileUtil;
import com.rss.core.util.PojoUtil;
import com.sun.syndication.feed.synd.SyndEntry;

public class HinduFeedProcessor implements FeedProcessor<AbstractFeedDetails> {

    private static final Logger log = LoggerFactory.getLogger(HinduFeedProcessor.class);

    @Override
    public void process(AbstractFeedDetails detPro) {
        SyndEntry feed = (SyndEntry) detPro.getFeed();
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("url", feed.getUri());
        metaData.put("title", feed.getTitle());
        metaData.put("description", feed.getDescription());
        // TODO:need to put content and category
        metaData.put("content", null);
        metaData.put("category", null);
        MetaDataPojo pojo = PojoUtil.createPojo(metaData, MetaDataPojo.class);
        String extractedContent = null;
        try {
            extractedContent = PojoUtil.convertPojoToString(pojo);
            if (extractedContent != null) {
                String filePath = detPro.getFilePathToWrite() + "/" + getFileName(feed.getUri());
                FileUtil.writeContentToFile(filePath, extractedContent, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getFileName(String uri) {
        String[] strings = uri.split("/");
        String tempString = strings[strings.length - 1];
        int index = tempString.indexOf("?");
        String fileName = tempString.substring(0, index);
        return fileName;
    }

}
