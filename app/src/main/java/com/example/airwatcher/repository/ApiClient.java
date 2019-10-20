package com.example.airwatcher.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final String BASE_URL_WAQI  = "https://api.waqi.info/feed/";

    private static Retrofit retrofitWAQI = null;
    public static final String WAQIToken = "65cb6c9cf73c7e5dc3ff45358240656af6a3be3a";

    private static Retrofit retrofit = null;
    private static Retrofit retrofitAirAQ = null;

    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }

    public static Retrofit getClientWAQI() {

        if ( retrofitWAQI == null) {
            retrofitWAQI = new Retrofit.Builder()
                    .baseUrl(BASE_URL_WAQI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitWAQI;
    }

    public static Retrofit getClientAirAQ() {
        retrofitWAQI = new Retrofit.Builder()
                .baseUrl(BASE_URL_WAQI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitWAQI;
    }

}
