package org.example.service.api;

import org.example.exception.UserNotFoundException;
import org.example.model.RoleEnum;
import org.example.model.UserData;

import java.util.List;

public interface UserService {

    UserData getUserById(String userId) throws UserNotFoundException;

    List<UserData> getAllUser();

    void updateUserBlockStatus(String userId, boolean block) throws UserNotFoundException;

    UserData updateUser(String userId, UserData userData) throws UserNotFoundException;

    void addRoleToUser(String userId, RoleEnum role) throws UserNotFoundException;

    void removeRoleFromUser(String userId, RoleEnum role) throws UserNotFoundException;
}
