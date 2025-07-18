package com.example.olditemtradeplatform.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }
}
