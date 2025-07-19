package com.example.olditemtradeplatform.authority.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "사용자 정지 요청 DTO")
public record SuspendRequestDTO(

        @Schema(description = "게시글 ID (신고 대상 게시글)", example = "42", requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive
        @NotNull(message = "작성자 ID는 필수입니다.")
        Long postId,

        @Schema(description = "신고자(요청자) ID", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive
        @NotNull(message = "신고자 ID는 필수입니다.")
        Long reporterId,

        @Schema(description = "정지 기한 (yyyy-MM-dd 형식, 오늘 이후)", example = "2025-07-31", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @FutureOrPresent(message = "정지 기한은 오늘 이후여야 합니다.")
        LocalDate suspendUntil,

        @Schema(description = "정지 사유 (최대 500자)", example = "부적절한 게시물 다수 등록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 500, message = "정지 사유는 500자를 넘길 수 없습니다.")
        String suspendReason

) {}
