package org.example.controller.api;

import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/subscription", produces = MediaType.APPLICATION_JSON_VALUE)
public interface SubscriptionApi {

    @PostMapping("/subscribe/{followingId}")
    ResponseEntity<Void> subscribe(@PathVariable String followingId) throws UserNotFoundException;

    @PostMapping("/unsubscribe/{followingId}")
    ResponseEntity<Void> unsubscribe(@PathVariable String followingId) throws UserNotFoundException;

    @GetMapping("/followings/{userId}")
    List<UserDTO> getUserFollowings(@PathVariable String userId) throws UserNotFoundException;

    @GetMapping("/followers/{userId}")
    List<UserDTO> getUserFollowers(@PathVariable String userId) throws UserNotFoundException;
}
