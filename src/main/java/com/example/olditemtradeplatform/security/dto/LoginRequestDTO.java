package com.example.olditemtradeplatform.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(

        @Schema(
                description = "사용자 아이디 (로그인용)",
                example = "user123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "이메일은 필수입니다.")
        String userId,

        @Schema(
                description = "비밀번호",
                example = "password123!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "비밀번호는 필수입니다.")
        String password

) {}
