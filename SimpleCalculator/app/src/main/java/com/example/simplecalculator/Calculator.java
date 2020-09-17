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
        return x / y;
    }

    public enum Operation {SUM, SUB, MULT, DIV}
}
