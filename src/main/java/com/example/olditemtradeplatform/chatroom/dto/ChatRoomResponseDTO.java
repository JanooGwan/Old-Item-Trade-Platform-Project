package com.example.olditemtradeplatform.chatroom.dto;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomResponseDTO {

    private Long roomId;
    private String member1Nickname;
    private String member2Nickname;

    public static ChatRoomResponseDTO from(ChatRoom chatRoom) {
        return ChatRoomResponseDTO.builder()
                .roomId(chatRoom.getId())
                .member1Nickname(chatRoom.getMember1().getNickname())
                .member2Nickname(chatRoom.getMember2().getNickname())
                .build();
    }
}
