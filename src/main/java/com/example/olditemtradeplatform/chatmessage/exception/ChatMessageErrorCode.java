package com.example.olditemtradeplatform.chatmessage.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ChatMessageErrorCode {

    CHATROOM_NOT_FOUND("채팅방이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    MEMBER_NOT_FOUND("회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_CHAT_ACCESS("해당 채팅방에 접근할 수 없습니다.", HttpStatus.FORBIDDEN),
    MESSAGE_NOT_FOUND("메시지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ChatMessageErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
