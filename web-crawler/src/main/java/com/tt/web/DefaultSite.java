package com.tt.web;

import java.net.URL;
import java.util.regex.Pattern;

import com.tt.core.util.Verify;

/**
 * Default implementation.
 */
public class DefaultSite implements Site {

    private final URL siteUrl;

    private final URL crawlStartUrl;
    private UrlGenerator urlGenerator;

    private final Pattern crawlUrlPattern;
    private final Pattern scrapeUrlPattern;

    /**
     * Base Constructor with site URL and crawl start URL.
     * 
     * @param siteUrl
     * @param crawlStartUrl
     */
    public DefaultSite(URL siteUrl, URL crawlStartUrl) {
        super();

        // Sanity Checks
        Verify.notNull(siteUrl);

        this.siteUrl = siteUrl;
        this.crawlStartUrl = crawlStartUrl;

        Pattern crawlAll = Pattern.compile("**");
        this.crawlUrlPattern = crawlAll;

        Pattern scrapeAll = Pattern.compile("**");
        this.scrapeUrlPattern = scrapeAll;
    }

    /**
     * Constructor with no specific scrape or crawl patterns, but a URL generator.
     * 
     * @param siteUrl URL of the site
     * @param crawlStartUrl URL to start crawling with..
     * @param urlGenerator a {@link UrlGenerator} to generate URLs to crawl/scrape
     */
    public DefaultSite(URL siteUrl, URL crawlStartUrl, UrlGenerator urlGenerator) {
        this(siteUrl, crawlStartUrl);
        this.urlGenerator = urlGenerator;
    }


    /**
     * Constructor with all but URLGenerator
     * 
     * @param siteUrl URL of the site
     * @param crawlStartUrl URL to start crawling with..
     * @param crawlUrlPattern {@link Pattern} of the URLs to crawl
     * @param scrapeUrlPattern {@link Pattern} of the URLs to scrape
     */
    public DefaultSite(URL siteUrl, URL crawlStartUrl, Pattern crawlUrlPattern, Pattern scrapeUrlPattern) {
        super();
        this.siteUrl = siteUrl;
        this.crawlStartUrl = crawlStartUrl;
        this.crawlUrlPattern = crawlUrlPattern;
        this.scrapeUrlPattern = scrapeUrlPattern;
    }


    @Override
    public URL getSiteUrl() {
        return siteUrl;
    }

    @Override
    public URL getCrawlStartUrl() {
        return crawlStartUrl;
    }

    @Override
    public UrlGenerator getUrlGenerator() {
        return urlGenerator;
    }

    public void setUrlGenerator(UrlGenerator urlGenerator) {
        if (urlGenerator != null) {
            throw new RuntimeException("Setting urlGenerator more than once is not allowed");
        }

        this.urlGenerator = urlGenerator;
    }

    @Override
    public Pattern getCrawlUrlPattern() {
        return crawlUrlPattern;
    }

    @Override
    public boolean isCrawlable(URL url) {
        // Sanity check
        Verify.notNull(url);
        return crawlUrlPattern.matcher(url.toString()).matches();
    }

    @Override
    public Pattern getScrapeUrlPattern() {
        return scrapeUrlPattern;
    }

    @Override
    public boolean isScrapable(URL url) {
        // Sanity check
        Verify.notNull(url);
        return scrapeUrlPattern.matcher(url.toString()).matches();
    }
    
    
    @Override
    public String getFileName(Page page) {
        Verify.notNull(page);
        
        String pagePath = page.getUrl().getPath();
        return pagePath;
    }

}
