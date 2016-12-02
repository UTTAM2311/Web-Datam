package com.rss.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rss.core.FeedDetails;
import com.rss.core.FeedProcessor;

public class Initiator implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Initiator.class);

    private FeedProcessor processor;
    private FeedQueueImpl feedsQueue;

    private boolean flag = true;

    public Initiator(FeedProcessor processor, FeedQueueImpl feedsQueue) {
        this.feedsQueue = feedsQueue;
        this.processor = processor;
    }

    @Override
    public void run() {
        log.info("Started the inititator class");
        while (flag) {
            FeedDetails feedDetails = feedsQueue.dequeue();
            processor.process(feedDetails);
        }
    }

    public void stopTask() {
        flag = false;
    }
}
