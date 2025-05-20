package org.example.exception;

public class VideoStorageException extends RuntimeException {

    public VideoStorageException(String message, Exception e) {
        super(message, e);
    }

    public VideoStorageException(String message) {
        super(message);
    }
}
