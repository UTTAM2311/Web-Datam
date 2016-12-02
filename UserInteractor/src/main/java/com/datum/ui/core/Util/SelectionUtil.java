package com.datum.ui.core.Util;

import java.util.Random;

public class SelectionUtil {

    private static Random randomFun = new Random();

    public static String randomSelection(String[] topics) {
        int index = randomFun.nextInt(topics.length);
        return topics[index];
    }

}
