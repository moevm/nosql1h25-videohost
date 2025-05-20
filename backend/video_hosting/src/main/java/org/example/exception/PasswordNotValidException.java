package org.example.exception;

public class PasswordNotValidException extends RuntimeException {

    public PasswordNotValidException(String message) {
        super(message);
    }
}
