package com.example.gymrat_backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s ikke fundet med %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
