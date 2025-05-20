package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.DataTransferApi;
import org.example.service.api.DataTransferService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
class DataTransferController implements DataTransferApi {

    private final DataTransferService dataTransferService;

    @Override
    public ResponseEntity<Resource> exportData(
            DataTransferService.Format format) throws IOException {

        byte[] data = dataTransferService.exportAllData(format);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"export." + format.name().toLowerCase() + "\"")
                .contentType(MediaType.parseMediaType(getMediaType(format)))
                .contentLength(data.length)
                .body(resource);
    }

    @Override
    public ResponseEntity<String> importData(
            MultipartFile file,
            DataTransferService.Format format) throws IOException {

        dataTransferService.importAllData(file.getBytes(), format);
        return ResponseEntity.ok("Data imported successfully");
    }

    private String getMediaType(DataTransferService.Format format) {
        return switch (format) {
            case JSON -> "application/json";
            case XML -> "application/xml";
        };
    }
}
