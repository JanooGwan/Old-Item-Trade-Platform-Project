package com.example.olditemtradeplatform.authority.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthorityErrorCode implements BaseErrorCode {

    REPORT_NOT_FOUND("신고 내역이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    MEMBER_NOT_FOUND("해당 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    INVALID_ROLE("존재하지 않는 역할입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    AuthorityErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
