package com.example.warehouseplatform.api;

public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
}
