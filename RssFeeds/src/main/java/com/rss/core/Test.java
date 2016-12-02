package com.rss.core;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class Test {

    public static void main(String[] args) throws IOException, IllegalArgumentException, FeedException {
        // String urlString = "http://www.espncricinfo.com/ci/content/rss/feeds_rss_cricket.html";
        // ROMERssFeedImpl im = new ROMERssFeedImpl();
        // im.feed(new URL(urlString));

        URL url = new URL("http://www.thehindu.com/news/national/karnataka/?service=rss");
        XmlReader reader = null;
        reader = new XmlReader(url);
        SyndFeed feed = new SyndFeedInput().build(reader);
        System.out.println("Feed Title: " + feed.getEntries().size());
        
        for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
            SyndEntry entry = (SyndEntry) i.next();
            System.out.println(entry.getTitle()+ " Link : "+entry.getLink());
        }
        close(reader);
    }

    private static void close(XmlReader reader) {
        if (reader != null)
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}

