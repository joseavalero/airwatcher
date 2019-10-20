package com.example.airwatcher.repository;

import com.example.airwatcher.model.AirNow;
import com.example.airwatcher.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AirNowApiInterface {

    @GET("zipCode")
    Call<List<AirNow>> getAirNowByZipCode(@Query("format") String format, @Query("zipCode")String zipCode,
                                          @Query("date") String date,@Query("distance")String distance,
                                          @Query("API_KEY")String api_key);


}
