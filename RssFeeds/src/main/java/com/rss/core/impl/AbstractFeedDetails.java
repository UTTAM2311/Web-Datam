package com.rss.core.impl;

import com.rss.core.FeedDetails;
import com.sun.syndication.feed.synd.SyndEntry;

public class AbstractFeedDetails implements FeedDetails {

    private final SyndEntry feed;
    private final String filePathToWrite;

    public AbstractFeedDetails(String filePathToWrite, SyndEntry feed) {
        this.filePathToWrite = filePathToWrite;
        this.feed = feed;
    }

    @Override
    public String getFilePathToWrite() {
        return this.filePathToWrite;
    }

    @Override
    public Object getFeed() {
        return this.feed;
    }

}
