package com.rss.core.hindu;

import com.rss.core.impl.AbstractFeedDetails;
import com.sun.syndication.feed.synd.SyndEntry;

public class HinduFeedDetails extends AbstractFeedDetails {

    public HinduFeedDetails(String filePathToWrite, SyndEntry feed) {
        super(filePathToWrite, feed);
    }
}
