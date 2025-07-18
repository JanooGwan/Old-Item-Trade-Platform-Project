package com.example.olditemtradeplatform.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDTO {

    @Size(max = 500, message = "이메일은 500자를 넘을 수 없습니다.")
    @NotNull(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    String email;

    @Size(max = 100, message = "비밀번호는 100자를 넘을 수 없습니다.")
    @NotNull(message = "비밀번호는 필수입니다.")
    String password;
}