package com.techelevator;

import org.junit.Test;

import static org.junit.Assert.*;

public class GumTest {
    @Test
   public void testGumConstructor(){
        String item = "Trident";
        double price = 1.25;
        String expected = "Chew Chew, Yum!";
        Gum gum = new Gum(item,price);
        assertEquals(item, gum.getItem());
        assertEquals(price, gum.getPrice(),0.5);
        assertEquals(expected, gum.getSound());
    }

}