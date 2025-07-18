package com.example.olditemtradeplatform.global.exception;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomExceptionResponse {

    int Status;
    String message;

    public static CustomExceptionResponse from(BaseErrorCode code) {
        return new CustomExceptionResponse(
                code.getStatus().value(),
                code.getMessage()
        );
    }
}
