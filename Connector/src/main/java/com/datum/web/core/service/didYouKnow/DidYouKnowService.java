package com.datum.web.core.service.didYouKnow;

import com.data.processor.didUKnow.DidYouKnowDataProcessor;
import com.datam.web.core.Constants;
import com.datam.web.core.service.AbstractService;

public class DidYouKnowService extends AbstractService {

    private DidYouKnowDataProcessor processor;
    private static boolean isRunning = false;

    private static final String siteName = "Did_You_Know";
    private static final String indir = Constants.inStore + "did_you_know";
    private static final String outdir = Constants.outStore + "did_you_know";
    private static final int threads = 4;
    private static DidYouKnowSite didYouKnowSite = new DidYouKnowSite();

    public DidYouKnowService() {
        super(siteName, didYouKnowSite, indir, outdir, threads);
        processor = new DidYouKnowDataProcessor();
        isRunning = true;
    }

    @Override
    public void implementer() {

    }

    @Override
    public void preprocessor(String inputDir, String outputDir) {
        processor.startProcess(inputDir, outputDir);
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
