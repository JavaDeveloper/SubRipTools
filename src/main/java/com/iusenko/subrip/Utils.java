package com.iusenko.subrip;

/**
 *
 * @author iusenko
 */
public class Utils {

    public static boolean isBlank(String text) {
        return text == null || "".equals(text.trim());
    }
}
