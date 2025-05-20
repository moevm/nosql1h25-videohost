package org.example.controller.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.dto.VideoDTO;
import org.example.model.ReactionEnum;
import org.example.model.VideoData;
import org.example.service.api.CommentService;
import org.example.service.api.ReactionService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMapper {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final ReactionService reactionService;

    @SneakyThrows
    public VideoDTO map(VideoData videoData) {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setVideoId(videoData.getId());
        videoDTO.setDescription(videoData.getDescription());
        videoDTO.setTags(videoData.getTags());
        videoDTO.setTitle(videoData.getTitle());
        videoDTO.setVideoHidden(videoData.isVideoHidden());
        videoDTO.setUploadDate(videoData.getUploadDate());
        videoDTO.setViews(videoData.getViews());
        videoDTO.setUserId(videoData.getUser().getId());
        videoDTO.setS3Key(videoData.getS3Key());
        videoDTO.setAuthor(videoData.getUser().getUsername());
        videoDTO.setFileName(videoData.getFilename());

        ReactionEnum reactionEnum;
        try {
            reactionEnum = reactionService.isUserSetReaction(videoData.getId());
        } catch (UsernameNotFoundException e) {
            reactionEnum = null;
        }
        videoDTO.setUserReaction(reactionEnum);

        videoDTO.setComments(commentService.getAllVideoComments(videoData.getId())
                .stream()
                .map(commentMapper::map)
                .toList()
        );
        videoDTO.setReactions(reactionService.getNumberVideoReactions(videoData.getId()));
        return videoDTO;
    }
}
