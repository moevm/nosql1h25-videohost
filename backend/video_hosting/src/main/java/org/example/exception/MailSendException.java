package org.example.exception;

public class MailSendException extends RuntimeException {

    public MailSendException(String message, Exception e) {
        super(message, e);
    }
}
