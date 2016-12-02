package com.rss.core.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rss.core.FeedDetails;
import com.rss.core.FeedsQueue;
import com.rss.core.util.Verify;

public class FeedQueueImpl implements FeedsQueue<FeedDetails> {

    private static final Logger log = LoggerFactory.getLogger(FeedQueueImpl.class);

    private BlockingQueue<FeedDetails> feedQueue;

    public FeedQueueImpl() {
        this.feedQueue = new LinkedBlockingQueue<FeedDetails>();
    }

    @Override
    public void enqueue(FeedDetails element) {
        Verify.notNull(element);

        feedQueue.add(element);
        log.debug("Enqueue FeedDetails : {}", element);

    }

    @Override
    public void enqueue(List<FeedDetails> elements) {
        Verify.notNull(elements);

        feedQueue.addAll(elements);
        log.debug("Enqueue multiple feeds, in total : {}", elements.size());
    }

    @Override
    public FeedDetails dequeue() {
        try {
            FeedDetails element = feedQueue.take();
            log.debug("Dequeue  element : {}", element);
            return element;
        } catch (InterruptedException e) {
            log.error(e.getMessage() + e);
        }
        return null;

    }

    @Override
    public boolean isEmpty() {
        return feedQueue.isEmpty();
    }

}
