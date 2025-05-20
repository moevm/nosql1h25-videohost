package org.example.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.example.config.MinioProperties;
import org.example.exception.VideoStorageException;
import org.example.service.api.S3Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
class S3ServiceImpl implements S3Service {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;


    @Override
    public String uploadFile(String fileName, MultipartFile file) {

        createBucket();

        if (file.isEmpty() || file.getOriginalFilename() == null) {
           throw new VideoStorageException("Video must have name");
        }

        saveVideo(file, fileName);


        return getFileUrl(fileName);
    }

    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            throw new VideoStorageException("Failed to delete video from storage", e);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadVideo(String fileName) {
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(fileName)
                            .build());

            Resource resource = new InputStreamResource(stream);

            String contentType = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(fileName)
                            .build()).contentType();

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new VideoStorageException("Failed to download video from storage", e);
        }
    }

    private void createBucket() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .build());
            }
        } catch (Exception e) {
            throw new VideoStorageException("Failed to create bucket", e);
        }
    }

    private void saveVideo(final MultipartFile file, final String fileName) {
        try {
            InputStream inputStream;
            try {
                inputStream = file.getInputStream();
            } catch (Exception e) {
                throw new RuntimeException();
            }

            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(minioProperties.getBucket())
                    .object(fileName)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            throw new VideoStorageException("Failed to save video in storage", e);
        }
    }

    private String getFileUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minioProperties.getBucket())
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            throw new VideoStorageException("Failed to get url from storage", e);
        }
    }
}
