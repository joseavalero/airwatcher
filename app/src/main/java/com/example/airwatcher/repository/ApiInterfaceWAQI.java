package com.example.airwatcher.repository;

import com.example.airwatcher.model.DataWAQI;
import com.example.airwatcher.model.Post;
import com.example.airwatcher.model.WAQIBoundCall.ResponseBoundWAQI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterfaceWAQI {
    //https://api.waqi.info/feed/here/?token=65cb6c9cf73c7e5dc3ff45358240656af6a3be3a
    //Por coordenadas geo:10.3;20.7
    @GET("/feed/geo:{latitud};{longitud}/")
    Call<DataWAQI> getPorCoordenadas(@Path("latitud")String latitud, @Path("longitud")String longitud, @Query("token") String token);

    //https://api.waqi.info/map/bounds/?latlng=39.379436,116.091230,40.235643,116.784382&token=65cb6c9cf73c7e5dc3ff45358240656af6a3be3a

    @GET ("/map/bounds/")
    Call<ResponseBoundWAQI> getAQIByLimitsBounds(@Query("latlng") String bounds, @Query("token") String token);

}

// https://api.waqi.info/bounds/?latlng=30.0,30.0,30.0,30.0&token=65cb6c9cf73c7e5dc3ff45358240656af6a3be3a
// https://api.waqi.info/map/bounds/?latlng=39.379436,116.091230,40.235643,116.784382&token=65cb6c9cf73c7e5dc3ff45358240656af6a3be3a