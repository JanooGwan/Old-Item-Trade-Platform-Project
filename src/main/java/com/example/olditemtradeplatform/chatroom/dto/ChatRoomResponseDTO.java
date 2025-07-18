package com.example.olditemtradeplatform.chatroom.dto;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ChatRoomResponseDTO(

        @Positive
        @NotNull(message = "채팅방 ID는 필수입니다.")
        Long roomId,

        @Size(message = "상대방의 닉네임은 1~10자여야 합니다.")
        @NotNull(message = "상대방의 닉네임은 필수입니다.")
        String otherUserNickname

) {
    public static ChatRoomResponseDTO from(ChatRoom chatRoom, Long currentUserId) {
        String otherNickname = chatRoom.getMember1().getId().equals(currentUserId)
                ? chatRoom.getMember2().getNickname()
                : chatRoom.getMember1().getNickname();

        return new ChatRoomResponseDTO(chatRoom.getId(), otherNickname);
    }
}
