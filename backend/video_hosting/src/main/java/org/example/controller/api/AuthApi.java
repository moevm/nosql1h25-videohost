package org.example.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.ResetPasswordDTO;
import org.example.dto.request.AuthRequest;
import org.example.dto.response.AuthResponse;
import org.example.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AuthApi {

    @PostMapping("/register")
    ResponseEntity<Void> register(@RequestBody AuthRequest request);

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) throws UserNotFoundException;

    @PostMapping("/logout")
    ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response);

    @GetMapping("/accountVerification/{token}")
    ResponseEntity<Void> verifyAccount(@PathVariable String token);

    @PatchMapping("/reset")
    ResponseEntity<Void> resetPasswordUrl(@RequestParam String usernameOrEmail) throws UserNotFoundException;

    @PatchMapping("/resetPassword/{token}")
    ResponseEntity<Void> reset(@RequestParam String token, @RequestBody ResetPasswordDTO resetPasswordDTO);

    @GetMapping("/reset/{token}")
    ResponseEntity<Void> resetPage(@PathVariable String token);
}
