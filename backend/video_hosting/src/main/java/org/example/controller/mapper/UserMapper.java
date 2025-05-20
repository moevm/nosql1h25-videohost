package org.example.controller.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.dto.UpdateUserDTO;
import org.example.dto.UserDTO;
import org.example.model.UserData;
import org.example.service.UserAuthServiceImpl;
import org.example.service.api.SubscriptionService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final SubscriptionService subscriptionService;
    private final UserAuthServiceImpl userAuthService;

    @SneakyThrows
    public UserDTO map(UserData userData) {
        String userAuthId;
        try {
            userAuthId = userAuthService.getUserId();
        } catch (UsernameNotFoundException e) {
            userAuthId = null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(userData.getId());
        userDTO.setBlocked(userData.isBlocked());
        userDTO.setEmail(userData.getEmail());
        userDTO.setUsername(userData.getUsername());
        userDTO.setRegistrationDate(userData.getRegistrationDate());
        userDTO.setSubscriberCount(userData.getSubscriberCount());
        userDTO.setRoles(userData.getRoles());
        userDTO.setUserSubscribe(userAuthId != null && subscriptionService.isSubscribed(userAuthId, userDTO.getUserId()));

        return userDTO;
    }

    public UserData map(UpdateUserDTO updateUserDTO) {
        UserData userData = new UserData();
        userData.setUsername(updateUserDTO.getUsername());
        return userData;
    }
}
