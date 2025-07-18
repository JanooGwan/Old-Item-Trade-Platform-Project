package com.example.olditemtradeplatform.global.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponse> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(CustomExceptionResponse.from(ex.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleValidation(MethodArgumentNotValidException e) {
        String firstErrorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("입력값 오류");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(firstErrorMessage)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionResponse> handleGeneral(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomExceptionResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("서버 오류가 발생했습니다.")
                        .build());
    }
}
