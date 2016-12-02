package com.data.processor;

public interface DataProcessor {

    /**
     * Processes the content present in the inputDir and write to outDir
     * 
     * @param inputDir
     * @param outDir
     */
    public void startProcess(String inputDir, String outDir);

    public void stopProcess();

}
