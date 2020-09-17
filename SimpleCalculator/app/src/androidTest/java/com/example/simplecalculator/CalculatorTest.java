package com.example.simplecalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    public void sum() {
        assertEquals(5.6, calculator.sum(2.5,3.1),0.1);
    }

    @Test
    public void sub() {
        assertEquals(1.3, calculator.sub(2.7,1.4),0.1);
    }

    @Test
    public void mult() {
        assertEquals(12.0, calculator.mult(3.0,4.0),0.1);
    }

    @Test
    public void div() {
        assertEquals(5.0, calculator.div(15.0,3.0),0.1);
    }
}