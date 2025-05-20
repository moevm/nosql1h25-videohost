package org.example.controller.api;

import org.example.exception.UserNotFoundException;
import org.example.model.RoleEnum;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AdminApi {

    @PatchMapping("/{userId}/block")
    ResponseEntity<Void> toggleUserBlockStatus(@PathVariable String userId, @RequestParam boolean block) throws UserNotFoundException;

    @PatchMapping("/add/{userId}")
    ResponseEntity<Void> addRoleToUser(@PathVariable String userId, @RequestParam RoleEnum role) throws UserNotFoundException;

    @PatchMapping("/remove/{userId}")
    ResponseEntity<Void> removeRoleFromUser(@PathVariable String userId, @RequestParam RoleEnum role) throws UserNotFoundException;

    @PatchMapping("/hidden/{videoId}")
    ResponseEntity<Void> hiddenVideo(@PathVariable String videoId) throws UserNotFoundException;
}
