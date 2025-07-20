package com.example.olditemtradeplatform.global.exception;

import com.example.olditemtradeplatform.security.exception.AuthErrorCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponse> handleCustomException(CustomException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        CustomExceptionResponse response = CustomExceptionResponse.from(errorCode);
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<String> messages = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        CustomExceptionResponse response = new CustomExceptionResponse(
                "VALIDATION_ERROR",
                HttpStatus.BAD_REQUEST.value(),
                String.join(", ", messages)
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleUsernameNotFound(UsernameNotFoundException e) {
        CustomExceptionResponse response = new CustomExceptionResponse(
                AuthErrorCode.MEMBER_NOT_FOUND.name(),
                AuthErrorCode.MEMBER_NOT_FOUND.getHttpStatus().value(),
                AuthErrorCode.MEMBER_NOT_FOUND.getMessage()
        );
        return new ResponseEntity<>(response, AuthErrorCode.MEMBER_NOT_FOUND.getHttpStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomExceptionResponse> handleBadCredentials(BadCredentialsException e) {
        CustomExceptionResponse response = new CustomExceptionResponse(
                AuthErrorCode.BAD_CREDENTIALS.name(),
                AuthErrorCode.BAD_CREDENTIALS.getHttpStatus().value(),
                AuthErrorCode.BAD_CREDENTIALS.getMessage()
        );
        return new ResponseEntity<>(response, AuthErrorCode.BAD_CREDENTIALS.getHttpStatus());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<CustomExceptionResponse> handleDisabled(DisabledException e) {
        CustomExceptionResponse response = new CustomExceptionResponse(
                AuthErrorCode.DISABLED_ACCOUNT.name(),
                AuthErrorCode.DISABLED_ACCOUNT.getHttpStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(response, AuthErrorCode.DISABLED_ACCOUNT.getHttpStatus());
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<CustomExceptionResponse> handleInternalAuthentication(InternalAuthenticationServiceException e) {
        Throwable cause = e.getCause();
        if (cause instanceof DisabledException) {
            return handleDisabled((DisabledException) cause);
        }
        return new ResponseEntity<>(new CustomExceptionResponse(
                AuthErrorCode.AUTHENTICATION_FAILED.name(),
                AuthErrorCode.AUTHENTICATION_FAILED.getHttpStatus().value(),
                AuthErrorCode.AUTHENTICATION_FAILED.getMessage()
        ), AuthErrorCode.AUTHENTICATION_FAILED.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionResponse> handleUnexpectedException(Exception e) {
        CustomExceptionResponse response = new CustomExceptionResponse(
                "INTERNAL_SERVER_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버 오류가 발생했습니다."
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
