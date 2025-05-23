package org.example.exception;

public class TakingAwayRightsYourselfException extends RuntimeException {

    public TakingAwayRightsYourselfException() {
        super("You can't deprive yourself of your rights\n");
    }
}
