package com.example.olditemtradeplatform.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupRequestDTO {

    @NotBlank(message = "회원 ID는 필수입니다.")
    @Size(max = 20, message = "회원 ID는 20자를 넘길 수 없습니다.")
    String userId;

    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    @Size(max = 500, message = "이메일은 최대 500자여야 합니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    String email;

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    @Size(max = 100, message = "비밀번호는 100자를 넘길 수 없습니다.")
    String password;

    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    @Size(max = 10, message = "닉네임은 10자를 넘길 수 없습니다.")
    String nickname;
}
