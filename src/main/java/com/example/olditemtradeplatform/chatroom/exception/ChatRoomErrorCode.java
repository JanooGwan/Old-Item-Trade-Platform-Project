package com.example.olditemtradeplatform.chatroom.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ChatRoomErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CANNOT_CHAT_WITH_SELF("자기 자신과는 채팅할 수 없습니다.", HttpStatus.BAD_REQUEST),
    SENDER_NOT_FOUND("보내는 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RECEIVER_NOT_FOUND("받는 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ChatRoomErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
