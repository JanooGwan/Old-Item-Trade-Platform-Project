package com.example.olditemtradeplatform.authority.dto;

import com.example.olditemtradeplatform.member.domain.Member;

import java.time.LocalDate;

public record SuspendStatusResponseDTO(
        String userId,
        String nickName,
        LocalDate suspendUntil,
        String suspendReason
) {
    public static SuspendStatusResponseDTO from(Member member) {
        return new SuspendStatusResponseDTO(
                member.getUserId(),
                member.getNickname(),
                member.getSuspendUntil(),
                member.getSuspendReason()
        );
    }
}
