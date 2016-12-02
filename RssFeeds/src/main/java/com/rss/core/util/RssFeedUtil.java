package com.rss.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rss.core.impl.MetaDataPojo;
import com.sun.syndication.feed.synd.SyndEntry;

public class RssFeedUtil {

    public static List<String> extactContentFromFeeds(List<SyndEntry> list) {
        List<String> metaDataList = new ArrayList<>();
        for (SyndEntry entry : list) {
            try {
                metaDataList.add(extactContentFromFeeds(entry));
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return metaDataList;
    }

    public static String extactContentFromFeeds(SyndEntry feed) throws JsonProcessingException {
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("url", feed.getUri());
        metaData.put("title", feed.getTitle());
        metaData.put("description", feed.getDescription());
        // TODO:need to put content and category
        metaData.put("content", null);
        metaData.put("category", null);
        MetaDataPojo pojo = PojoUtil.createPojo(metaData, MetaDataPojo.class);
        return PojoUtil.convertPojoToString(pojo);
    }

}
