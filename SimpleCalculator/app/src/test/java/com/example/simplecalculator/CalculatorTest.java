package com.example.simplecalculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    public void sum() {
        assertEquals(5.6, calculator.sum(2.5, 3.1), 0.1);
    }

    @Test
    public void sub() {
        assertEquals(1.3, calculator.sub(2.7, 1.4), 0.1);
    }

    @Test
    public void mult() {
        assertEquals(12.0, calculator.mult(3.0, 4.0), 0.1);
    }

    @Test
    public void div() {
        assertEquals(5.0, calculator.div(15.0, 3.0), 0.1);
    }

    /*@Test
    public void divByZero() {
        assertEquals(Double.POSITIVE_INFINITY,calculator.div(5,0),0.01);
    }*/

    @Test(expected = IllegalArgumentException.class)
    public void divByZeroThrows() {
        calculator.div(5, 0);
    }

    @Test
    public void powWith2PositiveIntegers() {
        assertEquals(8, calculator.pow(2, 3), 0.01);
    }

    @Test
    public void powWith1stNegative() {
        assertEquals(-27, calculator.pow(-3, 3), 0.1);
    }

    @Test
    public void powWith2ndNegative() {
        assertEquals(0.00032, calculator.pow(5, -5), 0.00001);
    }

    @Test
    public void powWith0And2ndPositive() {
        assertEquals(0, calculator.pow(0, 5), 0.1);
    }

    @Test
    public void powWith0And1stPositive() {
        assertEquals(1, calculator.pow(5, 0), 0.1);
    }

    @Test
    public void powWith0AndNegativeOne() {
        assertEquals(Double.POSITIVE_INFINITY, calculator.pow(0, -1), 0.1);
    }

    @Test
    public void powWithNegative0AndNegative() {
        assertEquals(Double.POSITIVE_INFINITY, calculator.pow(-0, -5), 0.1);
    }
}