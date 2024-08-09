package com.techelevator;

import java.math.BigDecimal;

//Inventory item = new Inventory(...)
public class Inventory{
    private String item;
    private double price;
    private int quantity = 5;
    private String sound;


    public void setItem(String item) {
        this.item = item;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public Inventory(String item, double price, String sound){
        this.item = item;
        this.price = price;
        this.quantity=5;
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }

    public String getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
