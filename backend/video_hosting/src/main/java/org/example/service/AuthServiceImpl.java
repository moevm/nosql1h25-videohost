package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.config.JwtService;
import org.example.exception.*;
import org.example.model.RoleEnum;
import org.example.model.UserData;
import org.example.model.VerificationTokenData;
import org.example.repository.UserRepository;
import org.example.dto.request.AuthRequest;
import org.example.dto.response.AuthResponse;
import org.example.repository.VerificationTokenRepository;
import org.example.service.api.AuthService;
import org.example.service.api.MailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse registration(AuthRequest request) {
        if (Objects.equals(request.getUsername(), "") || Objects.equals(request.getPassword(), "")) {
            throw new UsernameNotValidException("Username or password is blank");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent() || userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }

        if (request.getPassword().length() < 6) {
            throw new PasswordNotValidException("Password must be at least 6 characters");
        }

        UserData user = new UserData();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.getRoles().add(RoleEnum.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String verificationToken = generateVerificationToken(user);
        mailService.sendMail(user.getEmail(),
                "Please Activate your Account",
                 "Thank you for signing up, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + verificationToken);

        String jwtToken = jwtService.generateToken(user);

        log.info("User - {} has successfully registered ", user.getUsername());

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    @Transactional
    public AuthResponse login(AuthRequest request) throws UserNotFoundException {
        if (Objects.equals(request.getUsername(), "") || Objects.equals(request.getPassword(), "")) {
            throw new IllegalArgumentException("Username or password is blank");
        }

        UserData user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (user.isBlocked()) {
            throw new UserBannedException();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        log.info("User - {} successfully logged in", user.getUsername());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public void verifyAccount(String token) {
        VerificationTokenData verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(VerificationTokenNotFoundException::new);
        fetchUserAndEnable(verificationToken);
    }

    private void fetchUserAndEnable(VerificationTokenData verificationToken) {
        String userId = verificationToken.getUser().getId();
        UserData user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
        user.setBlocked(false);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
    }

    private String generateVerificationToken(UserData user) {
        String token = UUID.randomUUID().toString();
        VerificationTokenData verificationToken = new VerificationTokenData();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
