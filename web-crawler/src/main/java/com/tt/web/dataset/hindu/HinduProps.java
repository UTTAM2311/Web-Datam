package com.tt.web.dataset.hindu;

import java.util.Properties;

import com.tt.core.util.PropertyUtil;

public class HinduProps {

    public static final String FILE_NAME = "hindu.properties";

    private static final String PROP_SITE_URL = "site.hindu.site_url";
    private static final String PROP_CRAWL_START_URL = "site.hindu.crawl.start_url";
    private static final String PROP_CRAWL_URL_REGEX = "site.hindu.crawl.url_regex";
    private static final String PROP_SCRAPE_URL_REGEX = "site.hindu.scrape.url_regex";
    private static final String PROP_URL_GEN_START_DATE = "site.hindu.url_gen.start_date";
    private static final String PROP_URL_GEN_END_DATE = "site.hindu.url_gen.end_date";
    private static final String PROP_DOWNLOAD_LOCATION = "site.hindu.download.location";

    private static final Properties PROPS;
    static {
        PROPS = PropertyUtil.load(FILE_NAME);
    }


    /**
     * Utility Class
     */
    private HinduProps() {}


    /* --- Property Access --- */

    public static String getSiteUrl() {
        return PROPS.getProperty(PROP_SITE_URL);
    }

    public static String getCrawlStartUrl() {
        return PROPS.getProperty(PROP_CRAWL_START_URL);
    }

    public static String getCrawlUrlPattern() {
        return PROPS.getProperty(PROP_CRAWL_URL_REGEX);
    }

    public static String getScrapeUrlPattern() {
        return PROPS.getProperty(PROP_SCRAPE_URL_REGEX);
    }

    public static String getUrlGeneratorStartDate() {
        return PROPS.getProperty(PROP_URL_GEN_START_DATE);
    }

    public static String getUrlGeneratorEndDate() {
        return PROPS.getProperty(PROP_URL_GEN_END_DATE);
    }

    public static String getDownloadLocation() {
        return PROPS.getProperty(PROP_DOWNLOAD_LOCATION);
    }


}
