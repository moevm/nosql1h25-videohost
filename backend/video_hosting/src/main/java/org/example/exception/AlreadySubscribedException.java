package org.example.exception;

public class AlreadySubscribedException extends RuntimeException {

    public AlreadySubscribedException() {
        super("User has already subscribed");
    }
}
