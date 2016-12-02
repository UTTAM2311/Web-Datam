package com.rss.core.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.rss.core.RssFeeder;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;


public class ROMERssFeedImpl implements RssFeeder {

    private static final Logger log = LoggerFactory.getLogger(ROMERssFeedImpl.class);

    @Override
    public List<SyndEntry> feed(URL url) {

        SyndFeed feed = null;
        InputStream is = null;
        List<SyndEntry> feeds = null;

        try {
            log.debug("Started  conecting to get the from URL {}", url);
            URLConnection openConnection = url.openConnection();
            is = url.openConnection().getInputStream();
            if ("gzip".equals(openConnection.getContentEncoding())) {
                is = new GZIPInputStream(is);
            }
            InputSource source = new InputSource(is);
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(source);
            feeds = feed.getEntries();
            log.debug("successly got the feeds from URL {}", url);
        } catch (Exception e) {
            log.error("Exception occured when building the feed object out of the url:{}, error:{}", url,
                    e.getMessage());
        } finally {
            closeStream(is);
        }
        return feeds;
    }

    private void closeStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
