package com.example.olditemtradeplatform.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return errorCode.getMessage();
    }

    public int getStatusCode() {
        return errorCode.getStatus().value();
    }

    public org.springframework.http.HttpStatus getStatus() {
        return errorCode.getStatus();
    }
}
