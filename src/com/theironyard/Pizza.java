package com.theironyard;

/**
 * Created by Ben on 6/16/16.
 */
public class Pizza {

    Integer id;
    String size;
    String crust;
    String sauce;
    String topping;

    public Pizza() {
    }

    public Pizza(Integer id, String size, String crust, String sauce, String topping) {
        this.id = id;
        this.size = size;
        this.crust = crust;
        this.sauce = sauce;
        this.topping = topping;

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

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

}
