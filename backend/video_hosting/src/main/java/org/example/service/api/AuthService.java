package org.example.service.api;

import org.example.dto.request.AuthRequest;
import org.example.dto.response.AuthResponse;
import org.example.exception.UserNotFoundException;

public interface AuthService {

    AuthResponse registration(AuthRequest request);

    AuthResponse login(AuthRequest request) throws UserNotFoundException;

    void verifyAccount(String token);
}
