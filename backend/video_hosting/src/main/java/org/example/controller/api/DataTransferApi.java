package org.example.controller.api;

import org.example.service.api.DataTransferService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping(value = "/api/transfer")
public interface DataTransferApi {

    @GetMapping("/export")
    ResponseEntity<Resource> exportData(
            @RequestParam(defaultValue = "JSON") DataTransferService.Format format) throws IOException;

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> importData(
            @RequestParam("file") MultipartFile file,
            @RequestParam DataTransferService.Format format) throws IOException;
}
