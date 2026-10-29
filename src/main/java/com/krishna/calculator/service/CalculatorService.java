package com.krishna.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double add(double a, double b) {
        return check(a + b);
    }

    public double subtract(double a, double b) {
        return check(a - b);
    }

    public double multiply(double a, double b) {
        return check(a * b);
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed");
        }
        return check(a / b);
    }

    // big numbers can overflow to Infinity
    private double check(double result) {
        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException("Result is too large");
        }
        return result;
    }
}
