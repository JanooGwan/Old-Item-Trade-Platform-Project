package com.example.olditemtradeplatform.member.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRegisterRequestDTO {

    @Schema(description = "사용자 ID (로그인용 아이디)", example = "kang123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "아이디는 필수로 입력해야 합니다.")
    @Size(min = 5, max = 20, message = "아이디는 5~20자로 입력해주세요.")
    private String userId;

    @Schema(description = "비밀번호", example = "securePass!123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    @Size(min = 1, max = 100, message = "비밀번호는 1~100자로 입력해주세요.")
    private String password;

    @Schema(description = "비밀번호 확인", example = "securePass!123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "비밀번호 확인을 입력해야 합니다.")
    private String passwordConfirm;

    @Schema(description = "이메일", example = "kang123@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    @Size(min = 1, max = 500, message = "이메일은 1~500자로 입력해주세요.")
    @Email
    private String email;

    @Schema(description = "닉네임", example = "강개발", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자로 입력해주세요.")
    private String nickname;

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
