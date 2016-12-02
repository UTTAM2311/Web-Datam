package com.datam.web.core.service;

import com.tt.web.Site;
import com.tt.web.crawler.Crawler;
import com.tt.web.crawler.JsoupCrawler;
import com.tt.web.scraper.JsoupScraper;
import com.tt.web.scraper.Scraper;
import com.tt.web.service.ContentExtractor;

public abstract class AbstractService extends ContentExtractor implements Service {
    private String name;
    private String inputDir;
    private String outputDir;

    // by default uses Jsoup for crawling and scraping
    private static JsoupCrawler jsoupCrawler = JsoupCrawler.getInstance();
    private static JsoupScraper jsoupScraper = JsoupScraper.getInstance();

    /**
     * 
     * @param name
     * @param site
     * @param location
     */
    public AbstractService(String name, Site site, String inputDir, String outputDir) {
        this(name, site, jsoupCrawler, jsoupScraper, inputDir, outputDir);
    }

    /**
     * 
     * @param name
     * @param site
     * @param location
     * @param threadPoolSize
     */
    public AbstractService(String name, Site site, String inputDir, String outputDir, int threadPoolSize) {
        this(name, site, jsoupCrawler, jsoupScraper, inputDir, outputDir, threadPoolSize);
    }

    /**
     * 
     * @param name
     * @param crawl
     * @param scrap
     * @param site
     * @param location
     */
    public AbstractService(String name, Site site, Crawler crawl, Scraper scrap, String inputDir, String outputDir) {
        super(site, crawl, scrap);
        this.name = name;
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    /**
     * 
     * @param name
     * @param site
     * @param crawl
     * @param scrap
     * @param location
     * @param threadPoolSize
     */
    public AbstractService(String name, Site site, Crawler crawl, Scraper scrap, String inputDir, String outputDir,
            int threadPoolSize) {
        super(site, crawl, scrap, threadPoolSize);
        this.name = name;
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    /**
     * general implementation needed to be done if any
     */
    public abstract void implementer();

    /**
     * pre-processing steps needed to be done once if the files are downloaded into location
     * 
     * @param location
     */
    public abstract void preprocessor(String inDir, String outputDir);

    @Override
    public void startService() {
        extractTo(inputDir);
        implementer();
        preprocessor(inputDir, outputDir);
    }
    
    

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInputDir() {
        return inputDir;
    }

    public String getoutputDir() {
        return outputDir;
    }
}
