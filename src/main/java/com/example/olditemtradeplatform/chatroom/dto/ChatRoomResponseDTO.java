package com.example.olditemtradeplatform.chatroom.dto;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomResponseDTO {

    private Long roomId;
    private String otherUserNickname;

    public static ChatRoomResponseDTO from(ChatRoom chatRoom, Long currentUserId) {
        String otherNickname = chatRoom.getMember1().getId().equals(currentUserId)
                ? chatRoom.getMember2().getNickname()
                : chatRoom.getMember1().getNickname();

        return ChatRoomResponseDTO.builder()
                .roomId(chatRoom.getId())
                .otherUserNickname(otherNickname)
                .build();
    }
}
