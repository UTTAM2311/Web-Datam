package com.datam.web.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datam.web.backend.DefaultContext;
import com.datam.web.core.service.Hindu.HinduFeedService;
import com.datam.web.core.service.Hindu.HinduService;
import com.datum.web.core.service.didYouKnow.DidYouKnowService;
import com.datum.web.core.service.factSlides.FactsSlidesService;

public class StandaloneContext extends DefaultContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneContext.class);
    private static final String contextName = "standaloneContext";
    private static StandaloneContext instance = new StandaloneContext();

    private StandaloneContext() {
        super(contextName);
        try {
            instance.addService(new HinduService());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        try {
            instance.addService(new HinduFeedService());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        try {
            instance.addService(new FactsSlidesService());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        try {
            instance.addService(new DidYouKnowService());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static StandaloneContext getInstance() {
        return instance;
    }

    public void start() {
        instance.startAllServices();
    }

    public void stop() {
        instance.stopAllServices();
    }


}
