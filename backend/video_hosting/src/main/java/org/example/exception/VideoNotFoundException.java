package org.example.exception;

public class VideoNotFoundException extends RuntimeException {

    public VideoNotFoundException(String id) {
        super("Video with id " +  id + " was not found");
    }
}
