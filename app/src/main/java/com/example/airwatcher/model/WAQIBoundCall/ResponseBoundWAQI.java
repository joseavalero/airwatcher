package com.example.airwatcher.model.WAQIBoundCall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBoundWAQI {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DatumWAQI> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DatumWAQI> getData() {
        return data;
    }

    public void setData(List<DatumWAQI> data) {
        this.data = data;
    }


}
