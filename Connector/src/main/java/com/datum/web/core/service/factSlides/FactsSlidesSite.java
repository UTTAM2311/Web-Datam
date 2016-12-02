package com.datum.web.core.service.factSlides;

import java.net.URL;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.core.util.Verify;
import com.tt.web.DefaultSite;
import com.tt.web.Page;
import com.tt.web.UrlGenerator;
import com.webCrawller.util.PropertyBinderModule;

public class FactsSlidesSite extends DefaultSite {

    private static final Logger logger = LoggerFactory.getLogger(FactsSlidesSite.class);

    private static final String FILE = "factslides.properties";
    private static final String FILE_EXTENTION;
    private final static int threads;

    private static URL siteUrl = null;
    private static URL crawlStartUrl = null;

    private static final Pattern CRAWL_URL_PATTERN;
    private static final Pattern SCRAPE_URL_PATTERN;


    static {
        PropertyBinderModule propertiesBinder = new PropertyBinderModule(FILE);
        try {
            siteUrl = new URL(propertiesBinder.getSiteUrl());
            crawlStartUrl = new URL(propertiesBinder.getCrawlStartUrl());

            logger.info("Site URL : " + siteUrl);
            logger.info("Crawl start URL : " + crawlStartUrl);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // crawl pattern
        CRAWL_URL_PATTERN = Pattern.compile(propertiesBinder.getCrawlUrlPattern());

        // scrape pattern
        SCRAPE_URL_PATTERN = Pattern.compile(propertiesBinder.getScrapeUrlPattern());

        // File format in which scrapped data to be stored
        FILE_EXTENTION = propertiesBinder.getFileExtention();

        threads = propertiesBinder.getThreads();
    }

    public FactsSlidesSite() {
        super(siteUrl, crawlStartUrl, CRAWL_URL_PATTERN, SCRAPE_URL_PATTERN);
    }

    @Override
    public UrlGenerator getUrlGenerator() {
        return null;
    }

    @Override
    public String getFileName(Page page) {
        Verify.notNull(page);

        String pagePath = page.getUrl().getPath();
        String fileName = pagePath.substring(1);

        return fileName + "." + FILE_EXTENTION;
    }

    public int getThreads() {
        return threads;
    }


}
