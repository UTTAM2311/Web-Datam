package com.data.processor;

/**
 * A class to store all the details required for processing the data
 * 
 * @author manojk
 *
 */
public interface ProcessingDetails {

    public String getFilePathToRead();

    public String getFilePathToWrite();

    public Object getExtractedContent();

    public void setExtractedContent(Object extrcatedContent);

    public String getRawContent();

    public void setRawContent(String rawContent);

}
