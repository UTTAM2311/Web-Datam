package com.tt.web;

import java.net.URL;
import java.util.regex.Pattern;

/**
 * Any site that needs to crawled, need to implement this interface. The implementations would be
 * the entry points for any sort of crawling or scraping activity.
 * 
 * <p>
 * Here is what expected out of the implementation/definition :
 * <li>SiteURL : URL of the site</li>
 * <li>CrawlStartURL : A URL to start the crawling with. (SiteURL will be used if none provided.)</li>
 * <li>CrawlURLPatterns : URL patterns to crawl</li>
 * <li>ScrapeURLPatterns : URL patterns to scrape</li>
 * <li>URLGenerator : A URL generator to generate URLs which can't be crawled directly.</li>
 * </p>
 * 
 * Note that not every method needs to be implemented. For example, a site doesn't necessarily have
 * a URL generator.
 */
public interface Site {


    /**
     * Get the base URL of the site.
     * 
     * @return {@link URL} of the site
     */
    URL getSiteUrl();

    /**
     * The URL to start the crawling with.
     * 
     * @return {@link URL} of the start page
     */
    URL getCrawlStartUrl();

    /**
     * URL generator to generate URLs of the site.
     * 
     * @return {@link UrlGenerator} implementation for this site.
     */
    UrlGenerator getUrlGenerator();


    /* --- Crawling --- */

    /**
     * Gets the URL pattern, that should be crawled.
     * 
     * @return URL {@link Pattern} object
     */
    Pattern getCrawlUrlPattern();

    /**
     * Check if the URL of this site of the site can be crawled.
     * 
     * @param url URL to verify
     * @return true if it matches the allowed crawl URL pattern
     */
    boolean isCrawlable(URL url);


    /* --- Scraping --- */

    /**
     * Gets the URL pattern, that should be scraped.
     * 
     * @return URL {@link Pattern} object
     */
    Pattern getScrapeUrlPattern();

    /**
     * Check if the URL of this site is can be scraped.
     * 
     * @param url URL to verify
     * @return true if it matches the allowed scrape URL pattern
     */
    boolean isScrapable(URL url);
    
    
    
    /* --- Other methods --- */
    
    /**
     * Gets the filename for the given page object.
     * 
     * @return file name to save/download the page with.
     */
    String getFileName(Page page);


}
