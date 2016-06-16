package com.theironyard;

import java.util.ArrayList;

/**
 * Created by Ben on 6/16/16.
 */
public class Pizza {

    Integer id;
    String size;
    String crust;
    String sauce;
    int userId;
    ArrayList<Toppings> topping = new ArrayList<>();

    public Pizza(int userId) {
        this.userId = userId;
    }

    public Pizza() {
    }

    public Pizza(Integer id, String size, String crust, String sauce) {
        this.id = id;
        this.size = size;
        this.crust = crust;
        this.sauce = sauce;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
