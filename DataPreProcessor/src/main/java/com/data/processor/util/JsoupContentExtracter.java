package com.data.processor.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupContentExtracter {
    public static List<String> getExtractedContent(Document doc, String regexPattern, String tag) {
        Verify.notNull(doc, regexPattern, tag);
        List<String> extractedContentList = new ArrayList<>();
        Pattern pat = Pattern.compile(regexPattern);
        Elements scriptTags = doc.getElementsByTag(tag);
        for (Element ele : scriptTags) {
            String extractedString = ele.toString();
            Matcher matcher = pat.matcher(extractedString);
            if (matcher.find()) {
                extractedContentList.add(matcher.group(0));
            }
        }
        return Collections.unmodifiableList(extractedContentList);
    }

    public static String getMatchingPattern(String content, String pattern) {
        String matchingContent = null;
        Pattern pat = Pattern.compile(pattern);
        Matcher matcher = pat.matcher(content);
        if (matcher.find()) {
            matchingContent = matcher.group(0);
        }
        return matchingContent;
    }

    public static Map<String, Object> getMetadata(Document document) {
        Map<String, Object> metadata = new HashMap<>();

        // metadata with fields containing only property

        for (Element metaElem : document.select("meta")) {
            String name = metaElem.attr("property");
            String content = metaElem.attr("content");
            metadata.put(name, content);
        }
        return metadata;
    }


}
