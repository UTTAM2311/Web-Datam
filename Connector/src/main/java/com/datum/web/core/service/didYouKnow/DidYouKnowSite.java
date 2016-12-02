package com.datum.web.core.service.didYouKnow;

import java.net.URL;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.core.util.Verify;
import com.tt.web.DefaultSite;
import com.tt.web.Page;
import com.tt.web.UrlGenerator;
import com.webCrawller.util.PropertyBinderModule;

public class DidYouKnowSite extends DefaultSite {

    private static final Logger logger = LoggerFactory.getLogger(DidYouKnowSite.class);

    private static final String FILE = "DidUKnow.properties";
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


        threads = propertiesBinder.getThreads();
    }

    public DidYouKnowSite() {
        super(siteUrl, crawlStartUrl, CRAWL_URL_PATTERN, SCRAPE_URL_PATTERN);
    }

    @Override
    public UrlGenerator getUrlGenerator() {
        return null;
    }

    @Override
    public String getFileName(Page page) {
        Verify.notNull(page);

        String uri = page.getUrl().toString();
        String[] names = uri.split("\\?");
        String filname = FilenameUtils.getName(names[0]);
        String name = FilenameUtils.removeExtension(filname);
        String ext = FilenameUtils.getExtension(filname);

        String fileName = name + StringUtils.remove(names[1], "page=") + "." + ext;

        return fileName;
    }

    public int getThreads() {
        return threads;
    }


}
