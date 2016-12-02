package com.data.processor.factsSlides;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.data.processor.DefaultProcessingDetails;
import com.data.processor.Processor;
import com.data.processor.core.MetaDataPojo;
import com.data.processor.util.Constants;
import com.data.processor.util.JsoupContentExtracter;
import com.data.processor.util.PojoUtils;
import com.google.common.collect.Lists;

public class FactSlidesProcessor implements Processor<DefaultProcessingDetails> {
    private static final Logger log = LoggerFactory.getLogger(FactSlidesProcessor.class);

    private final String tag = "script";
    private final String emptyContent = "";
    private final String postProcessingRegex = "(\\'.*\\')";
    private final String preProcessingRegex = "(var\\sslideTexts\\s=\\snew\\sArray.*\\'\\);)";

    @Override
    public void process(DefaultProcessingDetails processingDetails) {
        Document doc = Jsoup.parse(processingDetails.getRawContent());
        try {
            List<String> extractedContentList = JsoupContentExtracter.getExtractedContent(doc, preProcessingRegex, tag);
            if (!extractedContentList.isEmpty()) {
                String extractedContent = extractedContentList.get(0);
                String requiredContent =
                        JsoupContentExtracter.getMatchingPattern(extractedContent, postProcessingRegex);
                String[] factsArray = StringUtils.split(requiredContent.replace("','", "/////"), "////");
                Map<String, Object> filteredMetadata = getMetaData(doc, factsArray);
                MetaDataPojo pojo = PojoUtils.createPojo(filteredMetadata, MetaDataPojo.class);
                PojoUtils.writePojoToFile(pojo, processingDetails.getFilePathToWrite());
            } else {
                log.warn("There is no extracted content for file : {}", processingDetails.getFilePathToRead());
                processingDetails.setExtractedContent(emptyContent);
            }
        } catch (Exception e) {
            // TODO: collect all the files which are not able to be processed
            log.error("Exception due to :" + e.getMessage());
        }
    }

    private Map<String, Object> getMetaData(Document doc, String[] factsArray) {
        Map<String, Object> metaData = JsoupContentExtracter.getMetadata(doc);
        Map<String, Object> filteredMetadata = new HashMap<>();
        String url = (String) metaData.get("og:url");
        String cat = StringUtils.split(url, "/")[2];
        filteredMetadata.put(Constants.URL, url);
        filteredMetadata.put(Constants.TITLE, metaData.get("og:title"));
        filteredMetadata.put(Constants.DESCRIPTION, metaData.get("og:description"));
        filteredMetadata.put(Constants.CONTENT, Lists.newArrayList(factsArray));
        filteredMetadata.put(Constants.CATEGORY, Lists.newArrayList(cat));
        filteredMetadata.put(Constants.TAGS, Lists.newArrayList(cat));
        return filteredMetadata;
    }

}
