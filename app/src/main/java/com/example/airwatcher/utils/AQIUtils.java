package com.example.airwatcher.utils;


import android.graphics.Color;

public class AQIUtils {

    private static AQIUtils aqiUtilsInstance;

    public static AQIUtils getInstance(){
        if (aqiUtilsInstance == null){ //if there is no instance available... create new one
            aqiUtilsInstance = new AQIUtils();
        }
        return aqiUtilsInstance;
    }

    public int getColor(Integer aqi){

       if(aqi<50){
           return Color.parseColor("#44009966");
       }else if ( aqi > 51 && aqi <= 100){
           return Color.parseColor("#44ffde33");
       } else if ( aqi > 101 && aqi <= 150) {
           return Color.parseColor("#44ff9933");
       } else if ( aqi > 151 && aqi <= 200) {
           return Color.parseColor( "#44cc0033");
       } else if ( aqi > 201 && aqi <= 300) {
           return Color.parseColor("#44660099");
       } else {
           return Color.parseColor("#447e0023");
       }
    }
}
