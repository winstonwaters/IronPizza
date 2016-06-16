package com.theironyard;

/**
 * Created by Ben on 6/16/16.
 */
public class Toppings {
    Integer id;
    String topping;

    public Toppings() {
    }

    public Toppings(Integer id, String topping) {
        this.id = id;
        this.topping = topping;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }
}
