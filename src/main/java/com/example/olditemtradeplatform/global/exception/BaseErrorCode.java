package com.example.olditemtradeplatform.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    String name();
    String getMessage();
    HttpStatus getHttpStatus();
}
