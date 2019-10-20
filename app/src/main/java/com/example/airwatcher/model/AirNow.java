package com.example.airwatcher.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirNow {

    @SerializedName("DateIssue")
    @Expose
    private String dateIssue;
    @SerializedName("DateForecast")
    @Expose
    private String dateForecast;
    @SerializedName("ReportingArea")
    @Expose
    private String reportingArea;
    @SerializedName("StateCode")
    @Expose
    private String stateCode;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("ParameterName")
    @Expose
    private String parameterName;
    @SerializedName("AQI")
    @Expose
    private Integer aQI;
    @SerializedName("Category")
    @Expose
    private AirNowCategory category;
    @SerializedName("ActionDay")
    @Expose
    private Boolean actionDay;
    @SerializedName("Discussion")
    @Expose
    private String discussion;

    public String getDateIssue() {
        return dateIssue;
    }

    public void setDateIssue(String dateIssue) {
        this.dateIssue = dateIssue;
    }

    public String getDateForecast() {
        return dateForecast;
    }

    public void setDateForecast(String dateForecast) {
        this.dateForecast = dateForecast;
    }

    public String getReportingArea() {
        return reportingArea;
    }

    public void setReportingArea(String reportingArea) {
        this.reportingArea = reportingArea;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Integer getAQI() {
        return aQI;
    }

    public void setAQI(Integer aQI) {
        this.aQI = aQI;
    }

    public AirNowCategory getCategory() {
        return category;
    }

    public void setCategory(AirNowCategory category) {
        this.category = category;
    }

    public Boolean getActionDay() {
        return actionDay;
    }

    public void setActionDay(Boolean actionDay) {
        this.actionDay = actionDay;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

}


