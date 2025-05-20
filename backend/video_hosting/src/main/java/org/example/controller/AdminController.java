package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.AdminApi;
import org.example.exception.UserNotFoundException;
import org.example.model.RoleEnum;
import org.example.service.UserAuthServiceImpl;
import org.example.service.api.UserService;
import org.example.service.api.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
class AdminController implements AdminApi {

    private final UserService userService;
    private final VideoService videoService;
    private final UserAuthServiceImpl userAuthService;

    @Override
    public ResponseEntity<Void> addRoleToUser(String userId, RoleEnum role) throws UserNotFoundException {
        userService.addRoleToUser(userId, RoleEnum.ADMIN);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removeRoleFromUser(String userId, RoleEnum role) throws UserNotFoundException {
        userService.removeRoleFromUser(userId, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> toggleUserBlockStatus(String userId, boolean block) throws UserNotFoundException {
        userService.updateUserBlockStatus(userId, block);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> hiddenVideo(String videoId) throws UserNotFoundException {
        String userId = userAuthService.getUserId();
        videoService.toggleVideoVisibility(videoId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
