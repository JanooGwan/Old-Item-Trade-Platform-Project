package com.example.olditemtradeplatform.reportofpost.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReportOfPostErrorCode implements BaseErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글이 없습니다."),
    DUPLICATE_REPORT(HttpStatus.CONFLICT, "이미 이 게시글을 신고했습니다."),
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "신고 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
