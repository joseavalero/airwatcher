package com.example.airwatcher.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String dateFormat = "yyyy-MM-dd";

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
