package com.example.airwatcher.repository;

import android.util.Log;

import com.example.airwatcher.model.DataWAQI;
import com.example.airwatcher.model.WAQIBoundCall.ResponseBoundWAQI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class WAQIClient {
    private static final WAQIClient mInstance = new WAQIClient();
    private static ApiInterfaceWAQI mInterface;

    private static final String WAQIToken = "65cb6c9cf73c7e5dc3ff45358240656af6a3be3a";
    private static final String BASE_URL_WAQI  = "https://api.waqi.info/feed/";



    private int AQI = 0;
    private ResponseBoundWAQI listResponseBound;


    private WAQIClient() {

    }
    public static WAQIClient getInstance(){
        if (mInterface == null){ //if there is no instance available... create new one
            mInterface = getClientWAQI().create(ApiInterfaceWAQI.class);
        }

        return mInstance;
    }


    public static Retrofit getClientWAQI() {

        Retrofit retrofitWAQI = new Retrofit.Builder()
                    .baseUrl(BASE_URL_WAQI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofitWAQI;
    }

    public void getAQIByLocation(double latitude, double longitude, final OnResponseListener responseCallback) {

        Call<DataWAQI> mCall = mInterface.getPorCoordenadas(latitude + "", longitude + "", this.WAQIToken);

        mCall.enqueue(new Callback<DataWAQI>() {
            @Override
            public void onResponse(Call<DataWAQI> call, Response<DataWAQI> response) {

                int aqi = response.body().getData().getAqi();
                setAQI(aqi);
                responseCallback.responseReceived();
            }

            @Override
            public void onFailure(Call<DataWAQI> call, Throwable t) {
                Log.e(TAG, t.getMessage());

            }
        });


    }

    //https://api.waqi.info/map/bounds/?latlng=39.379436,116.091230,40.235643,116.784382&token=65cb6c9cf73c7e5dc3ff45358240656af6a3be3a
    public void getAQUIByBounds(double northWestBound, double southEastBound, double northEastBound, double southWestBound, final OnResponseListener mResponseListener) {

        String bounds = northWestBound +"," + southEastBound + "," + northEastBound + "," + southWestBound;
        Log.d("URLBounds", bounds);
        Call<ResponseBoundWAQI> mCall = mInterface.getAQIByLimitsBounds(bounds, WAQIToken);
        Log.d("URLBounds", mCall.request().url().toString());
        mCall.enqueue(new Callback<ResponseBoundWAQI>() {
            @Override
            public void onResponse(Call<ResponseBoundWAQI> call, Response<ResponseBoundWAQI> response) {
                listResponseBound = response.body();
                mResponseListener.responseReceived();
            }

            @Override
            public void onFailure(Call<ResponseBoundWAQI> call, Throwable t) {
            }
        });

    }




    public int getAQI() {
        return AQI;
    }

    public void setAQI(int AQI) {
        this.AQI = AQI;
    }

    public ResponseBoundWAQI getListResponseBound() {
        return listResponseBound;
    }

    public void setListResponseBound(ResponseBoundWAQI listResponseBound) {
        this.listResponseBound = listResponseBound;
    }
}
