package org.example.service.api;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface S3Service {

    String uploadFile(String fileName, MultipartFile file);

    void deleteFile(String fileName);

    ResponseEntity<Resource> downloadVideo(String fileName);

}
