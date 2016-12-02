package com.tt.web.scraper;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.core.util.Verify;
import com.tt.web.DefaultPage;
import com.tt.web.Page;

/**
 * {@link Scraper} implementation using JSoup library.
 */
public class JsoupScraper implements Scraper {

    private static final Logger log = LoggerFactory.getLogger(JsoupScraper.class);

    public static final Integer TIMEOUT_MILLIS = 10000;


    /**
     * Private constructor for singleton pattern.
     */
    private JsoupScraper() {

    }

    private static JsoupScraper instance = new JsoupScraper();

    public static JsoupScraper getInstance() {
        return instance;
    }

    @Override
    public Page scrape(URL inUrl) throws IOException {
        return scrape(inUrl, TIMEOUT_MILLIS);
    }

    public Page scrape(URL inUrl, int timeOutMills) throws IOException {
        Verify.notNull(inUrl);

        Document document = Jsoup.connect(inUrl.toString()).timeout(timeOutMills).get();
        String rawContent = document.html();
        Map<String, Object> metadata = getMetadata(inUrl, document);

        // create a new page
        DefaultPage page = new DefaultPage(inUrl, rawContent, metadata);
        return page;
    }

    @Override
    public Page scrape(URL inUrl, Pattern scrapeContentPattern) throws IOException {
        Verify.notNull(inUrl);
        Verify.notNull(scrapeContentPattern);

        DefaultPage page = (DefaultPage) this.scrape(inUrl);
        String rawContent = page.getRawContent();
        Matcher matcher = scrapeContentPattern.matcher(rawContent);
        String scrapedContent = "";
        while (matcher.find())
            scrapedContent = scrapedContent + matcher.group(1);
        try {
            page.setScrapedContent(scrapedContent);
        } catch (IllegalAccessException e) {
            log.error("Error occured for the url {}", inUrl, " Because of :{}", e.getMessage());
        }
        return page;
    }



    /* --- Private methods --- */

    /**
     * Extract meta-tags from document
     * 
     * @param inUrl
     * @param document
     * @return
     */
    private Map<String, Object> getMetadata(URL inUrl, Document document) {
        Map<String, Object> metadata = new HashMap<>();

        // metadata
        metadata.put(Page.ARTICLE_URL, inUrl.toString());

        // page meta tags
        for (Element metaElem : document.select("meta")) {
            String name = metaElem.attr("name");
            String content = metaElem.attr("content");

            metadata.put(name, content);
        }

        return metadata;
    }

}
