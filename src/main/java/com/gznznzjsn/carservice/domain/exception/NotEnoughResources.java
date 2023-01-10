package com.gznznzjsn.carservice.domain.exception;

public class NotEnoughResources extends RuntimeException {
    public NotEnoughResources(String message) {
        super(message);
    }
}
