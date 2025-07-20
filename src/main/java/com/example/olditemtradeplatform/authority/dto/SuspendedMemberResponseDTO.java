package com.example.olditemtradeplatform.authority.dto;

import com.example.olditemtradeplatform.member.domain.Member;

import java.time.LocalDate;

public record SuspendedMemberResponseDTO(
        String userId,
        String nickName,
        LocalDate suspendUntil,
        String suspendReason
) {
    public static SuspendedMemberResponseDTO from(Member member) {
        return new SuspendedMemberResponseDTO(
                member.getUserId(),
                member.getNickname(),
                member.getSuspendUntil(),
                member.getSuspendReason()
        );
    }
}
