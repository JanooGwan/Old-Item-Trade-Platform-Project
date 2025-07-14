package com.example.olditemtradeplatform.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateRequestDTO {

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    @Size(min = 1, max = 100, message = "비밀번호는 1~100자로 입력해주세요.")
    private String password;

    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    @Size(min = 1, max = 500, message = "이메일은 1~500자로 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자로 입력해주세요.")
    private String nickname;
}
