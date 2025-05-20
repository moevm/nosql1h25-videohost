package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.SubscriptionApi;
import org.example.controller.mapper.UserMapper;
import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.example.service.UserAuthServiceImpl;
import org.example.service.api.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
class SubscriptionController implements SubscriptionApi {

    private final SubscriptionService subscriptionService;
    private final UserMapper userMapper;
    private final UserAuthServiceImpl userAuthService;

    @Override
    public ResponseEntity<Void> subscribe(String followingId) throws UserNotFoundException {
        String followerId = userAuthService.getUserId();
        subscriptionService.subscribe(followerId, followingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> unsubscribe(String followingId) throws UserNotFoundException {
        String followerId = userAuthService.getUserId();
        subscriptionService.unsubscribe(followerId, followingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public List<UserDTO> getUserFollowings(String userId) throws UserNotFoundException {
        return subscriptionService.getUserFollowings(userId)
                .stream()
                .map(userMapper::map)
                .toList();
    }

    @Override
    public List<UserDTO> getUserFollowers(String userId) throws UserNotFoundException {
        return subscriptionService.getUserFollowers(userId)
                .stream()
                .map(userMapper::map)
                .toList();
    }
}
