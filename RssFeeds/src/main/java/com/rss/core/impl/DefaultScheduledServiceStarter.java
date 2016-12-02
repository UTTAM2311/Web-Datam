package com.rss.core.impl;

import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntry;

public class DefaultScheduledServiceStarter implements Runnable {
    
    private static final Logger log = LoggerFactory.getLogger(DefaultScheduledServiceStarter.class);

    private ROMERssFeedImpl rssFeedImpl;
    private FeedQueueImpl feedQueue;
    private FeedHistoryHolder feedHistoryHolder;
    private String filePathToWrite;
    private URL feedUrl;
    private String site;
    
    private static int count = 0;

    public DefaultScheduledServiceStarter(ROMERssFeedImpl rssFeedImpl, FeedQueueImpl feedsQueue, String filePathToWrite,
            FeedHistoryHolder feedHistoryHolder, URL feedUrl, String site) {
        this.rssFeedImpl = rssFeedImpl;
        this.feedQueue = feedsQueue;
        this.feedHistoryHolder = feedHistoryHolder;
        this.filePathToWrite = filePathToWrite;
        this.feedUrl = feedUrl;
        this.site = site;
    }

    @Override
    public void run() {
        count = count + 1;
        log.info("Started the rss feed service for site {},for {}  time", site, count);
        
        List<SyndEntry> feedsList = rssFeedImpl.feed(feedUrl);
        List<SyndEntry> newFeedList = feedHistoryHolder.getUpdatedFeeds(feedsList);
        for (SyndEntry feed : newFeedList) {
            feedQueue.enqueue(new AbstractFeedDetails(filePathToWrite, feed));
        }
    }

}
