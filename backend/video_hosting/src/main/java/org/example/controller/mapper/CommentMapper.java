package org.example.controller.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.CommentDTO;
import org.example.model.CommentData;
import org.example.service.UserAuthServiceImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserAuthServiceImpl userAuthService;

    public CommentDTO map(CommentData commentData) {
        String userAuthId;
        try {
            userAuthId = userAuthService.getUserId();
        } catch (UsernameNotFoundException e) {
            userAuthId = null;
        }
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(commentData.getId());
        commentDTO.setText(commentData.getText());
        commentDTO.setCreationDate(commentData.getCreationDate());
        commentDTO.setUserId(commentData.getUser().getId());
        commentDTO.setUserComment(userAuthId != null && Objects.equals(userAuthService.getUserId(), commentData.getUser().getId()));
        commentDTO.setAuthor(commentData.getUser().getUsername());
        return commentDTO;
    }
}
