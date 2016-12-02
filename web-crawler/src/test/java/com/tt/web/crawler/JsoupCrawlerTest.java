package com.tt.web.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JsoupCrawlerTest {

    private JsoupCrawler jsCrawler;
    
    private URL crawlUrl;
    
    
    /* --- Setup --- */
    
    @Before
    public void setUp() throws MalformedURLException {
        jsCrawler = JsoupCrawler.getInstance();
        crawlUrl = new URL("http://www.factslides.com/");
    }

    @After
    public void tearDown() {
        jsCrawler = null;
        crawlUrl = null;
    }

    
    /* --- Tests --- */
    
    @Test(expected = IllegalArgumentException.class)
    public void test_crawl_null_input() throws IOException {
        jsCrawler.crawl(null);
        
    }
    
    @Test
    public void test_crawl_correct_input() throws IOException {
        List<URL> urls = jsCrawler.crawl(crawlUrl);
        Assert.assertNotNull(urls);
        System.out.println(urls.size());
        Assert.assertTrue(urls.size() > 0);
    }
    
}
