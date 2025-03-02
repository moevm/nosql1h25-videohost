package org.example.service.api;

import org.example.exception.UserNotFoundException;
import org.example.model.UserData;

import java.util.List;

public interface UserApi {

    List<UserData> getAllUsers();

    UserData getUserById(String id) throws UserNotFoundException;

    void createUser(UserData user);

    UserData updateUser(String id, UserData userDetails);

    void deleteUser(String id);
}
