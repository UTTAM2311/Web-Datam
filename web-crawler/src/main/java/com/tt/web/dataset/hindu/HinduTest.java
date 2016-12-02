package com.tt.web.dataset.hindu;

import com.tt.web.crawler.JsoupCrawler;
import com.tt.web.scraper.JsoupScraper;
import com.tt.web.service.ContentExtractor;

public class HinduTest {

    private static JsoupCrawler jsoupCrawler = JsoupCrawler.getInstance();
    private static JsoupScraper jsoupScraper = JsoupScraper.getInstance();
    private static HinduSite hinduSite = new HinduSite();

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        ContentExtractor extractor = new ContentExtractor(hinduSite, jsoupCrawler, jsoupScraper, 1);
        extractor.extractTo("/");

    }


}
