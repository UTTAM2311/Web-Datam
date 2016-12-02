package com.tt.web.scraper;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import com.tt.web.Page;

/**
 * A simple web page scraper definition.
 */
public interface Scraper {

    /**
     * Scrapes the web URL passed and gets the whole content.
     * 
     * @param inUrl URL of the page to be scraped.
     * @return scraped webPage
     */
    Page scrape(URL inUrl) throws IOException;

    /**
     * Scrapes the web URL passed and gets the content matching the passed pattern.
     * 
     * @param inUrl URL of the page to be scraped
     * @param contentPattern content pattern to match and extract
     * @return scraped webPage
     */
    Page scrape(URL inUrl, Pattern contentPattern)throws IOException;

}
