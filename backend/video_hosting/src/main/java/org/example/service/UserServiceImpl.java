package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.TakingAwayRightsYourselfException;
import org.example.exception.UserBannedException;
import org.example.exception.UserNotFoundException;
import org.example.model.RoleEnum;
import org.example.model.UserData;
import org.example.repository.UserRepository;
import org.example.service.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAuthServiceImpl userAuthService;

    @Override
    public UserData getUserById(String userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public List<UserData> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateUserBlockStatus(String userId, boolean block) throws UserNotFoundException {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setBlocked(block);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void addRoleToUser(String userId, RoleEnum role) throws UserNotFoundException {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeRoleFromUser(String userId, RoleEnum role) throws UserNotFoundException {

        if (Objects.equals(userId, userAuthService.getUserId())) {
            throw new TakingAwayRightsYourselfException();
        }

        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserData updateUser(String userId, UserData userData) throws UserNotFoundException {
        UserData oldUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (oldUser.isBlocked()) {
            throw new UserBannedException();
        }

        userData.setId(oldUser.getId());
        assignNonEditableFields(userData, oldUser);

        return userRepository.save(userData);
    }

    private void assignNonEditableFields(UserData newData, UserData oldData) {
        newData.setEmail(oldData.getEmail());
        newData.setPassword(oldData.getPassword());
        newData.setSubscriberCount(oldData.getSubscriberCount());
        newData.setRegistrationDate(oldData.getRegistrationDate());
        newData.setBlocked(oldData.isBlocked());
    }
}
