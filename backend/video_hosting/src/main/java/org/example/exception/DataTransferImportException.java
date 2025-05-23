package org.example.exception;

public class DataTransferImportException extends RuntimeException {

    public DataTransferImportException(String message) {
        super("Failed to import data: " + message);
    }
}
