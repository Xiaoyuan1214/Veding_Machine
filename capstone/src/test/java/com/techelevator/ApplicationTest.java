package com.techelevator;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationTest {
    @Test
    public void test_changes(){
        Application app = new Application();
        assertEquals("Quarters: 74 Dimes: 0 Nickels: 0 Pennies: 0", app.changes(18.5));
        assertEquals("Quarters: 3 Dimes: 2 Nickels: 0 Pennies: 4", app.changes(0.99));
    }

}