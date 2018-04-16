package com.example.kejin.iot_demo.data_class;

/**
 * Created by kejin on 28/03/2018.
 */

public class DataRecord {
    private String location;
    private int distance;
    private String amenity;
    private String duration;
    private String start_time;
    private String end_time;
    private int price;
    private int totoal_money;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    private String owner;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotoal_money() {
        return totoal_money;
    }

    public void setTotoal_money(int totoal_money) {
        this.totoal_money = totoal_money;
    }


    public DataRecord(String location, int distance, String amenity, String duration, String start_time, String end_time, int price, int totoal_money, String email) {
        this.location = location;
        this.distance = distance;
        this.amenity = amenity;
        this.duration = duration;
        this.start_time = start_time;
        this.end_time = end_time;
        this.price = price;
        this.totoal_money = totoal_money;
        this.owner =email;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }



}
