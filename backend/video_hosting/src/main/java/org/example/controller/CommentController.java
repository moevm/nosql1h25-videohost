package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.CommentApi;
import org.example.controller.mapper.CommentMapper;
import org.example.dto.AddCommentDTO;
import org.example.dto.CommentDTO;
import org.example.dto.UpdateCommentDTO;
import org.example.exception.UserNotFoundException;
import org.example.service.UserAuthServiceImpl;
import org.example.service.api.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequiredArgsConstructor
class CommentController implements CommentApi {

    private final CommentService commentService;
    private final UserAuthServiceImpl userAuthService;
    private final CommentMapper commentMapper;

    @Override
    public ResponseEntity<Void> addComment(String videoId, AddCommentDTO addCommentDTO) {
        commentService.addComment(userAuthService.getUserId(), videoId, addCommentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteComment(String commentId) throws UserNotFoundException {
        String userId = userAuthService.getUserId();
        commentService.deleteComment(userId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDTO> updateVideo(String commentId, UpdateCommentDTO commentDTO) {
        return new ResponseEntity<>(commentMapper.map(commentService.update(commentId, commentDTO)), HttpStatus.OK);
    }
}
