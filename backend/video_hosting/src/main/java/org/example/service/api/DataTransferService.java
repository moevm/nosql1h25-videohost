package org.example.service.api;

import java.io.IOException;

public interface DataTransferService {

    byte[] exportAllData(Format format) throws IOException;

    void importAllData(byte[] bytes, Format format) throws IOException;

    enum Format {
        JSON, XML
    }
}
