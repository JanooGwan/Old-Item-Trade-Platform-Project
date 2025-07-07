package com.example.olditemtradeplatform.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRegisterRequestDTO {

    @NotBlank
    @Size(min = 4, max = 20)
    private String userId;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 20)
    private String nickname;
}
