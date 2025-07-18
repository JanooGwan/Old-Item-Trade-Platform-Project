package com.example.olditemtradeplatform.member.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRegisterRequestDTO {

    @NotBlank(message = "아이디는 필수로 입력해야 합니다.")
    @Size(min = 5, max = 20, message = "아이디는 5~20자로 입력해주세요.")
    String userId;

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    @Size(min = 1, max = 100, message = "비밀번호는 1~100자로 입력해주세요.")
    String password;

    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    @Size(min = 1, max = 500, message = "이메일은 1~500자로 입력해주세요.")
    @Email
    String email;

    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자로 입력해주세요.")
    String nickname;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .userId(this.userId)
                .password(encodedPassword)
                .email(this.email)
                .nickname(this.nickname)
                .role(Role.NORMAL)
                .isSuspended(false)
                .suspendUntil(null)
                .build();
    }
}
