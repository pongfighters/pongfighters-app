package com.pongfighters.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by nmarsollier on 11/25/16.
 */

public class DateUtils {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    public static java.util.Date parseDate(String date) throws ParseException {
        return formatter.parse(date);
    }

    public static String formatDate(java.util.Date date) {
        return formatter.format(date);
    }
}
