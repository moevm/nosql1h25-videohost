package org.example.exception;

public class DataTransferExportException extends RuntimeException {

    public DataTransferExportException(String message) {
        super("Failed to export data: " + message);
    }
}
