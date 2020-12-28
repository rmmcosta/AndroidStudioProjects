package com.example.droidcafe_withsettings;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Before
    public void setup() {
        MainActivity.initializeCart();
    }

    @Test
    public void addToCart() {
        assertTrue(MainActivity.getCartItems().isEmpty());
        MainActivity.addToCart("Donut");
        assertFalse(MainActivity.getCartItems().isEmpty());
    }

    @Test
    public void removeFromCart() {
    }

    @Test
    public void getCartItems() {
    }
}