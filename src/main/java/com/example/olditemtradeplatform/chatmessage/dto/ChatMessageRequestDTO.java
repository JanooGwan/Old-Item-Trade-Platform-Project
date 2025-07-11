package com.example.olditemtradeplatform.chatmessage.dto;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;
import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageRequestDTO {

    @NotBlank
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
