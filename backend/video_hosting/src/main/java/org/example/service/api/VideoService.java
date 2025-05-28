package org.example.service.api;

import org.example.dto.AddVideoDTO;
import org.example.dto.UpdateVideoDTO;
import org.example.exception.UserNotFoundException;
import org.example.model.VideoData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    String uploadVideo(String userId, AddVideoDTO addVideoDTO, MultipartFile file) throws UserNotFoundException;

    Page<VideoData> getAllVideos(Pageable pageable);

    VideoData getVideo(String videoId);

    Page<VideoData> getSubscriptionVideos(String userId, Pageable pageable) throws UserNotFoundException;

    VideoData toggleVideoVisibility(String videoId, String userId) throws UserNotFoundException;

    void delete(String videoId, String userId) throws UserNotFoundException;

    List<VideoData> getUserVideos(String authUserId, String userId) throws UserNotFoundException;

    VideoData update(String videoId, UpdateVideoDTO videoDTO);
}
