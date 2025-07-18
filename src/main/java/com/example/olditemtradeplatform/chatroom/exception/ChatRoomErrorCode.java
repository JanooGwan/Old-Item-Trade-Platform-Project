package com.example.olditemtradeplatform.chatroom.exception;

import com.example.olditemtradeplatform.global.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatRoomErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    CANNOT_CHAT_WITH_SELF(HttpStatus.BAD_REQUEST, "자기 자신과는 채팅할 수 없습니다."),
    SENDER_NOT_FOUND(HttpStatus.NOT_FOUND, "보내는 사용자를 찾을 수 없습니다."),
    RECEIVER_NOT_FOUND(HttpStatus.NOT_FOUND, "받는 사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
