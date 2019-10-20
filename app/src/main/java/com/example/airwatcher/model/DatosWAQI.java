package com.example.airwatcher.model;
import android.os.Debug;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosWAQI {

        @SerializedName("aqi")
        @Expose
        private Integer aqi;
        @SerializedName("idx")
        @Expose
        private Integer idx;
        @SerializedName("attributions")
        @Expose
        private List<AtributosWAQI> attributions = null;
        @SerializedName("city")
        @Expose
        private CityWAQI city;
        @SerializedName("dominentpol")
        @Expose
        private String dominentpol;
        @SerializedName("iaqi")
        @Expose
        private AqiWAQI iaqi;
        /*@SerializedName("time")
        @Expose
        private Time time;*/
        @SerializedName("debug")
        @Expose
        private Debug debug;

        public Integer getAqi() {
            return aqi;
        }

        public void setAqi(Integer aqi) {
            this.aqi = aqi;
        }

        public Integer getIdx() {
            return idx;
        }

        public void setIdx(Integer idx) {
            this.idx = idx;
        }

        public List<AtributosWAQI> getAttributions() {
            return attributions;
        }

        public void setAttributions(List<AtributosWAQI> attributions) {
            this.attributions = attributions;
        }

        public CityWAQI getCity() {
            return city;
        }

        public void setCity(CityWAQI city) {
            this.city = city;
        }

        public String getDominentpol() {
            return dominentpol;
        }

        public void setDominentpol(String dominentpol) {
            this.dominentpol = dominentpol;
        }

        public AqiWAQI getIaqi() {
            return iaqi;
        }

        public void setIaqi(AqiWAQI iaqi) {
            this.iaqi = iaqi;
        }


 /*       public Time getTime() {
            return time;
        }

        public void setTime(Time time) {
            this.time = time;
        }
*/
        public Debug getDebug() {
            return debug;
        }

        public void setDebug(Debug debug) {
            this.debug = debug;
        }

    }

