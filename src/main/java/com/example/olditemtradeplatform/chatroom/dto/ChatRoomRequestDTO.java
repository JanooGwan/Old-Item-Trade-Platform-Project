package com.example.olditemtradeplatform.chatroom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChatRoomRequestDTO(

        @Schema(description = "채팅할 상대 회원 ID", example = "42")
        @NotNull(message = "memberId는 필수로 입력해야 합니다.")
        @Positive(message = "memberId는 양수여야 합니다.")
        Long memberId,

        @Schema(description = "채팅할 상대 닉네임", example = "gwanwoo123")
        @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
        String nickname

) {}
