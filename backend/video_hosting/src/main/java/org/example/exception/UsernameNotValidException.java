package org.example.exception;

public class UsernameNotValidException extends RuntimeException {

    public UsernameNotValidException(String message) {
        super(message);
    }
}
