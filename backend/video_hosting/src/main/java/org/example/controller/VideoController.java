package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.VideoApi;
import org.example.controller.mapper.VideoMapper;
import org.example.dto.AddVideoDTO;
import org.example.dto.UpdateVideoDTO;
import org.example.dto.VideoDTO;
import org.example.exception.UserNotFoundException;
import org.example.service.UserAuthServiceImpl;
import org.example.service.api.S3Service;
import org.example.service.api.VideoService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
class VideoController implements VideoApi {

    private final VideoService videoService;
    private final VideoMapper videoMapper;
    private final S3Service s3Service;
    private final UserAuthServiceImpl userAuthService;

    @Override
    public ResponseEntity<String> uploadVideo(String userId, String title, String description, List<String> tags, MultipartFile file) throws Exception {
        AddVideoDTO addVideoDTO = new AddVideoDTO();
        addVideoDTO.setDescription(description);
        addVideoDTO.setTitle(title);
        addVideoDTO.setTags(tags);

        return new ResponseEntity<>(videoService.uploadVideo(userId, addVideoDTO, file), HttpStatus.OK);
    }

    @Override
    public Page<VideoDTO> getSubscriptionVideos(String userId, int page, int size) throws UserNotFoundException {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "uploadDate")
        );
        return videoService.getSubscriptionVideos(userId, pageable)
                .map(videoMapper::map);
    }

    @Override
    public ResponseEntity<Resource> downloadVideo(String fileName) {
        return s3Service.downloadVideo(fileName);
    }

    @Override
    public ResponseEntity<VideoDTO> toggleVideoVisibility(String videoId) throws UserNotFoundException {
        String userId = userAuthService.getUserId();
        return new ResponseEntity<>(videoMapper.map(videoService.toggleVideoVisibility(videoId, userId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VideoDTO> getVideo(String videoId) {
        return new ResponseEntity<>(videoMapper.map(videoService.getVideo(videoId)), HttpStatus.OK);
    }

    @Override
    public Page<VideoDTO> getAllVideos(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "uploadDate") // Сортировка по полю uploadDate
        );
        return videoService.getAllVideos(pageable)
                .map(videoMapper::map);
    }

    @Override
    public ResponseEntity<Void> deleteVideo(String videoId) throws UserNotFoundException {
        String userId = userAuthService.getUserId();
        videoService.delete(videoId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public List<VideoDTO> getUserVideos(String userId) throws UserNotFoundException {
        String authUserId;
        try {
            authUserId = userAuthService.getUserId();
        } catch (UsernameNotFoundException e) {
            authUserId = null;
        }
        return videoService.getUserVideos(authUserId, userId).stream()
                .map(videoMapper::map)
                .toList();
    }

    @Override
    public ResponseEntity<VideoDTO> updateVideo(String videoId, UpdateVideoDTO videoDTO) {
        return new ResponseEntity<>(videoMapper.map(videoService.update(videoId, videoDTO)), HttpStatus.OK);
    }
}
