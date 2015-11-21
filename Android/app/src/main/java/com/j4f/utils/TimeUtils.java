package com.j4f.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by TuanTQ on 11/21/15.
 */
public class TimeUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM, yyyy");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public static String formatDate(Calendar c) {
        return dateFormat.format(c.getTime());
    }

    public static String formatTime(Calendar c) {
        return timeFormat.format(c.getTime());
    }
}
