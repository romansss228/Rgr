package com.example.demo.Classes;

public class Street {
    private int streetId;
    private String streetName;

    public Street() {}

    public Street(int streetId, String streetName) {
        this.streetId = streetId;
        this.streetName = streetName;
    }

    public int getStreetId() {
        return streetId;
    }

    public void setStreetId(int streetId) {
        this.streetId= streetId;
    }

    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }



}