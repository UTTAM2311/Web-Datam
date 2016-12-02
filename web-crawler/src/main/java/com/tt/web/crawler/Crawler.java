package com.tt.web.crawler;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Simple definition of Crawler.
 */
public interface Crawler {

    /**
     * Crawl a given {@link URL} and extract all the URLs present in that Page.
     * 
     * @param inUrl URL to crawl
     * @return list of URLs present in the passed page
     * @throws IOException 
     */
    List<URL> crawl(URL inUrl) throws IOException;

    /**
     * Crawl a given {@link URL} and extract all the URLs present in that Page that matches the
     * passed pattern.
     * 
     * @param inUrl URL to crawl
     * @param urlPattern pattern to filter the crawled URLs
     * @return list of URLs present in the passed page
     * @throws IOException
     */
    List<URL> crawl(URL inUrl, Pattern urlPattern) throws IOException;

}
