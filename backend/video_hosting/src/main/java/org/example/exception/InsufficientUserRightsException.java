package org.example.exception;

public class InsufficientUserRightsException extends RuntimeException {

    public InsufficientUserRightsException(String message) {
        super(message);
    }
}
