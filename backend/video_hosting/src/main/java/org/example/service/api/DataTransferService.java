package org.example.service.api;

public interface DataTransferService {

    byte[] exportAllData(Format format);

    void importAllData(byte[] bytes, Format format);

    enum Format {
        JSON, XML
    }
}
