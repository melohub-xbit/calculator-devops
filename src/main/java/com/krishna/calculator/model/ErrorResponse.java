package com.krishna.calculator.model;

public record ErrorResponse(int status, String error, String message, String path) {
}
