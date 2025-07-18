package com.example.olditemtradeplatform.reportofpost.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReportOfPostErrorCode implements BaseErrorCode {

    POST_NOT_FOUND("해당 게시글이 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_REPORT("이미 이 게시글을 신고했습니다.", HttpStatus.CONFLICT),
    REPORT_NOT_FOUND("신고 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ReportOfPostErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
