package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddCommentDTO;
import org.example.dto.UpdateCommentDTO;
import org.example.exception.*;
import org.example.model.CommentData;
import org.example.model.RoleEnum;
import org.example.model.UserData;
import org.example.model.VideoData;
import org.example.repository.CommentRepository;
import org.example.repository.UserRepository;
import org.example.repository.VideoRepository;
import org.example.service.api.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Override
    @Transactional
    public CommentData addComment(String userId, String videoId, AddCommentDTO addCommentDTO) {
        UserData userData = userRepository.findById(userId)
                .orElseThrow();

        if (userData.isBlocked()) {
            throw new UserBannedException();
        }

        VideoData video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));

        CommentData commentData = CommentData.builder()
                .text(addCommentDTO.getText())
                .user(userData)
                .video(video)
                .build();

        return commentRepository.save(commentData);
    }

    @Override
    @Transactional
    public void deleteComment(String userId, String commentId) throws UserNotFoundException {
        UserData userData = userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(userId));

        CommentData commentData = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (userData.getRoles().contains(RoleEnum.ADMIN) || Objects.equals(commentData.getUser().getId(), userId)) {
            commentRepository.delete(commentData);
        } else {
            throw new InsufficientUserRightsException("User doesn't have enough rights");
        }
    }

    @Override
    public List<CommentData> getAllVideoComments(String videoId) {
        return commentRepository.findByVideo_Id(videoId);
    }

    @Override
    @Transactional
    public CommentData update(String commentId, UpdateCommentDTO commentDTO) {
        CommentData comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (commentDTO.getText() != null) {
            comment.setText(commentDTO.getText());
        }

        return commentRepository.save(comment);
    }
}
