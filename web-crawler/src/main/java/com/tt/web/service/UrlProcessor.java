package com.tt.web.service;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tt.web.Page;
import com.tt.web.Site;
import com.tt.web.concurrent.impl.UrlQueue;
import com.tt.web.crawler.Crawler;
import com.tt.web.scraper.Scraper;
import com.tt.web.util.FileUtil;
import com.tt.web.util.JsonUtil;

public final class UrlProcessor implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(UrlProcessor.class);

    private final Site site;
    private final Crawler crawler;
    private final Scraper scraper;

    private boolean nextUrlPresent = true;

    private UrlQueue baseUrlQueue;
    private String location;
    private Map<URL, Exception> failedUrlMap = new HashMap<>();
    private int processorNumber;
    private ArrayNode node = JsonUtil.mapper().createArrayNode();

    public UrlProcessor(int processorNumber, Site site, Crawler crawler, Scraper scraper, UrlQueue baseUrlQueue,
            String location) {
        this.processorNumber = processorNumber;
        this.crawler = crawler;
        this.scraper = scraper;
        this.baseUrlQueue = baseUrlQueue;
        this.location = location;
        this.site = site;
        log.info("Initialising processor numbered : {}", processorNumber);
    }

    @Override
    public void run() {

        log.info("UrlProcessor no. {} has started", processorNumber);

        while (nextUrlPresent) {
            // get new URL from Queue
            URL url = baseUrlQueue.dequeue();
            if (url != null) {
                processUrl(url, location);
            } else {
                log.info("-------- Terminated ------");
                nextUrlPresent = false;
            }
        }
        writeFailedUrls();
    }

    public void stopTask() {
        nextUrlPresent = false;
    }

    public boolean isTaskStopped() {
        return !nextUrlPresent;
    }

    private void writeFailedUrls() {
        if (!failedUrlMap.isEmpty()) {
            failedUrlMap.entrySet().forEach(e -> {
                ObjectNode urlNode = JsonUtil.mapper().createObjectNode();
                urlNode.put("URL", e.getKey().toString());
                urlNode.put("ExceptionMessage", e.getValue().getMessage());
                node.add(urlNode);
            });

            FileUtil.writeToFile("Storage/content/", "FailedUrls.json", node.toString(), true);
        }
    }

    /**
     * Processes the given URL for content. Process steps ..
     * <li>Check if it can be crawled. If yes, crawl and add URLs to baseUrlQueue.</li>
     * <li>Check if it can be scraped. If yes, scrape and save it to the directory.</li>
     * 
     * @param inUrl URL to process
     */
    private void processUrl(URL inUrl, String location) {
        log.info("Processing URL : {}", inUrl);

        // check for crawling
        boolean isCrawlable = site.isCrawlable(inUrl);
        log.info("url is crawlabel : " + isCrawlable);
        if (isCrawlable) {
            log.info("crawlable url : " + inUrl);
            try {
                List<URL> crawledUrls = crawler.crawl(inUrl);
                log.info("Crawled urls " + crawledUrls);
                if (crawledUrls != null && crawledUrls.size() > 0) {
                    baseUrlQueue.enqueueIfNew(crawledUrls);
                }
            } catch (IOException e) {
                log.info("Error occured for the URL:{}", inUrl, " due to " + e.getMessage());
                failedUrlMap.put(inUrl, e);
            }


        }
        // check for scraping
        boolean isScrapable = site.isScrapable(inUrl);
        if (isScrapable) {
            log.info("scrable url : " + inUrl);
            try {
                Page page = scraper.scrape(inUrl);
                if (page != null) {
                    this.savePageToLocal(page, location);
                }
            } catch (IOException e) {
                log.info("Error occured for the URL:{}", inUrl, " due to " + e.getMessage());
                failedUrlMap.put(inUrl, e);
            }

        }

    }

    /**
     * Saves the scraped page to the passed location
     * 
     * @param page
     * @param location
     */
    private void savePageToLocal(Page page, String location) {

        // get site specific filename
        String fileName = site.getFileName(page);

        // Write to file
        FileUtil.writeToFile(location, fileName, page.getRawContent());
    }

}

