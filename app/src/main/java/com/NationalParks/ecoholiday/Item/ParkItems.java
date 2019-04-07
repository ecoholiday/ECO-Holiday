package com.NationalParks.ecoholiday.Item;

public class ParkItems {
    String NationalParks,Area,Latitude,Longitude,Status;
    int Distance,NPID;

    public int getNPID() {
        return NPID;
    }

    public void setNPID(int NPID) {
        this.NPID = NPID;
    }

    public String getNationalParks() {
        return NationalParks;
    }

    public void setNationalParks(String nationalParks) {
        NationalParks = nationalParks;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        Distance = distance;
    }
}
