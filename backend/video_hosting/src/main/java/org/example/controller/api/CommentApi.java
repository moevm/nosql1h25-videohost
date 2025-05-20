package org.example.controller.api;

import jakarta.validation.Valid;
import org.example.dto.AddCommentDTO;
import org.example.dto.CommentDTO;
import org.example.dto.UpdateCommentDTO;
import org.example.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public interface CommentApi {

    @PostMapping("/add/{videoId}")
    ResponseEntity<Void> addComment(@PathVariable String videoId, @RequestBody AddCommentDTO addCommentDTO);

    @DeleteMapping("/delete/{commentId}")
    ResponseEntity<Void> deleteComment(@PathVariable String commentId) throws UserNotFoundException;

    @PatchMapping("/update/{commentId}")
    ResponseEntity<CommentDTO> updateVideo(@PathVariable String commentId, @Valid @RequestBody UpdateCommentDTO commentDTO);
}
