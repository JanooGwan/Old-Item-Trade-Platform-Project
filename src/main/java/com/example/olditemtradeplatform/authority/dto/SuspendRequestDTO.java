package com.example.olditemtradeplatform.authority.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "회원 정지 요청 DTO")
public record SuspendRequestDTO(

        @Schema(description = "정지 기한 (yyyy-MM-dd 형식, 오늘 이후)", example = "2025-07-31", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "정지 기한은 필수입니다.")
        @FutureOrPresent(message = "정지 기한은 오늘 이후여야 합니다.")
        LocalDate suspendUntil,

        @Schema(description = "정지 사유 (최대 500자)", example = "욕설 및 부적절한 행위", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "정지 사유는 필수입니다.")
        @Size(max = 500, message = "정지 사유는 500자를 넘길 수 없습니다.")
        String suspendReason

) {}
