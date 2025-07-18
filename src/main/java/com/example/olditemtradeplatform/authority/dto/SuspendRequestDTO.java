package com.example.olditemtradeplatform.authority.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuspendRequestDTO {

    @Positive
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long postId;

    @Positive
    @NotNull(message = "신고자 ID는 필수입니다.")
    private Long reporterId;

    @FutureOrPresent(message = "정지 기한은 오늘 이후여야 합니다.")
    private LocalDate suspendUntil;

    @Size(max = 500, message = "정지 사유는 500자를 넘길 수 없습니다.")
    private String suspendReason;
}