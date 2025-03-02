package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.ControllerApi;
import org.example.exception.UserNotFoundException;
import org.example.model.UserData;
import org.example.service.api.UserApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class Controller implements ControllerApi {

    private final UserApi service;

    @Override
    public List<UserData> getAllUsers() {
        return service.getAllUsers();
    }

    @Override
    public UserData getUserById(String id) throws UserNotFoundException {
        return service.getUserById(id);
    }

    @Override
    public void createUser(UserData user) {
        service.createUser(user);
    }

    @Override
    public UserData updateUser(String id, UserData userDetails) {
        return service.updateUser(id, userDetails);
    }

    @Override
    public void deleteUser(String id) {
        service.deleteUser(id);
    }
}
