package com.example.olditemtradeplatform.chatmessage.dto;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageResponseDTO(
        Long chatRoomId,
        Long senderId,
        String senderNickname,
        String content,
        LocalDateTime sentDate,
        Long sentAt,
        boolean isRead
) {
    public static ChatMessageResponseDTO from(ChatMessage message) {
        return new ChatMessageResponseDTO(
                message.getChatroom().getId(),
                message.getSender().getId(),
                message.getSender().getNickname(),
                message.getContent(),
                message.getSentDate(),
                message.getSentAt(),
                message.isRead()
        );
    }
}
