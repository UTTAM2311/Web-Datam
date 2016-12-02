package com.datam.web.core.service.Hindu;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.core.util.Verify;
import com.tt.web.DefaultSite;
import com.tt.web.Page;
import com.tt.web.UrlGenerator;

public class HinduSite extends DefaultSite {

    private static final Logger logger = LoggerFactory.getLogger(HinduSite.class);

    private static URL SITE_URL = null;
    private static URL CRAWL_START_URL = null;

    private static final Pattern CRAWL_URL_PATTERN;
    private static final Pattern SCRAPE_URL_PATTERN;

    private static Date startDate;
    private static Date endDate;

    private UrlGenerator urlGenerator;

    static {
        try {
            SITE_URL = new URL(HinduProps.getSiteUrl());
            CRAWL_START_URL = new URL(HinduProps.getCrawlStartUrl());
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            startDate = dateFormat.parse(HinduProps.getUrlGeneratorStartDate());
            endDate = dateFormat.parse(HinduProps.getUrlGeneratorEndDate());

            logger.info("Site URL : " + SITE_URL);
            logger.info("Crawl start URL : " + CRAWL_START_URL);
            logger.info("Content will be downloaded fron DATE : " + startDate + " to DATE : " + endDate);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // crawl pattern
        CRAWL_URL_PATTERN = Pattern.compile(HinduProps.getCrawlUrlPattern());

        // scrape pattern
        SCRAPE_URL_PATTERN = Pattern.compile(HinduProps.getScrapeUrlPattern());

    }

    public HinduSite() {
        super(SITE_URL, CRAWL_START_URL, CRAWL_URL_PATTERN, SCRAPE_URL_PATTERN);
    }

    @Override
    public UrlGenerator getUrlGenerator() {
        if (urlGenerator == null) {
            urlGenerator = new HinduUrlGenerator(startDate, endDate);
        }
        return urlGenerator;
    }


    @Override
    public String getFileName(Page page) {
        Verify.notNull(page);

        String pagePath = page.getUrl().getPath();

        int lastSlash = pagePath.lastIndexOf("/");
        String pageFolderStr = pagePath.substring(0, lastSlash);
        String pageArticleName = pagePath.substring(lastSlash + 1, pagePath.length());

        // construct new path
        String baseFolderStr = pageFolderStr.substring(0, pageFolderStr.lastIndexOf("/"));

        return baseFolderStr + "/" + pageArticleName;
    }

}