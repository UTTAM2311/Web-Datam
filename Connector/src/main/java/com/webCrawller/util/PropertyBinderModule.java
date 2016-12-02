package com.webCrawller.util;

import java.util.Properties;

import com.tt.core.util.PropertyUtil;

public class PropertyBinderModule {

    private static final String PROP_SITE_URL = "site_url";
    private static final String PROP_CRAWL_START_URL = "site.crawl.start_url";
    private static final String PROP_CRAWL_URL_REGEX = "site.crawl.url_regex";
    private static final String PROP_SCRAPE_URL_REGEX = "site.scrape.url_regex";
    private static final String PROP_DOWNLOAD_LOCATION = "site.download.location";
    private static final String PROP_FILE_EXTENTION = "site.fileStroreFormat";
    private static final String PROP_THREADS = "site.threads";
    private static final String DEFAULT_THREADS = "4";

    private final String file;
    private final Properties PROPS;

    public PropertyBinderModule(String filePath) {
        this.file = filePath;
        this.PROPS = PropertyUtil.load(this.file);
    }

    /* --- Property Access --- */

    public String getSiteUrl() {
        return PROPS.getProperty(PROP_SITE_URL);
    }

    public String getCrawlStartUrl() {
        return PROPS.getProperty(PROP_CRAWL_START_URL);
    }

    public String getCrawlUrlPattern() {
        return PROPS.getProperty(PROP_CRAWL_URL_REGEX);
    }

    public String getScrapeUrlPattern() {
        return PROPS.getProperty(PROP_SCRAPE_URL_REGEX);
    }

    public String getDownloadLocation() {
        return PROPS.getProperty(PROP_DOWNLOAD_LOCATION);
    }

    public String getFileExtention() {
        return PROPS.getProperty(PROP_FILE_EXTENTION);
    }

    public String getProperty(String key) {
        return PROPS.getProperty(key);
    }

    public int getThreads() {
        String threads = PROPS.getProperty(PROP_THREADS, DEFAULT_THREADS);
        return Integer.parseInt(threads);

    }



}
