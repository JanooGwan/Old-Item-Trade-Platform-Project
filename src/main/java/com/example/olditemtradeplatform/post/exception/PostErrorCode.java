package com.example.olditemtradeplatform.post.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PostErrorCode implements BaseErrorCode {

    POST_NOT_FOUND("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_AUTHOR("작성자만 수정/삭제할 수 있습니다.", HttpStatus.FORBIDDEN),
    FILE_SAVE_FAILED("이미지 저장 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    PostErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
