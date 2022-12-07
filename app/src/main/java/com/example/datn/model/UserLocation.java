package com.example.datn.model;

public class UserLocation {
    Double longitdute,latitude;

    public UserLocation(Double longitdute, Double latitude) {
        this.longitdute = longitdute;
        this.latitude = latitude;
    }

    public Double getLongitdute() {
        return longitdute;
    }

    public void setLongitdute(Double longitdute) {
        this.longitdute = longitdute;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
