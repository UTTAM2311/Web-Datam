package com.datam.web.core.service;

public interface Service {
    /**
     * returns the name of this service
     * 
     * @return
     */
    String getName();

    /**
     * returns the location of the extracted files
     * 
     * @return
     */
    String getInputDir();
    
    boolean isServiceRunning();

    void startService();
    
    void stopService();

}
