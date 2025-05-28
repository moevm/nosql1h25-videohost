package org.example.controller.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.example.dto.UpdateVideoDTO;
import org.example.dto.VideoDTO;
import org.example.exception.UserNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(value = "/api/video", produces = MediaType.APPLICATION_JSON_VALUE)
public interface VideoApi {

    @PostMapping(value = "/upload/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadVideo(@PathVariable String userId, @RequestParam String title, @RequestParam String description,
                                       @RequestParam List<String> tags, @RequestPart("file") MultipartFile file) throws Exception;

    @GetMapping("/subscription/{userId}")
    Page<VideoDTO> getSubscriptionVideos(@PathVariable String userId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size)
            throws UserNotFoundException;

    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downloadVideo(@PathVariable String fileName);

    @PatchMapping("/visibility/{videoId}")
    ResponseEntity<VideoDTO> toggleVideoVisibility(@PathVariable String videoId) throws UserNotFoundException;

    @GetMapping("/{videoId}")
    ResponseEntity<VideoDTO> getVideo(@PathVariable String videoId);

    @GetMapping("/all")
    Page<VideoDTO> getAllVideos(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size);

    @DeleteMapping("/delete/{videoId}")
    ResponseEntity<Void> deleteVideo(@PathVariable String videoId) throws UserNotFoundException;

    @GetMapping("/user/{userId}")
    List<VideoDTO> getUserVideos(@PathVariable String userId) throws UserNotFoundException;

    @PatchMapping("/update/{videoId}")
    ResponseEntity<VideoDTO> updateVideo(@PathVariable String videoId, @Valid @RequestBody UpdateVideoDTO videoDTO);
}
