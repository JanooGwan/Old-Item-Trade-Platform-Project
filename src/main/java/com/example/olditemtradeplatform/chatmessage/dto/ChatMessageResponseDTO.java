package com.example.olditemtradeplatform.chatmessage.dto;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageResponseDTO {

    private Long chatRoomId;
    private Long senderId;
    private String senderNickname;
    private String content;
    private LocalDateTime sentDate;
    private Long sentAt;
    private boolean isRead;

    public static ChatMessageResponseDTO from(ChatMessage message) {
        return ChatMessageResponseDTO.builder()
                .chatRoomId(message.getChatroom().getId())
                .senderId(message.getSender().getId())
                .senderNickname(message.getSender().getNickname())
                .content(message.getContent())
                .sentDate(message.getSentDate())
                .sentAt(message.getSentAt())
                .isRead(message.isRead())
                .build();
    }
}
