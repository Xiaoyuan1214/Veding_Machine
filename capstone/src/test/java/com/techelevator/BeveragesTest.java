package com.techelevator;

import org.junit.Test;

import static org.junit.Assert.*;

public class BeveragesTest {
    @Test
    public void testBeveragesConstructor(){
        String item = "Chi Forest";
        double price = 1.79;
        String expected = "Glug Glug, Yum!";
        Beverages beverages = new Beverages(item,price);
        assertEquals(item, beverages.getItem());
        assertEquals(price, beverages.getPrice(),0.001);
        assertEquals(expected, beverages.getSound());
    }

}