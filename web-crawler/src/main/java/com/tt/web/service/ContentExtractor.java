package com.tt.web.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.core.util.Verify;
import com.tt.web.Site;
import com.tt.web.UrlGenerator;
import com.tt.web.concurrent.impl.UrlQueue;
import com.tt.web.crawler.Crawler;
import com.tt.web.scraper.Scraper;
import com.tt.web.util.FileUtil;

/**
 * ContentExtractor is the base set up class for setting up a site for content download. It expects
 * the definitions of a {@link Site}, and implementations for {@link Crawler} and {@link Scraper}.
 * 
 * <p>
 * An {@link ExecutorService} with a fixed ThreadPool is used to concurrently go through the site
 * pages and download/extract content as specified in the {@link Site} definition.
 * </p>
 * 
 * @see Site
 * @see Crawler
 * @see Scraper
 */
public class ContentExtractor {

    private static final Logger log = LoggerFactory.getLogger(ContentExtractor.class);

    private final Site site;
    private final Crawler crawler;
    private final Scraper scraper;

    private List<UrlProcessor> taskList = new ArrayList<>();

    private UrlQueue baseUrlQueue;

    // Executor service support
    private ExecutorService executorService;

    // Default thread pool size
    private int threadPoolSize;


    /* --- Constructors --- */

    public ContentExtractor(Site site, Crawler crawler, Scraper scraper) {
        this(site, crawler, scraper, 4);
    }

    public ContentExtractor(Site site, Crawler crawler, Scraper scraper, int threadPoolSize) {

        // Sanity Checks
        Verify.notNull(site, crawler, scraper);
        Verify.notLowerBounded(threadPoolSize, 0);

        this.threadPoolSize = threadPoolSize;
        this.site = site;
        this.crawler = crawler;
        this.scraper = scraper;

        this.init();
    }

    /* --- Private Methods --- */

    /**
     * Initialise the content extractor.
     * 
     * <p>
     * Note: ContextExtractor should be initialised only once.
     * <p>
     */
    private void init() {

        log.info("Initialising the content extractor for site : {}", site.getSiteUrl());

        // initialise Queue
        baseUrlQueue = new UrlQueue();

        // if present, add the crawl start URL
        URL crawlStartUrl = this.site.getCrawlStartUrl();
        if (crawlStartUrl != null) {
            baseUrlQueue.enqueue(crawlStartUrl);
        }

        // if URLGenerator is present, generate and add all URLs to Queue
        UrlGenerator generator = site.getUrlGenerator();
        if (generator != null) {
            boolean nextUrlPresent = generator.isNextUrlPresent();
            while (nextUrlPresent) {
                URL nextUrl = generator.getNextURL();
                baseUrlQueue.enqueueIfNew(nextUrl);
                log.info("Generator - next url : {}", nextUrl.toString());

                nextUrlPresent = generator.isNextUrlPresent();
            }
        }

        // Initialise the executor service
        executorService = Executors.newFixedThreadPool(threadPoolSize);
        log.info("Initialising the executor service with a fixed threadpool of size : {}", threadPoolSize);

    }


    /* --- Public Methods --- */

    /**
     * Extracts the web content to the given location.
     * 
     * @param location path to where the content should be downloaded
     */
    public void extractTo(String location) {
        Verify.hasText(location);

        FileUtil.createDir(location);
        log.info("Download location for web content : {}", location);

        // Extract using executor service
        for (int i = 0; i < threadPoolSize; i++) {
            UrlProcessor urlProcessor = new UrlProcessor(i, site, crawler, scraper, baseUrlQueue, location);
            executorService.execute(urlProcessor);
            taskList.add(urlProcessor);
        }

    }

    public void stopWebCrawlingService() {
        
        for (UrlProcessor urlProcessor : taskList) {
            urlProcessor.stopTask();
        }
        
        executorService.shutdown();
    }
}
