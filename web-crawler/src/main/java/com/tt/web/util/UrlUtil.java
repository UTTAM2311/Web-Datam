package com.tt.web.util;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;

import com.tt.core.util.Verify;

/**
 * Bunch of Web URL utilities.
 */
public class UrlUtil {

    private static final String[] URL_SCHEMES = {"http", "https"};
    private static final UrlValidator URL_VALIDATOR = new UrlValidator(URL_SCHEMES);

    /**
     * Utility class
     */
    private UrlUtil() {}
    
    /**
     * Validate if given URL is a valid URL or not.
     * 
     * @param url URL to validate.
     * @return true if the URL is valid.
     */
    public static boolean isValidURL(String url) {
        return URL_VALIDATOR.isValid(url);
    }


    /**
     * Match a URL against a URL pattern.
     * 
     * @param urlPattern pattern to match against.
     * @param url URL to be matched
     * @return true if the URL matches the pattern
     */
    public static boolean isMatchingUrl(Pattern urlPattern, URL url) {
        // sanity checks
        Verify.notNull(url);

        return isMatchingUrl(urlPattern, url.toString());
    }

    /**
     * Match a URL string against a URL pattern.
     * 
     * @param urlPattern regex pattern to match against.
     * @param url URL to be matched
     * @return true if the URL matches the pattern
     */
    private static boolean isMatchingUrl(Pattern urlPattern, String urlStr) {
        // sanity checks
        Verify.notNull(urlPattern);
        Verify.hasText(urlStr);

        Matcher matcher = urlPattern.matcher(urlStr);

        return matcher.matches();
    }

}
