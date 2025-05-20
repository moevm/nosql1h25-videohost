package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.UserApi;
import org.example.controller.mapper.UserMapper;
import org.example.dto.UpdateUserDTO;
import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.example.service.UserAuthServiceImpl;
import org.example.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
class UserController implements UserApi {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserAuthServiceImpl userAuthService;

    @Override
    public ResponseEntity<UserDTO> getProfile() throws UserNotFoundException {
        String userId = userAuthService.getUserId();
        return new ResponseEntity<>(userMapper.map(userService.getUserById(userId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(String userId) throws UserNotFoundException {
        return new ResponseEntity<>(userMapper.map(userService.getUserById(userId)), HttpStatus.OK);
    }

    @Override
    public List<UserDTO> getAllUser() {
        return userService.getAllUser()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(String userId, UpdateUserDTO updateUserDTO) throws UserNotFoundException {
        return new ResponseEntity<>(userMapper.map(userService.updateUser(userId, userMapper.map(updateUserDTO))), HttpStatus.OK);
    }
}
