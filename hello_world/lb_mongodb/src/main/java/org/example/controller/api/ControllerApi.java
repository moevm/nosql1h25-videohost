package org.example.controller.api;

import org.example.exception.UserNotFoundException;
import org.example.model.UserData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
public interface ControllerApi {

    @GetMapping
    List<UserData> getAllUsers();

    @GetMapping("/{id}")
    UserData getUserById(@PathVariable String id) throws UserNotFoundException;

    @PostMapping("/add")
    void createUser(@RequestBody UserData user);

    @PutMapping("/{id}")
    UserData updateUser(@PathVariable String id, @RequestBody UserData userDetails);

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable String id);
}
