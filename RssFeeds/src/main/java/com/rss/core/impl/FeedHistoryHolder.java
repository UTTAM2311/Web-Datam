package com.rss.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntry;

public class FeedHistoryHolder implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(FeedHistoryHolder.class);

    private Map<String, Object> historyMap = new HashMap<>();
    private static int count = 0;
    private final String site;

    public FeedHistoryHolder(String site) {
        this.site = site;
    }

    public List<SyndEntry> getUpdatedFeeds(List<SyndEntry> list) {
        List<SyndEntry> updatedFeeds = new ArrayList<>();
        synchronized (historyMap) {
            for (SyndEntry entry : list) {
                String uri = entry.getUri();
                // if there is no updated date it means it is new feed. If there is any updated date
                // and if we have that feed in history then compare the updated dates.
                if (historyMap.containsKey(uri) && entry.getUpdatedDate() != null) {
                    if (historyMap.get(uri) != null) {
                        long latestUpdatedDate = entry.getUpdatedDate().getTime();
                        long previousUpdatedDate = (long) historyMap.get(uri);
                        if (previousUpdatedDate < latestUpdatedDate) {
                            updatedFeeds.add(entry);
                            historyMap.put(uri, latestUpdatedDate);
                        }
                    } else {
                        updatedFeeds.add(entry);
                        historyMap.put(uri, entry.getUpdatedDate().getTime());
                    }
                } else if (historyMap.containsKey(uri) && entry.getUpdatedDate() == null) {
                    continue;
                }

                else {
                    historyMap.put(uri, null);
                    updatedFeeds.add(entry);
                }
            }
        }

        return updatedFeeds;
    }

    @Override
    public void run() {
        count = count + 1;
        synchronized (historyMap) {
            historyMap.clear();
            log.info("Clearing  the rss feed history for {} for {}  time", site, count);
        }
    }


}
