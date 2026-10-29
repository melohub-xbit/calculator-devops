package com.krishna.calculator.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorServiceTest {

    private final CalculatorService service = new CalculatorService();

    @Test
    void add() {
        assertEquals(5, service.add(2, 3));
        assertEquals(-1, service.add(2, -3));
        assertEquals(0.3, service.add(0.1, 0.2), 1e-9);
    }

    @Test
    void subtract() {
        assertEquals(-1, service.subtract(2, 3));
        assertEquals(10, service.subtract(5, -5));
    }

    @Test
    void multiply() {
        assertEquals(6, service.multiply(2, 3));
        assertEquals(-8, service.multiply(-2, 4));
        assertEquals(0, service.multiply(7, 0));
    }

    @Test
    void divide() {
        assertEquals(2, service.divide(6, 3));
        assertEquals(2.5, service.divide(5, 2));
        assertEquals(-3, service.divide(9, -3));
    }

    @Test
    void divideByZeroThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.divide(5, 0));
        assertEquals("Division by zero is not allowed", ex.getMessage());
    }

    @Test
    void overflowThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.multiply(Double.MAX_VALUE, 10));
    }
}
