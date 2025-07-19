package com.example.olditemtradeplatform.chatmessage.dto;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;
import com.example.olditemtradeplatform.chatmessage.domain.ChatMessageId;
import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public record ChatMessageRequestDTO(

        @Schema(description = "채팅방 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive
        @NotNull(message = "채팅방 ID는 필수입니다.")
        Long chatRoomId,

        @Schema(description = "보낸 사람(회원) ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive
        @NotNull(message = "보낸 사람 ID는 필수입니다.")
        Long senderId,

        @Schema(description = "채팅 메시지 내용", example = "안녕하세요, 상품 아직 구매 가능할까요?", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "메시지 내용은 필수입니다.")
        @Size(min = 1, max = 1000, message = "메시지는 1000자 이내로 입력해주세요.")
        String content

) {
    public ChatMessage toEntity(Member sender, ChatRoom chatRoom, Long sentAt) {
        return ChatMessage.builder()
                .id(new ChatMessageId(chatRoom.getId(), sender.getId()))
                .sender(sender)
                .chatroom(chatRoom)
                .content(content)
                .isRead(false)
                .build();
    }
}
