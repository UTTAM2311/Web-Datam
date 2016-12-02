package com.tt.web.crawler;

import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import com.tt.core.util.annotation.ToBeImplemented;

/**
 * Default implementation of Scraper.
 */
@ToBeImplemented("Use JsoupCrawler instead")
public class DefaultCrawler implements Crawler {

    @Override
    public List<URL> crawl(URL inUrl) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<URL> crawl(URL inUrl, Pattern urlPattern) {
        // TODO Auto-generated method stub
        return null;
    }

}
