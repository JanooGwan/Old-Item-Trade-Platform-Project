package com.example.olditemtradeplatform.chatroom.dto;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ChatRoomResponseDTO(Long roomId, String otherUserNickname) {
    public static ChatRoomResponseDTO from(ChatRoom chatRoom, Long currentUserId) {
        String otherNickname = chatRoom.getMember1().getId().equals(currentUserId)
                ? chatRoom.getMember2().getNickname()
                : chatRoom.getMember1().getNickname();

        return new ChatRoomResponseDTO(chatRoom.getId(), otherNickname);
    }
}
