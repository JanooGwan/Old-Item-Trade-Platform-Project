package com.example.olditemtradeplatform.postimage.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PostImageErrorCode implements BaseErrorCode {

    POST_NOT_FOUND("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    POST_IMAGE_NOT_FOUND("이미지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FILE_SAVE_FAILED("파일 저장에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    PostImageErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
