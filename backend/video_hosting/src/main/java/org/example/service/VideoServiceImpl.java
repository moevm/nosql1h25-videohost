package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddVideoDTO;
import org.example.dto.UpdateVideoDTO;
import org.example.exception.UserBannedException;
import org.example.exception.UserNotFoundException;
import org.example.exception.VideoNotFoundException;
import org.example.model.RoleEnum;
import org.example.model.SubscriptionData;
import org.example.model.UserData;
import org.example.model.VideoData;
import org.example.repository.SubscriptionRepository;
import org.example.repository.UserRepository;
import org.example.repository.VideoRepository;
import org.example.service.api.S3Service;
import org.example.service.api.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class VideoServiceImpl implements VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public String uploadVideo(String userId, AddVideoDTO addVideoDTO, MultipartFile file) throws UserNotFoundException {

        UserData userData = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (userData.isBlocked()) {
            throw new UserBannedException();
        }

        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        String url = s3Service.uploadFile(fileName, file);

        VideoData data = VideoData.builder()
                .user(userData)
                .s3Key(cleanS3UrlWithRegex(url))
                .title(addVideoDTO.getTitle())
                .description(addVideoDTO.getDescription())
                .tags(addVideoDTO.getTags())
                .filename(fileName)
                .build();

        videoRepository.save(data);
        log.info("Video added successfully");

        return data.getS3Key();
    }

    private String cleanS3UrlWithRegex(String originalUrl) {
        if (originalUrl == null || originalUrl.isEmpty()) {
            return originalUrl;
        }

        return originalUrl
                .replaceFirst("://s3:", "://localhost:")
                .replaceAll("\\?.*$", "");
    }

    @Transactional
    @Override
    public void delete(String videoId, String userId) throws UserNotFoundException {
        VideoData video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));

        if (!video.getUser().getId().equals(userId)) {
            UserData currentUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            if (!currentUser.getRoles().contains(RoleEnum.ADMIN)) {
                throw new AccessDeniedException("You don't have permission to delete this video");
            }
        }

        s3Service.deleteFile(video.getS3Key());

        videoRepository.delete(video);

        log.info("Video {} deleted by user {}", videoId, userId);
    }

    @Override
    public Page<VideoData> getAllVideos(Pageable pageable) {
        return videoRepository.findByIsVideoHiddenFalse(pageable);
    }

    @Override
    @Transactional
    public VideoData getVideo(String videoId) {
        return videoRepository.findById(videoId)
                .map(this::incrementViews)
                .orElseThrow(() -> new VideoNotFoundException(videoId));
    }

    @Override
    @Transactional
    public Page<VideoData> getSubscriptionVideos(String userId, Pageable pageable) throws UserNotFoundException {
        UserData userData = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<SubscriptionData> subscriptions = subscriptionRepository.findByFollower_Id(userData.getId());

        List<UserData> followings = subscriptions.stream()
                .map(SubscriptionData::getFollowing)
                .toList();

        if (followings.isEmpty()) {
            return Page.empty();
        }

        return videoRepository.findByUserInAndIsVideoHiddenFalse(followings, pageable);
    }

    @Override
    @Transactional
    public VideoData toggleVideoVisibility(String videoId, String userId) throws UserNotFoundException {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        VideoData video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));

        if (userId.equals(video.getUser().getId()) || user.getRoles().contains(RoleEnum.ADMIN)) {
            video.setVideoHidden(!video.isVideoHidden());
        }

        return videoRepository.save(video);
    }

    private VideoData incrementViews(VideoData videoData) {
        videoData.setViews(videoData.getViews() + 1);

        return videoRepository.save(videoData);
    }

    @Override
    @Transactional
    public List<VideoData> getUserVideos(String authUserId, String userId) throws UserNotFoundException {
        UserData userData = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (authUserId == null) {
            videoRepository.findByUser(userData);
        }

        return Objects.equals(authUserId, userId) ?
                videoRepository.findByUser(userData)
                :
                videoRepository.findByUserAndIsVideoHiddenFalse(userData);
    }

    @Override
    @Transactional
    public VideoData update(String videoId, UpdateVideoDTO updateVideo) {

        VideoData video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));

        if (updateVideo.getTitle() != null) {
            video.setTitle(updateVideo.getTitle());
        }

        if (updateVideo.getDescription() != null) {
            video.setDescription(updateVideo.getDescription());
        }

        if (updateVideo.getTags() != null) {
            video.setTags(updateVideo.getTags());
        }

        return videoRepository.save(video);
    }
}
