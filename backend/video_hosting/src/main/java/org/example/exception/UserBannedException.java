package org.example.exception;

public class UserBannedException extends RuntimeException {

    public UserBannedException() {
        super("The user is banned!");
    }
}
