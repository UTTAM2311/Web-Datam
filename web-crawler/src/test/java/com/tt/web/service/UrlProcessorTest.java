package com.tt.web.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.tt.web.concurrent.impl.UrlQueue;
import com.tt.web.crawler.JsoupCrawler;
import com.tt.web.dataset.hindu.HinduSite;
import com.tt.web.scraper.JsoupScraper;

public class UrlProcessorTest {

    private JsoupScraper scrapper;
    private JsoupCrawler crawler;
    private HinduSite site;
    private String location;
    private UrlQueue baseUrlQueue;
    private int processorNumber;

    private UrlProcessor urlProcessor;

    @Before
    public void setUp() throws MalformedURLException {
        scrapper = JsoupScraper.getInstance();
        crawler = JsoupCrawler.getInstance();
        site = new HinduSite();
        location = "/home/manojk/Desktop/hindu";
        baseUrlQueue = new UrlQueue();
        processorNumber = 1;
        
        baseUrlQueue.enqueueIfNew(new URL("http://www.thehindu.com/template/1-0-1/widget/archive/archiveWebDayRest.jsp?d=2009-08-18"));
    }

    @Test
    public void test_run() {
        urlProcessor = new UrlProcessor(processorNumber, site, crawler, scrapper, baseUrlQueue, location);
        urlProcessor.run();
    }

}

