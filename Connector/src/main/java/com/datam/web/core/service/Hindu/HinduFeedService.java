package com.datam.web.core.service.Hindu;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datam.web.core.Constants;
import com.datam.web.core.service.DatamFeedService;
import com.rss.core.hindu.HinduFeedProcessor;

public class HinduFeedService extends DatamFeedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HinduFeedService.class);

    // we can reed this from a properties file or keep them constants
    private final static String serviceName = "HinduFeedService";
    private final static String URI = "http://www.thehindu.com/?service=rss";
    private final static String filePathToWrite =  Constants.outStore +"hinduFeed/";
    private final static long scheduleToStartService = 3600;
    private final static long scheduleToClearHistory = 7200;
    private final static int threadCount = 4;

    public HinduFeedService() throws Exception {
        super(serviceName, getFeedUrl(), scheduleToStartService, scheduleToClearHistory, filePathToWrite, threadCount,
                new HinduFeedProcessor());

    }

    public static URL getFeedUrl() {
        URL url = null;
        try {
            return new URL(URI);
        } catch (MalformedURLException e) {
            LOGGER.error("Error in the FeedURL:{}  due to:{} ", URI, e.getMessage());
        }
        return url;
    }


}
