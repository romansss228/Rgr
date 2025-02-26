package com.example.demo.Classes;

public class House {
    private int houseId;
    private int houseNum;


    public House() {}

    public House(int houseId, int houseNum) {
        this.houseId = houseId;
        this.houseNum = houseNum;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId= houseId;
    }

    public int getHouseNum() {
        return houseNum;
    }
    public void setHouseNum(int houseNum) {
        this.houseNum = houseNum;
    }



}
