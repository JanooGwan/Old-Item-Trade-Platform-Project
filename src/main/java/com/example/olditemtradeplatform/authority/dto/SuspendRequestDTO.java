package com.example.olditemtradeplatform.authority.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuspendRequestDTO {

    private Long postId;
    private Long reporterId;
    private LocalDate suspendUntil;
    private String suspendReason;
}