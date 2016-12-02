package com.tt.web;

import java.net.URL;
import java.util.Map;

/**
 * Represents a single web page of a site.
 */
public interface Page {
    
    /**
     * Article related
     */
    public static final String ARTICLE_AUTHOR = "article-author";
    
    public static final String ARTICLE_DATED = "article-dated";
    
    public static final String ARTICLE_LOCATION = "article-location";
    
    public static final String ARTICLE_NAME = "article-name";
    
    public static final String ARTICLE_TAG_TREE = "article-tag-tree";
    public static final String ARTICLE_TAGS = "article-tags";
    
    public static final String ARTICLE_UID = "article-uid";
    public static final String ARTICLE_URL = "article-url";


    /**
     * Site related
     */
    public static final String SITE_NAME = "site-name";
    

    /**
     * Get the unique URL of the page.
     * 
     * @return {@link URL} object for this page
     */
    URL getUrl();

    /**
     * Gets the complete/raw content of the current web page.
     * 
     * @return the page content as string
     */
    String getRawContent();

    /**
     * This is a subset (rather the substring) of the raw content, containing only the necessary
     * information.
     * 
     * @return the page scraped content as string
     */
    String getScrapedContent();

    /**
     * Any metadata of the page that is stored.
     * 
     * @return a map of key values.
     */
    Map<String, Object> getMetadata();

}
