package com.example.olditemtradeplatform.like.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum LikeErrorCode implements BaseErrorCode {

    POST_NOT_FOUND("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    MEMBER_NOT_FOUND("회원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    LIKE_NOT_FOUND("좋아요 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    LikeErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
