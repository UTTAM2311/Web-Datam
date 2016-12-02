package com.tt.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class WebReader {
    /**
     * get the Content data present in the URL. Max size that returned String can withhold is upto
     * 12MB.
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public static String getData(String url) throws Exception {
        StringBuffer text = new StringBuffer();

        URL page = new URL(url);
        InputStreamReader in = new InputStreamReader(page.openStream());

        BufferedReader buff = new BufferedReader(in);
        String line = buff.readLine();

        while (line != null) {
            text.append(line + "\n");
            line = buff.readLine();
        }
        return text.toString();
    }
}
