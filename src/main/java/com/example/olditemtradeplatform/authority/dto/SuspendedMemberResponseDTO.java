package com.example.olditemtradeplatform.authority.dto;

import com.example.olditemtradeplatform.member.domain.Member;

import java.time.LocalDate;

public record SuspendedMemberResponseDTO(
        Long userId,
        String nickName,
        LocalDate suspendUntil,
        String suspendReason
) {
    public static SuspendedMemberResponseDTO from(Member member) {
        return new SuspendedMemberResponseDTO(
                member.getId(),
                member.getNickname(),
                member.getSuspendUntil(),
                member.getSuspendReason()
        );
    }
}
