package com.example.airwatcher.utils;

import android.util.Log;

import com.example.airwatcher.model.AirNow;
import com.example.airwatcher.repository.AirNowApiClient;
import com.example.airwatcher.repository.AirNowApiInterface;
import com.example.airwatcher.repository.OnResponseListener;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirNowUtils {

    private static AirNowUtils airNowUtilsInstance;

    private String api_key = "BDB85406-77F8-4DD2-B3CA-6F4B588DE109";
    private String format = "application/json";
    private String distance = "25";
    private Integer AQI = 0;
    HashMap<String, Integer> zipCodesInfo = new HashMap<String,Integer>();

    private AirNowApiInterface airNowApiInterface;
    private static final String TAG = "AirNowUtils";

    private AirNowUtils(){}  //private constructor.

    public static AirNowUtils getInstance(){
        if (airNowUtilsInstance == null){ //if there is no instance available... create new one
            airNowUtilsInstance = new AirNowUtils();
            airNowUtilsInstance.setAirNowApiInterface(AirNowApiClient.getClient().create(AirNowApiInterface.class));
        }

        return airNowUtilsInstance;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getFormat() {
        return format;
    }

    public String getDistance() {
        return distance;
    }

    /*AirNow Api information by zip code*/
    public void getAirNowByZipCode(final String zipCode, final String date, final OnResponseListener callback) {
        String format = AirNowUtils.getInstance().getFormat();
        String distance = AirNowUtils.getInstance().getDistance();
        String api_key = AirNowUtils.getInstance().getApi_key();

        Call<List<AirNow>> call = airNowApiInterface.getAirNowByZipCode(format,zipCode,date,distance,api_key);
        call.enqueue(new Callback<List<AirNow>>() {
            @Override
            public void onResponse(Call<List<AirNow>> call, Response<List<AirNow>> response) {
                List<AirNow> airNows = response.body();
                if (airNows != null && airNows.size()>0) {
                    Log.d(TAG, airNows.toString());
                    Log.d(TAG, "Date: " + airNows.get(0).getDateIssue());
                    Log.d(TAG, "AQI: " + airNows.get(0).getAQI().toString());
                    //AirNowUtils.getInstance().setAQI(airNows.get(0).getAQI());
                    //Map management
                    zipCodesInfo.put(zipCode,airNows.get(0).getAQI());

                    callback.responseReceived();

                }
            }

            @Override
            public void onFailure(Call<List<AirNow>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public Integer getAQI() {
        return AQI;
    }

    public void setAQI(Integer AQI) {
        this.AQI = AQI;
    }

    public AirNowApiInterface getAirNowApiInterface() {
        return airNowApiInterface;
    }

    public void setAirNowApiInterface(AirNowApiInterface airNowApiInterface) {
        this.airNowApiInterface = airNowApiInterface;
    }

    public HashMap<String, Integer> getZipCodesInfo() {
        return zipCodesInfo;
    }
}
