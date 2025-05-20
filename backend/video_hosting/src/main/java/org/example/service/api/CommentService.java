package org.example.service.api;

import org.example.dto.AddCommentDTO;
import org.example.dto.UpdateCommentDTO;
import org.example.exception.UserNotFoundException;
import org.example.model.CommentData;

import java.util.List;

public interface CommentService {

    CommentData addComment(String userId, String videoId, AddCommentDTO addCommentDTO);

    void deleteComment(String userId, String commentId) throws UserNotFoundException;

    List<CommentData> getAllVideoComments(String videoId);

    CommentData update(String commentId, UpdateCommentDTO commentDTO);
}
