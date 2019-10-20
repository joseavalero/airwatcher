package com.example.airwatcher.model.WAQIBoundCall;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumWAQI {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("uid")
    @Expose
    private Integer uid;
    @SerializedName("aqi")
    @Expose
    private String aqi;
    @SerializedName("station")
    @Expose
    private StationWAQI station;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public StationWAQI getStation() {
        return station;
    }

    public void setStation(StationWAQI station) {
        this.station = station;
    }

}
