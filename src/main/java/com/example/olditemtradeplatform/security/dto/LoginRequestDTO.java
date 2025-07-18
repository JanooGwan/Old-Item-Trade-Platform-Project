package com.example.olditemtradeplatform.security.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDTO {

    @NotNull(message = "이메일은 필수입니다.")
    String userId;

    @NotNull(message = "비밀번호는 필수입니다.")
    String password;
}