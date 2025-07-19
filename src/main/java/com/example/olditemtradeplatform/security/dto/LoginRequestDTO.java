package com.example.olditemtradeplatform.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDTO {

    @Schema(
            description = "사용자 아이디 (로그인용)",
            example = "user123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "이메일은 필수입니다.")
    private String userId;

    @Schema(
            description = "비밀번호",
            example = "password123!",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;
}
