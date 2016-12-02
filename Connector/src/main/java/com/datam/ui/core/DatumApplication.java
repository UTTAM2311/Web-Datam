package com.datam.ui.core;

import com.datam.ui.routes.ServiceRoutes;

import spark.servlet.SparkApplication;

public class DatumApplication implements SparkApplication {

    @Override
    public void init() {
        ServiceRoutes.getInstance().createRoutes();
    }

}
