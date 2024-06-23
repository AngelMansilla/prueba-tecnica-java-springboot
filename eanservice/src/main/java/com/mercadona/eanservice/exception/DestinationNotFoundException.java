package com.mercadona.eanservice.exception;

public class DestinationNotFoundException extends RuntimeException {
    public DestinationNotFoundException(String message) {
        super(message);
    }
}
