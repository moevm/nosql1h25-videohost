package org.example.controller.api;

import org.example.dto.AddReactionDTO;

import org.example.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/reaction", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ReactionApi {

    @PostMapping("/add/{videoId}")
    ResponseEntity<Void> addReaction(@PathVariable String videoId, @RequestBody AddReactionDTO reactionDTO) throws UserNotFoundException;
}
