package com.tt.web.dataset.hindu;

import org.junit.Assert;
import org.junit.Test;


public class HinduPropsTest {

    @Test
    public void test_getSiteUrl() {
        Assert.assertEquals("http://www.thehindu.com/", HinduProps.getSiteUrl());
    }

    @Test
    public void test_getDownloadLocation() {
        Assert.assertEquals("/tmp/hindu_test/", HinduProps.getDownloadLocation());
    }

    @Test
    public void test_getCrawlStartUrl() {
        Assert.assertEquals("http://www.thehindu.com/archive/web/2016/08/17", HinduProps.getCrawlStartUrl());
    }

    @Test
    public void test_getCrawlUrlPattern() {
        Assert.assertEquals("http://www.thehindu.com/archive/web/2009/.*|.*jsp.*", HinduProps.getCrawlUrlPattern());
    }

    @Test
    public void test_getScrapeUrlPattern() {
        Assert.assertEquals("http://www.thehindu.com/.*.ece", HinduProps.getScrapeUrlPattern());
    }

    @Test
    public void test_getUrlGeneratorStartDate() {
        Assert.assertEquals("18-08-2016", HinduProps.getUrlGeneratorStartDate());
    }

    @Test
    public void test_getUrlGeneratorEndDate() {
        Assert.assertEquals("18-08-2016", HinduProps.getUrlGeneratorEndDate());
    }
}
