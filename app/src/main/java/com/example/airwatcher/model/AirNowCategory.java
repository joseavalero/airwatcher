package com.example.airwatcher.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirNowCategory {

    @SerializedName("Number")
    @Expose
    private Integer number;
    @SerializedName("Name")
    @Expose
    private String name;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

