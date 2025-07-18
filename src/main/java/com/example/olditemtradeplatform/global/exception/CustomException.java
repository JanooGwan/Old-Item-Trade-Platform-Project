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

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }

    public int getHttpStatus() {
        return errorCode.getHttpStatus().value();
    }

    public HttpStatus getStatus() {
        return errorCode.getHttpStatus();
    }
}
