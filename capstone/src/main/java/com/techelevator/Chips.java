package com.techelevator;

import java.math.BigDecimal;

public class Chips extends Inventory implements Sound{

    public Chips(String item, double price){
        super(item,price,"Crunch Crunch, Yum!");
    }


}
