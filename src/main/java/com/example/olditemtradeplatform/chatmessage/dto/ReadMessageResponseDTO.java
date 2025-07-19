package com.example.olditemtradeplatform.chatmessage.dto;

public record ReadMessageResponseDTO(
        Long chatRoomId,
        Long readerId
) {
    public static ReadMessageResponseDTO of(Long chatRoomId, Long readerId) {
        return new ReadMessageResponseDTO(chatRoomId, readerId);
    }
}
