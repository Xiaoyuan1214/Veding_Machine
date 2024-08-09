package com.techelevator;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChipsTest {
    @Test
    public void testChipsConstructor(){
        String item = "Lay's";
        double price = 2.5;
        String expected = "Crunch Crunch, Yum!";
        Chips chip = new Chips(item,price);
        assertEquals(item, chip.getItem());
        assertEquals(price, chip.getPrice(),0.5);
        assertEquals(expected, chip.getSound());
    }

}