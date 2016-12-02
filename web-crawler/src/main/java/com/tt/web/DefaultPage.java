package com.tt.web;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation.
 */
public class DefaultPage implements Page {

    private final URL url;
    private String rawContent;
    private String scrapedContent;
    private Map<String, Object> metadata;


    /* --- Constructors --- */

    public DefaultPage(URL pageUrl) {
        this(pageUrl, null);
    }

    public DefaultPage(URL pageUrl, String rawContent) {
        this(pageUrl, rawContent, null);
    }

    public DefaultPage(URL pageUrl, String rawContent, Map<String, Object> metadata) {
        super();
        this.url = pageUrl;
        this.rawContent = rawContent;
        this.metadata = metadata;
    }

    /* --- Getters and Setters --- */

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) throws IllegalAccessException {
        if (this.rawContent != null)
            throw new IllegalAccessException("rawContent is already set and is not allowed to modify");

        this.rawContent = rawContent;
    }

    @Override
    public String getScrapedContent() {
        return scrapedContent;
    }

    public void setScrapedContent(String scrapedContent) throws IllegalAccessException {
        if (this.scrapedContent != null)
            throw new IllegalAccessException("scrapedContent is already set and is not allowed to modify");

        this.scrapedContent = scrapedContent;
    }

    @Override
    public Map<String, Object> getMetadata() {
        if (this.metadata == null) {
            return Collections.unmodifiableMap(new HashMap<String, Object>());
        }

        return Collections.unmodifiableMap(metadata);
    }

    public void setMetadata(Map<String, Object> metadata) throws IllegalAccessException {
        if (this.metadata != null)
            throw new IllegalAccessException("metadata is already set and is not allowed to modify");

        this.metadata = metadata;
    }
}
