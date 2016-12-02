package com.tt.web.scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.core.util.Verify;
import com.tt.web.DefaultPage;
import com.tt.web.Page;

/**
 * Default implementation of Scraper.
 */
public class DefaultScraper implements Scraper {

    private static final Logger log = LoggerFactory.getLogger(DefaultScraper.class);

    /**
     * Private constructor for singleton pattern.
     */
    private DefaultScraper() {

    }
    
    private static DefaultScraper instance = new DefaultScraper();
    
    public static DefaultScraper getInstance() {
        return instance;
    }
    
    
    /* --- Implementations --- */
    
    @Override
    public Page scrape(URL inUrl) {
        Verify.notNull(inUrl);

        // get raw content
        String rawContent = getRawContent(inUrl);
        
        // get metadata
        Map<String, Object> metadata = getMetadata(inUrl, rawContent);

        // create a new page
        DefaultPage page = new DefaultPage(inUrl, rawContent, metadata);
        
        return page;
    }

    @Override
    public Page scrape(URL inUrl, Pattern scrapeContentPattern) {

        Verify.notNull(inUrl);
        Verify.notNull(scrapeContentPattern);

        DefaultPage page = (DefaultPage) scrape(inUrl);
        String rawContent = page.getRawContent();
        Matcher matcher = scrapeContentPattern.matcher(rawContent);

        String scrapedContent = "";
        while (matcher.find()) {
            scrapedContent = scrapedContent + matcher.group(1);
        }
        
        try {
            page.setScrapedContent(scrapedContent);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return page;
    }


    /* --- Private methods --- */

    private String getRawContent(URL inUrl) {
        StringBuffer text = new StringBuffer();
        try {
            InputStreamReader in = new InputStreamReader(inUrl.openStream());
            BufferedReader buff = new BufferedReader(in);
            
            String line = buff.readLine();
            while (line != null) {
                text.append(line + "\n");
                line = buff.readLine();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return text.toString();
    }
    
    
    private Map<String, Object> getMetadata(URL inUrl, String rawContent) {
        Map<String, Object> metadata = new HashMap<>();
        
        metadata.put(Page.ARTICLE_URL, inUrl.toString());
        // TODO finish it.. i.e., add more info 

        return metadata;
    }

}
