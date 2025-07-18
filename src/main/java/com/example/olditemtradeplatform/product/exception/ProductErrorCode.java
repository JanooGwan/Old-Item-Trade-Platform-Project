package com.example.olditemtradeplatform.product.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProductErrorCode implements BaseErrorCode {

    POST_NOT_FOUND("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND("상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ProductErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
