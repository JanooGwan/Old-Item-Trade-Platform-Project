package com.example.olditemtradeplatform.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus getStatus();
    String getMessage();
}
