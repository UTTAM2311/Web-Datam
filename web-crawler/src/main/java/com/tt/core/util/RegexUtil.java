package com.tt.core.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Odd ball Regex utilities.
 */
public class RegexUtil {

    /**
     * Utility class
     */
    private RegexUtil() {}

    /**
     * Method which checks whether the input "stringToMatch" matches with any one of the pattern in
     * list of "patterns"
     * 
     * @param stringToMatch
     * @param patterns
     * @return boolean
     */

    public static boolean isMatched(String stringToMatch, List<Pattern> patterns) {
        // TODO refactor it to avoid loop
        for (Pattern pattern : patterns) {
            boolean isMatch = pattern.matcher(stringToMatch).matches();
            if (isMatch) {
                return isMatch;
            }
        }

        return false;
    }

}
