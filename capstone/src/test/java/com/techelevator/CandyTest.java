package com.techelevator;

import org.junit.Test;

import static org.junit.Assert.*;

public class CandyTest {
    @Test
    public void testCandyConstructor(){
        String item = "Hi Chew";
        double price = 1.75;
        String expected = "Munch Munch, Yum!";
        Candy candy = new Candy(item,price);
        assertEquals(item, candy.getItem());
        assertEquals(price, candy.getPrice(),0.5);
        assertEquals(expected, candy.getSound());
    }

}