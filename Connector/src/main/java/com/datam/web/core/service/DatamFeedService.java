package com.datam.web.core.service;

import java.net.URL;

import com.rss.core.FeedProcessor;
import com.rss.core.impl.AbstractFeedService;

public class DatamFeedService extends AbstractFeedService implements Service {

    private String serviceName;
    private String inputDir;
    private static boolean isRunning = false;

    @SuppressWarnings("rawtypes")
    public DatamFeedService(String serviceName, URL feedUrl, String filePathToWrite, FeedProcessor processor)
            throws Exception {
        super(serviceName, feedUrl, filePathToWrite, processor);
        this.serviceName = serviceName;
        this.inputDir = filePathToWrite;
    }

    @SuppressWarnings("rawtypes")
    public DatamFeedService(String serviceName, URL feedUrl, long scheduleToStartService, long scheduleToClearHistory,
            String filePathToWrite, int threadCount, FeedProcessor processor) throws Exception {
        super(serviceName, feedUrl, scheduleToStartService, scheduleToClearHistory, filePathToWrite, threadCount,
                processor);
        this.serviceName = serviceName;
        this.inputDir = filePathToWrite;
    }

    @Override
    public String getName() {
        return serviceName;
    }

    @Override
    public String getInputDir() {
        return inputDir;
    }

    @Override
    public void startService() {
        isRunning = true;
        startFeedService();
    }

    @Override
    public void stopService() {
        isRunning = false;
        stopFeedService();
    }

    @Override
    public boolean isServiceRunning() {
        return isRunning;
    }

}
