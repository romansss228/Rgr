package com.example.demo.Classes;

public class Apartment {
    private int apartmentId;
    private int apartmentNum;


    public Apartment() {}

    public Apartment(int apartmentId, int apartmentNum) {
        this.apartmentId = apartmentId;
        this.apartmentNum = apartmentNum;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId= apartmentId;
    }

    public int getApartmentNum() {
        return apartmentNum;
    }
    public void setApartmentNum(int apartmentNum) {
        this.apartmentNum = apartmentNum;
    }



}
