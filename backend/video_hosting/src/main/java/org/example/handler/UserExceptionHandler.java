package org.example.handler;

import jakarta.validation.ValidationException;
import org.example.dto.response.ErrorResponse;
import org.example.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    ResponseEntity<ErrorResponse> userAlreadyExistsHandler(UserAlreadyExistException e) {
        return new ResponseEntity<>(new ErrorResponse("User already exists"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ErrorResponse> userNotFoundHandler(UserNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VideoNotFoundException.class)
    ResponseEntity<ErrorResponse> videoNotFoundHandler(VideoNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    ResponseEntity<ErrorResponse> commentNotFoundHandler(CommentNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    ResponseEntity<ErrorResponse> subscriptionNotFoundHandler(SubscriptionNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VerificationTokenNotFoundException.class)
    ResponseEntity<ErrorResponse> verificationTokenNotFoundHandler(VerificationTokenNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotValidException.class)
    ResponseEntity<ErrorResponse> usernameNotValidHandler(UsernameNotValidException e) {
        return new ResponseEntity<>(new ErrorResponse("Username not valid"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotValidException.class)
    ResponseEntity<ErrorResponse> passwordNotValidHandler(PasswordNotValidException e) {
        return new ResponseEntity<>(new ErrorResponse("Password not valid"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadySubscribedException.class)
    ResponseEntity<ErrorResponse> alreadyFollowingHandler(AlreadySubscribedException e) {
        return new ResponseEntity<>(new ErrorResponse("User already following"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserBannedException.class)
    ResponseEntity<ErrorResponse> userBannedHandler(UserBannedException e) {
        return new ResponseEntity<>(new ErrorResponse("User has been banned"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorResponse> nullPointerHandler(ValidationException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ErrorResponse> illegalArgumentHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VideoStorageException.class)
    ResponseEntity<ErrorResponse> videoStorageHandler(VideoStorageException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(SubscribeYourselfException.class)
    ResponseEntity<ErrorResponse> subscribeYourselfHandler(SubscribeYourselfException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientUserRightsException.class)
    ResponseEntity<ErrorResponse> insufficientUserRightsHandler(InsufficientUserRightsException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailSendException.class)
    ResponseEntity<ErrorResponse> mailSendHandler(MailSendException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_GATEWAY);
    }
}
