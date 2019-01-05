package com.shivam.solarcalculator.data.models;

import com.google.android.gms.maps.model.LatLng;

public class Address {
    public static final String TABLE_NAME = "addresses";
 
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "tvAddress";
    public static final String COLUMN_LATLONG = "latlong";

    private int id;
    private String address;
    private String latlong;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_LATLONG + " TEXT"
                    + ")";
 
    public Address() {
    }

    public Address(int id, String address, String  latLng) {
        this.id = id;
        this.address = address;
        this.latlong = latLng;
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

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public LatLng getRealLatlong() {
        String[] temp = this.latlong.replace("lat/lng: ", "").
                replace("(","").replace(")","").split(",");
        double lati = Double.parseDouble(temp[0]);
        double longi = Double.parseDouble(temp[1]);
        return new LatLng(lati,longi);
    }



}