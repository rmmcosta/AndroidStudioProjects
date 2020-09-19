package com.example.simplecalculator;

public class Calculator {
    public double sum(double x, double y) {
        return x + y;
    }

    public double sub(double x, double y) {
        return x - y;
    }

    public double mult(double x, double y) {
        return x * y;
    }

    public double div(double x, double y) {
        if (y == 0) {
            throw new IllegalArgumentException("Divide by 0 is not allowed!");
        }
        return x / y;
    }

    public double pow(double x, double y) {
        return Math.pow(x,y);
    }

    public enum Operation {SUM, SUB, MULT, DIV, POW}
}
