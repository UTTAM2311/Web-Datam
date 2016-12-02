package com.tt.web.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.web.util.UrlUtil;

/**
 * {@link Crawler} implementation using JSoup library.
 */
public class JsoupCrawler implements Crawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsoupCrawler.class);
    private static final int TIMEOUT = 5000;

    /**
     * Private constructor for singleton pattern.
     */
    private JsoupCrawler() {

    }

    private static JsoupCrawler instance = new JsoupCrawler();

    public static JsoupCrawler getInstance() {
        return instance;
    }

    @Override
    public List<URL> crawl(URL inUrl) throws IOException {
        return crawl(inUrl, TIMEOUT);
    }

    public List<URL> crawl(URL inUrl, int timeOut) throws IOException {
        LOGGER.info("Input url in JsoupCrawler : " + inUrl);
        Document doc = Jsoup.parse(inUrl, timeOut);
        return getURLs(doc, null);
    }

    @Override
    public List<URL> crawl(URL inUrl, Pattern urlPattern) throws IOException {
        return crawl(inUrl, urlPattern, TIMEOUT);
    }

    public List<URL> crawl(URL inUrl, Pattern urlPattern, int timeout) throws IOException {
        Document doc = Jsoup.parse(inUrl, timeout);
        return getURLs(doc, urlPattern);
    }

    /**
     * Extracts all the urls from {@link Document} which are of format {@link Pattern}
     * 
     * @param doc
     * @param urlPattern
     * @return All extracted urls
     * @throws MalformedURLException
     */
    private List<URL> getURLs(Document doc, Pattern urlPattern) throws MalformedURLException {
        List<URL> urls = new ArrayList<URL>();
        Matcher match = null;
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String url = link.attr("abs:href");
            // add all urls if urlPattern is null
            if (urlPattern == null) {
                if (UrlUtil.isValidURL(url)) {
                    urls.add(new URL(url));
                }
            } else {
                match = urlPattern.matcher(url);
                if (match.matches())
                    urls.add(new URL(url));
            }
        }
        return urls;

    }

}
