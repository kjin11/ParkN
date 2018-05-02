package com.example.kejin.iot_demo;

/**
 * This is used for storing the (hard coded) info about the item we're selling.
 * Integrated by WenTing Lee 03/29/2018
 */
public class ItemInfo {
    private final String name;
    private final int imageResourceId;

    // Micros are used for prices to avoid rounding errors when converting between currencies.
    private final long priceMicros;

    public ItemInfo(String name, long price, int imageResourceId) {
        this.name = name;
        this.priceMicros = price;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public long getPriceMicros() {
        return priceMicros;
    }



}
