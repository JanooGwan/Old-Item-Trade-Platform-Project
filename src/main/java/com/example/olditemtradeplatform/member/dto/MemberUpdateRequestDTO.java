package com.example.olditemtradeplatform.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateRequestDTO {

    @Schema(description = "현재 비밀번호", example = "currentPass123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "현재 비밀번호는 필수로 입력해야 합니다.")
    private String currentPassword;

    @Schema(description = "새 비밀번호", example = "newPass456", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "새 비밀번호는 필수로 입력해야 합니다.")
    @Size(min = 1, max = 100, message = "비밀번호는 1~100자로 입력해주세요.")
    private String newPassword;

    @Schema(description = "새 비밀번호 확인", example = "newPass456", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "비밀번호 확인은 필수로 입력해야 합니다.")
    private String confirmPassword;

    @Schema(description = "이메일", example = "updated@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    @Size(min = 1, max = 500, message = "이메일은 1~500자로 입력해주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Schema(description = "닉네임", example = "관우개발자", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자로 입력해주세요.")
    private String nickname;
}
