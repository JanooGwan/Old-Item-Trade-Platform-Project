package com.example.olditemtradeplatform.chatmessage.dto;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageResponseDTO {

    private String senderNickname;
    private String content;
    private LocalDateTime sentDate;
    private Long sentAt;
    private boolean isRead;

    public static ChatMessageResponseDTO from(ChatMessage message) {
        return ChatMessageResponseDTO.builder()
                .senderNickname(message.getSender().getNickname())
                .content(message.getContent())
                .sentDate(message.getSentDate())
                .sentAt(message.getSentAt())
                .isRead(message.isRead())
                .build();
    }
}
