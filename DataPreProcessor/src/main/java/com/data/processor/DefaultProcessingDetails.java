package com.data.processor;

import com.data.processor.util.Verify;

public class DefaultProcessingDetails implements ProcessingDetails {

    private final String filePathToLoad;
    private final String filePathToWrite;
    private String extContent;
    private String rContent;

    public DefaultProcessingDetails(String filePathToLoad, String filePathToWrite) {
        this.filePathToLoad = filePathToLoad;
        this.filePathToWrite = filePathToWrite;
    }

    @Override
    public String getFilePathToRead() {
        return filePathToLoad;
    }

    @Override
    public String getFilePathToWrite() {
        return filePathToWrite;
    }

    @Override
    public Object getExtractedContent() {
        return this.extContent;
    }

    @Override
    public void setExtractedContent(Object extrcatedContent) {
        Verify.isNull(extContent);
        Verify.notNull(extrcatedContent);
        this.extContent = (String) extrcatedContent;

    }

    @Override
    public String getRawContent() {
        return this.rContent;
    }

    @Override
    public void setRawContent(String rawContent) {
        Verify.isNull(rContent);
        Verify.notNull(rawContent);
        this.rContent = rawContent;
    }

}
