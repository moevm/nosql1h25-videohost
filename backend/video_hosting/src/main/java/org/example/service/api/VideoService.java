package org.example.service.api;

import org.example.dto.AddVideoDTO;
import org.example.dto.UpdateVideoDTO;
import org.example.exception.UserNotFoundException;
import org.example.model.VideoData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    String uploadVideo(String userId, AddVideoDTO addVideoDTO, MultipartFile file) throws UserNotFoundException;

    List<VideoData> getAllVideos();

    VideoData getVideo(String videoId);

    List<VideoData> getSubscriptionVideos(String userId) throws UserNotFoundException;

    VideoData toggleVideoVisibility(String videoId, String userId) throws UserNotFoundException;

    void delete(String videoId, String userId) throws UserNotFoundException;

    List<VideoData> getUserVideos(String authUserId, String userId) throws UserNotFoundException;

    VideoData update(String videoId, UpdateVideoDTO videoDTO);
}
