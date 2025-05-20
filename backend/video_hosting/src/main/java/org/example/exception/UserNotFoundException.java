package org.example.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String id) {
        super("User with id " +  id + " was not found");
    }
}
