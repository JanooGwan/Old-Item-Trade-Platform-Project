package com.example.olditemtradeplatform.chatmessage.dto;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;
import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageRequestDTO {

    @NotBlank(message = "메시지 내용은 필수입니다.")
    @Size(min = 1, max = 1000, message = "메시지는 1000자 이내로 입력해주세요.")
    private String content;

    public ChatMessage toEntity(Member sender, ChatRoom chatRoom, Long sentAt) {
        return ChatMessage.builder()
                .sender(sender)
                .chatroom(chatRoom)
                .content(content)
                .isRead(false)
                .sentAt(sentAt)
                .build();
    }
}
