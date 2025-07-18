package com.example.olditemtradeplatform.authority.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthorityErrorCode implements BaseErrorCode {

    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "신고 내역이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "존재하지 않는 역할입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
