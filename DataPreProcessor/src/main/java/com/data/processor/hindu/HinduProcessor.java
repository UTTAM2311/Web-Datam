package com.data.processor.hindu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.data.processor.DefaultProcessingDetails;
import com.data.processor.Processor;
import com.data.processor.core.MetaDataPojo;
import com.data.processor.util.Constants;
import com.data.processor.util.PojoUtils;

public class HinduProcessor implements Processor<DefaultProcessingDetails> {
    private static final Logger log = LoggerFactory.getLogger(HinduProcessor.class);

    private static String tag = "meta";

    @Override
    public void process(DefaultProcessingDetails processingDetails) {
        try {
            Document doc = Jsoup.parse(processingDetails.getRawContent());
            Map<String, Object> prop = fetchMetaData(doc);
            MetaDataPojo pojo = PojoUtils.createPojo(prop, MetaDataPojo.class);
            PojoUtils.writePojoToFile(pojo, processingDetails.getFilePathToWrite());
        } catch (IOException e) {
            log.error("Error in processing the file:{}  due to:{}", processingDetails.getFilePathToRead(),
                    e.getMessage());
        }

    }

    private static Map<String, Object> fetchMetaData(Document doc) {
        Map<String, Object> artProperties = new HashMap<>();
        Elements ogTags = doc.select(tag);

        List<String> tags = new ArrayList<>();

        for (int i = 0; i < ogTags.size(); i++) {
            Element tag = ogTags.get(i);
            String key = tag.attr("property");
            if (StringUtils.isBlank(key)) {
                key = tag.attr("name");
            }
            String value = tag.attr("content");

            if (StringUtils.contains(key, StringUtils.lowerCase("published_time"))) {
                artProperties.put(Constants.Created_Date_and_Time, value);
            } else if (StringUtils.contains(key, StringUtils.lowerCase("modified_time"))) {
                artProperties.put(Constants.Modified_Date_and_Time, value);
            } else if (StringUtils.contains(key, StringUtils.lowerCase(HinduConstants.SECTION))) {
                artProperties.put(HinduConstants.SECTION, value);
            } else if (StringUtils.contains(key, StringUtils.lowerCase(HinduConstants.TAG))) {
                tags.add(value);
            } else if (StringUtils.contains(key, StringUtils.lowerCase("og:url"))) {
                artProperties.put(Constants.URL, value);
            } else if (StringUtils.contains(key, StringUtils.lowerCase("og:type"))) {
                artProperties.put(HinduConstants.TYPE, value);
            } else if (StringUtils.contains(key, StringUtils.lowerCase("og:title"))) {
                artProperties.put(Constants.TITLE, value);
            } else if (StringUtils.equals(key, StringUtils.lowerCase(Constants.DESCRIPTION))) {
                artProperties.put(Constants.DESCRIPTION, value);
                artProperties.put(Constants.CONTENT, Arrays.asList(value));
            } else if (StringUtils.equals(key, StringUtils.lowerCase(Constants.AUTHOR))) {
                artProperties.put(Constants.AUTHOR, Arrays.asList(value));
            }
        }
        artProperties.put(Constants.TAGS, tags);
        artProperties.put(Constants.CATEGORY, tags);
        return artProperties;
    }
}
