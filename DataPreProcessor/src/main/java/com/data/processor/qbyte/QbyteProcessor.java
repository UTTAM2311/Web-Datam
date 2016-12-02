package com.data.processor.qbyte;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.data.processor.DefaultProcessingDetails;
import com.data.processor.Processor;

public class QbyteProcessor implements Processor<DefaultProcessingDetails> {

    @Override
    public void process(DefaultProcessingDetails processingDetails) {
        Document doc = Jsoup.parse(processingDetails.getRawContent());

    }

}
