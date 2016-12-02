package com.tt.web.util;

import java.util.Properties;

import com.tt.core.util.PropertyUtil;

public class ProjectProps {

    public static final String FILE_NAME = "project.properties";

    private static final String PROP_CE_THREAD_POOL_SIZE = "ce.thread_pool.size";

    private static final Properties PROPS;
    static {
        PROPS = PropertyUtil.load(FILE_NAME);
    }

    /**
     * Utility Class
     */
    private ProjectProps() {}

    

    /* --- Property Access --- */

    public static int getThreadpoolSize() {
        String size = PROPS.getProperty(PROP_CE_THREAD_POOL_SIZE, "1");
        return Integer.parseInt(size);
    }



}
