package com.example.airwatcher.model;

public class MarkerModel {

    private String zipCode;
    private double latitude;
    private double longitude;
    private int AQI;
    private int color;

    public int getAQI() {
        return AQI;
    }

    public void setAQI(int AQI) {
        this.AQI = AQI;
    }

    public MarkerModel(String zipCode, double latitude, double longitude, int AQI, int color) {
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.AQI = AQI;
        this.color = color;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
