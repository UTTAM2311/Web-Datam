package com.tt.web;

import java.net.URL;

/**
 * URL generator for sites. Can be used when the crawling/scraping URLs of a site follow a pattern.
 * Other interesting use-cases would be:
 * 
 * <p>
 * <li>Not all URLs of a site be extracted by crawling...</li>
 * <li>To crawl the APIs, which are called in AJAX way..</li>
 * <P>
 */
public interface UrlGenerator {

    /**
     * Generate a URL.
     * 
     * @return the generated site Url.
     */
    URL getNextURL();

    /**
     * Returns true if more URLs are present.
     * 
     * @return true if generator can generate next URL.
     */
    boolean isNextUrlPresent();

}
