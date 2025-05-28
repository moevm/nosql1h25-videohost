package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.controller.api.AuthApi;
import org.example.dto.ResetPasswordDTO;
import org.example.dto.request.AuthRequest;
import org.example.dto.response.AuthResponse;
import org.example.exception.UserNotFoundException;
import org.example.service.UserAuthServiceImpl;
import org.example.service.api.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@CrossOrigin
@RestController
@RequiredArgsConstructor
class AuthController implements AuthApi {

    private final AuthService authService;
    private final UserAuthServiceImpl userAuthService;

    @Override
    public ResponseEntity<Void> register(AuthRequest request) {
        authService.registration(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest request) throws UserNotFoundException {
        AuthResponse authResponse = authService.login(request);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok("Logout initiated");
    }

    @Override
    public ResponseEntity<Void> verifyAccount(String token) {
        authService.verifyAccount(token);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:5173/successRegister/" + token))
                .build();
    }

    @Override
    public ResponseEntity<Void> resetPasswordUrl(String usernameOrEmail) throws UserNotFoundException {
        authService.resetPasswordUrl(usernameOrEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> reset(String token, ResetPasswordDTO resetPasswordDTO) {
        authService.resetPassword(token, resetPasswordDTO.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> resetPage(String token) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:5173/NewPassword/" + token))
                .build();
    }
}
