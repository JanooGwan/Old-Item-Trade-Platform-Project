package com.example.olditemtradeplatform.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    String getCode();
    HttpStatus getHttpStatus();
    String getMessage();
}
