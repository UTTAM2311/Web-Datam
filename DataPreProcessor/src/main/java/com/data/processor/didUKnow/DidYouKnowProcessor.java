package com.data.processor.didUKnow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
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

public class DidYouKnowProcessor implements Processor<DefaultProcessingDetails> {
    private static final Logger log = LoggerFactory.getLogger(DidYouKnowProcessor.class);
    private static String didUKnow = "did you knows";
    private static String url = "http://www.did-you-knows.com/did-you-know-facts/";

    @Override
    public void process(DefaultProcessingDetails processingDetails) {
        try {
            Document doc = Jsoup.parse(processingDetails.getRawContent());
            String fileName = processingDetails.getFilePathToRead();

            MetaDataPojo pojo = PojoUtils.createPojo(fetchData(doc, fileName), MetaDataPojo.class);
            PojoUtils.writePojoToFile(pojo, processingDetails.getFilePathToWrite());
        } catch (IOException e) {
            log.error("Error in processing the file:{}  due to:{}", processingDetails.getFilePathToRead(),
                    e.getMessage());
        }
    }

    public Map<String, Object> fetchData(Document doc, String fileName) throws IOException {

        Map<String, Object> artProperties = new HashMap<>();
        List<String> tags = new ArrayList<>();
        List<String> cate = new ArrayList<>();
        List<String> content = new ArrayList<>();


        artProperties.put(Constants.TITLE, "Interesting-Fact");
        Elements titles = doc.select("title");
        for (Element title : titles) {
            String data = StringUtils.lowerCase(title.text());
            if (StringUtils.contains(data, didUKnow)) {
                String cat = StringUtils.remove(data, didUKnow);
                cate.add(cat);
                tags.add(cat);
            }
        }
        Elements lists = doc.select("ul");
        for (Element li : lists) {
            Elements dykLists = li.getElementsByAttributeValue("class", "dykList");
            for (Element lis : dykLists) {
                Elements facts = lis.getElementsByTag("li");
                for (Element fact : facts) {
                    Elements texts = fact.getElementsByAttributeValue("class", "dykText");
                    texts.forEach(text -> content.add(text.text()));
                }
            }
        }
        Elements divs = doc.getElementsByTag("div");
        String num = null;
        for (Element div : divs) {
            Elements elems = div.getElementsByAttributeValue("class", "pagePagintionLinks");
            for (Element ele : elems) {
                num = ele.getElementsByTag("span").first().text();
            }
        }

        String ext = FilenameUtils.getExtension(fileName);
        String name = FilenameUtils.removeExtension(FilenameUtils.getName(fileName));
        String type = name.replaceAll("[^A-Za-z]", "");
        if (StringUtils.isNotBlank(num) && StringUtils.isNoneBlank(type))
            artProperties.put(Constants.URL, url + type + "." + ext + "?page=" + num);

        artProperties.put(Constants.CATEGORY, cate);
        artProperties.put(Constants.TAGS, tags);
        artProperties.put(Constants.CONTENT, content);

        return artProperties;
    }

}
