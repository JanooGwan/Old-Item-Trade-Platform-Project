package com.example.olditemtradeplatform.member.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND("회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_USER_ID("이미 사용 중인 아이디입니다.", HttpStatus.CONFLICT),
    DUPLICATE_EMAIL("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    DUPLICATE_NICKNAME("이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
    INVALID_CURRENT_PASSWORD("현재 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    PASSWORD_CONFIRM_MISMATCH("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_NOT_FOUND("해당 닉네임의 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ACCESS_DENIED("권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus status;

    MemberErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
