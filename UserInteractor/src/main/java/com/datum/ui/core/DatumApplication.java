package com.datum.ui.core;

import com.datum.ui.core.routes.DatumRoute;

import spark.servlet.SparkApplication;

public class DatumApplication implements SparkApplication {

    @Override
    public void init() {
        DatumRoute.getInstance().createRoutes();
    }

}
