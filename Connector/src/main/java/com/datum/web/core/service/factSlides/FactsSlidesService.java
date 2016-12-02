package com.datum.web.core.service.factSlides;

import com.data.processor.factsSlides.FactSlidesDataProcessor;
import com.datam.web.core.Constants;
import com.datam.web.core.service.AbstractService;

public class FactsSlidesService extends AbstractService {

    private FactSlidesDataProcessor processor;
    private static boolean isRunning = false;

    private static final String siteName = "FactSlides";
    private static final String indir = Constants.inStore + "factSlides";
    private static final String outdir = Constants.outStore + "factSlides";
    private static final int threads = 4;
    private static FactsSlidesSite factsSlidesSite = new FactsSlidesSite();


    public FactsSlidesService() {
        super(siteName, factsSlidesSite, indir, outdir, threads);
        processor = new FactSlidesDataProcessor();
        isRunning = true;
    }


    @Override
    public void implementer() {

    }

    @Override
    public void preprocessor(String inDir, String outDir) {
        processor.startProcess(inDir, outDir);
    }

    @Override
    public void stopService() {
        // to stop this service we have stop all the sub-services like crawling, scraping,
        // pre-processing...

        // Stops the web-crawlling and scrapping service
        stopWebCrawlingService();

        // Stops the pre-processing service
        processor.stopProcess();
        isRunning = false;

    }


    @Override
    public boolean isServiceRunning() {
        return isRunning;
    }

}
