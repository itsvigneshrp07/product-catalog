package com.example.catalog.web.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}
