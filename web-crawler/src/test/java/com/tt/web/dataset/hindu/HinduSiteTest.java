package com.tt.web.dataset.hindu;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.tt.web.DefaultPage;

public class HinduSiteTest {

    private HinduSite hinduSite = new HinduSite();
    private String urlString = "http://www.thehindu.com/opinion/lead/seeking-a-fair-deal-for-muslims/article4626.ece";
    
    @Test
    public void test_getUrlGenerator() {
        HinduUrlGenerator hinduUrlGenerator = (HinduUrlGenerator) hinduSite.getUrlGenerator();
        Assert.assertNotNull(hinduUrlGenerator);
    }

    @Test
    public void test_getFileName() throws MalformedURLException {
        String expectedResult = "/opinion/lead/article4626.ece";
        URL url = new URL(urlString);
        DefaultPage dafaultPage = new DefaultPage(url, "someContent");
        Assert.assertEquals(expectedResult, hinduSite.getFileName(dafaultPage));
    }
    
    @Test
    public void test_getFileName1() throws MalformedURLException {
        String expectedResult = "/opinion/lead/article4626.ece";
        String urlString = "http://www.factslides.com/s-Ancient-Greece";
        URL url = new URL(urlString);
        DefaultPage dafaultPage = new DefaultPage(url, "someContent");
        HinduSite hinduSite = new HinduSite();
        System.out.println(hinduSite.getFileName(dafaultPage));
        //Assert.assertEquals(expectedResult, hinduSite.getFileName(dafaultPage));
    }
}
