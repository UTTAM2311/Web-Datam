package com.datam.web.core.service.Hindu;

import com.data.processor.hindu.HinduDataProcessor;
import com.datam.web.core.Constants;
import com.datam.web.core.service.AbstractService;

public class HinduService extends AbstractService {

    private HinduDataProcessor processor;
    private static boolean isRunning = false;

    private static final String siteName = "Hindu";
    private static final String indir = Constants.inStore + "hindu";
    private static final String outdir = Constants.outStore + "hindu";
    private static final int threads = 4;
    private static HinduSite hinduSite = new HinduSite();

    public HinduService() {
        super(siteName, hinduSite, indir, outdir, threads);
        isRunning = true;
        processor = new HinduDataProcessor();
    }

    @Override
    public void implementer() {

    }

    @Override
    public void preprocessor(String indir, String outDir) {
        processor.startProcess(indir, outDir);
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
