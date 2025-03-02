package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.model.UserData;
import org.example.repository.UserRepository;
import org.example.service.api.UserApi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserService implements UserApi {

    private final UserRepository repository;

    @Override
    public List<UserData> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public UserData getUserById(String id) throws UserNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void createUser(UserData user) {
        repository.save(user);
    }

    public UserData updateUser(String id, UserData userDetails) {
        userDetails.setId(id);
        return repository.save(userDetails);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }
}
