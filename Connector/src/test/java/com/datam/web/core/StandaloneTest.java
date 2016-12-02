package com.datam.web.core;


import com.datam.web.backend.BackgroundContext;
import com.datam.web.backend.DefaultBackGroundContext;
import com.datam.web.core.service.Hindu.HinduService;
import com.datum.web.core.service.factSlides.FactsSlidesService;

public class StandaloneTest {

    public static void main(String[] args) {

        BackgroundContext context = new DefaultBackGroundContext();
        context.addService(new HinduService());
        context.addService(new FactsSlidesService());
        context.startAllServices();
        context.stopAllServices();
    }

}
