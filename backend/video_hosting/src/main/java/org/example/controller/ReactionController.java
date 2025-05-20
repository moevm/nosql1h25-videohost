package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.ReactionApi;
import org.example.dto.AddReactionDTO;
import org.example.exception.UserNotFoundException;
import org.example.service.UserAuthServiceImpl;
import org.example.service.api.ReactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
class ReactionController implements ReactionApi {

    private final ReactionService reactionService;
    private final UserAuthServiceImpl userAuthService;

    @Override
    public ResponseEntity<Void> addReaction(String videoId, AddReactionDTO reactionDTO) throws UserNotFoundException {
        String userId = userAuthService.getUserId();
        reactionService.addReaction(userId, videoId, reactionDTO.getType());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
