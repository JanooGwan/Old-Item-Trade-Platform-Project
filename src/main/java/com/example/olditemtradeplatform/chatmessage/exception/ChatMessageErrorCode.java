package com.example.olditemtradeplatform.chatmessage.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatMessageErrorCode implements BaseErrorCode {

    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    UNAUTHORIZED_CHAT_ACCESS(HttpStatus.FORBIDDEN, "해당 채팅방에 접근할 수 없습니다."),
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "메시지를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
