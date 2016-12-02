package com.tt.web.scraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.tt.web.Page;
import com.tt.web.util.FileUtil;

public class JsoupScraperTest {

    private JsoupScraper scraper = JsoupScraper.getInstance();
    private String url = "http://www.factslides.com/s-Indonesia";

    @Test
    public void test_scrape() throws IOException {
        Page page = scraper.scrape(new URL(url));
        Assert.assertEquals(url, page.getUrl().toString());
        Assert.assertNotNull(page.getRawContent());
        //System.out.println(page.getRawContent());
        FileUtil.writeToFile("/home/manojk/Desktop/", "puzzle1.html", page.getRawContent());
    }

}
