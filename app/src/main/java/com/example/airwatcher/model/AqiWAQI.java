package com.example.airwatcher.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AqiWAQI {

    @SerializedName("h")
    @Expose
    private atributeAQI h;
    @SerializedName("no2")
    @Expose
    private atributeAQI no2;
    @SerializedName("o3")
    @Expose
    private atributeAQI o3;
    @SerializedName("pm10")
    @Expose
    private atributeAQI pm10;
    @SerializedName("pm25")
    @Expose
    private atributeAQI pm25;
    @SerializedName("so2")
    @Expose
    private atributeAQI so2;


    public atributeAQI getH() {
        return h;
    }

    public void setH(atributeAQI h) {
        this.h = h;
    }

    public atributeAQI getNo2() {
        return no2;
    }

    public void setNo2(atributeAQI no2) {
        this.no2 = no2;
    }

    public atributeAQI getO3() {
        return o3;
    }

    public void setO3(atributeAQI o3) {
        this.o3 = o3;
    }

    public atributeAQI getPm10() {
        return pm10;
    }

    public void setPm10(atributeAQI pm10) {
        this.pm10 = pm10;
    }

    public atributeAQI getPm25() {
        return pm25;
    }

    public void setPm25(atributeAQI pm25) {
        this.pm25 = pm25;
    }

    public atributeAQI getSo2() {
        return so2;
    }

    public void setSo2(atributeAQI so2) {
        this.so2 = so2;
    }



}


class atributeAQI{

    @SerializedName("v")
    @Expose
    private Double v;

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }

}

