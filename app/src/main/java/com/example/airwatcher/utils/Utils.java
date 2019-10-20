package com.example.airwatcher.utils;

import java.util.regex.Pattern;

public class Utils {

    public static boolean validateZipCode(String zipCode) {
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);

        if (pattern.matcher(zipCode).matches()) {
            return true;
        }

        return false;
    }
}
