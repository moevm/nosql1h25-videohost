package org.example.controller.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.example.dto.UpdateUserDTO;
import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserApi {

    @GetMapping("/profile")
    ResponseEntity<UserDTO> getProfile() throws UserNotFoundException;

    @GetMapping("/{userId}")
    ResponseEntity<UserDTO> getUserById(@PathVariable String userId) throws UserNotFoundException;

    @GetMapping(value = "/all")
    Page<UserDTO> getAllUser(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size);

    @PatchMapping("/update/{userId}")
    ResponseEntity<UserDTO> updateUser(@PathVariable String userId, @RequestBody UpdateUserDTO updateUserDTO) throws UserNotFoundException;

}
