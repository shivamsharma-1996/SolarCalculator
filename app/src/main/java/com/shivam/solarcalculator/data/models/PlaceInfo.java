package com.shivam.solarcalculator.data.models;


/**
 * Created by Shivam Sharma on 3/1/2019.
 */

public class PlaceInfo{
    private int id;
    private double latitude;
    private double longitude;
    private String address;

    public PlaceInfo(int id, String address, double latitude, double longitude) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PlaceInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", tvAddress='" + address + '\'' +
                '}';
    }
}
