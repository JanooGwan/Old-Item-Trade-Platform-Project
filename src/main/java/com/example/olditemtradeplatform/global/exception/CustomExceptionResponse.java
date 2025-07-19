package com.example.olditemtradeplatform.global.exception;


public record CustomExceptionResponse(
        String code,
        int status,
        String message
) {
    public static CustomExceptionResponse from(BaseErrorCode code) {
        return new CustomExceptionResponse(
                code.name(),
                code.getHttpStatus().value(),
                code.getMessage()
        );
    }
}
