package com.example.olditemtradeplatform.global.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomExceptionResponse {

    String code;
    int status;
    String message;

    public static CustomExceptionResponse from(BaseErrorCode code) {
        return new CustomExceptionResponse(
                code.getCode(),
                code.getHttpStatus().value(),
                code.getMessage()
        );
    }
}
