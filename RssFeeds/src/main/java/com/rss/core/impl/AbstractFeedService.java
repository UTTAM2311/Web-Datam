package com.rss.core.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rss.core.FeedProcessor;
import com.rss.core.FeedService;
import com.rss.core.util.FileUtil;

public class AbstractFeedService implements FeedService {

    private static final Logger log = LoggerFactory.getLogger(AbstractFeedService.class);

    private final long scheduleToStartService;
    private final long scheduleToClearHistory;
    private final URL feedUrl;
    private final String filePathToWrite;

    private ScheduledExecutorService historyExecutorService;
    private ScheduledExecutorService serviceExecutorService;

    private ROMERssFeedImpl rssFeedImpl;
    private FeedHistoryHolder feedHistoryHolder;
    private FeedQueueImpl feedsQueue = new FeedQueueImpl();

    private List<Initiator> taskList = new ArrayList<>();

    private final String site;

    // Executor service support
    private ExecutorService executorService;

    @SuppressWarnings("rawtypes")
    public AbstractFeedService(String site, URL feedUrl, long scheduleToStartService, long scheduleToClearHistory,
            String filePathToWrite, int threadCount, FeedProcessor processor) throws Exception {

        this.scheduleToClearHistory = scheduleToClearHistory;
        this.scheduleToStartService = scheduleToStartService;
        this.feedUrl = feedUrl;
        this.filePathToWrite = filePathToWrite;
        this.site = site;

        FileUtil.createFolder(filePathToWrite);

        rssFeedImpl = new ROMERssFeedImpl();
        feedHistoryHolder = new FeedHistoryHolder(site);

        executorService = Executors.newFixedThreadPool(threadCount);
        historyExecutorService = Executors.newScheduledThreadPool(1);
        serviceExecutorService = Executors.newScheduledThreadPool(1);

        for (int i = 0; i < threadCount; i++) {
            Initiator initiatior = new Initiator(processor, feedsQueue);
            executorService.submit(initiatior);
            taskList.add(initiatior);
        }
    }

    @SuppressWarnings("rawtypes")
    public AbstractFeedService(String site, URL feedUrl, String filePathToWrite, FeedProcessor processor)
            throws Exception {
        // Default values
        this(site, feedUrl, 900, 7200, filePathToWrite, 4, processor);
    }

    public String getSite() {
        return site;
    }

    @Override
    public void startFeedService() {

        log.info("Rss Service started for {}", site);
        log.info("Rss Service for {}, is set to run for every {} seconds ", site, scheduleToStartService);
        log.info("Rss Service for {}, is set to update  for every {} seconds ", site, scheduleToClearHistory);

        DefaultScheduledServiceStarter serviceStarter = new DefaultScheduledServiceStarter(rssFeedImpl, feedsQueue,
                filePathToWrite, feedHistoryHolder, feedUrl, site);

        serviceExecutorService.scheduleAtFixedRate(serviceStarter, 0, scheduleToStartService, TimeUnit.SECONDS);
        historyExecutorService.scheduleAtFixedRate(feedHistoryHolder, scheduleToClearHistory, scheduleToClearHistory,
                TimeUnit.SECONDS);
    }

    @Override
    public void stopFeedService() {

        log.info("Rss Service stopped for {}", site);

        serviceExecutorService.shutdown();
        historyExecutorService.shutdown();

        for (Initiator initiatior : taskList) {
            initiatior.stopTask();
        }

        executorService.shutdown();
    }

}
