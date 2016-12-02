package com.tt.web.concurrent.impl;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.core.concurrent.HistoryQueue;
import com.tt.core.util.Verify;

/**
 * History Queue implementation for {@link URL} objects.
 *
 */
public class UrlQueue implements HistoryQueue<URL> {

    private static final Logger log = LoggerFactory.getLogger(UrlQueue.class);

    private BlockingQueue<URL> urlQueue;
    private HashSet<URL> urlHistory;


    /**
     * Constructor.
     */
    public UrlQueue() {
        super();

        // initialise objects
        this.urlQueue = new LinkedBlockingQueue<URL>();
        this.urlHistory = new HashSet<URL>();
    }

    /* --- Implementations --- */

    @Override
    public void enqueue(URL url) {
        Verify.notNull(url);

        urlQueue.add(url);
        urlHistory.add(url);
        log.debug("Enqueue URL : {}", url);
    }

    @Override
    public void enqueue(List<URL> urls) {
        Verify.notNull(urls);

        urlQueue.addAll(urls);
        urlHistory.addAll(urls);
        log.debug("Enqueue multiple URLs, in total : {}", urls.size());
    }


    @Override
    public void enqueueIfNew(URL url) {
        Verify.notNull(url);

        if (wasInQueue(url)) {
            log.debug("Not adding URL to queue as it is in history : {}", url);
        } else {
            this.enqueue(url);
            log.debug("Enqueue URL : {}", url);
        }
    }

    @Override
    public void enqueueIfNew(List<URL> urls) {
        Verify.notNull(urls);

        log.debug("About to enqueue multiple URLs only if new, in total : {}", urls.size());
        for (URL url : urls) {
            this.enqueueIfNew(url);
        }
    }


    @Override
    public URL dequeue() {
        try {
            URL url = urlQueue.poll(10, TimeUnit.SECONDS);
            log.debug("Dequeue  URL : {}", url);
            return url;
        } catch (InterruptedException e) {
            log.error(e.getMessage() + e);
        }
        return null;
    }


    @Override
    public boolean isEmpty() {
        return urlQueue.isEmpty();
    }

    @Override
    public boolean wasInQueue(URL url) {
        boolean isPresent = urlHistory.contains(url);
        log.debug("Passed URL was already in history : {}", url);

        return isPresent;
    }
}
